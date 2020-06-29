package com.paymybuddy.transferapps.integration;


import com.paymybuddy.transferapps.domain.UserRelation;
import com.paymybuddy.transferapps.domain.UserAccount;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AddRelativeControllerTestIT extends AbstractIT{


    @Test
    public void fillFriendFormWithSuccess() throws Exception {
        mvc.perform(get("/userHome/friend/add")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("relationEmail", "friend@test.com")
                .content("friend@test.com")
                .sessionAttr("dto", new UserRelation())
        )
                .andExpect(status().isOk())
                .andExpect(view().name("FriendAdd"));
    }

    @Test
    public void postNewFriendWithSuccess() throws Exception {
        mvc.perform(post("/userHome/friend/adding")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("email","friend2@test.com")
        )
        .andExpect(status().isFound())
        .andExpect(view().name("redirect:/userHome"));
        assertThat(relativeEmailRepository.findByUserAccount(userAccountRepository.findByEmail("test@test.com").get()))
                .hasSize(2);
    }

    @Test
    public void post2NewFriendWithSuccess() throws Exception {
        mvc.perform(post("/userHome/friend/adding")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("email","friend2@test.com")
        )
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/userHome"));
        assertThat(relativeEmailRepository.findByUserAccount(userAccountRepository.findByEmail("test@test.com").get())).hasSize(2);

        mvc.perform(post("/userHome/friend/adding")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("email","friend3@test.com")
        )
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/userHome"));
        assertThat(relativeEmailRepository.findByUserAccount(userAccountRepository.findByEmail("test@test.com").get())).hasSize(3);
        assertThat(relativeEmailRepository.findByUserAccountAndRelativeAccount(
                userAccountRepository.findByEmail("test@test.com").get(),
                userAccountRepository.findByEmail("friend2@test.com").get()))
                .isPresent();
    }
}
