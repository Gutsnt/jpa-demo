package net.charlie;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import net.charlie.model.Categoria;
import net.charlie.model.Perfil;
import net.charlie.model.Usuario;
import net.charlie.model.Vacante;
import net.charlie.repository.CategoriasRepository;
import net.charlie.repository.PerfilesRepository;
import net.charlie.repository.UsuariosRepository;
import net.charlie.repository.VacantesRepository;

@SpringBootApplication
public class JpaDemoApplication implements CommandLineRunner {
	
	@Autowired
	private CategoriasRepository repoCategorias;
	
	@Autowired
	private VacantesRepository repoVacantes;
	
	@Autowired
	private UsuariosRepository repoUsuarios;
	
	@Autowired
	private PerfilesRepository repoPerfiles;
	
	//NO TOCAR
	public static void main(String[] args) {
		SpringApplication.run(JpaDemoApplication.class, args);	
	
	}
	
	@Override
	public void run(String... args) throws Exception {
		buscarVacantesPorVariosEstatus();
	}
	
	// Query Method Buscar Vacantes por Varios Estatus(In)
	private void buscarVacantesPorVariosEstatus() {
		String[] estatus = new String[] {"Eliminada","Creada"};
		List<Vacante> list = repoVacantes.findByEstatusIn(estatus);
		System.out.println("Registros Encontrado "+ list.size());
		for(Vacante v: list){
			System.out.println(v.getId() + " " + v.getNombre()+": $"+ v.getSalario() + " " + v.getEstatus());
		}
	}
	
	// Query Method Buscar Vacantes por rango de sueldo(Between)
	private void buscarVacantesSalario() {
		List<Vacante> list = repoVacantes.findBySalarioBetweenOrderBySalarioDesc(7000,14000);
		System.out.println("Registros Encontrado "+ list.size());
		for(Vacante v: list){
			System.out.println(v.getId() + " " + v.getNombre()+": $"+ v.getSalario());
		}
	}
	
	// Query Method Buscar Vacantes por Destacado y Estados Ordenar por Id desc
	private void buscarVacantePorDestacadoEstatus() {
		List<Vacante> list = repoVacantes.findByDestacadoAndEstatusOrderByIdDesc(1, "Aprobada");
		System.out.println("Registros Encontrado "+ list.size());
		for(Vacante v: list){
			System.out.println(v.getId() + " " + v.getNombre()+" "+ v.getEstatus() + " " + v.getDestacada());
		}
	}
	// Query Method Buscar Vacante por Estatus
	private void buscarVacantePorEstatus(){
		List<Vacante> list = repoVacantes.findByEstatus("Eliminada");
		System.out.println("Registros Encontrado "+ list.size());
		for(Vacante v: list){
			System.out.println(v.getId() + " " + v.getNombre()+" "+ v.getEstatus());
		}
		
	}
	
	
	private void buscarUsuario(){
		Optional<Usuario> optional = repoUsuarios.findById(8);
		if(optional.isPresent()) {
			Usuario u = optional.get();
			System.out.println("Usuario " + u.getNombre());
			System.out.println("Perfiles asignados");
			for(Perfil p: u.getPerfiles()) {
				System.out.println(p.getPerfil());
			}
		}
		else {
			System.out.println("Usuario no encontrado");
		}
	}
	
	//crear usuario con dos perfil
	private void crearUsuarioConDosPerfil() {
		Usuario user = new Usuario();
		user.setNombre("Charlie Mendoza");
		user.setEmail("charlie.mendoza1337@gmail.com");
		user.setFechaRegistro(new Date());
		user.setUsername("Preacher");
		user.setPassword("0000");
		user.setEstatus(1);
		
		Perfil per1 = new Perfil();
		per1.setId(2);
		
		Perfil per2 = new Perfil();
		per2.setId(3);
		
		user.agregar(per1);
		user.agregar(per2);
		repoUsuarios.save(user);
	}
	
	//Metodo para crear perfiles
	private void crearPerfilesAplicacion() {
		repoPerfiles.saveAll(getPerfilesAplicacion());
	}
	
	//guardar vacante
	
