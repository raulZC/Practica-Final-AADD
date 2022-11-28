package aadd.persistencia.jpa.bean;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "categoria_restaurante")
public class CategoriaRestaurante implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2534398731386584071L;
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	@Column(name = "categoria")
	private String categoria;
	
	@ManyToMany(cascade = CascadeType.ALL)
	private List<Restaurante> restaurantes;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

}