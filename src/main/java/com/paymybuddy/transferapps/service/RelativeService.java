package com.paymybuddy.transferapps.service;


import com.paymybuddy.transferapps.domain.UserRelation;
import com.paymybuddy.transferapps.repositories.RelativeEmailRepository;
import com.paymybuddy.transferapps.repositories.UserAccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Slf4j
public class RelativeService {



    @Autowired
    private RelativeEmailRepository relativeEmailRepository;
    @Autowired
    protected UserAccountRepository userAccountRepository;
    @Autowired
    private MyAppUserDetailsService myAppUserDetailsService;

    /**
     * -addAFriend(relationEmail) links two userAccount in order to allow both to send money each other
     */
    public boolean addAFriend(String relationEmail) {
        UserRelation userRelation = new UserRelation();
        if (!userAccountRepository.findByEmail(relationEmail).isEmpty()) {
            userRelation.setRelativeAccount(userAccountRepository.findByEmail(relationEmail).get());
            userRelation.setUserAccount(myAppUserDetailsService.currentUserAccount());
            Optional<UserRelation> existingRelation =
                    relativeEmailRepository.findByUserAccountAndRelativeAccount(
                            myAppUserDetailsService.currentUserAccount(),
                            userRelation.getRelativeAccount());
            if (existingRelation.isEmpty()) {
                relativeEmailRepository.save(userRelation);
                return true;
            } else {
                throw new ResponseStatusException(HttpStatus.CONFLICT,
                        "The email is already added to your friendlist");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT,
                    "The email "+relationEmail+" is not recorded in the database");
        }
    }

    /**
     * -getRelatives() git a list of emails corresponding to all UserAccount linked to the current UserAccount
     */
    public List<String> getRelatives() {
        List<String> relativeList = new ArrayList<>();
        for (UserRelation relative : relativeEmailRepository.findByUserAccount(myAppUserDetailsService.currentUserAccount())) {
            relativeList.add(relative.getRelativeAccount().getEmail());
        }
        return relativeList;
    }
}