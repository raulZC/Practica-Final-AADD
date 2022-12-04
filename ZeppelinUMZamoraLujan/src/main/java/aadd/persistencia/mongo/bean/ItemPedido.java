package aadd.persistencia.mongo.bean;

import java.io.Serializable;
import java.time.LocalDate;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

public class ItemPedido implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@BsonId
    private ObjectId id;
    private Integer plato;
    private Integer pedido;
    private Integer cantidad;
    private Double precioTotal;
    
    //getters y setters
    
	public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}
	public Integer getPlato() {
		return plato;
	}
	public void setPlato(Integer plato) {
		this.plato = plato;
	}
	public Integer getPedido() {
		return pedido;
	}
	public void setPedido(Integer pedido) {
		this.pedido = pedido;
	}
	public Integer getCantidad() {
		return cantidad;
	}
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	public Double getPrecioTotal() {
		return precioTotal;
	}
	public void setPrecioTotal(Double precioTotal) {
		this.precioTotal = precioTotal;
	}
    
   
}
