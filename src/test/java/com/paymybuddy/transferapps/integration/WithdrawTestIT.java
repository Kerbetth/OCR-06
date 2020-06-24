package com.paymybuddy.transferapps.integration;


import com.paymybuddy.transferapps.domain.BankAccount;
import com.paymybuddy.transferapps.domain.UserAccount;
import com.paymybuddy.transferapps.dto.Deposit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


public class WithdrawTestIT extends AbstractIT{


    private UserAccount account = new UserAccount();
    private BankAccount bankAccount = new BankAccount();

    @BeforeEach
    public void setup() {
        account.setEmail("test@test.com");
        account.setName("user");
        account.setPassword("password");
        account.setRole("ADMIN");
        account.setMoneyAmount(50);
        userAccountRepository.save(account);
        bankAccount.setAccountIban("5555");
        bankAccount.setAccountName("myAccount");
        bankAccount.setUserAccount(account);
        bankAccountRepository.save(bankAccount);

    }


    @Test
    public void accessWithdrawFormWithSuccess() throws Exception {
        mvc.perform(get("/userHome/withdrawMoney/withdraw")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("amount", "50")
                .content("amount")
                .sessionAttr("dto", new Deposit())
        )
                .andExpect(status().isOk())
                .andExpect(view().name("withdrawMoney"));
    }

    @Test
    public void withdrawMoneyWithSuccess() throws Exception {
        Deposit withdraw = new Deposit();
        withdraw.setAccountName("myAccount");
        withdraw.setDescription("a description");
        mvc.perform(post("/userHome/withdrawMoney/withdrawing")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("accountName",withdraw.getAccountName())
                .param("description",withdraw.getDescription())
                .param("amount", "20.00")
                .requestAttr("withdraw", withdraw)
                .contentType(MediaType.APPLICATION_XHTML_XML)
        )
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/userHome"));

        assertThat(bankAccountRepository.findByUserAccount(account)).hasSize(1);
        assertThat(bankAccountRepository.findByAccountIban("5555")).isPresent();
        assertThat(transactionRepository.findByUserAccount(account)).hasSize(1);
        assertThat(transactionRepository.findByUserAccount(account).get(0).getDescription()).isEqualTo(withdraw.getDescription());
        assertThat(userAccountRepository.findByEmail("test@test.com").get().getMoneyAmount()).isEqualTo(70);
    }

    @Test
    public void withdrawMoney2timesWithSuccess() throws Exception {
        Deposit withdraw = new Deposit();
        withdraw.setAccountName("myAccount");
        mvc.perform(post("/userHome/withdrawMoney/withdrawing")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("accountName",withdraw.getAccountName())
                .param("description",withdraw.getDescription())
                .param("amount", "20.00")
                .requestAttr("withdraw", withdraw)
                .contentType(MediaType.APPLICATION_XHTML_XML)
        )
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/userHome"));

        assertThat(bankAccountRepository.findByUserAccount(account)).hasSize(1);
        assertThat(bankAccountRepository.findByAccountIban("5555")).isPresent();
        assertThat(userAccountRepository.findByEmail("test@test.com").get().getMoneyAmount()).isEqualTo(70);

        mvc.perform(post("/userHome/withdrawMoney/withdrawing")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("accountName",withdraw.getAccountName())
                .param("description",withdraw.getDescription())
                .param("amount", "20.00")
                .requestAttr("withdraw", withdraw)
                .contentType(MediaType.APPLICATION_XHTML_XML)
        )
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/userHome"));
        assertThat(userAccountRepository.findByEmail("test@test.com").get().getMoneyAmount()).isEqualTo(90);
    }

    @Test
    public void returnErrorIfMoneyAmountBreakTheMaximumAmountPossible() throws Exception {
        Deposit withdraw = new Deposit();
        withdraw.setAccountName("myAccount");
        mvc.perform(post("/userHome/withdrawMoney/withdrawing")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("accountName",withdraw.getAccountName())
                .param("description",withdraw.getDescription())
                .param("amount", "11000.00")
                .requestAttr("withdraw", withdraw)
                .contentType(MediaType.APPLICATION_XHTML_XML)
        )
                .andExpect(status().isFound())
                .andExpect(status().is3xxRedirection());

        assertThat(bankAccountRepository.findByUserAccount(account)).hasSize(1);
        assertThat(bankAccountRepository.findByAccountIban("5555")).isPresent();
        assertThat(userAccountRepository.findByEmail("test@test.com").get().getMoneyAmount()).isEqualTo(50);
        assertThat(transactionRepository.findAll()).isEmpty();
    }
}