package com.gmail.alekmiel91.decisionmaking.toolkit.data.validation;

import com.gmail.alekmiel91.decisionmaking.toolkit.data.Alternative;
import com.gmail.alekmiel91.decisionmaking.toolkit.data.RawDecisionMatrix;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.stream.Collectors;

/**
 * @author Aleksander Mielczarek
 *         Created on 2015-04-03.
 */
public class UniqueAlternativesValidator implements ConstraintValidator<UniqueAlternatives, RawDecisionMatrix> {
    @Override
    public void initialize(UniqueAlternatives constraintAnnotation) {
    }

    @Override
    public boolean isValid(RawDecisionMatrix value, ConstraintValidatorContext context) {
        if (value.getAlternatives() == null) {
            return false;
        }
        return value.getAlternatives().stream()
                .map(Alternative::getName)
                .collect(Collectors.toSet())
                .size() == value.getAlternatives().size();
    }

}
