package com.hoang.backend_learning.contraint.validators;

import com.hoang.backend_learning.contraint.anotation.ValidPhoneNumber;
import com.hoang.backend_learning.dto.ErrorsDto;
import com.hoang.backend_learning.dto.PhoneNumberDto;
import com.hoang.backend_learning.service.UserService;
import com.hoang.backend_learning.utils.FnCommon;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidPhoneNumberValidator implements ConstraintValidator<ValidPhoneNumber, PhoneNumberDto> {
    @Autowired
    UserService userService;

    @Override
    public void initialize(ValidPhoneNumber constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(PhoneNumberDto phoneNumberDto, ConstraintValidatorContext context) {
        if (phoneNumberDto == null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorsDto.EMPTY)
                .addConstraintViolation();
            return false;
        }

        if (userService.getUserIdByPhoneNumber(phoneNumberDto) != null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorsDto.USED)
                .addConstraintViolation();
            return false;
        }
        return FnCommon.isValidPhoneNumber(phoneNumberDto);
    }
}
