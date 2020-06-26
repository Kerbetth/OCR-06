package com.paymybuddy.transferapps.integration;


import com.paymybuddy.transferapps.dto.SendMoney;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


public class WithdrawTestIT extends AbstractIT{



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
    public void withdrawMoneyWithSuccess() throws Exception {
        SendMoney withdraw = new SendMoney();
        withdraw.setTarget("myBank");
        withdraw.setDescription("a description");
        mvc.perform(post("/userHome/withdrawMoney/withdrawing")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("target",withdraw.getTarget())
                .param("description",withdraw.getDescription())
                .param("amount", "20.00")
        )
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/userHome"));

        assertThat(bankAccountRepository.findByUserAccount(userAccountRepository.findByEmail("test@test.com").get())).hasSize(1);
        assertThat(bankAccountRepository.findByAccountIban("5555")).isPresent();
        assertThat(transactionRepository.findByUserAccount(userAccountRepository.findByEmail("test@test.com").get())).hasSize(2);
        assertThat(transactionRepository.findByUserAccount(userAccountRepository.findByEmail("test@test.com").get()).get(0).getDescription()).isEqualTo(withdraw.getDescription());
        assertThat(userAccountRepository.findByEmail("test@test.com").get().getMoneyAmount()).isEqualTo(120);
    }

    @Test
    public void withdrawMoney2timesWithSuccess() throws Exception {
        SendMoney withdraw = new SendMoney();
        withdraw.setTarget("myBank");
        mvc.perform(post("/userHome/withdrawMoney/withdrawing")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("target",withdraw.getTarget())
                .param("description",withdraw.getDescription())
                .param("amount", "20.00")
        )
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/userHome"));

        assertThat(bankAccountRepository.findByUserAccount(userAccountRepository.findByEmail("test@test.com").get())).hasSize(1);
        assertThat(bankAccountRepository.findByAccountIban("5555")).isPresent();
        assertThat(userAccountRepository.findByEmail("test@test.com").get().getMoneyAmount()).isEqualTo(120);

        mvc.perform(post("/userHome/withdrawMoney/withdrawing")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("target",withdraw.getTarget())
                .param("description",withdraw.getDescription())
                .param("amount", "20.00")
        )
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/userHome"));
        assertThat(userAccountRepository.findByEmail("test@test.com").get().getMoneyAmount()).isEqualTo(140);
    }

    @Test
    public void returnErrorIfMoneyAmountBreakTheMaximumAmountPossible() throws Exception {
        SendMoney withdraw = new SendMoney();
        withdraw.setTarget("myAccount");
        mvc.perform(post("/userHome/withdrawMoney/withdrawing")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("target",withdraw.getTarget())
                .param("description",withdraw.getDescription())
                .param("amount", "11000.00")
        )
                .andExpect(status().is4xxClientError());

        assertThat(bankAccountRepository.findByUserAccount(userAccountRepository.findByEmail("test@test.com").get())).hasSize(1);
        assertThat(bankAccountRepository.findByAccountIban("5555")).isPresent();
        assertThat(userAccountRepository.findByEmail("test@test.com").get().getMoneyAmount()).isEqualTo(100);
        assertThat(transactionRepository.findAll()).hasSize(1);
    }
}