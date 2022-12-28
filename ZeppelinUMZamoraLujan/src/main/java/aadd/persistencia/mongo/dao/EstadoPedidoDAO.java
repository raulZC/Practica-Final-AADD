package aadd.persistencia.mongo.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;

import aadd.persistencia.jpa.bean.TipoEstado;
import aadd.persistencia.mongo.bean.EstadoPedido;
import aadd.persistencia.mongo.bean.Pedido;

public class EstadoPedidoDAO extends ExtensionMongoDAO<EstadoPedido> {

	private static EstadoPedidoDAO estadoPedidoDAO;

	public static EstadoPedidoDAO getEstadopedidoDAO() {
		if (estadoPedidoDAO == null) {
			estadoPedidoDAO = new EstadoPedidoDAO();
		}
		return estadoPedidoDAO;
	}

	@Override
	public void createCollection() {
		collection = db.getCollection("estadoPedido", EstadoPedido.class).withCodecRegistry(defaultCodecRegistry);
	}
	
	public EstadoPedido crearEstadoPedido(ObjectId pedido, Date fechaEstado, TipoEstado estado) {
		EstadoPedido estPed = new EstadoPedido();
		estPed.setPedido(pedido);
		estPed.setFechaEstado(fechaEstado);
		estPed.setEstado(estado.toString());
		collection.insertOne(estPed);
		return estPed;
	}

	public List<EstadoPedido> findByEstado(String estado) {

		Bson query = Filters.eq("estado", estado);
		FindIterable<EstadoPedido> resultados = collection.find(query);
		MongoCursor<EstadoPedido> it = resultados.iterator();
		List<EstadoPedido> pedidos = new ArrayList<EstadoPedido>();
		while (it.hasNext()) {
			pedidos.add(it.next());
		}
		return pedidos;
	}

	public List<EstadoPedido> findByPedido(ObjectId pedido) {

		Bson query = Filters.eq("pedido", pedido);
		FindIterable<EstadoPedido> resultados = collection.find(query);
		MongoCursor<EstadoPedido> it = resultados.iterator();
		List<EstadoPedido> estadoPedidos = new ArrayList<EstadoPedido>();
		while (it.hasNext()) {
			estadoPedidos.add(it.next());
		}
		return estadoPedidos;
	}
	
	public EstadoPedido findByID(ObjectId id) {
		Bson query = Filters.eq("id", id);
		FindIterable<EstadoPedido> resultados = collection.find(query);
		MongoCursor<EstadoPedido> it = resultados.iterator();
		return it.tryNext();
	}

}