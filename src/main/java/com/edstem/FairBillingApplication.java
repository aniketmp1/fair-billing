package com.edstem;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.edstem.service.BillCalculator;

@SpringBootApplication
public class FairBillingApplication implements CommandLineRunner{

	@Autowired
	private BillCalculator calculator;
	
	public static void main(String[] args) throws IOException {
		SpringApplication.run(FairBillingApplication.class, args);		
	}

	@Override
	public void run(String... args) throws Exception {
		calculator.calculate(args[0]);
	}
	
	

}
