package aadd.persistencia.jpa.bean;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "restaurante")
public class Restaurante implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	@Column(name = "nombre")
	private String nombre;
	@Column(name = "fecha_alta", columnDefinition = "DATE")
	private LocalDate fechaAlta;
	@Column(name = "valoracion_global")
	private Double valoracionGlobal;
	@Column(name = "num_valoraciones")
	private Integer numValoraciones;
	@Column(name = "num_penalizaciones")
	private Integer numPenalizaciones;
	@JoinColumn(name = "responsable")
	@ManyToOne
	private Usuario responsable;
	@OneToMany(mappedBy = "restaurante", cascade = CascadeType.ALL)
	private List<Plato> platos;
	@ManyToMany(mappedBy = "restaurantes")
	private List<CategoriaRestaurante> categorias;
	@OneToMany(mappedBy = "restaurante")
	private List<Incidencia> incidencias;
	public List<Plato> getPlatos() {
		return platos;
	}

	public void setPlatos(List<Plato> platos) {
		this.platos = platos;
	}

	public Usuario getResponsable() {
		return responsable;
	}

	public void setResponsable(Usuario responsable) {
		this.responsable = responsable;
	}

	private static final long serialVersionUID = 1L;

	public Restaurante() {
		super();
	}
	// getters y setters

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public LocalDate getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(LocalDate fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Double getValoracionGlobal() {
		return valoracionGlobal;
	}

	public void setValoracionGlobal(Double valoracionGlobal) {
		this.valoracionGlobal = valoracionGlobal;
	}

	public Integer getNumValoraciones() {
		return numValoraciones;
	}

	public void setNumValoraciones(Integer numValoraciones) {
		this.numValoraciones = numValoraciones;
	}

	public Integer getNumPenalizaciones() {
		return numPenalizaciones;
	}

	public void setNumPenalizaciones(Integer numPenalizaciones) {
		this.numPenalizaciones = numPenalizaciones;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<CategoriaRestaurante> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<CategoriaRestaurante> categorias) {
		this.categorias = categorias;
	}
	
}