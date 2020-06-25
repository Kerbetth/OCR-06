package com.paymybuddy.transferapps.unit.controller;


import com.paymybuddy.transferapps.controllers.WithDrawMoneyControllers;
import com.paymybuddy.transferapps.dto.SendMoney;
import com.paymybuddy.transferapps.service.MoneyTransferService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.ui.Model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class WithdrawControllerTest {

    @Mock
    private MoneyTransferService moneyTransferService;
    @Mock
    private Model model;


    @InjectMocks
    WithDrawMoneyControllers withDrawMoneyControllers = new WithDrawMoneyControllers();

    @Test
    public void withdrawControllers() {
        String result = withDrawMoneyControllers.withdrawMoney(model);
        verify(model, (times(1))).addAttribute(eq("withdrawMoney"), ArgumentMatchers.any());
        verify(model, (times(1))).addAttribute(eq("bankAccounts"), ArgumentMatchers.any());

        assertThat(result).isEqualTo("withdrawMoney");
    }

    @Test
    public void withdrawingReturnGoodURL() {
        SendMoney deposit= new SendMoney();
        when(moneyTransferService.withDrawMoneyFromBankAndAddOnTheAccount(any())).thenReturn(true);
        String result = withDrawMoneyControllers.withdrawing(deposit);
        assertThat(result).isEqualTo("redirect:/userHome");
    }

    @Test
    public void withdrawingReturnGoodURLWhenWrong() {
        SendMoney deposit= new SendMoney();
        when(moneyTransferService.withDrawMoneyFromBankAndAddOnTheAccount(any())).thenReturn(false);
        String result = withDrawMoneyControllers.withdrawing(deposit);
        assertThat(result).isEqualTo("redirect:/userHome/withdrawMoney/withdraw");
    }
}