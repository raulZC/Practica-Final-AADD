package aadd.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import aadd.persistencia.dto.ItemPedidoDTO;
import aadd.persistencia.dto.PedidoDTO;
import aadd.persistencia.dto.RestauranteDTO;
import aadd.persistencia.jpa.bean.Plato;
import aadd.persistencia.jpa.bean.TipoEstado;
import aadd.persistencia.mongo.bean.ItemPedido;
import aadd.web.usuario.UserSessionWeb;
import aadd.zeppelinum.ServicioGestionPedido;
import aadd.zeppelinum.ServicioGestionPlataforma;

@Named
@ViewScoped
public class EstadoPedidoWeb implements Serializable {

	private static final long serialVersionUID = 1L;

	private ServicioGestionPedido servicioPedido;
	private ServicioGestionPlataforma servicioGestion;

	@Inject
	private FacesContext facesContext;
	@Inject
	protected UserSessionWeb userSessionWeb;

	private List<PedidoDTO> listaPedidosDTO;
	private PedidoDTO pedido;
	private int id;

	public EstadoPedidoWeb() {
		servicioGestion = ServicioGestionPlataforma.getServicioGestionPlataforma();
		servicioPedido = ServicioGestionPedido.getServicioGestionPedido();
		listaPedidosDTO = new ArrayList<PedidoDTO>();

	}

	public void avanzarEstadoPedido(int id) {
		TipoEstado estado = servicioPedido.avanzarEstado(listaPedidosDTO.get(id - 1).getIdReal());
		if (estado.equals(TipoEstado.ERROR)) {
			PrimeFaces current = PrimeFaces.current();
			current.executeScript("PF('errorEstado').show();");
		}

	}

	public void cancelarPedido(int id) {
		TipoEstado estado = servicioPedido.cancelarPedido(listaPedidosDTO.get(id - 1).getIdReal());
		if (estado.equals(TipoEstado.ERROR)) {
			PrimeFaces current = PrimeFaces.current();
			current.executeScript("PF('errorEstado').show();");
		}
	}

	public List<PedidoDTO> getListaPedidosDTO() {
		List<RestauranteDTO> restaurentes = servicioGestion
				.getRestauranteByResponsable(userSessionWeb.getUsuario().getId());
		listaPedidosDTO = new ArrayList<PedidoDTO>();
		for (RestauranteDTO restauranteDTO : restaurentes) {
			listaPedidosDTO.addAll(servicioPedido.findPedidoByRestaurante(restauranteDTO.getId()));
		}
		return listaPedidosDTO;
	}

	public UserSessionWeb getUserSessionWeb() {
		return userSessionWeb;
	}

	public void setUserSessionWeb(UserSessionWeb userSessionWeb) {
		this.userSessionWeb = userSessionWeb;
	}

	public PedidoDTO getPedido() {
		return pedido;
	}

	public void setPedido(PedidoDTO pedido) {
		this.pedido = pedido;
	}

	public String getNombreRestaurante() {
		if (listaPedidosDTO.isEmpty())
			return null;
		return listaPedidosDTO.get(id).getNombreRestaurante();
	}

	public String getFechaHora() {
		if (listaPedidosDTO.isEmpty())
			return null;
		return listaPedidosDTO.get(id).getFechaHora();
	}

	public String getDireccion() {
		if (listaPedidosDTO.isEmpty())
			return null;
		return listaPedidosDTO.get(id).getDireccion();
	}

	public String getImporte() {
		if (listaPedidosDTO.isEmpty())
			return null;
		return listaPedidosDTO.get(id).getImporte() + " €";
	}

	public String getNombreRepartidor() {
		if (listaPedidosDTO.isEmpty())
			return null;
		return listaPedidosDTO.get(id).getNombreRepartidor();
	}

	public String getComentario() {
		if (listaPedidosDTO.isEmpty())
			return null;
		return listaPedidosDTO.get(id).getComentario();
	}

	public List<ItemPedidoDTO> getListaItems() {
		if (listaPedidosDTO.isEmpty())
			return null;
		List<ItemPedidoDTO> lista = new ArrayList<ItemPedidoDTO>();
		List<ItemPedido> listaItem = listaPedidosDTO.get(id).getListaItems();
		for (ItemPedido itemPedido : listaItem) {
			Plato plato = servicioGestion.getPlatoByid(itemPedido.getPlato());
			lista.add(new ItemPedidoDTO(itemPedido.getId(), plato.getTitulo(), itemPedido.getCantidad(),
					itemPedido.getPrecioTotal()));
		}
		return lista;
	}

}