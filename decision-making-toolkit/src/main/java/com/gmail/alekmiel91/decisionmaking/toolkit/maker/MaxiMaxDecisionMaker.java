package com.gmail.alekmiel91.decisionmaking.toolkit.maker;

import com.gmail.alekmiel91.decisionmaking.toolkit.data.DecisionMatrix;
import com.gmail.alekmiel91.decisionmaking.toolkit.data.Output;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Aleksander Mielczarek
 *         Created on 2015-04-01.
 */
public class MaxiMaxDecisionMaker implements DecisionMaker {
    @Override
    public Set<String> makeDecision(DecisionMatrix decisionMatrix) {
        final Map<String, Double> alternativesValues = new HashMap<>(decisionMatrix.getDecisionTable().rowKeySet().size());

        decisionMatrix.getDecisionTable().rowKeySet().stream().forEach(alternative -> {
            double max = decisionMatrix.getDecisionTable().row(alternative).values().stream()
                    .map(Output::getValue)
                    .mapToDouble(Double::valueOf)
                    .max().getAsDouble();

            alternativesValues.put(alternative.getName(), max);
        });

        double max = alternativesValues.values().stream()
                .mapToDouble(Double::valueOf)
                .max().getAsDouble();

        return alternativesValues.entrySet().stream()
                .filter(entry -> entry.getValue() == max)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }
}
