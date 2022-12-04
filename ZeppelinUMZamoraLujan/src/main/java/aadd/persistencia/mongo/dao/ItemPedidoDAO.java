package aadd.persistencia.mongo.dao;

import java.util.ArrayList;
import java.util.List;

import org.bson.conversions.Bson;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;

public class ItemPedidoDAO extends ExtensionMongoDAO<ItemPedidoDAO> {

	private static ItemPedidoDAO itemPedidoDAO;

	public static ItemPedidoDAO getItemPedidoDAO() {
		if (itemPedidoDAO == null) {
			itemPedidoDAO = new ItemPedidoDAO();
		}
		return itemPedidoDAO;
	}

	@Override
	public void createCollection() {
		collection = db.getCollection("itemPedidoDAO", ItemPedidoDAO.class).withCodecRegistry(defaultCodecRegistry);
	}

	public List<ItemPedidoDAO> findByPedido(Integer pedido) {

		Bson query = Filters.eq("pedido", pedido);
		FindIterable<ItemPedidoDAO> resultados = collection.find(query);
		MongoCursor<ItemPedidoDAO> it = resultados.iterator();
		List<ItemPedidoDAO> itemPedidos = new ArrayList<ItemPedidoDAO>();
		while (it.hasNext()) {
			itemPedidos.add(it.next());
		}
		return itemPedidos;
	}
}