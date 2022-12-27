package aadd.web;

import java.io.Serializable;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;


import aadd.persistencia.dto.PedidoDTO;
import aadd.web.usuario.UserSessionWeb;
import aadd.zeppelinum.ServicioGestionPedido;
import aadd.zeppelinum.ServicioGestionPlataforma;

@Named
@ViewScoped
public class PedidoListWeb   implements Serializable {

	private static final long serialVersionUID = 1L;

	private ServicioGestionPedido servicioPedido;
	private ServicioGestionPlataforma servicioestion;
	
	@Inject
	private FacesContext facesContext;
	@Inject
	protected UserSessionWeb userSessionWeb;


	public PedidoListWeb() {
		servicioestion = ServicioGestionPlataforma.getServicioGestionPlataforma();
		servicioPedido = ServicioGestionPedido.getServicioGestionPedido();
	
	}
	
	public void  verDetalle() {
		return ;
	}

	public List<PedidoDTO> getListaPedidosDTO() {
		return servicioPedido.findByCliente(userSessionWeb.getUsuario().getId());
	}
	

	public UserSessionWeb getUserSessionWeb() {
		return userSessionWeb;
	}

	public void setUserSessionWeb(UserSessionWeb userSessionWeb) {
		this.userSessionWeb = userSessionWeb;
	}

	
	


}