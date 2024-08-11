package com.compulnyx.project.test_excercise.Domains.customer.service.impl;
/*
*
@author ameda
@project test-excercise
*
*/

import com.compulnyx.project.test_excercise.Domains.Account.entity.Account;
import com.compulnyx.project.test_excercise.Domains.Account.entity.AccountType;
import com.compulnyx.project.test_excercise.Domains.Account.service.AccountService;
import com.compulnyx.project.test_excercise.Domains.Transaction.entity.Transaction;
import com.compulnyx.project.test_excercise.Domains.customer.entity.Customer;
import com.compulnyx.project.test_excercise.Domains.customer.repo.CustomerRepository;
import com.compulnyx.project.test_excercise.Domains.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final AccountService accountService;

    @Override
    public BigDecimal getCustomerBalance(Integer accountId) {
        return accountService.getAccountBalance(accountId);
    }

    @Override
    public List<Transaction> getMiniStatement(Integer customerId) {
        return accountService.getMiniStatement(customerId);
    }

    @Override
    public BigDecimal deposit(BigDecimal depositAmount, Integer customerId) {
        Account account = accountService.getAccountByCustomerId(customerId);
        return accountService.deposit(account.getId(),depositAmount);
    }

    @Override
    public BigDecimal withdraw(BigDecimal withDrawAmount, Integer customerId) {
        Account account = accountService.getAccountByCustomerId(customerId);
        return accountService.withdraw(account.getId(),withDrawAmount);
    }

    @Override
    public BigDecimal sendAmountFromXCustomerToYCustomer(Integer senderId, Integer receiverId, Double amountSent) {
        return null;
    }

    @Override
    public Customer findCustomerById(Integer customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(()->new RuntimeException("customer not found"));
    }

    @Override
    public String createAccount(AccountType accountType, Integer customerId) {
        return accountService.createAccount(accountType,customerId);
    }
}
