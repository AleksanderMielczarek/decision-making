package com.gmail.alekmiel91.decisionmaking.toolkit.data.validation;

import com.gmail.alekmiel91.decisionmaking.toolkit.data.RawDecisionMatrix;
import com.gmail.alekmiel91.decisionmaking.toolkit.data.Scene;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.stream.Collectors;

/**
 * @author Aleksander Mielczarek
 *         Created on 2015-04-03.
 */
public class UniqueScenesValidator implements ConstraintValidator<UniqueScenes, RawDecisionMatrix> {
    @Override
    public void initialize(UniqueScenes constraintAnnotation) {
    }

    @Override
    public boolean isValid(RawDecisionMatrix value, ConstraintValidatorContext context) {
        if (value.getScenes() == null) {
            return false;
        }
        return value.getScenes().stream()
                .map(Scene::getName)
                .collect(Collectors.toSet())
                .size() == value.getScenes().size();
    }

}
