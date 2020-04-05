package com.es.core.cart.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class QuantityValidator implements ConstraintValidator<ValidQuantity, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        try {
            long value = Long.parseLong(s);
            return value >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
