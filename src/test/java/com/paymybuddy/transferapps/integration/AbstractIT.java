package com.paymybuddy.transferapps.integration;


import com.paymybuddy.transferapps.repositories.BankAccountRepository;
import com.paymybuddy.transferapps.repositories.RelativeEmailRepository;
import com.paymybuddy.transferapps.repositories.TransactionRepository;
import com.paymybuddy.transferapps.repositories.UserAccountRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;


@SpringBootTest
@WithMockUser(authorities = "ADMIN", username = "test@test.com")
@AutoConfigureMockMvc(addFilters = false)
@Sql({"test_data_init.sql"})
public abstract class AbstractIT {

    @Autowired
    ApplicationContext applicationContext;
    @Autowired
    protected UserAccountRepository userAccountRepository;
    @Autowired
    protected BankAccountRepository bankAccountRepository;
    @Autowired
    protected MockMvc mvc;
    @Autowired
    protected RelativeEmailRepository relativeEmailRepository;
    @Autowired
    protected TransactionRepository transactionRepository;

    @AfterEach
    void cleanup() {
        applicationContext.getBeansOfType(CrudRepository.class)
                .values()
                .forEach(CrudRepository::deleteAll);
    }
}
