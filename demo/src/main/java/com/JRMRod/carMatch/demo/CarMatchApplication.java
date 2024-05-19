package com.JRMRod.carMatch.demo;

import com.JRMRod.carMatch.demo.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CarMatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(CarMatchApplication.class, args);
	}
	
	@Override
	public void run(String... args)throws Exception{
		Principal principal = new Principal();
		principal.exibeMenu();
	}
}
