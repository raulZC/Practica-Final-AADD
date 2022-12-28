package aadd.persistencia.mongo.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;

import aadd.persistencia.mongo.bean.ItemPedido;

public class ItemPedidoDAO extends ExtensionMongoDAO<ItemPedido> {

	private static ItemPedidoDAO itemPedidoDAO;

	public static ItemPedidoDAO getItemPedidoDAO() {
		if (itemPedidoDAO == null) {
			itemPedidoDAO = new ItemPedidoDAO();
		}
		return itemPedidoDAO;
	}

	@Override
	public void createCollection() {
		collection = db.getCollection("itemPedido", ItemPedido.class).withCodecRegistry(defaultCodecRegistry);
	}
	
	public ItemPedido crearItemPedido(Integer plato, ObjectId pedido, Integer cantidad, Double precioTota) {
		ItemPedido itemPed = new ItemPedido();
		itemPed.setPlato(plato);
		itemPed.setPedido(pedido);
		itemPed.setCantidad(cantidad);
		itemPed.setPrecioTotal(precioTota);
		collection.insertOne(itemPed);
		return itemPed;
	}

	public List<ItemPedido> findByPedido(ObjectId pedido) {

		Bson query = Filters.eq("pedido", pedido);
		FindIterable<ItemPedido> resultados = collection.find(query);
		MongoCursor<ItemPedido> it = resultados.iterator();
		List<ItemPedido> itemPedidos = new ArrayList<ItemPedido>();
		while (it.hasNext()) {
			itemPedidos.add(it.next());
		}
		return itemPedidos;
	}
}