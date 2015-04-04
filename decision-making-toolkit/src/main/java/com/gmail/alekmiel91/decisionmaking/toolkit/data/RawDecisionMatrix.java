package com.gmail.alekmiel91.decisionmaking.toolkit.data;

import com.gmail.alekmiel91.decisionmaking.toolkit.Context;
import com.gmail.alekmiel91.decisionmaking.toolkit.data.validation.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

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

    @Valid
    @NotEmpty(message = "{error.raw.decision.matrix.alternatives.not.empty}")
    private List<Alternative> alternatives;

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
        alternatives.forEach(Defaultable::applyDefault);

        List<String> logs = new LinkedList<>();

        if ((factors == null || factors.isEmpty()) && scenes != null && !scenes.isEmpty()) {
            Factor factor = new Factor(Context.INSTANCE.getResources().getString("decision.matrix.table.factor"),
                    Collections.singletonList("A"),
                    "A",
                    Collections.nCopies(scenes.size(), 1.0));

            String log = MessageFormat.format(Context.INSTANCE.getResources().getString("log.default.raw.decision.matrix.factor"),
                    Context.INSTANCE.getResources().getString("decision.matrix.table.factor"), "A", "A", "1.0");

            logs.add(log);
            factors = Collections.singletonList(factor);
        }

        if (scenes != null && !scenes.isEmpty()) {
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
                            return MessageFormat.format(Context.INSTANCE.getResources().getString("log.default.scene.probability"), scene.getName(), newProbability);
                        })
                        .forEach(logs::add);
            }
        }
        
        return logs;
    }
}
