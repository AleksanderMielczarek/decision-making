package com.gmail.alekmiel91.decisionmaking.toolkit.maker;

import com.gmail.alekmiel91.decisionmaking.toolkit.data.DecisionMatrix;
import org.jooq.lambda.Seq;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Aleksander Mielczarek
 *         Created on 2015-04-02.
 */
public class MinimumExpectedRegretDecisionMaker implements DecisionMaker {
    @Override
    public String makeDecision(DecisionMatrix decisionMatrix) {
        final Map<String, Double> alternativesValues = new HashMap<>(decisionMatrix.getDecisionTable().rowKeySet().size());

        decisionMatrix.getDecisionTable().rowKeySet().stream().forEach(alternative -> {
            double weightedProbability = Seq.zip(decisionMatrix.getDecisionTable().columnKeySet().stream(), decisionMatrix.getDecisionTable().row(alternative).values().stream())
                    .mapToDouble(tuple -> tuple.v1().getProbability() * tuple.v2().getValue())
                    .sum();

            alternativesValues.put(alternative.getName(), weightedProbability);
        });

        return alternativesValues.entrySet().stream().min((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1).get().getKey();
    }
}
