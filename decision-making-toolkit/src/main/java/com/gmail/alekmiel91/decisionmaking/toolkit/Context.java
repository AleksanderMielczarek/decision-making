package com.gmail.alekmiel91.decisionmaking.toolkit;

import lombok.Getter;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.hibernate.validator.resourceloading.PlatformResourceBundleLocator;

import javax.validation.Validation;
import javax.validation.Validator;
import java.util.ResourceBundle;

/**
 * @author Aleksander Mielczarek
 *         Created on 2015-04-03.
 */
@Getter
public enum Context {
    INSTANCE;

    private final Validator validator = Validation.byDefaultProvider()
            .configure()
            .messageInterpolator(new ResourceBundleMessageInterpolator(new PlatformResourceBundleLocator("toolkit")))
            .buildValidatorFactory()
            .getValidator();

    private final ResourceBundle resources = ResourceBundle.getBundle("toolkit");
}
