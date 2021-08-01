package com.hoang.backend_learning.contraint.validators;

import com.hoang.backend_learning.contraint.anotation.ValidRequiredCustom;
import com.hoang.backend_learning.dto.ErrorsDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidRequiredCustomValidator implements ConstraintValidator<ValidRequiredCustom, Object> {

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();

        if (value == null || (value.getClass().equals(String.class) && value.equals(""))) {
            context.buildConstraintViolationWithTemplate(ErrorsDto.EMPTY)
                .addConstraintViolation();
            return false;
        }

        return true;
    }
}
