package com.paymybuddy.transferapps.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Getter
@Setter
public class SendMoney {
    @NotBlank
    String target;
    String description;
    @Positive
    @NotBlank
    double amount;
}
