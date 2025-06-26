package com.ameda.compulnyx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan( basePackages = "com.ameda.compulnyx")
public class BusinessApplication {
    public static void main( String[] args ) {
        SpringApplication.run(BusinessApplication.class,args);
    }
}
