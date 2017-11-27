package com.merciqui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.merciqui.dao.ComedienRepository;
import com.merciqui.entities.Comedien;

@EnableAutoConfiguration
@SpringBootApplication
public class MerciQuiApplication implements CommandLineRunner{
	
	
	
	public static void main(String[] args) {
		SpringApplication.run(MerciQuiApplication.class, args);
	}
	
	@Override
	public void run(String... arg0) throws Exception {
		
	}
}
