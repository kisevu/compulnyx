package com.ameda.compulnyx.utility.validator;

import com.ameda.compulnyx.utility.annotation.DOBRange;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

/**
 * Author: kev.Ameda
 */

public class DOBValidator  implements ConstraintValidator<DOBRange, LocalDate> {
    private static final LocalDate MIN_DATE = LocalDate.of(2000, 1, 1);
    private static final LocalDate MAX_DATE = LocalDate.of(2010, 12, 31);

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        if (value == null) return false;
        return !value.isBefore(MIN_DATE) && !value.isAfter(MAX_DATE);
    }
}
