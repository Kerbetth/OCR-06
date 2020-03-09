package com.paymybuddy.transferapps.service;

import com.paymybuddy.transferapps.constants.DBMysSqlQuery;
import com.paymybuddy.transferapps.domain.BankAccount;
import com.paymybuddy.transferapps.domain.UserAccount;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;

import java.util.List;


public interface BankAccountRepository extends CrudRepository<BankAccount, Long> {
    @Query(value = DBMysSqlQuery.GET_ACCOUNT_INFO, nativeQuery = true)
    List<BankAccount> findByEmail(String email);
}