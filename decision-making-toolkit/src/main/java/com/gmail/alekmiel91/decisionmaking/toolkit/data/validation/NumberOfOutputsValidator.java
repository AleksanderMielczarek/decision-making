package com.gmail.alekmiel91.decisionmaking.toolkit.data.validation;

import com.gmail.alekmiel91.decisionmaking.toolkit.data.RawDecisionMatrix;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Aleksander Mielczarek
 *         Created on 2015-04-03.
 */
public class NumberOfOutputsValidator implements ConstraintValidator<NumberOfOutputs, RawDecisionMatrix> {
    @Override
    public void initialize(NumberOfOutputs constraintAnnotation) {
    }

    @Override
    public boolean isValid(RawDecisionMatrix value, ConstraintValidatorContext context) {
        return !(value.getOutputs() == null || value.getAlternatives() == null || value.getScenes() == null) && value.getOutputs().size() == value.getAlternatives().size() * value.getScenes().size();
    }

}
