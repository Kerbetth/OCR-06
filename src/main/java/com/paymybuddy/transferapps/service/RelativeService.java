package com.paymybuddy.transferapps.service;


import com.paymybuddy.transferapps.domain.RelationEmail;
import com.paymybuddy.transferapps.repositories.RelativeEmailRepository;
import com.paymybuddy.transferapps.repositories.UserAccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Slf4j
public class RelativeService {

    /**
     * -addAFriend(relationEmail) links two userAccount in order to allow both to send money each other
     * -getRelatives() git a list of emails corresponding to all UserAccount linked to the current UserAccount
     */

    @Autowired
    private RelativeEmailRepository relativeEmailRepository;
    @Autowired
    protected UserAccountRepository userAccountRepository;
    @Autowired
    private MyAppUserDetailsService myAppUserDetailsService;


    public boolean addAFriend(RelationEmail relationEmail) {
        if (!userAccountRepository.findByEmail(relationEmail.getRelativeEmail()).isEmpty()) {
            relationEmail.setUserAccount(myAppUserDetailsService.currentUserAccount());
            Optional<RelationEmail> existingRelation =
                    relativeEmailRepository.findByUserAccountAndRelativeEmail(
                            myAppUserDetailsService.currentUserAccount(),
                            relationEmail.getRelativeEmail());
            if (existingRelation.isEmpty()) {
                relativeEmailRepository.save(relationEmail);
                return true;
            } else {
                log.error("The email is already added to your friendlist");
                return false;
            }
        } else {
            log.error("The email "+relationEmail.getRelativeEmail()+" is not recorded in the database");
            return false;
        }
    }

    public List<String> getRelatives() {
        List<String> relativeList = new ArrayList<>();
        for (RelationEmail relative : relativeEmailRepository.findByUserAccount(myAppUserDetailsService.currentUserAccount())) {
            relativeList.add(relative.getRelativeEmail());
        }
        return relativeList;
    }
}