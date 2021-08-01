package com.hoang.backend_learning.dto;

import com.hoang.backend_learning.contraint.anotation.ValidRequiredCustom;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

@Data
@Accessors(chain = true)
public class VerifyDto {
    @ValidRequiredCustom
    private String verify;
}
