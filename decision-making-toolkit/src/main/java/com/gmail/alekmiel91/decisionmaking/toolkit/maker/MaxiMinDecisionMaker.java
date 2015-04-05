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
public class MaxiMinDecisionMaker implements DecisionMaker {
    @Override
    public Set<String> makeDecision(DecisionMatrix decisionMatrix) {
        final Map<String, Double> alternativesValues = new HashMap<>(decisionMatrix.getDecisionTable().rowKeySet().size());

        decisionMatrix.getDecisionTable().rowKeySet().stream().forEach(alternative -> {
            double min = decisionMatrix.getDecisionTable().row(alternative).values().stream()
                    .map(Output::getValue)
                    .mapToDouble(Double::valueOf)
                    .min().getAsDouble();

            alternativesValues.put(alternative.getName(), min);
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
