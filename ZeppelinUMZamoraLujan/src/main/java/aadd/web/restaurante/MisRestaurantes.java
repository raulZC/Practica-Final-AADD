package aadd.web.restaurante;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.Marker;

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

	public void buscar() {

		if (!keyword.isBlank()) {

			misRestaurantes = misRestaurantes.stream().filter(
					rest -> rest.getNombre().toLowerCase().contains(keyword.toLowerCase()) || Integer.toString(rest.getId()).contains(keyword))
					.collect(Collectors.toList());

		} else {
			misRestaurantes = servicio.getRestaurantesByUser(userSessionWeb.getUsuario().getId());
		}

	}

	public void clickOnEdit() {

		try {
			String contextoURL = facesContext.getExternalContext().getApplicationContextPath();
			facesContext.getExternalContext()
					.redirect(contextoURL + "/restaurante/formPlatos.xhtml?id=" + restauranteSelec.getId());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
