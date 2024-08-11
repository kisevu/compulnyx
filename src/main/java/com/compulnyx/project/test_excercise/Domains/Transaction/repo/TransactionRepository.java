package com.compulnyx.project.test_excercise.Domains.Transaction.repo;/*
*
@author ameda
@project test-excercise
*
*/

import com.compulnyx.project.test_excercise.Domains.Transaction.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Integer> {
    @Override
    Optional<Transaction> findById(Integer transactionId);
    
    Page<Transaction> findByAccountId(Integer accountId, Pageable pageable);
}
