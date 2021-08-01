package com.hoang.backend_learning.dto;

import com.hoang.backend_learning.contraint.anotation.ValidRequiredCustom;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Accessors(chain = true)
public class UserRegisterDto {

    @ValidRequiredCustom
    private String userName;

    @ValidRequiredCustom
    private String email;

    @ValidRequiredCustom
    private PhoneNumberDto phoneNumber;
}
