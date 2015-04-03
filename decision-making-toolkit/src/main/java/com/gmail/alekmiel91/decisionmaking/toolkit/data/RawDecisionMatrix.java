package com.gmail.alekmiel91.decisionmaking.toolkit.data;

import com.gmail.alekmiel91.decisionmaking.toolkit.data.validation.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
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
public class RawDecisionMatrix {

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

}
