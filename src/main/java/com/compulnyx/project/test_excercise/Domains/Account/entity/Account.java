package com.compulnyx.project.test_excercise.Domains.Account.entity;
/*
*
@author ameda
@project test-excercise
*
*/

import com.compulnyx.project.test_excercise.Domains.Transaction.entity.Transaction;
import com.compulnyx.project.test_excercise.common.DTO.BaseEntity;
import com.compulnyx.project.test_excercise.Domains.customer.entity.Customer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "accounts")
@EntityListeners(AuditingEntityListener.class)
public class Account extends BaseEntity {
    private Integer id;
    private BigDecimal accountBalance= new BigDecimal(0);
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @OneToMany(mappedBy = "account",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Transaction> transactions;
    @Enumerated(EnumType.STRING)
    private AccountType accountType;
    @Enumerated(EnumType.STRING)
    private CurrencyType currencyType;
    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;
}
