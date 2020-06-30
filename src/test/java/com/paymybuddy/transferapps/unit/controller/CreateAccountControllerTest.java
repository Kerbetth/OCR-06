package com.paymybuddy.transferapps.unit.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import com.paymybuddy.transferapps.controllers.CreateAccountControllers;
import com.paymybuddy.transferapps.dto.CreateAccount;
import com.paymybuddy.transferapps.service.UserService;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@ExtendWith(SpringExtension.class)
public class CreateAccountControllerTest {

    @Mock
    private UserService userService;
    @Mock
    private Model model;
    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    CreateAccountControllers createAccountControllers = new CreateAccountControllers();

    @Test
    public void createAccount() {
        String result = createAccountControllers.createAccount(model);
        verify(model, (times(1))).addAttribute(eq("createAccount"), ArgumentMatchers.any());

        assertThat(result).isEqualTo("CreateAccount");
    }

    @Test
    public void creatingAccount() {
        CreateAccount createAccount = new CreateAccount();

        String result = createAccountControllers.creatingAccount(createAccount, bindingResult);

        assertThat(result).isEqualTo("redirect:/");
    }

    @Test
    public void creatingAccountwithWrongData() {
        CreateAccount createAccount = new CreateAccount();
        when(bindingResult.hasErrors()).thenReturn(true);
        String result = createAccountControllers.creatingAccount(createAccount, bindingResult);

        assertThat(result).isEqualTo("redirect:/account/create");
    }
}