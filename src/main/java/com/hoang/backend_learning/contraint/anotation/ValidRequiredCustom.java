package com.hoang.backend_learning.contraint.anotation;

import com.hoang.backend_learning.contraint.validators.ValidRequiredCustomValidator;
import com.hoang.backend_learning.dto.ErrorsDto;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = ValidRequiredCustomValidator.class)
@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
public @interface ValidRequiredCustom {

    String message() default ErrorsDto.INVALID;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
