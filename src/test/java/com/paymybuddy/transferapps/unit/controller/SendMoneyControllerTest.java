package com.paymybuddy.transferapps.unit.controller;


import com.paymybuddy.transferapps.controllers.TransferControllers;
import com.paymybuddy.transferapps.dto.SendMoney;
import com.paymybuddy.transferapps.service.MoneyTransferService;
import com.paymybuddy.transferapps.service.RelativeService;
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

@ExtendWith(SpringExtension.class)
public class SendMoneyControllerTest {

    @Mock
    private MoneyTransferService moneyTransferService;
    @Mock
    private RelativeService relativeService;
    @Mock
    private Model model;


    @InjectMocks
    TransferControllers transferControllers = new TransferControllers();

    @Test
    public void transferControllers() {
        String result = transferControllers.sendMoney(model);
        verify(model, (times(1))).addAttribute(eq("sendMoney"), ArgumentMatchers.any());
        verify(model, (times(1))).addAttribute(eq("relativesEmail"), ArgumentMatchers.any());
        verify(model, (times(1))).addAttribute(eq("transactions"), ArgumentMatchers.any());

        assertThat(result).isEqualTo("Transfer");
    }

    @Test
    public void transferReturnGoodURL() {
        SendMoney sendMoney = new SendMoney();
        when(moneyTransferService.sendMoneyToARelative(sendMoney)).thenReturn(true);
        String result = transferControllers.sending(sendMoney);
        assertThat(result).isEqualTo("redirect:/userHome");
    }

    @Test
    public void withdrawingReturnGoodURLWhenWrong() {
        SendMoney sendMoney = new SendMoney();
        when(moneyTransferService.sendMoneyToARelative(sendMoney)).thenReturn(false);
        String result = transferControllers.sending(sendMoney);
        assertThat(result).isEqualTo("redirect:/userHome/transfer");
    }
}