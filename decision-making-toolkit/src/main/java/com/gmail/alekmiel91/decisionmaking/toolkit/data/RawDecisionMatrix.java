package com.gmail.alekmiel91.decisionmaking.toolkit.data;

import com.gmail.alekmiel91.decisionmaking.toolkit.Context;
import com.gmail.alekmiel91.decisionmaking.toolkit.data.validation.*;
import com.google.common.base.Strings;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import java.text.MessageFormat;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author Aleksander Mielczarek
 *         Created on 2015-04-02.
 */
@Getter
@Setter
@NumberOfOutputs
@NumberOfFactorsValues
@UniqueAlternatives
@UniqueScenes
@UniqueFactorsName
@TotalSceneProbability
public class RawDecisionMatrix implements Defaultable {

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    @Valid
    @NotEmpty(message = "{error.raw.decision.matrix.alternatives.not.empty}")
    private List<Alternative> alternatives;

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    @Valid
    @NotEmpty(message = "{error.raw.decision.matrix.scenes.not.empty}")
    private List<Scene> scenes;

    @Valid
    @NotEmpty(message = "{error.raw.decision.matrix.outputs.not.empty}")
    private List<Output> outputs;

    @Valid
    @NotEmpty(message = "{error.raw.decision.matrix.factors.not.empty}")
    private List<Factor> factors;


    @Override
    public List<String> applyDefaultAndLog() {
        List<String> logs = new LinkedList<>();
        Alphabet alphabet = new Alphabet();

        //alternative
        if (alternatives != null) {
            applyDefaultNames(alternatives,
                    alternative -> Strings.isNullOrEmpty(alternative.getName()),
                    Alternative::getName,
                    alphabet,
                    Alternative::setName,
                    "log.default.alternative.name",
                    logs);

            alternatives.stream().map(Defaultable::applyDefaultAndLog).forEach(logs::addAll);
        }

        //scene
        if (scenes != null) {
            applyDefaultNames(scenes,
                    scene -> Strings.isNullOrEmpty(scene.getName()),
                    Scene::getName,
                    alphabet,
                    Scene::setName,
                    "log.default.scene.name",
                    logs);

            applyDefaultProbabilities(scenes, "log.default.scene.probability", logs);
        }

        //output
        if (outputs != null) {
            applyDefaultNames(outputs,
                    output -> Strings.isNullOrEmpty(output.getName()),
                    Output::getName,
                    alphabet,
                    Output::setName,
                    "log.default.output.name",
                    logs);
        }

        //factor
        if (factors == null) {
            Factor factor = new Factor(Context.INSTANCE.getResources().getString("decision.matrix.table.factor"),
                    Collections.singletonList("A"),
                    "A",
                    Collections.nCopies(scenes.size(), 1.0));

            String log = MessageFormat.format(Context.INSTANCE.getResources().getString("log.default.raw.decision.matrix.factor"),
                    Factor.DEFAULT_FACTOR_NAME_TEMPLATE, "A", "A", "1.0");

            logs.add(log);
            factors = Collections.singletonList(factor);
        } else {
            applyDefaultFactorName(factors, "log.default.raw.decision.matrix.factor.factor.name", logs);
            applyDefaultFactorsNames(factors, alphabet, logs);

            factors.stream().map(Defaultable::applyDefaultAndLog).forEach(logs::addAll);
        }
        return logs;
    }

    private static <T> void applyDefaultNames(Collection<T> objects,
                                              Predicate<T> predicate,
                                              Function<T, String> stringFunction,
                                              Alphabet alphabet,
                                              BiConsumer<T, String> stringConsumer,
                                              String message,
                                              List<String> logs) {

        long count = objects.stream()
                .filter(predicate)
                .map(object -> {
                    List<String> names = objects.stream()
                            .map(stringFunction)
                            .collect(Collectors.toList());

                    String name = alphabet.next();
                    while (names.contains(name)) {
                        name = alphabet.next();
                    }

                    stringConsumer.accept(object, name);

                    return MessageFormat.format(Context.INSTANCE.getResources().getString(message), name);
                })
                .map(logs::add)
                .count();

        if (count > 0) {
            alphabet.reset();
        }
    }

    private static void applyDefaultProbabilities(List<Scene> scenes, String message, List<String> logs) {
        long numberOfNullProbabilities = scenes.stream()
                .filter(scene -> scene.getProbability() == null)
                .count();

        if (numberOfNullProbabilities > 0) {
            double sumOfNotNullProbabilities = scenes.stream()
                    .filter(scene -> scene.getProbability() != null)
                    .mapToDouble(Scene::getProbability)
                    .sum();

            final double newProbability = (1.0 - sumOfNotNullProbabilities) / numberOfNullProbabilities;

            scenes.stream()
                    .filter(scene -> scene.getProbability() == null)
                    .map(scene -> {
                        scene.setProbability(newProbability);
                        return MessageFormat.format(Context.INSTANCE.getResources().getString(message), scene.getName(), newProbability);
                    })
                    .forEach(logs::add);
        }
    }

    private static void applyDefaultFactorName(List<Factor> factors, String message, List<String> logs) {
        LongCounter longCounter = new LongCounter();

        for (Factor factor : factors) {
            if (Strings.isNullOrEmpty(factor.getFactorName())) {
                List<String> names = factors.stream()
                        .map(Factor::getFactorName)
                        .collect(Collectors.toList());

                long counter = longCounter.next();
                String name = counter == 1 ? Factor.DEFAULT_FACTOR_NAME_TEMPLATE : Factor.DEFAULT_FACTOR_NAME_TEMPLATE + counter;
                while (names.contains(name)) {
                    counter = longCounter.next();
                    name = counter == 1 ? Factor.DEFAULT_FACTOR_NAME_TEMPLATE : Factor.DEFAULT_FACTOR_NAME_TEMPLATE + counter;
                }

                factor.setFactorName(name);
                String log = MessageFormat.format(Context.INSTANCE.getResources().getString(message), name);
                logs.add(log);
            }
        }
    }

    private static void applyDefaultFactorsNames(List<Factor> factors, Alphabet alphabet, List<String> logs) {
        for (Factor factor : factors) {
            ListIterator<String> factorsNamesIterator = factor.getFactorsNames().listIterator();
            while (factorsNamesIterator.hasNext()) {
                if (Strings.isNullOrEmpty(factorsNamesIterator.next())) {
                    List<String> names = factor.getFactorsNames();
                    String newName = alphabet.next();
                    while (names.contains(newName)) {
                        newName = alphabet.next();
                    }
                    factorsNamesIterator.set(newName);
                    String log = MessageFormat.format(Context.INSTANCE.getResources().getString("log.default.raw.decision.matrix.factor.factors.names"), factor.getFactorName(), newName);
                    logs.add(log);
                }
            }
        }
    }
}
