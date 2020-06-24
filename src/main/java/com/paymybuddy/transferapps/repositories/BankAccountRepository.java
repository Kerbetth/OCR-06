package com.paymybuddy.transferapps.repositories;

import com.paymybuddy.transferapps.domain.BankAccount;
import com.paymybuddy.transferapps.domain.UserAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BankAccountRepository extends CrudRepository<BankAccount, Long> {
    List<BankAccount> findByUserAccount(UserAccount userAccount);
    Optional<BankAccount> findByAccountIban(String iban);
}
