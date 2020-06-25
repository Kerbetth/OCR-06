package com.paymybuddy.transferapps.integration;


import com.paymybuddy.transferapps.domain.BankAccount;
import com.paymybuddy.transferapps.domain.UserAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class AddBankAccountTestIT extends AbstractIT{


    @Test
    public void fillBankFormWithSuccess() throws Exception {
        mvc.perform(get("/userHome/bankAccount/add")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("accountName", "account1")
                .content("account1")
                .sessionAttr("dto", new BankAccount())
        )
                .andExpect(status().isOk())
                .andExpect(view().name("bankAccountAdd"));
    }

    @Test
    public void postNewBankAccountWithSuccess() throws Exception {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setAccountName("account1");
        bankAccount.setAccountIban("555444888");
        mvc.perform(post("/userHome/bankAccount/adding")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("accountName",bankAccount.getAccountName())
                .param("accountIban",bankAccount.getAccountIban())
        )
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/userHome"));
        assertThat(bankAccountRepository.findByUserAccount(userAccountRepository.findByEmail("test@test.com").get())).hasSize(2);
        assertThat(bankAccountRepository.findByAccountIban("555444888")).isPresent();
    }

    @Test
    public void post2NewBankAccountWithSuccess() throws Exception {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setAccountName("account1");
        bankAccount.setAccountIban("555444888");
        mvc.perform(post("/userHome/bankAccount/adding")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("accountName",bankAccount.getAccountName())
                .param("accountIban",bankAccount.getAccountIban())
        )
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/userHome"));

        assertThat(bankAccountRepository.findByUserAccount(userAccountRepository.findByEmail("test@test.com").get())).hasSize(2);
        assertThat(bankAccountRepository.findByAccountIban("555444888")).isPresent();
        bankAccount.setAccountIban("555555888");
        mvc.perform(post("/userHome/bankAccount/adding")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("accountName",bankAccount.getAccountName())
                .param("accountIban",bankAccount.getAccountIban())
        )
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/userHome"));
        assertThat(bankAccountRepository.findByUserAccount(userAccountRepository.findByEmail("test@test.com").get())).hasSize(3);
        assertThat(bankAccountRepository.findByAccountIban("555555888")).isPresent();
    }
}
