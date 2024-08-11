package com.compulnyx.project.test_excercise.Domains.Account.service;
/*
*
@author ameda
@project test-excercise
*
*/


import com.compulnyx.project.test_excercise.Domains.Account.entity.Account;
import com.compulnyx.project.test_excercise.Domains.Account.entity.AccountType;
import com.compulnyx.project.test_excercise.Domains.Transaction.entity.Transaction;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {
    BigDecimal getAccountBalance(Integer accountId);
    BigDecimal deposit(Integer accountId, BigDecimal amount);
    BigDecimal withdraw(Integer accountId, BigDecimal amount);
    String  createAccount(AccountType accountType, Integer customerId);
    Account getAccountByCustomerId(Integer customerId);
    Transaction transactionByTransactionId(Integer transactionId);
    List<Transaction> getMiniStatement(Integer customerId);
}
