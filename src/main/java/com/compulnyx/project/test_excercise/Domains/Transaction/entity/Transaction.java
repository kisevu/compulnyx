package com.compulnyx.project.test_excercise.Domains.Transaction.entity;
/*
*
@author ameda
@project test-excercise
*
*/

import com.compulnyx.project.test_excercise.Domains.Account.entity.Account;
import com.compulnyx.project.test_excercise.Domains.Account.entity.CurrencyType;
import com.compulnyx.project.test_excercise.common.DTO.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "transactions")
@EntityListeners(AuditingEntityListener.class)
public class Transaction extends BaseEntity {
    private Integer id;
    private BigDecimal amount;
    private BigDecimal deposit;
    private BigDecimal withdrawal;
    private String accountNumber = UUID.randomUUID().toString();

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    private TransactionType transactionType;
    private Integer sourceAccountId;
    private Integer destinationAccountId;
    private String description;
    private TransferStatus transferStatus;
    private CurrencyType currencyType;
    private ReferenceTransaction refNo;
}
