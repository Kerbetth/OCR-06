package com.paymybuddy.transferapps.unit.controller;

import com.paymybuddy.transferapps.controllers.AddfriendControllers;
import com.paymybuddy.transferapps.domain.UserRelation;
import com.paymybuddy.transferapps.dto.Relation;
import com.paymybuddy.transferapps.service.RelativeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.ui.Model;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class AddFriendControllerTest {

    @Mock
    private RelativeService relativeService;
    @Mock
    private Model model;

    @InjectMocks
    AddfriendControllers addfriendControllers = new AddfriendControllers();

    @Test
    public void fillFriendFormWithSuccess() {

        String result = addfriendControllers.addAFriendToYourList(model);
        verify(model, (times(1))).addAttribute(eq("relative"), any());

        assertThat(result).isEqualTo("FriendAdd");
    }

    @Test
    public void postNewFriendWithSuccess() {
        when(relativeService.addAFriend(any())).thenReturn(true);
        Relation relation = new Relation();
        relation.setEmail("friend@test.com");
        String result = addfriendControllers.addingAFriend(relation);

        assertThat(result).isEqualTo("redirect:/userHome");
    }
}
