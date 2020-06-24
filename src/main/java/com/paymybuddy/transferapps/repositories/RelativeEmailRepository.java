package com.paymybuddy.transferapps.repositories;

import com.paymybuddy.transferapps.domain.RelationEmail;
import com.paymybuddy.transferapps.domain.UserAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RelativeEmailRepository extends CrudRepository<RelationEmail, Long> {
    List<RelationEmail> findByUserAccount(UserAccount userAccount);
    Optional<RelationEmail> findByUserAccountAndRelativeEmail(UserAccount userAccount, String relativeEmail);
}
