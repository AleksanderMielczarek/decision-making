package com.gmail.alekmiel91.decisionmaking.toolkit.data.validation;

import com.gmail.alekmiel91.decisionmaking.toolkit.data.Factor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Aleksander Mielczarek
 *         Created on 2015-04-03.
 */
public class ChosenFactorExistValidator implements ConstraintValidator<ChosenFactorExist, Factor> {
    @Override
    public void initialize(ChosenFactorExist constraintAnnotation) {

    }

    @Override
    public boolean isValid(Factor value, ConstraintValidatorContext context) {
        return value.getFactorsNames().contains(value.getChosenFactor());
    }
}
