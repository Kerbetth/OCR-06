package com.paymybuddy.transferapps.dto;

import com.paymybuddy.transferapps.config.ValidPassword;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * Relation is a simple DTO which store the email write by the user during the "add friend" form
 */

@Getter
@Setter
public class Relation {
    @NotBlank
    String email;
}
