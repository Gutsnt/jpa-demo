package net.charlie;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import net.charlie.model.Categoria;
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
		//guardar();
		//buscarPorId();
		//modificar();
		eliminar();
	}
	
	//Delete
	private void eliminar () {
		int idcategoria = 1;
		repo.deleteById(idcategoria);
	}
	
	
	
	//Update
	
	private void modificar() {
		Optional<Categoria> optional = repo.findById(1);
		if (optional.isPresent()) {
			Categoria catTmp = optional.get();
			catTmp.setNombre("Ingeniero de software");
			catTmp.setDescripcion("Desarollo de Sistemas");
			repo.save(catTmp);
			System.out.println(optional.get());
		}
		else 
			System.out.println("Categoria no encontrada");
		
	}
	
	//READ
	private void buscarPorId() {
		Optional<Categoria> optional = repo.findById(5);
		if (optional.isPresent())
			System.out.println(optional.get());
		else 
			System.out.println("Categoria no encontrada");
		
	}
	//CREATE
	private void guardar() {
		Categoria cat = new Categoria();
		cat.setNombre("Finanzas");
		cat.setDescripcion("Trabajos relacionados con finanzas");
		repo.save(cat);
		System.out.println(cat);
	}

}
