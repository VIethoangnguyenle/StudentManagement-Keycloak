package com.hoang.backend_learning.dto;

import com.hoang.backend_learning.contraint.anotation.ValidRequiredCustom;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PasswordDto {

    @ValidRequiredCustom
    private String password;
}
