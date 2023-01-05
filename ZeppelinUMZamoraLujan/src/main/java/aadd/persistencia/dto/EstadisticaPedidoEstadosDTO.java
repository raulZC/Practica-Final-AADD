package aadd.persistencia.dto;

import java.io.Serializable;

public class EstadisticaPedidoEstadosDTO implements Serializable{ 
	
    private Integer num;
    private Integer total;
    
    public EstadisticaPedidoEstadosDTO(Integer num, Integer total) {
        super();
        this.num = num;
        this.total = total;
    }

    public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }       
}