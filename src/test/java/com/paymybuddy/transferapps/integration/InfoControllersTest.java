package com.paymybuddy.transferapps.integration;

import com.paymybuddy.transferapps.domain.UserAccount;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.hamcrest.Matchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class InfoControllersTest extends AbstractIT{



    @Test
    public void userAccountInfoControllers() throws Exception {
        mvc.perform(get("/userHome/profile"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("userAccount", any(UserAccount.class)))
                .andExpect(model().attribute("relatives",  any(ArrayList.class)))
                .andExpect(model().attribute("bankAccounts",  any(ArrayList.class)));
    }
}