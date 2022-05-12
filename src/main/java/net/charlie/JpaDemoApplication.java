package net.charlie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import net.charlie.repository.CategoriasRepository;

@SpringBootApplication
public class JpaDemoApplication implements CommandLineRunner {
	
	@Autowired
	private CategoriasRepository repo;
	//NO TOCAR
	public static void main(String[] args) {
		SpringApplication.run(JpaDemoApplication.class, args);	
	
	}
	
	@Override
	public void run(String... args) throws Exception {
		System.out.println(repo);
	}

}
