package aadd.persistencia.dto;

import java.io.Serializable;
import java.time.LocalDate;

public class IncidenciaDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	protected Integer id;
	protected LocalDate fechaCreacion;
	protected String descripcion;
	protected LocalDate fechaCierre;
	protected String comentarioCierre;
	
	public IncidenciaDTO(Integer id, LocalDate fechaCreacion, String descripcion, LocalDate fechaCierre,
			String comentarioCierre) {
		super();
		this.id = id;
		this.fechaCreacion = fechaCreacion;
		this.descripcion = descripcion;
		this.fechaCierre = fechaCierre;
		this.comentarioCierre = comentarioCierre;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDate getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(LocalDate fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public LocalDate getFechaCierre() {
		return fechaCierre;
	}

	public void setFechaCierre(LocalDate fechaCierre) {
		this.fechaCierre = fechaCierre;
	}

	public String getComentarioCierre() {
		return comentarioCierre;
	}

	public void setComentarioCierre(String comentarioCierre) {
		this.comentarioCierre = comentarioCierre;
	}
	


}