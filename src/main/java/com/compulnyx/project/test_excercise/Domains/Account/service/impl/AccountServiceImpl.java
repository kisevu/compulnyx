package com.compulnyx.project.test_excercise.Domains.Account.service.impl;
/*
*
@author ameda
@project test-excercise
*
*/

import com.compulnyx.project.test_excercise.Domains.Account.entity.Account;
import com.compulnyx.project.test_excercise.Domains.Account.entity.AccountStatus;
import com.compulnyx.project.test_excercise.Domains.Account.entity.AccountType;
import com.compulnyx.project.test_excercise.Domains.Account.entity.CurrencyType;
import com.compulnyx.project.test_excercise.Domains.Account.repo.AccountRepository;
import com.compulnyx.project.test_excercise.Domains.Account.service.AccountService;
import com.compulnyx.project.test_excercise.Domains.Transaction.entity.Transaction;
import com.compulnyx.project.test_excercise.Domains.Transaction.entity.TransactionType;
import com.compulnyx.project.test_excercise.Domains.Transaction.entity.TransferStatus;
import com.compulnyx.project.test_excercise.Domains.Transaction.repo.TransactionRepository;
import com.compulnyx.project.test_excercise.Domains.customer.entity.Customer;
import com.compulnyx.project.test_excercise.Domains.customer.repo.CustomerRepository;
import com.compulnyx.project.test_excercise.Domains.customer.service.CustomerService;
import com.compulnyx.project.test_excercise.common.exceptions.CustomerNotFoundException;
import com.compulnyx.project.test_excercise.common.exceptions.TransactionNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {
    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    @Override
    public BigDecimal getAccountBalance(Integer accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(()-> new RuntimeException("Account could not be found"));
        return accountRepository.save(account).getAccountBalance();
    }

    @Override
    @Transactional
    @Modifying
    public BigDecimal deposit(Integer accountId, BigDecimal amount) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }

        account.setAccountBalance(account.getAccountBalance().add(amount));
        accountRepository.save(account);

        Transaction transaction = Transaction.builder()
                .amount(amount)
                .transactionType(TransactionType.WITHDRAWAL)
                .createdDate(LocalDateTime.now())
                .sourceAccountId(account.getId())
                .transferStatus(TransferStatus.COMPLETED)
                .currencyType(account.getCurrencyType())
                .build();

        transactionRepository.save(transaction);
        return account.getAccountBalance();
    }

    @Override
    @Transactional
    @Modifying
    public BigDecimal withdraw(Integer accountId, BigDecimal amount) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }

        if (account.getAccountBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        account.setAccountBalance(account.getAccountBalance().subtract(amount));
        accountRepository.save(account);

        Transaction transaction = Transaction.builder()
                .amount(amount)
                .transactionType(TransactionType.WITHDRAWAL)
                .createdDate(LocalDateTime.now())
                .sourceAccountId(account.getId())
                .transferStatus(TransferStatus.COMPLETED)
                .currencyType(account.getCurrencyType())
                .build();
        transactionRepository.save(transaction);
        return account.getAccountBalance();
    }

    @Override
    @Transactional
    @Modifying
    public String createAccount(AccountType accountType, Integer customerId) {
        Customer found  = customerRepository.findById(customerId)
                .orElseThrow(()-> new CustomerNotFoundException("customer with given id could be found"));
        Account accountFound = accountRepository.findByCustomer(found);

        if(accountFound!=null){
            return "found";
        }

        Account account = Account.builder()
                .customer(found)
                .transactions(Set.of())
                .accountType(accountType)
                .currencyType(CurrencyType.KES)
                .accountStatus(AccountStatus.ACTIVE)
                .build();
        return "created";
    }

    @Override
    public Account getAccountByCustomerId(Integer customerId) {
        Customer customer  = customerRepository.findById(customerId)
                .orElseThrow(()-> new CustomerNotFoundException("customer with given id could be found"));
           return accountRepository.findByCustomer(customer);
    }

    @Override
    public Transaction transactionByTransactionId(Integer transactionId) {
        return transactionRepository.findById(transactionId)
                .orElseThrow(()-> new TransactionNotFoundException("Transaction with passed could not be found."));
    }

    @Override
    public List<Transaction> getMiniStatement(Integer customerId) {
        Customer customer  = customerRepository.findById(customerId)
                .orElseThrow(()-> new CustomerNotFoundException("customer with given id could be found"));
        Account account = accountRepository.findByCustomer(customer);
        List<Transaction> transactions = getLatestTransactions(account.getId(),1,10);
        return transactions;
    }
    private List<Transaction> getLatestTransactions(Integer accountId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Transaction> transactionPage = transactionRepository.findByAccountId(accountId, pageable);
        return transactionPage.getContent();
    }

    @Transactional
    public void transferFunds(Integer sourceAccountId, Integer destinationAccountId, BigDecimal amount) {
        // Validate amount
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Transfer amount must be positive");
        }

        // Retrieve accounts
        Account sourceAccount = accountRepository.findById(sourceAccountId)
                .orElseThrow(() -> new RuntimeException("Source account not found"));

        Account destinationAccount = accountRepository.findById(destinationAccountId)
                .orElseThrow(() -> new RuntimeException("Destination account not found"));

        // Check for sufficient balance in source account
        if (sourceAccount.getAccountBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance in source account");
        }

        // Deduct amount from source account
        sourceAccount.setAccountBalance(sourceAccount.getAccountBalance().subtract(amount));
        accountRepository.save(sourceAccount);

        // Add amount to destination account
        destinationAccount.setAccountBalance(destinationAccount.getAccountBalance().add(amount));
        accountRepository.save(destinationAccount);

        // Record transaction for source account
        Transaction sourceTransaction = Transaction.builder()
                .amount(amount)
                .transactionType(TransactionType.TRANSFER)
                .createdDate(LocalDateTime.now())
                .sourceAccountId(sourceAccount.getId())
                .destinationAccountId(destinationAccount.getId())
                .transferStatus(TransferStatus.COMPLETED)
                .currencyType(destinationAccount.getCurrencyType())
                .build();


        // Record transaction for destination account
        Transaction destinationTransaction = Transaction.builder()
                .amount(amount)
                .transactionType(TransactionType.TRANSFER)
                .createdDate(LocalDateTime.now())
                .sourceAccountId(sourceAccount.getId())
                .destinationAccountId(destinationAccount.getId())
                .transferStatus(TransferStatus.COMPLETED)
                .currencyType(destinationAccount.getCurrencyType())
                .build();
        transactionRepository.save(destinationTransaction);
    }
}
