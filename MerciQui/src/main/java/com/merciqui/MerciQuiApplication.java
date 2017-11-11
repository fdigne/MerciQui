package com.merciqui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.merciqui.dao.ComedienRepository;
import com.merciqui.entities.Comedien;

@SpringBootApplication
public class MerciQuiApplication implements CommandLineRunner{
	@Autowired
	private ComedienRepository comRep ;
	
	public static void main(String[] args) {
		SpringApplication.run(MerciQuiApplication.class, args);
	}
	
	@Override
	public void run(String... arg0) throws Exception {
		
	}
}
