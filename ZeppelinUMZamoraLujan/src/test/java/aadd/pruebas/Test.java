package aadd.pruebas;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;

import aadd.persistencia.dto.RestauranteDTO;
import aadd.persistencia.dto.UsuarioDTO;
import aadd.persistencia.jpa.EntityManagerHelper;
import aadd.persistencia.jpa.bean.CategoriaRestaurante;
import aadd.persistencia.jpa.bean.Incidencia;
import aadd.persistencia.jpa.bean.Restaurante;
import aadd.persistencia.jpa.bean.TipoUsuario;
import aadd.persistencia.jpa.bean.Usuario;
import aadd.persistencia.jpa.dao.CategoriaRestauranteDAO;
import aadd.persistencia.jpa.dao.IncidenciaDAO;
import aadd.persistencia.jpa.dao.RestauranteDAO;
import aadd.persistencia.jpa.dao.UsuarioDAO;
import aadd.zeppelinum.ServicioGestionPedido;
import aadd.zeppelinum.ServicioGestionPlataforma;

class Test {
	@org.junit.jupiter.api.Test
	void crearUsuario() {
		ServicioGestionPlataforma servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();
		LocalDate fechaNacimiento = LocalDate.of(1990, 1, 8);
		Integer usuario = servicio.registrarUsuario("ADMIN", "ADMIN", fechaNacimiento, "ADMIN@ADMIN.es",
				"1234", TipoUsuario.ADMIN);
		assertTrue(usuario != null);
	}

	@org.junit.jupiter.api.Test
	void validarUsuario() {
		ServicioGestionPlataforma servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();
		boolean exito = servicio.validarUsuario(1);
		assertTrue(exito);
	}

	@org.junit.jupiter.api.Test
	void crearRestaurantePlato() {
		ServicioGestionPlataforma servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();

		Integer rest = servicio.registrarRestaurante("Puerta de Murcia", 1, "Rio Madera", "30110", null, "Murcia",
				38.009109654488476, -1.1339542029796663, Arrays.asList(1));
		Integer rest2 = servicio.registrarRestaurante("Pistatxo", 1, "Alfaro", "30001", 12, "Murcia", 37.98654993575417,
				-1.1305437741450695, Arrays.asList(3));
		Integer rest3 = servicio.registrarRestaurante("El Barrilero de Jose", 1, "Marqués de Espinardo", "30100", 4,
				"Murcia", 38.00805160364204, -1.152337749004084,Arrays.asList(1,2));
		assertTrue(rest != null);
		assertTrue(rest2 != null);
		assertTrue(rest3 != null);
		boolean exito = servicio.nuevoPlato("Marmitako de bonito", "plato de bonito, patatas y cebolla con verduras",
				20d, rest);
		assertTrue(exito);
	}

	@org.junit.jupiter.api.Test
	public void loginTest() {
		ServicioGestionPlataforma servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();
		assertTrue(servicio.login("periquita@palotes.es", "12345") != null);
		assertFalse(servicio.login("mdclg3@um.es", "loquesea") != null);
		assertFalse(servicio.login("periquita@palotes.es", "123456") != null);
	}

	@org.junit.jupiter.api.Test
	public void checkUsuarioTest() {
		ServicioGestionPlataforma servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();
		assertTrue(servicio.isUsuarioRegistrado("periquita@palotes.es"));
		assertFalse(servicio.isUsuarioRegistrado("mdclg3@um.es"));
	}

	@org.junit.jupiter.api.Test
	void crearPlato() {
		ServicioGestionPlataforma servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();
		boolean exito = servicio.nuevoPlato("Plato no disponible", "plato que voy a cambiar a no disponible", 20d, 1);
		assertTrue(exito);
	}

	@org.junit.jupiter.api.Test
	public void platosByRestaurante() {
		ServicioGestionPlataforma servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();
		assertTrue(servicio.getMenuByRestaurante(1).size() > 1);
	}

	@org.junit.jupiter.api.Test
	public void buscarRestaurantes() {
		ServicioGestionPlataforma servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();
		// TODO he puesto > o = a 1 porque si se ejecutan varas veces los tes fallara

		assertTrue(servicio.getRestaurantesByFiltros("Pist", true, true, true).size() >= 1);
		assertTrue(servicio.getRestaurantesByFiltros("venta", true, true, true).size() == 0);
	}

