package com.gmail.alekmiel91.decisionmaking.toolkit.data.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Aleksander Mielczarek
 *         Created on 2015-04-03.
 */
@Documented
@Constraint(validatedBy = DoubleRangeValidator.class)
@Target({FIELD, TYPE_USE})
@Retention(RUNTIME)
public @interface DoubleRange {
    double min() default 0;

    double max() default 1;

    String message() default "{org.hibernate.validator.constraints.Range.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
