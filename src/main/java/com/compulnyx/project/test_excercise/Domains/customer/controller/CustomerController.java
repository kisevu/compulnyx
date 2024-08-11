package com.compulnyx.project.test_excercise.Domains.customer.controller;

import com.compulnyx.project.test_excercise.Domains.Account.entity.AccountType;
import com.compulnyx.project.test_excercise.Domains.Transaction.entity.Transaction;
import com.compulnyx.project.test_excercise.Domains.customer.service.CustomerService;
import com.compulnyx.project.test_excercise.security.auth.DTO.SignUpRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/*
*
@author ameda
@project test-excercise
*
*/
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("finance/customer")
@Tag(name="Customer")
public class CustomerController {
    private final CustomerService customerService;
    
    @PostMapping("/createAccount")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> signUp(@RequestParam("accountType")AccountType accountType,
                                    @PathVariable("customerId") Integer customerId) throws MessagingException {
        customerService.createAccount(accountType,customerId);
        try {
            customerService.createAccount(accountType,customerId);
            log.info("You have successfully created, account: {}",accountType.name());
            return new ResponseEntity<>("successfully created an account",HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error encountered: {}",e.getMessage());
            return new ResponseEntity<>("an account already exists",HttpStatus.FOUND);
        }

    }

    @GetMapping("/balance")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getBalance(@PathVariable("accountId") Integer accountId){
        BigDecimal customerBalance = customerService.getCustomerBalance(accountId);
        log.info("You have a balance of: {}",customerBalance);
        return ResponseEntity.ok().body(customerBalance);
    }

    @PostMapping("/deposit")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> deposit(@PathVariable("depositAmount") BigDecimal depositAmount,
                                     @PathVariable("customerId") Integer customerId){
        BigDecimal deposit = customerService.deposit(depositAmount, customerId);
        return ResponseEntity.ok().body(deposit);
    }

    @PostMapping("/withdraw")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> accept(@PathVariable("depositAmount") BigDecimal depositAmount,
                                     @PathVariable("customerId") Integer customerId){
        BigDecimal withdraw = customerService.withdraw(depositAmount, customerId);
        return ResponseEntity.accepted().body(withdraw);
    }

    @GetMapping("/ministatement")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> miniStatement(@PathVariable("custometId") Integer customerId){
        List<Transaction> miniStatement = customerService.getMiniStatement(customerId);
        return ResponseEntity.ok().body(miniStatement);
    }

//    @PutMapping("/transfer-btn-accounts")
//    @ResponseStatus(HttpStatus.CREATED)
//    public ResponseEntity<?> transferBtnAccounts(@PathVariable("senderId") Integer senderId,
//                                                 @PathVariable("receiverId") Integer receiverId,
//                                                 ){
//        customerService.sendAmountFromXCustomerToYCustomer()
//    }

}
