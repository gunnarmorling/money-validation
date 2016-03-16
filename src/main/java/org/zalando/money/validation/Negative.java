package org.zalando.money.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.DecimalMax;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {})
@DecimalMax(value = "0", inclusive = false)
public @interface Negative {

    String message() default "{org.zalando.money.validation.Negative.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
