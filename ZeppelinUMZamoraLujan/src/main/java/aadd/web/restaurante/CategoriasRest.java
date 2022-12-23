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

import aadd.persistencia.jpa.bean.CategoriaRestaurante;
import aadd.web.usuario.UserSessionWeb;
import aadd.zeppelinum.ServicioGestionPlataforma;

@Named("catRes")
@ViewScoped
public class CategoriasRest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7686526830304660614L;

	@Inject
	private FacesContext facesContext;
	@Inject
	protected UserSessionWeb userSessionWeb;
	private ServicioGestionPlataforma servicio;
	private String keyword;
	private List<CategoriaRestaurante> categoriasDisp;
	private String nombreCategoria;

	@PostConstruct
	public void init() {
		servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();
		categoriasDisp = servicio.getAllCategoriasRest();

	}

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

	public ServicioGestionPlataforma getServicio() {
		return servicio;
	}

	public void setServicio(ServicioGestionPlataforma servicio) {
		this.servicio = servicio;
	}

	public List<CategoriaRestaurante> getCategoriasDisp() {
		return categoriasDisp;
	}

	public void setCategoriasDisp(List<CategoriaRestaurante> categoriasDisp) {
		this.categoriasDisp = categoriasDisp;
	}



	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getNombreCategoria() {
		return nombreCategoria;
	}

	public void setNombreCategoria(String nombreCategoria) {
		this.nombreCategoria = nombreCategoria;
	}

	public void loadCategorias() {
		categoriasDisp = servicio.getAllCategoriasRest();
	}

	public void buscar() {

		if (!keyword.isBlank()) {

			categoriasDisp = categoriasDisp.stream()
					.filter(cat -> cat.getCategoria().toLowerCase().contains(keyword.toLowerCase()))
					.collect(Collectors.toList());

		} else {
			loadCategorias();
		}

	}

	public void crearCategoria() {

		if (!nombreCategoria.isBlank()) {

			Integer cat = servicio.crearCategoriaRestaurante(nombreCategoria);
			if (cat != null) {
				facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Plato creado", ""));
				loadCategorias();
			}
		}

	}

}
