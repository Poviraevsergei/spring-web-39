package com.tms.util;

import com.tms.model.annotations.SixSeven;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SixSevenValidator implements ConstraintValidator<SixSeven, Integer> {
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return value != null && value == 67;
    }
}
