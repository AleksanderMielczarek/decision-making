package com.gmail.alekmiel91.decisionmaking.toolkit.data.validation;

import com.gmail.alekmiel91.decisionmaking.toolkit.data.RawDecisionMatrix;
import com.gmail.alekmiel91.decisionmaking.toolkit.data.Scene;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Aleksander Mielczarek
 *         Created on 2015-04-03.
 */
public class TotalSceneProbabilityValidator implements ConstraintValidator<TotalSceneProbability, RawDecisionMatrix> {
    @Override
    public void initialize(TotalSceneProbability constraintAnnotation) {
    }

    @Override
    public boolean isValid(RawDecisionMatrix value, ConstraintValidatorContext context) {
        return value.getScenes() != null && value.getScenes().stream().mapToDouble(Scene::getProbability).sum() == 1.0;
    }
}
