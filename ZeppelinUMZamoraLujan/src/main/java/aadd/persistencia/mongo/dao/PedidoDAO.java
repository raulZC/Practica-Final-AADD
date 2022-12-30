package aadd.persistencia.mongo.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;

import aadd.persistencia.mongo.bean.Pedido;

public class PedidoDAO extends ExtensionMongoDAO<Pedido> {

	private static PedidoDAO pedidoDAO;

	public static PedidoDAO getPedidoDAO() {
		if (pedidoDAO == null) {
			pedidoDAO = new PedidoDAO();
		}
		return pedidoDAO;
	}

	@Override
	public void createCollection() {
		collection = db.getCollection("pedido", Pedido.class).withCodecRegistry(defaultCodecRegistry);
	}
	
	
	public Pedido crearPedido(Integer cliente, Integer repartidor, Integer restaurante, String direccion,
			 Date fechaHora,  Date fechaEsperado, String comentario, Double importe) {
		Pedido ped = new Pedido();
		ped.setCliente(cliente);
		ped.setRepartidor(repartidor);
		ped.setRestaurante(restaurante);
		ped.setDireccion(direccion);
		ped.setFechaHora(fechaHora);
		ped.setFechaEsperado(fechaEsperado);
		ped.setComentario(comentario);
		ped.setImporte(importe);
		collection.insertOne(ped);
		return ped;
	}


	public List<Pedido> findByCliente(Integer cliente) {

		Bson query = Filters.eq("cliente", cliente);
		FindIterable<Pedido> resultados = collection.find(query);
		MongoCursor<Pedido> it = resultados.iterator();
		List<Pedido> pedidos = new ArrayList<Pedido>();
		while (it.hasNext()) {
			pedidos.add(it.next());
		}
		return pedidos;
	}

	public List<Pedido> findByRepartidor(Integer repartidor) {

		Bson query = Filters.eq("repartidor", repartidor);
		FindIterable<Pedido> resultados = collection.find(query);
		MongoCursor<Pedido> it = resultados.iterator();
		List<Pedido> pedidos = new ArrayList<Pedido>();
		while (it.hasNext()) {
			pedidos.add(it.next());
		}
		return pedidos;
	}

	public Pedido findByIncidencia(Integer incidencia) {
		Bson query = Filters.eq("incidencia", incidencia);
		FindIterable<Pedido> resultados = collection.find(query);
		MongoCursor<Pedido> it = resultados.iterator();
		return it.tryNext();
	}
	
	public Pedido findByID(ObjectId id) {
		Bson query = Filters.eq("id", id);
		FindIterable<Pedido> resultados = collection.find(query);
		MongoCursor<Pedido> it = resultados.iterator();
		return it.tryNext();
	}
	
	public List<Pedido> findByRestaurante(Integer restaurante) {

		Bson query = Filters.eq("restaurante", restaurante);
		FindIterable<Pedido> resultados = collection.find(query);
		MongoCursor<Pedido> it = resultados.iterator();
		List<Pedido> pedidos = new ArrayList<Pedido>();
		while (it.hasNext()) {
			pedidos.add(it.next());
		}
		return pedidos;
	}


}