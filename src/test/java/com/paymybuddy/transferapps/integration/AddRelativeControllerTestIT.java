package com.paymybuddy.transferapps.integration;


import com.paymybuddy.transferapps.domain.RelationEmail;
import com.paymybuddy.transferapps.domain.UserAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AddRelativeControllerTestIT extends AbstractIT{


    private UserAccount relationAccount2 = new UserAccount();
/*
    @BeforeEach
    public void setup() {
        relationAccount2.setEmail("friend2@test.com");
        relationAccount2.setName("user3");
        relationAccount2.setPassword("anotherPass");
        relationAccount2.setRole("USER");
        userAccountRepository.save(relationAccount2);
    }*/

    @Test
    public void fillFriendFormWithSuccess() throws Exception {
        mvc.perform(get("/userHome/friend/add")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("relativeEmail", "friend@test.com")
                .content("friend@test.com")
                .sessionAttr("dto", new RelationEmail())
        )
                .andExpect(status().isOk())
                .andExpect(view().name("FriendAdd"));
    }

    @Test
    public void postNewFriendWithSuccess() throws Exception {
        RelationEmail relationEmail = new RelationEmail();
        relationEmail.setRelativeEmail("friend2@test.com");
        mvc.perform(post("/userHome/friend/adding")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("relativeEmail",relationEmail.getRelativeEmail())
        )
        .andExpect(status().isFound())
        .andExpect(view().name("redirect:/userHome"));
        assertThat(relativeEmailRepository.findByUserAccount(userAccountRepository.findByEmail("test@test.com").get()))
                .hasSize(2);
    }

    @Test
    public void post2NewFriendWithSuccess() throws Exception {
        RelationEmail relationEmail = new RelationEmail();
        relationEmail.setRelativeEmail("friend2@test.com");
        mvc.perform(post("/userHome/friend/adding")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("relativeEmail",relationEmail.getRelativeEmail())
        )
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/userHome"));
        assertThat(relativeEmailRepository.findByUserAccount(userAccountRepository.findByEmail("test@test.com").get())).hasSize(2);

        relationEmail.setId(85L);
        relationEmail.setRelativeEmail("friend3@test.com");
        mvc.perform(post("/userHome/friend/adding")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("relativeEmail",relationEmail.getRelativeEmail())
        )
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/userHome"));
        assertThat(relativeEmailRepository.findByUserAccount(userAccountRepository.findByEmail("test@test.com").get())).hasSize(3);
        assertThat(relativeEmailRepository.findByUserAccountAndRelativeEmail(userAccountRepository.findByEmail("test@test.com").get(),"friend2@test.com")).isPresent();
        assertThat(relativeEmailRepository.findByUserAccountAndRelativeEmail(userAccountRepository.findByEmail("test@test.com").get(),"friend3@test.com")).isPresent();
    }
}
