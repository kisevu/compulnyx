package com.compulnyx.project.test_excercise.Domains.customer.service;
/*
*
@author ameda
@project test-excercise
*
*/

import com.compulnyx.project.test_excercise.Domains.Account.entity.AccountType;
import com.compulnyx.project.test_excercise.Domains.Transaction.entity.Transaction;
import com.compulnyx.project.test_excercise.Domains.customer.entity.Customer;

import java.math.BigDecimal;
import java.util.List;

public interface CustomerService {
    BigDecimal getCustomerBalance(Integer accountId);
    List<Transaction> getMiniStatement(Integer customerId);
    BigDecimal deposit(BigDecimal depositAmount,Integer customerId); //deposit and returns customer's balance
    BigDecimal withdraw(BigDecimal withDrawAmount, Integer customerId); // withdraw and return balance
    BigDecimal sendAmountFromXCustomerToYCustomer(Integer senderId, Integer receiverId, Double amountSent);
    Customer findCustomerById(Integer customerId);
    String  createAccount(AccountType accountType, Integer customerId);
}
