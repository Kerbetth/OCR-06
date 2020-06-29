package com.paymybuddy.transferapps.domain;


import lombok.EqualsAndHashCode;
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
@Table(name = "TRANSACTION")
@EqualsAndHashCode(of = "id")
public class Transaction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Boolean isReceiving;
    @Column
    private String description;
    @Column(nullable = false)
    private double amount;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "useraccount_id", referencedColumnName="id")
    private UserAccount userAccount;
    @Column(nullable = false)
    private String target;
    @Column(nullable = false)
    private Timestamp date;
    @Column(nullable = false)
    private double perceiveAmountForApp;


    public Transaction(Boolean isReceiving, String description, double amount, UserAccount userAccount, String target, Timestamp date, double perceiveAmountForApp) {
        this.isReceiving = isReceiving;
        this.description = description;
        this.amount = amount;
        this.userAccount = userAccount;
        this.target = target;
        this.date = date;
        this.perceiveAmountForApp = perceiveAmountForApp;
    }
}
