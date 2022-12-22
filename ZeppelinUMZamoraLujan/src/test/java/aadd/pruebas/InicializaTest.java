package aadd.pruebas;


import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import aadd.persistencia.jpa.bean.CategoriaRestaurante;
import aadd.persistencia.jpa.bean.TipoUsuario;
import aadd.persistencia.jpa.dao.CategoriaRestauranteDAO;
import aadd.zeppelinum.ServicioGestionPlataforma;

class InicializaTest {
	
	@org.junit.jupiter.api.Test
	void init() {
		
		ServicioGestionPlataforma servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();
		LocalDate fechaNacimiento = LocalDate.of(1990, 1, 8);
		Integer usuario = servicio.registrarUsuario("ADMIN", "ADMIN", fechaNacimiento, "ADMIN@ADMIN.es",
				"1234", TipoUsuario.ADMIN);
		
		fechaNacimiento = LocalDate.of(1990, 1, 8);
		usuario = servicio.registrarUsuario("Mari", "Legaz", fechaNacimiento, "mclg@um.es", "12345",
				TipoUsuario.CLIENTE);
		
		fechaNacimiento = LocalDate.of(1990, 1, 8);
		usuario = servicio.registrarUsuario("Juan", "Perez", fechaNacimiento, "juanp@um.es", "12345",
				TipoUsuario.RIDER);
		
		fechaNacimiento = LocalDate.of(1990, 1, 8);
		usuario = servicio.registrarUsuario("Antonio", "Lopez", fechaNacimiento, "antl@um.es", "12345",
				TipoUsuario.RESTAURANTE);
		

		List<Integer> categorias = new ArrayList<Integer>();
		
		Integer cr1 = servicio.crearCategoriaRestaurante("Asiático");
		assertTrue(cr1 != null);
		Integer cr2 = servicio.crearCategoriaRestaurante("Italiano");
		assertTrue(cr2 != null);
		Integer cr3 = servicio.crearCategoriaRestaurante("Fast food");
		assertTrue(cr3 != null);
		
		categorias.add(cr1);
		categorias.add(cr2);
		categorias.add(cr3);
		
		Integer rest = servicio.registrarRestaurante("Puerta de Murcia", 1, "Rio Madera", "30110", null, "Murcia",
				38.009109654488476, -1.1339542029796663,categorias);
		Integer rest2 = servicio.registrarRestaurante("Pistatxo", 1, "Alfaro", "30001", 12, "Murcia", 37.98654993575417,
				-1.1305437741450695,categorias);
		Integer rest3 = servicio.registrarRestaurante("El Barrilero de Jose", 1, "Marqués de Espinardo", "30100", 4,
				"Murcia", 38.00805160364204, -1.152337749004084,categorias);
		assertTrue(rest != null);
		assertTrue(rest2 != null);
		assertTrue(rest3 != null);
		
	}
	

	@org.junit.jupiter.api.Test
	void crearCategoriaRestaurante() {
		ServicioGestionPlataforma servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();
		Integer cr1 = servicio.crearCategoriaRestaurante("Asiático");
		assertTrue(cr1 != null);
		Integer cr2 = servicio.crearCategoriaRestaurante("Italiano");
		assertTrue(cr2 != null);
		Integer cr3 = servicio.crearCategoriaRestaurante("Fast food");
		assertTrue(cr3 != null);

		Integer cr = servicio.crearCategoriaRestaurante("Asiático");
		assertTrue(cr != null);
		cr = servicio.crearCategoriaRestaurante("Italiano");
		assertTrue(cr != null);
		

	}

	
	@org.junit.jupiter.api.Test
	void crearRestaurante() {
		ServicioGestionPlataforma servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();
		
		List<Integer> categorias = new ArrayList<Integer>();
		Integer cr1 = servicio.crearCategoriaRestaurante("Frances");
		assertTrue(cr1 != null);
		Integer cr2 = servicio.crearCategoriaRestaurante("Español");
		assertTrue(cr2 != null);
		categorias.add(cr1);
		categorias.add(cr2);
		Integer rest3 = servicio.registrarRestaurante("El Barrilero de Jose", 1, "Marqués de Espinardo", "30100", 4,
				"Murcia", 38.00805160364204, -1.152337749004084,categorias);
		

	}
	
	
	@org.junit.jupiter.api.Test
	void a() {
		
		List<Integer> categoriasId = new ArrayList<Integer>(Arrays.asList(1,2,3)); 
		List<CategoriaRestaurante> cat =  CategoriaRestauranteDAO.getCategoriaRestauranteDAO().findByIds(categoriasId);
		if(cat==null) {
			
			System.out.println("cat es null --------------");
		}
		
	}
	
	
}
