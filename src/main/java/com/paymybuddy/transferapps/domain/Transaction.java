package com.paymybuddy.transferapps.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "transaction")
public class Transaction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(nullable = false)
    private Boolean sendingOrReceiving;
    @Column
    private String description;
    @Column(nullable = false)
    private double amount;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "useraccount_email", referencedColumnName="email")
    private UserAccount userAccount;
    @Column(nullable = false)
    private String relativeEmail;
    @Column(nullable = false)
    private Timestamp date;
    @Column(nullable = false)
    private double perceiveAmountForApp;


    public Transaction(Long id, Boolean sendingOrReceiving, String description, double amount, UserAccount userAccount, String relativeEmail, Timestamp date, double perceiveAmountForApp) {
        this.id = id;
        this.sendingOrReceiving = sendingOrReceiving;
        this.description = description;
        this.amount = amount;
        this.userAccount = userAccount;
        this.relativeEmail = relativeEmail;
        this.date = date;
        this.perceiveAmountForApp = perceiveAmountForApp;
    }
}
