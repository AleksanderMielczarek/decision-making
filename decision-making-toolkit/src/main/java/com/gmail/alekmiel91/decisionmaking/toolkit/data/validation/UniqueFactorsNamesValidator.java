package com.gmail.alekmiel91.decisionmaking.toolkit.data.validation;

import com.gmail.alekmiel91.decisionmaking.toolkit.data.Factor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;

/**
 * @author Aleksander Mielczarek
 *         Created on 2015-04-03.
 */
public class UniqueFactorsNamesValidator implements ConstraintValidator<UniqueFactorsNames, Factor> {
    @Override
    public void initialize(UniqueFactorsNames constraintAnnotation) {

    }

    @Override
    public boolean isValid(Factor value, ConstraintValidatorContext context) {
        if (value.getFactorsNames() == null) {
            return false;
        }
        return new HashSet<>(value.getFactorsNames()).size() == value.getFactorsNames().size();
    }
}
