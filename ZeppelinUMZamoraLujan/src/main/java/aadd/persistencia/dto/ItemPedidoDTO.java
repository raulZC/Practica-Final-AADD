package aadd.persistencia.dto;

import java.io.Serializable;

import org.bson.types.ObjectId;

public class ItemPedidoDTO implements Serializable {

	private static final long serialVersionUID = 1218387011373715432L;
	protected ObjectId id;
	protected String nombrePlato;
	protected Integer cantidad;
	protected Double precioTotal;
	
	public ItemPedidoDTO() {
		super();
	}
	
	
	public ItemPedidoDTO(ObjectId id, String nombrePlato, Integer cantidad, Double precioTotal) {
		super();
		this.id = id;
		this.nombrePlato = nombrePlato;
		this.cantidad = cantidad;
		this.precioTotal = precioTotal;
	}
	
	public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}
	public String getNombrePlato() {
		return nombrePlato;
	}
	public void setNombrePlato(String nombrePlato) {
		this.nombrePlato = nombrePlato;
	}
	public String getCantidad() {
		return cantidad.toString();
	}
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	public String getPrecioTotal() {
		return precioTotal + " €";
	}
	public void setPrecioTotal(Double precioTotal) {
		this.precioTotal = precioTotal;
	}
	

	
}