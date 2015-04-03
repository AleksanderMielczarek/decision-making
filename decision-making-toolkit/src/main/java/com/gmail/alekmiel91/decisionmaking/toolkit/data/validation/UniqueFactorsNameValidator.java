package com.gmail.alekmiel91.decisionmaking.toolkit.data.validation;

import com.gmail.alekmiel91.decisionmaking.toolkit.data.Factor;
import com.gmail.alekmiel91.decisionmaking.toolkit.data.RawDecisionMatrix;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.stream.Collectors;

/**
 * @author Aleksander Mielczarek
 *         Created on 2015-04-03.
 */
public class UniqueFactorsNameValidator implements ConstraintValidator<UniqueFactorsName, RawDecisionMatrix> {
    @Override
    public void initialize(UniqueFactorsName constraintAnnotation) {
    }

    @Override
    public boolean isValid(RawDecisionMatrix value, ConstraintValidatorContext context) {
        return value.getFactors().stream()
                .map(Factor::getFactorName)
                .collect(Collectors.toSet())
                .size() == value.getFactors().size();
    }

}
