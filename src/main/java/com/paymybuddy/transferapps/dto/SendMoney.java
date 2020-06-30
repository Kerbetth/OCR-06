package com.paymybuddy.transferapps.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

/**
 * SendMoney is a dto which store all needed data written by the user in order to
 * make a transaction between users, or betweeen user and a bank
 */

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
