package aadd.web.pedido;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import aadd.persistencia.dto.IncidenciaDTO;
import aadd.persistencia.dto.ItemPedidoDTO;
import aadd.persistencia.dto.PedidoDTO;
import aadd.persistencia.dto.PlatoDTO;
import aadd.persistencia.dto.RestauranteDTO;
import aadd.persistencia.mongo.bean.ItemPedido;
import aadd.web.usuario.UserSessionWeb;
import aadd.zeppelinum.ServicioGestionPedido;
import aadd.zeppelinum.ServicioGestionPlataforma;

@ViewScoped
@Named("verIncidenciasRestBean")
public class VerIncidenciasRestBean implements Serializable {

	private static final long serialVersionUID = -4976586837365181970L;

	@Inject
	private FacesContext facesContext;
	@Inject
	protected UserSessionWeb userSessionWeb;
	private ServicioGestionPlataforma servicio;
	private ServicioGestionPedido servicioPedido;
	private String keyword;
	private List<IncidenciaDTO> incidencias;
	private List<IncidenciaDTO> fiteredIncidencias;
	private Integer idRestaurante;
	private List<PlatoDTO> menu;
	private List<ItemPedido> listaItemPedidos;
	private boolean mostrarIncidenciasCerradas = false;
	private IncidenciaDTO incidenciaSelected;
	private PedidoDTO pedidoIncidencia;
	private List<ItemPedidoDTO> listaItemsPedidoSelect;
	private String comentarioCierre;

	
	@PostConstruct
	public void init() {
		servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();
		servicioPedido = ServicioGestionPedido.getServicioGestionPedido();

		if (mostrarIncidenciasCerradas) {
			loadIncidencias();
			
		} else {
			incidencias = servicioPedido.getIncidenciasNoCerradasByRest(idRestaurante);
		}
		
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

	public ServicioGestionPedido getServicioPedido() {
		return servicioPedido;
	}

	public void setServicioPedido(ServicioGestionPedido servicioPedido) {
		this.servicioPedido = servicioPedido;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public List<IncidenciaDTO> getIncidencias() {
		return incidencias;
	}

	public void setIncidencias(List<IncidenciaDTO> incidencias) {
		this.incidencias = incidencias;
	}

	public List<IncidenciaDTO> getFiteredIncidencias() {
		return fiteredIncidencias;
	}

	public void setFiteredIncidencias(List<IncidenciaDTO> fiteredIncidencias) {
		this.fiteredIncidencias = fiteredIncidencias;
	}

	public Integer getIdRestaurante() {
		return idRestaurante;
	}

	public void setIdRestaurante(Integer idRestaurante) {
		this.idRestaurante = idRestaurante;
	}

	public List<PlatoDTO> getMenu() {
		return menu;
	}

	public void setMenu(List<PlatoDTO> menu) {
		this.menu = menu;
	}

	public List<ItemPedido> getListaItemPedidos() {
		return listaItemPedidos;
	}

	public void setListaItemPedidos(List<ItemPedido> listaItemPedidos) {
		this.listaItemPedidos = listaItemPedidos;
	}

	public boolean isMostrarIncidenciasCerradas() {
		return mostrarIncidenciasCerradas;
	}

	public void setMostrarIncidenciasCerradas(boolean mostrarIncidenciasCerradas) {
		this.mostrarIncidenciasCerradas = mostrarIncidenciasCerradas;
	}

	public IncidenciaDTO getIncidenciaSelected() {
		return incidenciaSelected;
	}

	public void setIncidenciaSelected(IncidenciaDTO incidenciaSelected) {
		this.incidenciaSelected = incidenciaSelected;
	}

	public PedidoDTO getPedidoIncidencia() {
		return pedidoIncidencia;
	}

	public void setPedidoIncidencia(PedidoDTO pedidoIncidencia) {
		this.pedidoIncidencia = pedidoIncidencia;
	}

	public List<ItemPedidoDTO> getListaItemsPedidoSelect() {
		return listaItemsPedidoSelect;
	}

	public void setListaItemsPedidoSelect(List<ItemPedidoDTO> listaItemsPedidoSelect) {
		this.listaItemsPedidoSelect = listaItemsPedidoSelect;
	}

	public String getComentarioCierre() {
		return comentarioCierre;
	}

	public void setComentarioCierre(String comentarioCierre) {
		this.comentarioCierre = comentarioCierre;
	}


	public void loadIncidencias() {
		
		incidencias = servicioPedido.getIncidenciasByRest(idRestaurante);
		
	}
	public boolean isResponsable() {
		return servicio.getRestaurante(idRestaurante).getResponsable() == userSessionWeb.getUsuario().getId();
	}

	public void buscar(){

		if (mostrarIncidenciasCerradas) {
			loadIncidencias();
			
		} else {
			incidencias = servicioPedido.getIncidenciasNoCerradasByRest(idRestaurante);
		}
		
		if (!keyword.isBlank()) {

			incidencias = incidencias.stream().filter(inc -> inc.getRestaurante().getNombre().toLowerCase().contains(keyword.toLowerCase())
					|| inc.getUsuario().getNombre().toLowerCase().contains(keyword.toLowerCase()) || 
					Integer.toString(inc.getId()).contains(keyword))
					.collect(Collectors.toList());

		}

	}
	
	public void cerrarIncidencia() {
		PrimeFaces current = PrimeFaces.current();
		
		if(servicioPedido.cerrarIncidencia(incidenciaSelected.getId(), comentarioCierre)!=null) {
			
			current.executeScript("PF('incidenciaCerrada').show();");
		}else {
			
			current.executeScript("PF('errorCerrandoIncidencia').show();");
		}
		incidencias = servicioPedido.getIncidenciasNoCerradasByRest(idRestaurante);
	}

	

/*
	public void verDetalles() {
		
		pedidoIncidencia = servicioPedido.getPedidoByIncidencia(incidenciaSelected.getId());
		List<ItemPedidoDTO> lista = new ArrayList<ItemPedidoDTO>();
		List<ItemPedido> listaItem = pedidoIncidencia.getListaItems();
		for (ItemPedido itemPedido : listaItem) {
			Plato plato = servicio.getPlatoByid(itemPedido.getPlato());
			lista.add(new ItemPedidoDTO(itemPedido.getId(), plato.getTitulo(), itemPedido.getCantidad(),
					itemPedido.getPrecioTotal()));
		}
		return lista;
	}*/
}
