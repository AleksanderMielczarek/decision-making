package com.gmail.alekmiel91.decisionmaking.toolkit.data;

import com.gmail.alekmiel91.decisionmaking.toolkit.Context;
import com.gmail.alekmiel91.decisionmaking.toolkit.data.validation.ChosenFactorExist;
import com.gmail.alekmiel91.decisionmaking.toolkit.data.validation.DoubleRange;
import com.gmail.alekmiel91.decisionmaking.toolkit.data.validation.UniqueFactorsNames;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;

/**
 * @author Aleksander Mielczarek
 *         Created on 2015-04-02.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@UniqueFactorsNames
@ChosenFactorExist
public class Factor implements Defaultable {

    public static final String DEFAULT_FACTOR_NAME_TEMPLATE = Context.INSTANCE.getResources().getString("decision.matrix.table.factor");

    @NotEmpty(message = "{error.factor.factor.name.not.empty}")
    private String factorName;

    @NotEmpty(message = "{error.factor.factors.names.not.empty}")
    private List<String> factorsNames;

    @NotEmpty(message = "{error.factor.chosen.factor.not.empty}")
    private String chosenFactor;

    @Valid
    @NotEmpty(message = "{error.factor.factors.outputs.values.not.empty}")
    private List<@DoubleRange(min = 0, max = 1.0, message = "{error.factor.factors.outputs.values.double.range}") Double> factorsOutputsValues;

    @Override
    public List<String> applyDefaultAndLog() {
        if (chosenFactor == null) {
            chosenFactor = factorsNames.get(0);
            String log = MessageFormat.format(Context.INSTANCE.getResources().getString("log.default.raw.decision.matrix.factor.chosen.factor"), factorName, chosenFactor);
            return Collections.singletonList(log);
        }

        return Collections.emptyList();
    }
}
