package com.alurachallenge.literalura;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.alurachallenge.literalura.principal.Principal;
import com.alurachallenge.literalura.repository.LibroRespository;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner{
	@Autowired
	private LibroRespository respository;

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(respository);
		principal.iniciar();
	}

}
