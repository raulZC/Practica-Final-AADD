package aadd.web.restaurante;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import aadd.persistencia.dto.PlatoDTO;
import aadd.persistencia.dto.RestauranteDTO;
import aadd.zeppelinum.ServicioGestionPlataforma;

@Named
@ViewScoped
public class RestauranteMenuList implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private FacesContext facesContext;
	private Integer idRestaurante;
	private List<PlatoDTO> menu;
	private String titulo;
	private String descripcion;
	private Double precio;
	private ServicioGestionPlataforma servicio;
	private RestauranteDTO restauranteSeleccionado;

	public RestauranteMenuList() {
		servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();
	}

	public void loadMenu() {
		menu = servicio.getMenuByRestaurante(idRestaurante);
	}

	public void setIdRestaurante(Integer idRestaurante) {
		this.idRestaurante = idRestaurante;
		restauranteSeleccionado = servicio.getRestaurante(idRestaurante);
	}

	public Integer getidRestaurante() {
		return idRestaurante;
	}

	public List<PlatoDTO> getMenu() {
		return menu;
	}

	public void crearPlato() {
		boolean exito = servicio.nuevoPlato(titulo, descripcion, precio, idRestaurante);
		if (exito) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Plato creado", ""));
			loadMenu();
		}
	}
	// getters y setters necesarios

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

	public RestauranteDTO getRestauranteSeleccionado() {
		return restauranteSeleccionado;
	}

	public void setRestauranteSeleccionado(RestauranteDTO restauranteSeleccionado) {
		this.restauranteSeleccionado = restauranteSeleccionado;
	}

	public Integer getIdRestaurante() {
		return idRestaurante;
	}

	public void setMenu(List<PlatoDTO> menu) {
		this.menu = menu;
	}
	
	
	
}