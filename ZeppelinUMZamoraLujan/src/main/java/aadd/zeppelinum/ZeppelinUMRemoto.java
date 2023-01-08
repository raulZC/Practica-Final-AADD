package aadd.zeppelinum;

import java.util.List;

import javax.ejb.Remote;

import aadd.persistencia.dto.EstadisticaImporteDTO;
import aadd.persistencia.dto.EstadisticaPedidoEstadosDTO;
import aadd.persistencia.dto.EstadisticaOpinionDTO;
import aadd.persistencia.dto.EstadisticaPedidosDTO;

@Remote
public interface ZeppelinUMRemoto { 
    public Integer getNumVisitas(Integer idUsuario);
    public void pedidoIniciado(String pedido);
	public void pedidoRecogido(String pedido);
	public void penalizacionRestaurante(String pedido, int minutos);
    public List<EstadisticaOpinionDTO> getEstadisticasOpinion(Integer idUsuario);
    public List<EstadisticaPedidoEstadosDTO> getEstadisticasEstados(Integer idUsuario);
    public List<EstadisticaPedidosDTO> getEstadisticasPedidos(Integer idUsuario);
    public List<EstadisticaImporteDTO> getEstadisticasImporte(Integer idUsuario);

}