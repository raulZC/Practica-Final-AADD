package aadd.persistencia.mongo.dao;

import java.util.ArrayList;
import java.util.List;

import org.bson.conversions.Bson;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;

public class EstadoPedidoDAO extends ExtensionMongoDAO<EstadoPedidoDAO> {

	private static EstadoPedidoDAO estadoPedidoDAO;

	public static EstadoPedidoDAO getEstadopedidoDAO() {
		if (estadoPedidoDAO == null) {
			estadoPedidoDAO = new EstadoPedidoDAO();
		}
		return estadoPedidoDAO;
	}

	@Override
	public void createCollection() {
		collection = db.getCollection("estadoPedidoDAO", EstadoPedidoDAO.class).withCodecRegistry(defaultCodecRegistry);
	}

	public List<EstadoPedidoDAO> findByEstado(String estado) {

		Bson query = Filters.eq("estado", estado);
		FindIterable<EstadoPedidoDAO> resultados = collection.find(query);
		MongoCursor<EstadoPedidoDAO> it = resultados.iterator();
		List<EstadoPedidoDAO> pedidos = new ArrayList<EstadoPedidoDAO>();
		while (it.hasNext()) {
			pedidos.add(it.next());
		}
		return pedidos;
	}

	public List<EstadoPedidoDAO> findByPedido(Integer pedido) {

		Bson query = Filters.eq("pedido", pedido);
		FindIterable<EstadoPedidoDAO> resultados = collection.find(query);
		MongoCursor<EstadoPedidoDAO> it = resultados.iterator();
		List<EstadoPedidoDAO> estadoPedidos = new ArrayList<EstadoPedidoDAO>();
		while (it.hasNext()) {
			estadoPedidos.add(it.next());
		}
		return estadoPedidos;
	}
}