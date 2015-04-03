package com.gmail.alekmiel91.decisionmaking.toolkit.data;

import com.gmail.alekmiel91.decisionmaking.toolkit.Context;
import com.google.common.base.Preconditions;
import lombok.Getter;

import javax.validation.ConstraintViolation;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Aleksander Mielczarek
 *         Created on 2015-04-02.
 */
public class DecisionMatrixBuilder {

    @Getter
    private final RawDecisionMatrix rawDecisionMatrix = new RawDecisionMatrix();

    private List<Factor> factors = new ArrayList<>();

    DecisionMatrixBuilder(List<Alternative> alternatives, List<Scene> scenes, List<Output> outputs) {
        rawDecisionMatrix.setAlternatives(alternatives);
        rawDecisionMatrix.setScenes(scenes);
        rawDecisionMatrix.setOutputs(outputs);
    }

    public DecisionMatrixBuilder withFactor(Factor factor) {
        factors.add(factor);
        return this;
    }

    public DecisionMatrixBuilder withFactors(List<Factor> factors) {
        this.factors.addAll(factors);
        return this;
    }

    public DecisionMatrix build() {
        if ((factors == null || factors.isEmpty()) && rawDecisionMatrix.getScenes() != null && !rawDecisionMatrix.getScenes().isEmpty()) {
            Factor factor = new Factor(Context.INSTANCE.getResources().getString("decision.matrix.table.factor"),
                    Arrays.asList("A"),
                    "A",
                    Collections.nCopies(rawDecisionMatrix.getScenes().size(), 1.0));

            factors.add(factor);
        }
        rawDecisionMatrix.setFactors(factors);

        long numberOfNullProbabilities = rawDecisionMatrix.getScenes().stream()
                .filter(scene -> scene.getProbability() == null)
                .count();

        if (numberOfNullProbabilities > 0) {
            double sumOfNotNullProbabilities = rawDecisionMatrix.getScenes().stream()
                    .filter(scene -> scene.getProbability() != null)
                    .mapToDouble(Scene::getProbability)
                    .sum();

            final double newProbability = (1.0 - sumOfNotNullProbabilities) / numberOfNullProbabilities;

            rawDecisionMatrix.getScenes().stream()
                    .filter(scene -> scene.getProbability() == null)
                    .forEach(scene -> scene.setProbability(newProbability));
        }

        Set<ConstraintViolation<RawDecisionMatrix>> constraintViolations = Context.INSTANCE.getValidator().validate(rawDecisionMatrix);

        Preconditions.checkArgument(constraintViolations.isEmpty(), constraintViolations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(System.lineSeparator())));

        return new DecisionMatrix(this);
    }
}
