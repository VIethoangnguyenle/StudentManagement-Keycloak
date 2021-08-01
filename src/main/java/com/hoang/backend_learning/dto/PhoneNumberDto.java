package com.hoang.backend_learning.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PhoneNumberDto implements Serializable {
    private int countryCode;
    private long nationalNumber;

    @JsonIgnore
    public String getInternationNumber() {
        return "+" + countryCode + nationalNumber;
    }
}
