package com.paymybuddy.transferapps.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "relationEmail")
public class RelationEmail  implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "useraccount_email", referencedColumnName="email")
    private UserAccount userAccount;
    @Column(nullable = false)
    private String relativeEmail;
}
