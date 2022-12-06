package aadd.zeppelinum;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;
import javax.persistence.EntityManager;

import com.mongodb.client.model.geojson.Point;
import com.mongodb.client.model.geojson.Position;

import aadd.persistencia.dto.EstadisticaOpinionDTO;
import aadd.persistencia.dto.PlatoDTO;
import aadd.persistencia.dto.RestauranteDTO;
import aadd.persistencia.dto.UsuarioDTO;
import aadd.persistencia.jpa.EntityManagerHelper;
import aadd.persistencia.jpa.bean.CategoriaRestaurante;
import aadd.persistencia.jpa.bean.Plato;
import aadd.persistencia.jpa.bean.Restaurante;
import aadd.persistencia.jpa.bean.TipoUsuario;
import aadd.persistencia.jpa.bean.Usuario;
import aadd.persistencia.jpa.dao.CategoriaRestauranteDAO;
import aadd.persistencia.jpa.dao.PlatoDAO;
import aadd.persistencia.jpa.dao.RestauranteDAO;
import aadd.persistencia.jpa.dao.UsuarioDAO;
import aadd.persistencia.mongo.bean.Direccion;
import aadd.persistencia.mongo.dao.DireccionDAO;

public class ServicioGestionPlataforma {

	private static ServicioGestionPlataforma servicio;
	private static ZeppelinUMRemoto zeppelinumRemoto;


	public static ServicioGestionPlataforma getServicioGestionPlataforma() {
        if (servicio == null) {
            try {
                zeppelinumRemoto = (ZeppelinUMRemoto) InitialContextUtil.getInstance().lookup("ejb:AADD2022/ZeppelinUMZamoraLujanEJB/ZeppelinUMRemoto!aadd.zeppelinum.ZeppelinUMRemoto");
            } catch (NamingException e) {
                e.printStackTrace();
            }
            servicio = new ServicioGestionPlataforma();
        }
        return servicio;
    }

	public Integer registrarUsuario(String nombre, String apellidos, LocalDate fechaNacimiento, String email,
			String clave, TipoUsuario tipo) {

		EntityManager em = EntityManagerHelper.getEntityManager();
		try {
			em.getTransaction().begin();

			Usuario usu = new Usuario();
			usu.setNombre(nombre);
			usu.setApellidos(apellidos);
			usu.setFechaNacimiento(fechaNacimiento);
			usu.setEmail(email);
			usu.setClave(clave);
			usu.setTipo(tipo);

			if (tipo.name().equals("RESTAURANTE"))
				usu.setValidado(false);
			else
				usu.setValidado(true);
			UsuarioDAO.getUsuarioDAO().save(usu, em);

			em.getTransaction().commit();
			return usu.getId();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			em.close();
		}
	}

	public boolean validarUsuario(Integer usuario) {
		EntityManager em = EntityManagerHelper.getEntityManager();
		try {
			em.getTransaction().begin();

			Usuario usu = UsuarioDAO.getUsuarioDAO().findById(usuario);
			usu.setValidado(true);

			em.getTransaction().commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			em.close();
		}
	}

	public Integer registrarRestaurante(String nombre, Integer responsable, String calle, String codigoPostal,
			Integer numero, String ciudad, Double latitud, Double longitud) {

		EntityManager em = EntityManagerHelper.getEntityManager();
		try {
			em.getTransaction().begin();

			Restaurante r = new Restaurante();
			r.setResponsable(UsuarioDAO.getUsuarioDAO().findById(responsable));
			r.setNombre(nombre);
			r.setFechaAlta(LocalDate.now());
			r.setValoracionGlobal(0d);
			r.setNumPenalizaciones(0);
			r.setNumValoraciones(0);

			RestauranteDAO.getRestauranteDAO().save(r, em);
			// Código nuevo
			em.flush();
			Direccion d = new Direccion();
			d.setCalle(calle);
			d.setCiudad(ciudad);
			d.setCodigoPostal(codigoPostal);
			d.setCoordenadas(new Point(new Position(longitud, latitud)));
			d.setNumero(numero);
			d.setRestaurante(r.getId());

			DireccionDAO.getDireccionDAO().save(d);
			///
			em.getTransaction().commit();
			return r.getId();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			em.close();
		}
	}

	public boolean nuevoPlato(String titulo, String descripcion, double precio, Integer restaurante) {
		EntityManager em = EntityManagerHelper.getEntityManager();
		try {
			em.getTransaction().begin();

			Restaurante r = RestauranteDAO.getRestauranteDAO().findById(restaurante);
			Plato p = new Plato();
			p.setDescripcion(descripcion);
			p.setTitulo(titulo);
			p.setPrecio(precio);
			p.setRestaurante(r);
			p.setDisponibilidad(true);

			PlatoDAO.getPlatoDAO().save(p, em);

			em.getTransaction().commit();
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			em.close();
		}
	}
	
	public Integer crearCategoriaRestaurante(String nombreCategoria) {
		EntityManager em = EntityManagerHelper.getEntityManager();
		try {
			em.getTransaction().begin();

			CategoriaRestaurante categoriaRestaurante = new CategoriaRestaurante();
			categoriaRestaurante.setCategoria(nombreCategoria);
			
			CategoriaRestauranteDAO.getCategoriaRestauranteDAO().save(categoriaRestaurante, em);

			em.getTransaction().commit();
			return categoriaRestaurante.getId();

		} catch (Exception e) {
			e.printStackTrace();
			return null ;
		} finally {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			em.close();
		}
	}

	public boolean isUsuarioRegistrado(String email) {
		List<UsuarioDTO> u = UsuarioDAO.getUsuarioDAO().findByEmail(email);
		if (u != null && !u.isEmpty()) {
			return true;
		}
		return false;
	}

