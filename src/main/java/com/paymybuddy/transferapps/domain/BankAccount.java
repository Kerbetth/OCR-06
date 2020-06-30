package com.paymybuddy.transferapps.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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

    @Column(unique = true, nullable = false, length = 50)
    private String accountIban;
    @Column(nullable = false, length = 15)
    private String accountName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "useraccount_id", referencedColumnName="id")
    @NotNull
    private UserAccount userAccount;

}
