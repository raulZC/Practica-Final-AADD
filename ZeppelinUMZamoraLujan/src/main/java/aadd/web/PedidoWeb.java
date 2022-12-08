package aadd.web;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.map.DefaultMapModel;

import aadd.persistencia.jpa.bean.Plato;
import aadd.web.usuario.UserSessionWeb;
import aadd.zeppelinum.ServicioGestionPedido;
import aadd.zeppelinum.ServicioGestionPlataforma;

@Named
@ViewScoped
public class PedidoWeb implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Plato> listaPlatos;
	private Double precioTotal;
	private HashMap<Plato, Integer> cantidades;

	@Inject
	private FacesContext facesContext;
	@Inject
	private UserSessionWeb usuarioSesion;
	private ServicioGestionPedido servicio;

	public PedidoWeb() {
		servicio = ServicioGestionPedido.getServicioGestionPedido();
	}

	public void cancelar() {
		try {
			String contextoURL = facesContext.getExternalContext().getApplicationContextPath();
			facesContext.getExternalContext().redirect(contextoURL + "/restaurante/listLazy.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void añadirCantidad(int id) {

	}

	public FacesContext getFacesContext() {
		return facesContext;
	}

	public void setFacesContext(FacesContext facesContext) {
		this.facesContext = facesContext;
	}

	public UserSessionWeb getUsuarioSesion() {
		return usuarioSesion;
	}

	public void setUsuarioSesion(UserSessionWeb usuarioSesion) {
		this.usuarioSesion = usuarioSesion;
	}

}