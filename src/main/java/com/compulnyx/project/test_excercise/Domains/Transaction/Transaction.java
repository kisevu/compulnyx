package com.compulnyx.project.test_excercise.Domains.Transaction;
/*
*
@author ameda
@project test-excercise
*
*/

import com.compulnyx.project.test_excercise.Domains.Account.entity.Account;
import com.compulnyx.project.test_excercise.common.DTO.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
    private Double accountBalance=0.0;
    private Double deposit;
    private Double withdrawal;
    private String accountNumber;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
}