	private void guardarVacante() {
		Vacante vacante1 = new Vacante();
		vacante1.setNombre("Profesor de Matematica");
		vacante1.setDescripcion("Se solicita profesor de matematica");
		vacante1.setFecha(new Date());
		vacante1.setSalario(7866.0);
		vacante1.setEstatus("Aprobada");
		vacante1.setDestacada(0);
		vacante1.setImage("escuela.png");
		vacante1.setDetalles("<h1>Los requisitos para profesor de matematica<h1>");
		Categoria cat = new Categoria();
		cat.setId(15);
		vacante1.setCategoria(cat);
		repoVacantes.save(vacante1);

	}
	
	
	private void buscarVacantes(){
		List<Vacante> lista = repoVacantes.findAll();
		for(Vacante v : lista) {
			System.out.println(v.getId()+ " " +v.getNombre() + "Categoria :" + v.getCategoria().getNombre());
		}
	}
	
	//buscar todos ordenados por campo
	private void buscarTodosOrdenados() {
		List<Categoria> categorias = repoCategorias.findAll(Sort.by("nombre").descending());
		for (Categoria cat: categorias) {
			System.out.println(cat.getId() + "  " +cat.getNombre());
		}
	}
		//paginacion
	private void buscarTodoPaginacion() {
		Page<Categoria> page = repoCategorias.findAll(PageRequest.of(1, 5));
		System.out.println("Total Registros " + page.getTotalElements());
		System.out.println("Total de Paginas " + page.getTotalPages());
		for (Categoria cat: page.getContent()) {
			System.out.println(cat.getId() + "  " + cat.getNombre());
		}
	}
	
	 //paginacion y ordenamiento
	private void buscarTodoPaginacionOrdenamiento() {
		Page<Categoria> page = repoCategorias.findAll(PageRequest.of(1, 5,Sort.by("nombre").descending()));
		System.out.println("Total Registros " + page.getTotalElements());
		System.out.println("Total de Paginas " + page.getTotalPages());
		for (Categoria cat: page.getContent()) {
			System.out.println(cat.getId() + "  " + cat.getNombre());
		}
	}
	//borrar todo en lote
	private void borrarTodoEnBloque() {
		repoCategorias.deleteAllInBatch();
	}
	
	//guardar todo
	private void guardarTodo() {
		List<Categoria> categorias = getListaCategorias();
		repoCategorias.saveAll(categorias);
		
	}
	//Existe o no 
	private void existeId() {
		boolean existe = repoCategorias.existsById(7);
		System.out.println("la cateogria existe? " + existe);
	}
	//buscar todo
	private void buscarTodo() {
		List<Categoria> categorias = repoCategorias.findAll();
		for (Categoria cat: categorias) {
			System.out.println(cat.getId() + "  " +cat.getNombre());
		}
	}
	//buscartodoporid
	private void encontrarPorId() {
		List<Integer> ids = new LinkedList<Integer>();
		ids.add(1);
		ids.add(4);
		ids.add(10);
		Iterable<Categoria> categorias= repoCategorias.findAllById(ids);
		for(Categoria cat: categorias) {
			System.out.println(cat);
		}
		
	}
	
	//borrarTodo
	private void eliminarTodo() {
		repoCategorias.deleteAll();
		
	}
	//Conteo
	private void conteo(){
		long cout = repoCategorias.count();
		System.out.println("Total cateogrias " + cout);
		
	}
	//Delete
	private void eliminar () {
		int idcategoria = 1;
		repoCategorias.deleteById(idcategoria);
	}
	
	
	
	//Update
	
	private void modificar() {
		Optional<Categoria> optional = repoCategorias.findById(1);
		if (optional.isPresent()) {
			Categoria catTmp = optional.get();
			catTmp.setNombre("Ingeniero de software");
			catTmp.setDescripcion("Desarollo de Sistemas");
			repoCategorias.save(catTmp);
			System.out.println(optional.get());
		}
		else 
			System.out.println("Categoria no encontrada");
		
	}
	
	//READ
	private void buscarPorId() {
		Optional<Categoria> optional = repoCategorias.findById(5);
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
		repoCategorias.save(cat);
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

		private List<Perfil> getPerfilesAplicacion(){
			List<Perfil> lista = new LinkedList<Perfil>();
			
			Perfil per1 = new Perfil();
			per1.setPerfil("SUPERVISOR");
			
			Perfil per2 = new Perfil();
			per2.setPerfil("ADMINISTRADOR");
			
			Perfil per3 = new Perfil();
			per3.setPerfil("USUARIO");
			
			lista.add(per1);
			lista.add(per2);
			lista.add(per3);
			
			return lista;
			
		}
}
