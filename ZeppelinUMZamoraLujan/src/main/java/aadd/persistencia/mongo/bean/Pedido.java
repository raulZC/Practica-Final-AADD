package aadd.persistencia.mongo.bean;

import java.io.Serializable;
import java.time.LocalDate;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

public class Pedido implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@BsonId
    private ObjectId id;
    private Integer incidencia;
    private Integer cliente;
    private Integer repartidor;
    private Integer restaurante;
    private String direccion;
    private LocalDate fechaHora;
    private LocalDate fechaEsperado;
    private String comentario;
    private Double importe;
    
    
    //getters y setters
	
	public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}
	public Integer getIncidencia() {
		return incidencia;
	}
	public void setIncidencia(Integer incidencia) {
		this.incidencia = incidencia;
	}
	public Integer getCliente() {
		return cliente;
	}
	public void setCliente(Integer cliente) {
		this.cliente = cliente;
	}
	public Integer getRepartidor() {
		return repartidor;
	}
	public void setRepartidor(Integer repartidor) {
		this.repartidor = repartidor;
	}
	public Integer getRestaurante() {
		return restaurante;
	}
	public void setRestaurante(Integer restaurante) {
		this.restaurante = restaurante;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public LocalDate getFechaHora() {
		return fechaHora;
	}
	public void setFechaHora(LocalDate fechaHora) {
		this.fechaHora = fechaHora;
	}
	public LocalDate getFechaEsperado() {
		return fechaEsperado;
	}
	public void setFechaEsperado(LocalDate fechaEsperado) {
		this.fechaEsperado = fechaEsperado;
	}
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	public Double getImporte() {
		return importe;
	}
	public void setImporte(Double importe) {
		this.importe = importe;
	}

    
}
