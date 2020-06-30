package com.paymybuddy.transferapps.repositories;

import com.paymybuddy.transferapps.domain.UserRelation;
import com.paymybuddy.transferapps.domain.UserAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RelativeEmailRepository extends CrudRepository<UserRelation, Long> {
    List<UserRelation> findByUserAccount(UserAccount userAccount);
    Optional<UserRelation> findByUserAccountAndRelativeAccount(UserAccount userAccount, UserAccount relativeaccount);
}
