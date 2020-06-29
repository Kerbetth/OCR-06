package com.paymybuddy.transferapps.integration;


import com.paymybuddy.transferapps.dto.SendMoney;
import com.paymybuddy.transferapps.dto.SendMoney;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


public class DepositTestIT extends AbstractIT{


    @Test
    public void accessSendMoneyFormWithSuccess() throws Exception {
        mvc.perform(get("/userHome/depositMoney/deposit")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("amount", "50")
                .content("amount")
                .sessionAttr("dto", new SendMoney())
        )
                .andExpect(status().isOk())
                .andExpect(view().name("depositMoney"));
    }

    @Test
    public void accessWithdrawFormWithSuccess() throws Exception {
        mvc.perform(get("/userHome/withdrawMoney/withdraw")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("amount", "50")
                .content("amount")
                .sessionAttr("dto", new SendMoney())
        )
                .andExpect(status().isOk())
                .andExpect(view().name("withdrawMoney"));
    }

    @Test
    public void DepositMoneyWithSuccess() throws Exception {
        SendMoney sendMoney = new SendMoney();
        sendMoney.setTarget("myBank");
        sendMoney.setAmount(20);
        sendMoney.setDescription("a description");
        mvc.perform(post("/userHome/depositMoney/depositing")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("target",sendMoney.getTarget())
                .param("description",sendMoney.getDescription())
                .param("amount", "20.00")
        )
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/userHome"));

        assertThat(bankAccountRepository.findByUserAccount(userAccountRepository.findByEmail("test@test.com").get())).hasSize(1);
        assertThat(bankAccountRepository.findByAccountIban("5555")).isPresent();
        assertThat(transactionRepository.findByUserAccount(userAccountRepository.findByEmail("test@test.com").get())).hasSize(2);
        assertThat(transactionRepository.findByUserAccount(userAccountRepository.findByEmail("test@test.com").get()).get(1).getDescription()).isEqualTo(sendMoney.getDescription());
        assertThat(userAccountRepository.findByEmail("test@test.com").get().getMoneyAmount()).isEqualTo(80);
    }

    @Test
    public void DepositMoney2timesWithSuccess() throws Exception {
        SendMoney sendMoney = new SendMoney();
        sendMoney.setTarget("myBank");
        mvc.perform(post("/userHome/depositMoney/depositing")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("target",sendMoney.getTarget())
                .param("description",sendMoney.getDescription())
                .param("amount", "20.00")
        )
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/userHome"));

        assertThat(bankAccountRepository.findByUserAccount(userAccountRepository.findByEmail("test@test.com").get())).hasSize(1);
        assertThat(bankAccountRepository.findByAccountIban("5555")).isPresent();
        assertThat(userAccountRepository.findByEmail("test@test.com").get().getMoneyAmount()).isEqualTo(80);

        mvc.perform(post("/userHome/depositMoney/depositing")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("target",sendMoney.getTarget())
                .param("description",sendMoney.getDescription())
                .param("amount", "20.00")
        )
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/userHome"));
        assertThat(userAccountRepository.findByEmail("test@test.com").get().getMoneyAmount()).isEqualTo(60);
    }

    @Test
    public void DepositMoreMoneyThanItsOwnAndReturnError() throws Exception {
        SendMoney sendMoney = new SendMoney();
        sendMoney.setTarget("myBank");
        mvc.perform(post("/userHome/depositMoney/depositing")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("target",sendMoney.getTarget())
                .param("description",sendMoney.getDescription())
                .param("amount", "200.00")
        )
                .andExpect(status().is4xxClientError());

        assertThat(bankAccountRepository.findByUserAccount(userAccountRepository.findByEmail("test@test.com").get())).hasSize(1);
        assertThat(bankAccountRepository.findByAccountIban("5555")).isPresent();
        assertThat(userAccountRepository.findByEmail("test@test.com").get().getMoneyAmount()).isEqualTo(100);
        assertThat(transactionRepository.findAll()).hasSize(1);
    }
}