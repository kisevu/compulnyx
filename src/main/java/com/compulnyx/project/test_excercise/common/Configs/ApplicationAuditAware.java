package com.compulnyx.project.test_excercise.common.Configs;/*
*
@author ameda
@project Books
*
*/

import com.compulnyx.project.test_excercise.Domains.customer.entity.Customer;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class ApplicationAuditAware implements AuditorAware<Integer> {

    @Override
    public Optional<Integer> getCurrentAuditor() {
        //getting current auditor from context holder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication==null || !authentication.isAuthenticated() || authentication instanceof
                AnonymousAuthenticationToken){
            //if auth obj = null which means the customer is not authenticated
            return Optional.empty();
        }
        Customer principal = (Customer) authentication.getPrincipal();
        return Optional.ofNullable(principal.getId());
    }
}
