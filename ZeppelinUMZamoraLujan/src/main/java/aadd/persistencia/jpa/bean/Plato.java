package aadd.persistencia.jpa.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "plato")
@NamedQueries({
    @NamedQuery(name = "Plato.findPlatosDisponiblesByRestaurante", query = " SELECT p FROM Plato p WHERE p.disponibilidad = true and p.restaurante.id = :restaurante ")
})
public class Plato implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5936816004707370961L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	@Column(name = "titulo")
	private String titulo;
	@Column(name = "descripcion")
	@Lob
	private String descripcion;
	@Column(name = "precio")
	private Double precio;
	@Column(name = "disponibilidad")
	private boolean disponibilidad;
	@ManyToOne
	@JoinColumn(name = "restaurante")
	private Restaurante restaurante;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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

	public boolean isDisponibilidad() {
		return disponibilidad;
	}

	public void setDisponibilidad(boolean disponibilidad) {
		this.disponibilidad = disponibilidad;
	}

	public Restaurante getRestaurante() {
		return restaurante;
	}

	public void setRestaurante(Restaurante restaurante) {
		this.restaurante = restaurante;
	}

	public Plato() {
		super();
	}
}