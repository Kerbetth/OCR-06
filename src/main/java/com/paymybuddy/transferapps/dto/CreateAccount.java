package com.paymybuddy.transferapps.dto;

import com.paymybuddy.transferapps.config.ValidPassword;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * CreateAccount is a DTO which value "password" is verify to matches the security conditions before being
 * encrypted. Then the data is registered as a UserAccount object
 */


@Getter
@Setter
public class CreateAccount {
    @NotBlank
    String email;
    @NotBlank
    String name;
    @ValidPassword
    @Length(min=8, max=20, message = "Password should have 8 to 20 characters")
    String password;
    String confirmPassword;
}
