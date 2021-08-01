package com.hoang.backend_learning.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class StudentInfoDto {
    private String fullName;

    private String major;

    private int age;

    private String address;
}
