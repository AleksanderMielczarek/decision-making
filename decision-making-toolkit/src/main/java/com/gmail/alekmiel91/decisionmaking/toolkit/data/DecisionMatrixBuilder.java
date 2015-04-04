package com.gmail.alekmiel91.decisionmaking.toolkit.data;

import com.gmail.alekmiel91.decisionmaking.toolkit.Context;
import com.google.common.base.Preconditions;
import lombok.Getter;

import javax.validation.ConstraintViolation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Aleksander Mielczarek
 *         Created on 2015-04-02.
 */
public class DecisionMatrixBuilder {

    @Getter
    private final RawDecisionMatrix rawDecisionMatrix = new RawDecisionMatrix();

    DecisionMatrixBuilder(List<Alternative> alternatives, List<Scene> scenes, List<Output> outputs) {
        rawDecisionMatrix.setAlternatives(alternatives);
        rawDecisionMatrix.setScenes(scenes);
        rawDecisionMatrix.setOutputs(outputs);
    }

    public DecisionMatrixBuilder withFactor(Factor factor) {
        if (rawDecisionMatrix.getFactors() == null) {
            rawDecisionMatrix.setFactors(new ArrayList<>());
        }
        rawDecisionMatrix.getFactors().add(factor);
        return this;
    }

    public DecisionMatrixBuilder withFactors(List<Factor> factors) {
        if (rawDecisionMatrix.getFactors() == null) {
            rawDecisionMatrix.setFactors(new ArrayList<>(factors.size()));
        }
        rawDecisionMatrix.getFactors().addAll(factors);
        return this;
    }

    public DecisionMatrix build() {
        rawDecisionMatrix.applyDefault();

        Set<ConstraintViolation<RawDecisionMatrix>> constraintViolations = Context.INSTANCE.getValidator().validate(rawDecisionMatrix);

        Preconditions.checkArgument(constraintViolations.isEmpty(), constraintViolations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(System.lineSeparator())));

        return new DecisionMatrix(this);
    }
}
