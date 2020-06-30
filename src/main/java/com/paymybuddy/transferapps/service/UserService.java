package com.paymybuddy.transferapps.service;

import com.paymybuddy.transferapps.domain.*;
import com.paymybuddy.transferapps.dto.CreateAccount;
import com.paymybuddy.transferapps.repositories.UserAccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Random;



@Service
@Slf4j
public class UserService {

    @Autowired
    protected UserAccountRepository userAccountRepository;
    @Autowired
    private MyAppUserDetailsService myAppUserDetailsService;

    /**
     * createAccount() method create a new user account with encrypted password and save it in database
     */
    public void createAnAccount(CreateAccount createAccount) {
        if (createAccount.getPassword().equals(createAccount.getConfirmPassword())) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
            userAccountRepository.save(new UserAccount(
                    new Random().nextLong(),
                    createAccount.getEmail(),
                    createAccount.getName(), 0.0,
                    "USER",
                    encoder.encode(createAccount.getPassword()
                    )));
        } else {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "The two entries for the password don't match each other");
        }
    }

    /**
     * getAccountInfo() method retrieve name and email from the user
     */
    public UserAccount getAccountInfo() {
        return userAccountRepository.findByEmail(
                myAppUserDetailsService.currentUserAccount().getEmail()).get();
    }
}