package com.gmail.alekmiel91.decisionmaking.toolkit.data.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Aleksander Mielczarek
 *         Created on 2015-04-03.
 */
public class DoubleRangeValidator implements ConstraintValidator<DoubleRange, Double> {

    private double min;
    private double max;

    @Override
    public void initialize(DoubleRange constraintAnnotation) {
        min = constraintAnnotation.min();
        max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        return value >= min && value <= max;
    }
}
