package com.paymybuddy.transferapps.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "USERRELATION")
@EqualsAndHashCode(of = "id")
public class UserRelation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "useraccount_id", referencedColumnName="id")
    @NotNull
    private UserAccount userAccount;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "relativeaccount_id", referencedColumnName="id")
    @NotNull
    private UserAccount relativeAccount;
}
