package aadd.web.restaurante;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import aadd.persistencia.dto.PlatoDTO;
import aadd.persistencia.dto.RestauranteDTO;
import aadd.web.usuario.UserSessionWeb;
import aadd.zeppelinum.ServicioGestionPlataforma;

@Named("misRest")
@ViewScoped
public class MisRestaurantes implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4383989131092230884L;
	@Inject
	private FacesContext facesContext;
	@Inject
	protected UserSessionWeb userSessionWeb;
	private ServicioGestionPlataforma servicio;
	private List<RestauranteDTO> misRestaurantes;
	private List<PlatoDTO> menu;
	private PlatoDTO platoSelect;
	private Integer id;
	private String titulo;
	private String descripcion;
	private Double precio;
	private boolean disponibilidad;
	private RestauranteDTO restauranteSelec;

	public FacesContext getFacesContext() {
		return facesContext;
	}

	public void setFacesContext(FacesContext facesContext) {
		this.facesContext = facesContext;
	}

	public UserSessionWeb getUserSessionWeb() {
		return userSessionWeb;
	}

	public void setUserSessionWeb(UserSessionWeb userSessionWeb) {
		this.userSessionWeb = userSessionWeb;
	}

	public List<RestauranteDTO> getMisRestaurantes() {
		return misRestaurantes;
	}

	public void setMisRestaurantes(List<RestauranteDTO> misRestaurantes) {
		this.misRestaurantes = misRestaurantes;
	}

	public ServicioGestionPlataforma getServicio() {
		return servicio;
	}

	public void setServicio(ServicioGestionPlataforma servicio) {
		this.servicio = servicio;
	}

	public RestauranteDTO getRestauranteSelec() {
		return restauranteSelec;
	}

	public void setRestauranteSelec(RestauranteDTO restauranteSelec) {
		this.restauranteSelec = restauranteSelec;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	private String keyword;

	@PostConstruct
	public void init() {
		servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();
		misRestaurantes = servicio.getRestaurantesByUser(userSessionWeb.getUsuario().getId());

	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public List<PlatoDTO> getMenu() {
		return menu;
	}

	public void setMenu(List<PlatoDTO> menu) {
		this.menu = menu;
	}

	public PlatoDTO getPlatoSelect() {
		return platoSelect;
	}

	public void setPlatoSelect(PlatoDTO platoSelect) {
		this.platoSelect = platoSelect;
	}

	public boolean isDisponibilidad() {
		return disponibilidad;
	}

	public void setDisponibilidad(boolean disponibilidad) {
		this.disponibilidad = disponibilidad;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void buscar() {

		if (!keyword.isBlank()) {

			misRestaurantes = misRestaurantes.stream()
					.filter(rest -> rest.getNombre().toLowerCase().contains(keyword.toLowerCase())
							|| Integer.toString(rest.getId()).contains(keyword))
					.collect(Collectors.toList());

		} else {
			misRestaurantes = servicio.getRestaurantesByUser(userSessionWeb.getUsuario().getId());
		}

	}

	public void loadMenu() {
		menu = servicio.getAllMenuByRestaurante(restauranteSelec.getId());
	}

	public void clickOnShowMenu() {

		loadMenu();

	}

	public void crearPlato() {

		if (restauranteSelec != null) {

			boolean exito = servicio.nuevoPlato(titulo, descripcion, precio, restauranteSelec.getId());
			if (exito) {
				facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Plato creado", ""));
				loadMenu();
			}
		}
	}

	public void eliminarPlato() {

		if (platoSelect != null) {
			
			if (servicio.eliminarPlato(platoSelect.getId())) {

				facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Plato eliminado", ""));
				loadMenu();
			}
			
			
		} else {
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al intentar eliminar el plato", ""));
			
		}
	}
	
	
	public void selectPlatoEdit() {
		
		if(platoSelect!=null) {
			
			id=platoSelect.getId();
			titulo = platoSelect.getTitulo();
			descripcion= platoSelect.getDescripcion();
			precio=platoSelect.getPrecio();
			disponibilidad=platoSelect.isDisponibilidad();
			System.out.println(id+" "+titulo);
		}
		
		
	}
	
	public void updatePlato() {
		
		if(disponibilidad!=platoSelect.isDisponibilidad()) {
			
			servicio.cambiarDispPlato(platoSelect.getId(), disponibilidad);
			loadMenu();
		}
		
		
		
	}
}
