package com.gmail.alekmiel91.decisionmaking.toolkit.data;

import com.gmail.alekmiel91.decisionmaking.toolkit.Context;
import com.gmail.alekmiel91.decisionmaking.toolkit.DecisionMakingException;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static final Logger LOGGER = LoggerFactory.getLogger(DecisionMatrixBuilder.class);

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

    public DecisionMatrix build() throws DecisionMakingException {
        rawDecisionMatrix.applyDefault();

        Set<ConstraintViolation<RawDecisionMatrix>> constraintViolations = Context.INSTANCE.getValidator().validate(rawDecisionMatrix);

        if (!constraintViolations.isEmpty()) {
            constraintViolations.stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(LOGGER::error);

            throw new DecisionMakingException(constraintViolations.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining(System.lineSeparator(),
                            Context.INSTANCE.getResources().getString("exception.matrix.builder.build") + StringUtils.EMPTY, StringUtils.EMPTY)));
        }

        return new DecisionMatrix(this);
    }
}
