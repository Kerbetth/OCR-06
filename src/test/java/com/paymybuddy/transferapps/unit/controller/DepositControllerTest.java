package com.paymybuddy.transferapps.unit.controller;

import com.paymybuddy.transferapps.controllers.DepositControllers;
import com.paymybuddy.transferapps.dto.CreateAccount;
import com.paymybuddy.transferapps.dto.Deposit;
import com.paymybuddy.transferapps.service.MoneyTransferService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.ui.Model;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
public class DepositControllerTest {

    @Mock
    private MoneyTransferService moneyTransferService;
    @Mock
    private Model model;


    @InjectMocks
    DepositControllers depositControllers = new DepositControllers();

    @Test
    public void depositControllers() {
        String result = depositControllers.depositMoney(model);
        verify(model, (times(1))).addAttribute(eq("depositMoney"), ArgumentMatchers.any());
        verify(model, (times(1))).addAttribute(eq("bankAccounts"), ArgumentMatchers.any());

        assertThat(result).isEqualTo("depositMoney");
    }

    @Test
    public void depositingReturnGoodURL() {
        Deposit deposit= new Deposit();
        when(moneyTransferService.depositMoneyToBankAccount(any())).thenReturn(true);
        String result = depositControllers.depositing(deposit);

        assertThat(result).isEqualTo("redirect:/userHome");
    }

    @Test
    public void depositingReturnGoodURLWhenWrong() {
        Deposit deposit= new Deposit();
        when(moneyTransferService.depositMoneyToBankAccount(any())).thenReturn(false);
        String result = depositControllers.depositing(deposit);

        assertThat(result).isEqualTo("redirect:/userHome/depositMoney/deposit");
    }
}