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

import org.primefaces.PrimeFaces;

import aadd.persistencia.dto.UsuarioDTO;
import aadd.web.usuario.UserSessionWeb;
import aadd.zeppelinum.ServicioGestionPlataforma;

@Named("restNoValList")
@ViewScoped
public class RestaurantesNoValidadosList implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private FacesContext facesContext;
	@Inject
	protected UserSessionWeb userSessionWeb;
	private List<UsuarioDTO> usersNoVal;
	private ServicioGestionPlataforma servicio;
	private UsuarioDTO userSeleccionado;
	private String keyword;

	@PostConstruct
	public void init() {
		servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();
		usersNoVal = servicio.getUsRestauranteNoVal();

	}

	public void buscar() {

		if (!keyword.isBlank()) {

			usersNoVal = usersNoVal.stream().filter(act -> act.getNombre().contains(keyword)
					|| act.getApellidos().contains(keyword) || Integer.toString(act.getId()).contains(keyword))
					.collect(Collectors.toList());

		} else {
			usersNoVal = servicio.getUsRestauranteNoVal();
		}

	}

	public void validar() {

		// Si el usuario que esta logueado es admin
		if (userSeleccionado != null) {
			if (userSessionWeb.isAdmin()) {

				if(servicio.validarUsuario(userSeleccionado.getId())) {
					
					facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info",
							"Necesitas ser administrador para realizar esta acción."));
					usersNoVal = servicio.getUsRestauranteNoVal();
				}else {
					facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
							"Se ha producido un error al intentar validar el usuario"));
				}
				
			} else {
				facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
						"Necesitas ser administrador para realizar esta acción."));
				return;
			}
		}else {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
					"El usuario es null"));
			return;
		}
			

	}

	public FacesContext getFacesContext() {
		return facesContext;
	}

	public void setFacesContext(FacesContext facesContext) {
		this.facesContext = facesContext;
	}

	public List<UsuarioDTO> getUsersNoVal() {
		return usersNoVal;
	}

	public void setUsersNoVal(List<UsuarioDTO> usersNoVal) {
		this.usersNoVal = usersNoVal;
	}

	public ServicioGestionPlataforma getServicio() {
		return servicio;
	}

	public void setServicio(ServicioGestionPlataforma servicio) {
		this.servicio = servicio;
	}

	public UsuarioDTO getUserSeleccionado() {
		return userSeleccionado;
	}

	public void setUserSeleccionado(UsuarioDTO userSeleccionado) {
		this.userSeleccionado = userSeleccionado;
	}

	public RestaurantesNoValidadosList() {
		servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();
	}

	public UserSessionWeb getUserSessionWeb() {
		return userSessionWeb;
	}

	public void setUserSessionWeb(UserSessionWeb userSessionWeb) {
		this.userSessionWeb = userSessionWeb;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
}
