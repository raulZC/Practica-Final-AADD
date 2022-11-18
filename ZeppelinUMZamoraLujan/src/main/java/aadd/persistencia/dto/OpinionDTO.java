package aadd.persistencia.dto;

import java.io.Serializable;

public class OpinionDTO implements Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -9145858313721379296L;
	private String nombreUsuario;
    private String nombreRestaurante;
    private String comentario;
    private Double valoracion;
    //getters y setters
	public String getNombreUsuario() {
		return nombreUsuario;
	}
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	public String getNombreRestaurante() {
		return nombreRestaurante;
	}
	public void setNombreRestaurante(String nombreRestaurante) {
		this.nombreRestaurante = nombreRestaurante;
	}
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	public Double getValoracion() {
		return valoracion;
	}
	public void setValoracion(Double valoracion) {
		this.valoracion = valoracion;
	}
}
