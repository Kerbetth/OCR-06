package com.paymybuddy.transferapps.unit;


import com.paymybuddy.transferapps.domain.UserRelation;
import com.paymybuddy.transferapps.domain.UserAccount;
import com.paymybuddy.transferapps.repositories.RelativeEmailRepository;
import com.paymybuddy.transferapps.repositories.UserAccountRepository;
import com.paymybuddy.transferapps.service.MyAppUserDetailsService;
import com.paymybuddy.transferapps.service.RelativeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class RelativeServiceTest {

    @Mock
    private MyAppUserDetailsService myAppUserDetailsService;
    @Mock
    private RelativeEmailRepository relativeEmailRepository;
    @Mock
    private UserAccountRepository userAccountRepository;
    @Captor
    private ArgumentCaptor<UserRelation> acUserAccount;


    private UserAccount userAccount;
    private UserAccount userAccount2;
    private UserRelation relationEmail;


    @InjectMocks
    RelativeService relativeService;

    @BeforeEach
    public void setup() {
        acUserAccount = ArgumentCaptor.forClass(UserRelation.class);
        userAccount = new UserAccount();
        userAccount.setEmail("test@Mock.com");
        userAccount.setName("John");
        userAccount2 = new UserAccount();
        userAccount2.setEmail("a@guy.com");
        userAccount2.setName("John2");
        Optional<UserAccount> userAccountOptional = Optional.of(userAccount2);
        List<UserRelation> relatives = new ArrayList<>();
        relatives.add(relationEmail);
        when(userAccountRepository.findByEmail(any())).thenReturn(userAccountOptional);
        when(relativeEmailRepository.findByUserAccount(any())).thenReturn(relatives);
        when(myAppUserDetailsService.currentUserAccount()).thenReturn(userAccount);
    }

    @Test
    public void returnGoodAmountOfMoneyDuringWithdrawFromBank() {
        //ARRANGE
        //ACT
        relativeService.addAFriend("a@guy.com");
        //ASSERT
        verify(relativeEmailRepository).save(acUserAccount.capture());
        UserRelation result = acUserAccount.getValue();
        assertThat(result.getRelativeAccount().getEmail()).isEqualTo("a@guy.com");
        assertThat(result.getUserAccount().getEmail()).isEqualTo("test@Mock.com");
    }

    @Test
    public void returnGoodNumberOfRelative() {
        //ARRANGE
        List<UserRelation> relationEmails = new ArrayList<>();
        relationEmails.add(relationEmail);
        relationEmail = new UserRelation();
        relationEmail.setRelativeAccount(userAccount);
        relationEmail.setId(40L);
        relationEmails.add(relationEmail);
        relationEmail = new UserRelation();
        relationEmail.setRelativeAccount(userAccount);
        relationEmail.setId(50L);
        relationEmails.add(relationEmail);
        when(relativeEmailRepository.findByUserAccount(any())).thenReturn(relationEmails);
        //ACT
        List<String> result =relativeService.getRelatives();
        //ASSERT
        assertThat(result).hasSize(3);
    }
}