	@org.junit.jupiter.api.Test
	void buscarRestaurantesOrdenados() {
		ServicioGestionPlataforma servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();

		List<RestauranteDTO> restaurantes = servicio.getRestaurantesByCercanía(38.02410905700919, -1.1740641907325182,
				20, 0);
		for (RestauranteDTO r : restaurantes) {
			System.out.println(r.getNombre());
		}
		assertTrue(restaurantes.size() > 0);
	}

	@org.junit.jupiter.api.Test
	void crearUsuario2() {
		ServicioGestionPlataforma servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();
		LocalDate fechaNacimiento = LocalDate.of(1990, 1, 8);
		Integer usuario = servicio.registrarUsuario("Mari", "Legaz", fechaNacimiento, "mclg@um.es", "12345",
				TipoUsuario.CLIENTE);
		assertTrue(usuario != null);
	}

	@org.junit.jupiter.api.Test
	void crearOpinion() {
		ServicioGestionPedido servicio = ServicioGestionPedido.getServicioGestionPedido();
		servicio.opinar(2, 2, "Todo estupendo y muy rico", 10d);
		servicio.opinar(2, 1, "La comida llegó un poco fría", 7.5d);
		servicio.opinar(2, 3, "El menú es un poco escaso, pero todo muy bueno", 8d);
		servicio.opinar(2, 3, "Nos trajeron un plato cambiado", 5d);
		servicio.opinar(2, 4, "Siempre repetimos", 10d);
	}

	@org.junit.jupiter.api.Test
	void buscarOpiniones() {
		ServicioGestionPedido servicio = ServicioGestionPedido.getServicioGestionPedido();
		assertTrue(servicio.findByUsuario(2).size() == 15);
		assertTrue(servicio.findByRestaurante(3).size() == 6);
	}

	@org.junit.jupiter.api.Test
	void hacerpedido() {
		ServicioGestionPedido servicio = ServicioGestionPedido.getServicioGestionPedido();
		servicio.crearPedido();
	}

	@org.junit.jupiter.api.Test
	void crearCategoriaRestaurante() {
		ServicioGestionPlataforma servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();
		Integer cr = servicio.crearCategoriaRestaurante("Asiático");
		assertTrue(cr != null);
		cr = servicio.crearCategoriaRestaurante("Italiano");
		assertTrue(cr != null);
		cr = servicio.crearCategoriaRestaurante("Fast food");
		assertTrue(cr != null);

	}

	@org.junit.jupiter.api.Test
	void addCategoriaARestaurante() {
		ServicioGestionPlataforma servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();
		assertTrue(servicio.addCategoriaARestaurante(1, 1));
		assertFalse(servicio.addCategoriaARestaurante(1, 9));
		assertTrue(servicio.addCategoriaARestaurante(3, 1));

	}

	@org.junit.jupiter.api.Test
	void cambiarDisponibilidadPlato() {
		ServicioGestionPlataforma servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();
		assertTrue(servicio.cambiarDispPlato(1, false));

	}

	@org.junit.jupiter.api.Test
	void crearIncidencia() {
		ServicioGestionPlataforma servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();
		
		assertTrue(servicio.crearIncidencia("No ha llegado el pedido", 1, 1) != null);
		assertTrue(servicio.crearIncidencia("Envio de la comida incorrecta", 1, 1) != null);

		

	}
	@org.junit.jupiter.api.Test
	void findIncidenciasByUsuario() {
		ServicioGestionPlataforma servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();
		
		
		assertTrue(servicio.getIncidenciasByUsuario(2).isEmpty());
		assertFalse(servicio.getIncidenciasByUsuario(1).isEmpty());
		assertTrue(servicio.getIncidenciasByUsuario(1).size()==2);
		
	}
	
	
	
	@org.junit.jupiter.api.Test
	void findIncidenciasNoCerradas() {
		ServicioGestionPlataforma servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();
		
		assertFalse(servicio.getIncidenciasNoCerradas().isEmpty());
		assertTrue(servicio.getIncidenciasNoCerradas().size()==2);

		

	}
	@org.junit.jupiter.api.Test
	void findUsuarioRestNoValidados() {
		
		ServicioGestionPlataforma servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();
		
		assertFalse(servicio.getUsRestauranteNoVal().isEmpty());
		for (UsuarioDTO u : servicio.getUsRestauranteNoVal()) {
			
			System.out.println(u.getNombre());
		}
		
		
	}


}