	public UsuarioDTO login(String email, String clave) {
		List<UsuarioDTO> usuarios = UsuarioDAO.getUsuarioDAO().findByEmailClave(email, clave);
		if (usuarios.isEmpty()) {
			System.out.println("Usuario no encontrado, email o clave incorrectos");
			return null;
		} else {
			System.out.println("Usuario logueado " + usuarios.get(0).getNombre());
			return usuarios.get(0);
		}
	}

	public List<PlatoDTO> getMenuByRestaurante(Integer restaurante) {
		return PlatoDAO.getPlatoDAO().findPlatosDisponiblesByRestaurante(restaurante);
	}

	public List<RestauranteDTO> getRestaurantesByFiltros(String keyword, boolean verNovedades,
			boolean ordernarByValoracion, boolean ceroIncidencias) {
		if (keyword != null && keyword.isBlank()) {
			keyword = null;
		}
		LocalDate fecha = null;
		if (verNovedades) { // filtramos por aquellos dados de alta la última semana
			fecha = LocalDate.now();
			fecha = fecha.minusWeeks(1);
		}
		return RestauranteDAO.getRestauranteDAO().findRestauranteByFiltros(keyword, fecha, ordernarByValoracion,
				ceroIncidencias);
	}

	public List<RestauranteDTO> getRestaurantesByCercanía(Double latitud, Double longitud, int limite, int skip) {
		List<Direccion> direcciones = DireccionDAO.getDireccionDAO().findOrdenadoPorCercania(latitud, longitud, limite,
				skip);

		RestauranteDAO restauranteDAO = RestauranteDAO.getRestauranteDAO();
		List<RestauranteDTO> restaurantes = new ArrayList<>();
		for (Direccion d : direcciones) {
			Restaurante r = restauranteDAO.findById(d.getRestaurante());
			Position coordenadas = d.getCoordenadas().getCoordinates();

			RestauranteDTO restauranteDTO = new RestauranteDTO(r.getId(), r.getNombre(), r.getValoracionGlobal(),
					coordenadas.getValues().get(0), coordenadas.getValues().get(1), d.getCalle(), d.getCodigoPostal(),
					d.getCiudad(), d.getNumero());
			restaurantes.add(restauranteDTO);
		}
		return restaurantes;
	}

	public RestauranteDTO getDatosRestaurante(RestauranteDTO restaurante) {
		Direccion d = DireccionDAO.getDireccionDAO().findByRestaurante(restaurante.getId());
		Position coordenadas = d.getCoordenadas().getCoordinates();
		restaurante.setLongitud(coordenadas.getValues().get(0));
		restaurante.setLatitud(coordenadas.getValues().get(1));
		restaurante.setCalle(d.getCalle());
		restaurante.setCiudad(d.getCiudad());
		restaurante.setCodigoPostal(d.getCodigoPostal());
		restaurante.setNumero(d.getNumero());
		return restaurante;
	}

	public RestauranteDTO getRestaurante(Integer idRestaurante) {
		Restaurante restaurante = RestauranteDAO.getRestauranteDAO().findById(idRestaurante);
		return new RestauranteDTO(idRestaurante, restaurante.getNombre(), restaurante.getValoracionGlobal());
	}
	public List<Integer> getIdUsuariosByTipo(List<TipoUsuario> tipos){
	    return UsuarioDAO.getUsuarioDAO().findIdsByTipo(tipos);
	}
	public List<EstadisticaOpinionDTO> getEstadisticasOpinion(Integer idUsuario) {
	    return zeppelinumRemoto.getEstadisticasOpinion(idUsuario);
	}

	public Integer getNumVisitas(Integer idUsuario) {
	    return zeppelinumRemoto.getNumVisitas(idUsuario);
	}
	
	public boolean addCategoriaARestaurante(Integer idRestaurante, Integer idCategoria) {
	    EntityManager em = EntityManagerHelper.getEntityManager();
	    try {
	        em.getTransaction().begin();

	        Restaurante restaurante = RestauranteDAO.getRestauranteDAO().findById(idRestaurante);
	        if (restaurante == null) {
	            return false; // el restaurante no existe
	        }

	        CategoriaRestaurante categoria = CategoriaRestauranteDAO.getCategoriaRestauranteDAO().findById(idCategoria);
	        if (categoria == null) {
	            return false; // la categoría no existe
	        }

	        if(!restaurante.getCategorias().add(categoria)) {
	        	return false; // Si ya existe en la relación
	        }
	        if(categoria.getRestaurantes().add(restaurante)) {
	        	return false;	// Si ya existe en la relacción
	        }
	        
	       
	        RestauranteDAO.getRestauranteDAO().save(restaurante, em);
	        CategoriaRestauranteDAO.getCategoriaRestauranteDAO().save(categoria, em);
	        
	        em.getTransaction().commit();

	        return true;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    } finally {
	        if (em.getTransaction().isActive()) {
	            em.getTransaction().rollback();
	        }
	        em.close();
	    }
	}

	public boolean cambiarDispPlato(Integer idPlato, boolean disponible) {
		
	    EntityManager em = EntityManagerHelper.getEntityManager();
	    try {
	        em.getTransaction().begin();

	         Plato plato = PlatoDAO.getPlatoDAO().findById(idPlato);
	         plato.setDisponibilidad(disponible);
	         PlatoDAO.getPlatoDAO().save(plato, em);
	        em.getTransaction().commit();
	        return true;
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    } finally {
	        if (em.getTransaction().isActive()) {
	            em.getTransaction().rollback();
	        }
	        em.close();
	    }
	}
	
	
	  


}