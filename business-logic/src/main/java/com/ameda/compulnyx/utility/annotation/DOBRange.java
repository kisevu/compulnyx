package com.ameda.compulnyx.utility.annotation;

import com.ameda.compulnyx.utility.validator.DOBValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * Author: kev.Ameda
 */


@Documented
@Constraint(validatedBy = DOBValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface DOBRange {
    String message() default "DOB must be between 2000-01-01 and 2010-12-31";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
