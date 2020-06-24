package com.paymybuddy.transferapps.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "bankAccount")
public class BankAccount implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(unique = true, nullable = false)
    private String accountIban;
    @Column(nullable = false)
    private String accountName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "useraccount_email", referencedColumnName="email")
    private UserAccount userAccount;

}
