package net.charlie;

import java.util.LinkedList;
import java.util.List;
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
		//eliminar();
		//conteo();
		//eliminarTodo();
		//encontrarPorId();
		//buscarTodo();
		guardarTodo();
		
	}
	
	//guardar todo
	private void guardarTodo() {
		List<Categoria> categorias = getListaCategorias();
		repo.saveAll(categorias);
		
	}
	//Existe o no 
	private void existeId() {
		boolean existe = repo.existsById(7);
		System.out.println("la cateogria existe? " + existe);
	}
	//buscar todo
	private void buscarTodo() {
		Iterable<Categoria> categorias = repo.findAll();
		for (Categoria cat: categorias) {
			System.out.println(cat);
		}
	}
	//buscartodoporid
	private void encontrarPorId() {
		List<Integer> ids = new LinkedList<Integer>();
		ids.add(1);
		ids.add(4);
		ids.add(10);
		Iterable<Categoria> categorias= repo.findAllById(ids);
		for(Categoria cat: categorias) {
			System.out.println(cat);
		}
		
	}
	
	//borrarTodo
	private void eliminarTodo() {
		repo.deleteAll();
		
	}
	//Conteo
	private void conteo(){
		long cout = repo.count();
		System.out.println("Total cateogrias " + cout);
		
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
	
	private List<Categoria> getListaCategorias(){
		List<Categoria> list = new LinkedList<Categoria>();
		//categoria 1
		Categoria cat1 = new Categoria();
		cat1.setNombre("Programador de BlockChain");
		cat1.setDescripcion("Trabajos relacionados con cripto monedas");
		
		//categoria 2
		Categoria cat2 = new Categoria();
		cat2.setNombre("Soldado/Pintor");
		cat2.setDescripcion("Trabajos relacionados soldadura y pintura");
		
		//categoria 3
		Categoria cat3 = new Categoria();
		cat3.setNombre("Ingeniero industrial");
		cat3.setDescripcion("Trabajos relacionados con ingenieria industrial");
		
		list.add(cat1);
		list.add(cat2);
		list.add(cat3);
		
		return list;
		
	}

}
