package aadd.persistencia.dto;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;

import com.mysql.cj.x.protobuf.MysqlxCrud.Collection;

import aadd.persistencia.jpa.bean.TipoEstado;
import aadd.persistencia.mongo.bean.EstadoPedido;
import aadd.persistencia.mongo.bean.ItemPedido;

public class PedidoDTO implements Serializable {

	private static final long serialVersionUID = 1218387011373715432L;
	protected Integer id;
	protected ObjectId idReal;
	protected String nombreRestaurante;
	protected Date fechaHora;
	protected List<EstadoPedido> listaEstados;
	protected String estadoActual;
	protected String direccion;
	protected Double importe;
	protected List<ItemPedido> listaItems;
	protected String comentario;
	protected String nombreRepartidor;

	public PedidoDTO() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombreRestaurante() {
		return nombreRestaurante;
	}

	public void setNombreRestaurante(String nombreRestaurante) {
		this.nombreRestaurante = nombreRestaurante;
	}

	public String getFechaHora() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return format.format(fechaHora);

	}

	public void setFechaHora(Date fechaHora) {
		this.fechaHora = fechaHora;
	}

	public Double getImporte() {
		return importe;
	}

	public void setImporte(Double importe) {
		this.importe = importe;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public List<ItemPedido> getListaItems() {
		return listaItems;
	}

	public void setListaItems(List<ItemPedido> listaItems) {
		this.listaItems = listaItems;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public List<EstadoPedido> getListaEstados() {
		return listaEstados;
	}

	public void setListaEstados(List<EstadoPedido> listaEstados) {
		this.listaEstados = listaEstados;
	}

	public String getNombreRepartidor() {
		return nombreRepartidor;
	}

	public void setNombreRepartidor(String nombreRepartidor) {
		this.nombreRepartidor = nombreRepartidor;
	}

	public String getEstadoActual() {
		Collections.sort(listaEstados);
		return listaEstados.get(listaEstados.size() - 1).getEstado();
	}

	public void setEstadoActual(String estadoActual) {
		this.estadoActual = estadoActual;
	}

	public ObjectId getIdReal() {
		return idReal;
	}

	public void setIdReal(ObjectId idReal) {
		this.idReal = idReal;
	}
	
	

}