package aadd.persistencia.dto;

import java.io.Serializable;

public class EstadisticaImporteDTO implements Serializable{ 
	
    private Integer num;
    private Double total;
    private String restaurante;
    
    public EstadisticaImporteDTO(Integer num, Double total, String restaurante) {
        super();
        this.num = num;
        this.total = total;
        this.restaurante = restaurante;
    }

    public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

	public String getRestaurante() {
		return restaurante;
	}

	public void setRestaurante(String restaurante) {
		this.restaurante = restaurante;
	}       

}