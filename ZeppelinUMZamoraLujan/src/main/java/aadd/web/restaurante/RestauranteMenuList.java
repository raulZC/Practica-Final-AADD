package aadd.web.restaurante;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import aadd.persistencia.dto.PlatoDTO;
import aadd.persistencia.dto.RestauranteDTO;
import aadd.persistencia.jpa.bean.Plato;
import aadd.persistencia.jpa.bean.TipoUsuario;
import aadd.persistencia.mongo.bean.ItemPedido;
import aadd.web.usuario.UserSessionWeb;
import aadd.zeppelinum.ServicioGestionPedido;
import aadd.zeppelinum.ServicioGestionPlataforma;

@Named
@ViewScoped
public class RestauranteMenuList implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private FacesContext facesContext;
	@Inject
	protected UserSessionWeb userSessionWeb;
	private Integer idRestaurante;
	private List<PlatoDTO> menu;
	private String titulo;
	private String descripcion;
	private Double precio;
	private ServicioGestionPlataforma servicio;
	private ServicioGestionPedido servicioPedido;
	private RestauranteDTO restauranteSeleccionado;
	private Double precioTotal;
	private String direccion;
	private String comentario;
	private List<ItemPedido> listaItemPedidos;

	public RestauranteMenuList() {
		servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();
		servicioPedido = ServicioGestionPedido.getServicioGestionPedido();
		listaItemPedidos = new ArrayList<ItemPedido>();
		precioTotal = 0.0;
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
	
	public void realizarPedido() {
		ArrayList<TipoUsuario> litaTipos = new ArrayList<TipoUsuario>();
		litaTipos.add(TipoUsuario.RIDER);
		List<Integer> reaprtidores = servicio.getIdUsuariosByTipo(litaTipos);
		int reaprtidor = reaprtidores.get((int)(Math.random() * reaprtidores.size()));
		boolean exito = servicioPedido.crearPedido(userSessionWeb.getUsuario().getId(), reaprtidor, idRestaurante, 
				direccion, comentario, precioTotal, listaItemPedidos);
		if (exito) {
			try {
				String contextoURL = facesContext.getExternalContext().getApplicationContextPath();
				facesContext.getExternalContext().redirect(contextoURL);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
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
		for (PlatoDTO platoDTO : menu) {
			if (platoDTO.getId() == id) {
				precioTotal += platoDTO.getPrecio();
				añadirItem(platoDTO);
			}
		}
	}

	private void añadirItem(PlatoDTO plato) {
		for (ItemPedido item : listaItemPedidos) {
			if (plato.getId() == item.getPlato()) {
				item.setCantidad(item.getCantidad() + 1);
				return;
			}
		}
		listaItemPedidos.add(new ItemPedido(plato.getId(), 1, plato.getPrecio()));
	}

	public void quitarCantidad(int id) {
		for (PlatoDTO platoDTO : menu) {
			if (platoDTO.getId() == id) {
				quitarItem(platoDTO);
			}
		}
	}

	private void quitarItem(PlatoDTO plato) {
		for (ItemPedido item : listaItemPedidos) {
			if (plato.getId() == item.getPlato()) {
				if (item.getCantidad() > 1) {
					item.setCantidad(item.getCantidad() - 1);
				} else {
					listaItemPedidos.remove(item);
				}
				precioTotal -= plato.getPrecio();
			}
		}
	}

	public int getCantidad(int id) {
		for (ItemPedido item : listaItemPedidos)
			if (id == item.getPlato())
				return item.getCantidad();
		return 0;
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

	public Double getPrecioTotal() {
		return precioTotal;
	}

	public void setPrecioTotal(Double precioTotal) {
		this.precioTotal = precioTotal;
	}

	public UserSessionWeb getUserSessionWeb() {
		return userSessionWeb;
	}

	public void setUserSessionWeb(UserSessionWeb userSessionWeb) {
		this.userSessionWeb = userSessionWeb;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	
	
	

}