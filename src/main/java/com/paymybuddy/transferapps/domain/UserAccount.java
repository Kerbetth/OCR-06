package com.paymybuddy.transferapps.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "USERACCOUNT")
@EqualsAndHashCode(of = "id")
public class UserAccount implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private double moneyAmount;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String role;

    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, mappedBy = "userAccount")
    private Set<BankAccount> bankAccounts;

    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, mappedBy = "userAccount")
    private Set<UserRelation> relationEmails;


    public UserAccount(long id, String email, String name, Double moneyAmount, String role, String password) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.moneyAmount = moneyAmount;
        this.role = role;
        this.password = password;
    }
}
