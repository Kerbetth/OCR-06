package com.paymybuddy.transferapps.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "BANKACCOUNT")
@EqualsAndHashCode(of = "id")
public class BankAccount implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String accountIban;
    @Column(nullable = false)
    private String accountName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "useraccount_id", referencedColumnName="id")
    private UserAccount userAccount;

}
