package aadd.persistencia.mongo.bean;

import java.io.Serializable;
import java.util.Date;


import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

public class EstadoPedido implements Serializable, Comparable<EstadoPedido> {

	private static final long serialVersionUID = 1L;

	@BsonId
	private ObjectId id;
	private ObjectId pedido;
	private Date fechaEstado;
	private String estado;

	// getters y setters

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public ObjectId getPedido() {
		return pedido;
	}

	public void setPedido(ObjectId pedido) {
		this.pedido = pedido;
	}

	public Date getFechaEstado() {
		return fechaEstado;
	}

	public void setFechaEstado(Date fechaEstado) {
		this.fechaEstado = fechaEstado;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	@Override
	public int compareTo(EstadoPedido o) {
		if (this.getFechaEstado().after(o.getFechaEstado()))
			return 1;
		else if (this.getFechaEstado().before(o.getFechaEstado()))
			return -1;
		return 0;
	}
}
