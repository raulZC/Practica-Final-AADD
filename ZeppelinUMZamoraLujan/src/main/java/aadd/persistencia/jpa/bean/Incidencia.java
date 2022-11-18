package aadd.persistencia.jpa.bean;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "incidencia")
public class Incidencia {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	@Column(name = "fecha_creacion")
	private LocalDate fechaCreacion;
	@Column(name = "descripcion")
	@Lob
	private String descripcion;
	@Column(name = "fecha_cierre", columnDefinition = "DATE")
	private LocalDate fechaCierre;
	@Column(name = "comentario_cierre")
	@Lob
	private String comentarioCierre;
	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;
	@ManyToOne
	@JoinColumn(name = "restaurante_id")
	private Restaurante restaurante;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
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
