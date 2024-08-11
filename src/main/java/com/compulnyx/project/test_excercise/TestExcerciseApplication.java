package com.compulnyx.project.test_excercise;

import com.compulnyx.project.test_excercise.Domains.role.Role;
import com.compulnyx.project.test_excercise.Domains.role.RoleRepository;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableAsync
public class TestExcerciseApplication{

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.configure().load();
		System.setProperty("SERVER_PORT",dotenv.get("SERVER_PORT"));
		System.setProperty("DATABASE_URL",dotenv.get("DATABASE_URL"));
		System.setProperty("USER_NAME",dotenv.get("USER_NAME"));
		System.setProperty("USER_PASSWORD",dotenv.get("USER_PASSWORD"));
		System.setProperty("URL_PATH",dotenv.get("URL_PATH"));
		System.setProperty("SECRET-KEY",dotenv.get("SECRET-KEY"));
		System.setProperty("EXPIRATION",dotenv.get("EXPIRATION"));
		SpringApplication.run(TestExcerciseApplication.class, args);
	}
//	@Bean
//	public CommandLineRunner run(RoleRepository roleRepository){
//		return args ->{
//			var role = Role.builder()
//					.name("USER")
//					.build();
//			roleRepository.save(role);
//		};
//	}
}
