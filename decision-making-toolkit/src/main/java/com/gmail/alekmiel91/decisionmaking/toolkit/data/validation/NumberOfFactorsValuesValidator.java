package com.gmail.alekmiel91.decisionmaking.toolkit.data.validation;

import com.gmail.alekmiel91.decisionmaking.toolkit.data.RawDecisionMatrix;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Aleksander Mielczarek
 *         Created on 2015-04-03.
 */
public class NumberOfFactorsValuesValidator implements ConstraintValidator<NumberOfFactorsValues, RawDecisionMatrix> {
    @Override
    public void initialize(NumberOfFactorsValues constraintAnnotation) {
    }

    @Override
    public boolean isValid(RawDecisionMatrix value, ConstraintValidatorContext context) {
        return !(value.getFactors() == null || value.getScenes() == null) && value.getFactors().stream().allMatch(factor -> factor.getFactorsOutputsValues().size() == factor.getFactorsNames().size() * value.getScenes().size());
    }

}
