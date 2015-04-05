package com.gmail.alekmiel91.decisionmaking.toolkit.maker;

import com.gmail.alekmiel91.decisionmaking.toolkit.data.DecisionMatrix;
import com.gmail.alekmiel91.decisionmaking.toolkit.data.Factor;
import com.gmail.alekmiel91.decisionmaking.toolkit.data.Scene;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.jooq.lambda.Seq;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Aleksander Mielczarek
 *         Created on 2015-04-02.
 */
public class BayesianDecisionMaker implements DecisionMaker {
    @Override
    public Set<String> makeDecision(DecisionMatrix decisionMatrix) {
        List<String> chosenFactors = decisionMatrix.getRawDecisionMatrix().getFactors().stream()
                .map(Factor::getChosenFactor)
                .collect(Collectors.toList());

        List<Double> probabilities = decisionMatrix.getRawDecisionMatrix().getScenes().stream()
                .map(Scene::getProbability)
                .collect(Collectors.toList());

        List<Collection<Double>> chosenFactorsOutputsValues = Seq.zip(decisionMatrix.getFactorsTables().stream(), chosenFactors.stream())
                .map(tuple -> tuple.v1().row(tuple.v2()))
                .map(Map::values)
                .collect(Collectors.toList());

        List<List<Double>> probabilitiesFactorsOutputsValues = chosenFactorsOutputsValues.stream()
                .map(factorsOutputsValue -> Seq.zip(probabilities.stream(), factorsOutputsValue.stream())
                        .map(tuple -> tuple.v1() * tuple.v2())
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());

        List<Double> probabilitiesFactorsOutputsValuesSum = probabilitiesFactorsOutputsValues.stream()
                .map(probabilitiesFactorsOutputsValue -> probabilitiesFactorsOutputsValue.stream()
                        .mapToDouble(Double::doubleValue)
                        .sum())
                .collect(Collectors.toList());

        List<List<Double>> probabilitiesFactorsOutputsValuesDividedBySum = probabilitiesFactorsOutputsValues.stream()
                .map(probabilitiesFactorsOutputsValue -> Seq.zip(probabilitiesFactorsOutputsValue.stream(), probabilitiesFactorsOutputsValuesSum.stream())
                        .map(tuple -> tuple.v1() / tuple.v2())
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());

        Multimap<String, Double> factorAlternativesValues = ArrayListMultimap.create();
        probabilitiesFactorsOutputsValuesDividedBySum.forEach(probabilitiesFactorsOutputValuesDividedBySum ->
                decisionMatrix.getDecisionTable().rowKeySet().stream().forEach(alternative -> {
                    double bayesian = Seq.zip(decisionMatrix.getDecisionTable().row(alternative).values().stream(), probabilitiesFactorsOutputValuesDividedBySum.stream())
                            .mapToDouble(tuple -> tuple.v1().getValue() * tuple.v2())
                            .sum();

                    factorAlternativesValues.put(alternative.getName(), bayesian);
                }));

        double max = factorAlternativesValues.values().stream()
                .mapToDouble(Double::valueOf)
                .max().getAsDouble();

        return factorAlternativesValues.entries().stream()
                .filter(entry -> entry.getValue() == max)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }
}
