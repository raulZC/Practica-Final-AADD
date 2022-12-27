package aadd.persistencia.dto;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PedidoDTO implements Serializable {

	private static final long serialVersionUID = 1218387011373715432L;
	protected Integer id;
	protected String nombreRestaurante;
	protected Date fechaHora;
	protected String direccion;
	protected Double importe;

	public PedidoDTO() {

	}

	public PedidoDTO(Integer id, String nombreRestaurante, Date fechaHora, String direccion, Double importe) {
		super();
		this.id = id;
		this.nombreRestaurante = nombreRestaurante;
		this.fechaHora = fechaHora;
		this.direccion = direccion;
		this.importe = importe;
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

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Double getImporte() {
		return importe;
	}

	public void setImporte(Double importe) {
		this.importe = importe;
	}

}