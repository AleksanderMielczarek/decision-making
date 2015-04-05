package com.gmail.alekmiel91.decisionmaking.toolkit.maker;

import com.gmail.alekmiel91.decisionmaking.toolkit.data.DecisionMatrix;
import com.gmail.alekmiel91.decisionmaking.toolkit.data.Output;
import com.rits.cloning.Cloner;
import org.jooq.lambda.Seq;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Aleksander Mielczarek
 *         Created on 2015-04-01.
 */
public class SavageDecisionMaker implements DecisionMaker {
    @Override
    public Set<String> makeDecision(DecisionMatrix decisionMatrix) {

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

        Cloner cloner = new Cloner();
        DecisionMatrix regretDecisionMatrix = cloner.deepClone(decisionMatrix);

        Seq.zip(regretOutputs.stream(), regretDecisionMatrix.getDecisionTable().values().stream())
                .forEach(tuple -> tuple.v2().setValue(tuple.v1().getValue()));

        final Map<String, Double> alternativesValues = new HashMap<>(decisionMatrix.getDecisionTable().rowKeySet().size());

        regretDecisionMatrix.getDecisionTable().rowKeySet().stream().forEach(alternative -> {
            double max = decisionMatrix.getDecisionTable().row(alternative).values().stream()
                    .map(Output::getValue)
                    .mapToDouble(Double::valueOf)
                    .max().getAsDouble();

            alternativesValues.put(alternative.getName(), max);
        });

        double min = alternativesValues.values().stream()
                .mapToDouble(Double::valueOf)
                .min().getAsDouble();

        return alternativesValues.entrySet().stream()
                .filter(entry -> entry.getValue() == min)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }
}
