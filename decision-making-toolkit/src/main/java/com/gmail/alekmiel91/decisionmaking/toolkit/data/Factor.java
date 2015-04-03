package com.gmail.alekmiel91.decisionmaking.toolkit.data;

import com.gmail.alekmiel91.decisionmaking.toolkit.data.validation.ChosenFactorExist;
import com.gmail.alekmiel91.decisionmaking.toolkit.data.validation.DoubleRange;
import com.gmail.alekmiel91.decisionmaking.toolkit.data.validation.UniqueFactorsNames;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
@AllArgsConstructor
@NoArgsConstructor
@UniqueFactorsNames
@ChosenFactorExist
public class Factor {

    @NotEmpty(message = "{error.factor.factor.name.not.empty}")
    private String factorName;

    @NotEmpty(message = "{error.factor.factors.names.not.empty}")
    private List<String> factorsNames;

    @NotEmpty(message = "{error.factor.chosen.factor.not.empty}")
    private String chosenFactor;

    @Valid
    @NotEmpty(message = "{error.factor.factors.outputs.values.not.empty}")
    private List<@DoubleRange(min = 0, max = 1.0, message = "{error.factor.factors.outputs.values.double.range}") Double> factorsOutputsValues;

}
