package com.paymybuddy.transferapps.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendMoney {
    String target;
    String description;
    double amount;
}
