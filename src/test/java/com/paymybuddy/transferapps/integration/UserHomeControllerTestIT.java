package com.paymybuddy.transferapps.integration;


import com.paymybuddy.transferapps.domain.UserAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


public class UserHomeControllerTestIT extends AbstractIT {

    @Test
    public void getUserPage() throws Exception {
        mvc.perform(get("/userHome")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
                .andExpect(status().isOk())
                .andExpect(view().name("UserPage"));
    }
}
