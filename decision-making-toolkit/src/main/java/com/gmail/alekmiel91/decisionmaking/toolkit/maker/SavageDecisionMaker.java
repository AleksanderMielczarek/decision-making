package com.gmail.alekmiel91.decisionmaking.toolkit.maker;

import com.gmail.alekmiel91.decisionmaking.toolkit.data.DecisionMatrix;
import com.gmail.alekmiel91.decisionmaking.toolkit.data.Output;
import org.jooq.lambda.Seq;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Aleksander Mielczarek
 *         Created on 2015-04-01.
 */
public class SavageDecisionMaker implements DecisionMaker {
    @Override
    public String makeDecision(DecisionMatrix decisionMatrix) {

        List<Double> rowMaxes = decisionMatrix.getDecisionTable().rowKeySet().stream()
                .map(alternative -> decisionMatrix.getDecisionTable().row(alternative).values().stream()
                        .map(Output::getValue)
                        .mapToDouble(Double::valueOf)
                        .max().getAsDouble())
                .collect(Collectors.toList());

        List<Double> regretValues = decisionMatrix.getDecisionTable().rowKeySet().stream()
                .flatMap(alternative -> Seq.zip(rowMaxes.stream(), decisionMatrix.getDecisionTable().row(alternative).values().stream())
                        .map(tuple -> tuple.v1() - tuple.v2().getValue()))
                .collect(Collectors.toList());

        List<Output> regretOutputs = Seq.zip(decisionMatrix.getRawDecisionMatrix().getOutputs().stream(), regretValues.stream())
                .map(tuple -> new Output(tuple.v1().getName(), tuple.v2()))
                .collect(Collectors.toList());

        DecisionMatrix regretDecisionMatrix = DecisionMatrix
                .builder(decisionMatrix.getRawDecisionMatrix().getAlternatives(), decisionMatrix.getRawDecisionMatrix().getScenes(), regretOutputs)
                .withFactors(decisionMatrix.getRawDecisionMatrix().getFactors())
                .build();

        final Map<String, Double> alternativesValues = new HashMap<>(decisionMatrix.getDecisionTable().rowKeySet().size());

        regretDecisionMatrix.getDecisionTable().rowKeySet().stream().forEach(alternative -> {
            double max = decisionMatrix.getDecisionTable().row(alternative).values().stream()
                    .map(Output::getValue)
                    .mapToDouble(Double::valueOf)
                    .max().getAsDouble();

            alternativesValues.put(alternative.getName(), max);
        });

        return alternativesValues.entrySet().stream().min((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1).get().getKey();
    }
}
