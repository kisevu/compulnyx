package com.compulnyx.project.test_excercise.Domains.Account.repo;/*
*
@author ameda
@project test-excercise
*
*/

import com.compulnyx.project.test_excercise.Domains.Account.entity.Account;
import com.compulnyx.project.test_excercise.Domains.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account,Integer> {
    Account findByCustomer(Customer customer);
}
