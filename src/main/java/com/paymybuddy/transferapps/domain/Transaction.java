package com.paymybuddy.transferapps.domain;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
    @Column(nullable = false, columnDefinition="Tinyint")
    private Boolean isReceiving;
    @Column
    private String description;
    @Column(nullable = false, columnDefinition="Decimal(6,2)")
    private double amount;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "useraccount_id", referencedColumnName="id")
    @NotNull
    private UserAccount userAccount;
    @Column(nullable = false, length = 20)
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
