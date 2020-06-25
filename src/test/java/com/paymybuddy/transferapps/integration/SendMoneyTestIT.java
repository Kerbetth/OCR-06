package com.paymybuddy.transferapps.integration;


import com.paymybuddy.transferapps.domain.BankAccount;
import com.paymybuddy.transferapps.domain.RelationEmail;
import com.paymybuddy.transferapps.domain.UserAccount;
import com.paymybuddy.transferapps.dto.SendMoney;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.http.MediaType;


import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


public class SendMoneyTestIT extends AbstractIT{

    @Test
    public void accessSendMoneyFormWithSuccess() throws Exception {
        mvc.perform(get("/userHome/transfer")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content("amount")
                .sessionAttr("dto", new SendMoney())
        )
                .andExpect(status().isOk())
                .andExpect(view().name("Transfer"));
    }


    @Test
    public void sendMoneyWithSuccess() throws Exception {
        SendMoney sendMoney = new SendMoney();
        sendMoney.setRelativeEmail("friend@test.com");
        sendMoney.setAmount(20);
        sendMoney.setDescription("a good transfer");
        mvc.perform(post("/userHome/sendMoney/sending")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("relativeEmail",sendMoney.getRelativeEmail())
                .param("description",sendMoney.getDescription())
                .param("amount", "20.00")
                .requestAttr("deposit", sendMoney)
                .contentType(MediaType.APPLICATION_XHTML_XML)
        )
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/userHome"));
        assertThat(bankAccountRepository.findByUserAccount(userAccountRepository.findByEmail("test@test.com").get())).hasSize(1);
        assertThat(bankAccountRepository.findByAccountIban("5555")).isPresent();
        assertThat(userAccountRepository.findByEmail("test@test.com").get().getMoneyAmount()).isEqualTo(80);
    }
    @Test
    public void errorIfTooMuchMoneyInTheRelativeAccount() throws Exception {
        UserAccount userAccount = userAccountRepository.findByEmail("friend@test.com").get();
        userAccount.setMoneyAmount(9990);
        userAccountRepository.save(userAccount);

        SendMoney sendMoney = new SendMoney();
        sendMoney.setRelativeEmail("friend@test.com");
        sendMoney.setAmount(20);
        sendMoney.setDescription("a good transfer");
        mvc.perform(post("/userHome/sendMoney/sending")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("relativeEmail",sendMoney.getRelativeEmail())
                .param("description",sendMoney.getDescription())
                .param("amount", "20.00")
                .requestAttr("deposit", sendMoney)
                .contentType(MediaType.APPLICATION_XHTML_XML)
        )
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/userHome/transfer"));
        assertThat(bankAccountRepository.findByUserAccount(userAccountRepository.findByEmail("test@test.com").get())).hasSize(1);
        assertThat(bankAccountRepository.findByAccountIban("5555")).isPresent();
        assertThat(userAccountRepository.findByEmail("test@test.com").get().getMoneyAmount()).isEqualTo(100);
        assertThat(userAccountRepository.findByEmail("friend@test.com").get().getMoneyAmount()).isEqualTo(9990);
    }
    @Test
    public void errorIfNotEnoughMoneyOnTheAccount() throws Exception {
        SendMoney sendMoney = new SendMoney();
        sendMoney.setRelativeEmail("friend@test.com");
        sendMoney.setDescription("a good transfer");
        mvc.perform(post("/userHome/sendMoney/sending")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("relativeEmail",sendMoney.getRelativeEmail())
                .param("description",sendMoney.getDescription())
                .param("amount", "200.00")
                .requestAttr("deposit", sendMoney)
                .contentType(MediaType.APPLICATION_XHTML_XML)
        )
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/userHome/transfer"));
        assertThat(bankAccountRepository.findByUserAccount(userAccountRepository.findByEmail("test@test.com").get())).hasSize(1);
        assertThat(bankAccountRepository.findByAccountIban("5555")).isPresent();
        assertThat(userAccountRepository.findByEmail("test@test.com").get().getMoneyAmount()).isEqualTo(100);
        assertThat(userAccountRepository.findByEmail("friend@test.com").get().getMoneyAmount()).isEqualTo(100);
    }

    @Test
    public void errorIfNoExistingRelation() throws Exception {
        SendMoney sendMoney = new SendMoney();
        sendMoney.setRelativeEmail("noname@test.com");
        sendMoney.setDescription("a good transfer");
        mvc.perform(post("/userHome/sendMoney/sending")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("relativeEmail",sendMoney.getRelativeEmail())
                .param("description",sendMoney.getDescription())
                .param("amount", "10.00")
                .requestAttr("deposit", sendMoney)
                .contentType(MediaType.APPLICATION_XHTML_XML)
        )
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/userHome/transfer"));
        assertThat(bankAccountRepository.findByUserAccount(userAccountRepository.findByEmail("test@test.com").get())).hasSize(1);
        assertThat(bankAccountRepository.findByAccountIban("5555")).isPresent();
        assertThat(userAccountRepository.findByEmail("test@test.com").get().getMoneyAmount()).isEqualTo(100);
        assertThat(userAccountRepository.findByEmail("friend@test.com").get().getMoneyAmount()).isEqualTo(100);
    }

    @Test
    public void sendMoney2timesWithSuccess() throws Exception {
        SendMoney sendMoney = new SendMoney();
        sendMoney.setRelativeEmail("friend@test.com");
        sendMoney.setAmount(20);
        sendMoney.setDescription("a good transfer");
        mvc.perform(post("/userHome/sendMoney/sending")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("relativeEmail",sendMoney.getRelativeEmail())
                .param("description",sendMoney.getDescription())
                .param("amount", "20.00")
        )
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/userHome"));
        assertThat(bankAccountRepository.findByUserAccount(userAccountRepository.findByEmail("test@test.com").get())).hasSize(1);
        assertThat(bankAccountRepository.findByAccountIban("5555")).isPresent();
        assertThat(userAccountRepository.findByEmail("test@test.com").get().getMoneyAmount()).isEqualTo(80);

        mvc.perform(post("/userHome/sendMoney/sending")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("relativeEmail",sendMoney.getRelativeEmail())
                .param("description",sendMoney.getDescription())
                .param("amount", "10.00")
        )
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/userHome"));
        assertThat(userAccountRepository.findByEmail("test@test.com").get().getMoneyAmount()).isEqualTo(70);
    }
}