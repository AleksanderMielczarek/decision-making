package com.gmail.alekmiel91.decisionmaking.toolkit.data.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Aleksander Mielczarek
 *         Created on 2015-04-03.
 */
@Documented
@Constraint(validatedBy = UniqueFactorsNameValidator.class)
@Target(TYPE)
@Retention(RUNTIME)
public @interface UniqueFactorsName {
    String message() default "{error.raw.decision.matrix.unique.factors.name}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
