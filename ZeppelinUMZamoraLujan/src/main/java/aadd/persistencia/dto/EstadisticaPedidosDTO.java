package aadd.persistencia.dto;

import java.io.Serializable;

public class EstadisticaPedidosDTO implements Serializable{ 
	
    private Integer numDia;
    private Integer total;
    private String restaurante;
    
    public EstadisticaPedidosDTO(Integer numDia, Integer total, String restaurante) {
        super();
        this.numDia = numDia;
        this.total = total;
        this.restaurante = restaurante;
    }

    public Integer getNumDia() {
		return numDia;
	}

	public void setNumDia(Integer numDia) {
		this.numDia = numDia;
	}

	public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

	public String getRestaurante() {
		return restaurante;
	}

	public void setRestaurante(String restaurante) {
		this.restaurante = restaurante;
	}    
    
    
}