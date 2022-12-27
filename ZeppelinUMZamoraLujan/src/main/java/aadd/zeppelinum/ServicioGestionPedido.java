package aadd.zeppelinum;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.naming.NamingException;
import javax.persistence.EntityManager;

import org.bson.types.ObjectId;

import aadd.persistencia.dto.OpinionDTO;
import aadd.persistencia.dto.PedidoDTO;
import aadd.persistencia.jpa.EntityManagerHelper;
import aadd.persistencia.jpa.bean.Restaurante;
import aadd.persistencia.jpa.bean.TipoEstado;
import aadd.persistencia.jpa.bean.Usuario;
import aadd.persistencia.jpa.dao.RestauranteDAO;
import aadd.persistencia.jpa.dao.UsuarioDAO;
import aadd.persistencia.mongo.bean.ItemPedido;
import aadd.persistencia.mongo.bean.Opinion;
import aadd.persistencia.mongo.bean.Pedido;
import aadd.persistencia.mongo.dao.EstadoPedidoDAO;
import aadd.persistencia.mongo.dao.ItemPedidoDAO;
import aadd.persistencia.mongo.dao.OpinionDAO;
import aadd.persistencia.mongo.dao.PedidoDAO;

public class ServicioGestionPedido {

	private static ServicioGestionPedido servicio;
	private static ZeppelinUMRemoto zeppelinumRemoto;

	public static ServicioGestionPedido getServicioGestionPedido() {
		if (servicio == null) {
			try {
				zeppelinumRemoto = (ZeppelinUMRemoto) InitialContextUtil.getInstance().lookup(
						"ejb:AADD2022/ZeppelinUMZamoraLujanEJB/ZeppelinUMRemoto!aadd.zeppelinum.ZeppelinUMRemoto");
			} catch (NamingException e) {
				e.printStackTrace();
			}
			servicio = new ServicioGestionPedido();
		}
		return servicio;
	}

	public boolean opinar(Integer usuario, Integer restaurante, String comentario, Double valoracion) {
		OpinionDAO opinionDAO = OpinionDAO.getOpinionDAO();

		Opinion o = new Opinion();
		o.setOpinion(comentario);
		o.setRestaurante(restaurante);
		o.setUsuario(usuario);
		o.setValor(valoracion);

		ObjectId id = opinionDAO.save(o);
		if (id != null) {
			// si se ha creado tengo que modificar la nota del restaurante
			EntityManager em = EntityManagerHelper.getEntityManager();
			try {
				em.getTransaction().begin();
				Restaurante r = RestauranteDAO.getRestauranteDAO().findById(restaurante);
				Integer numeroValoraciones = r.getNumValoraciones();
				Integer nuevoNumValoraciones = numeroValoraciones + 1;
				r.setNumValoraciones(nuevoNumValoraciones);
				if (r.getNumValoraciones() == 0) {
					r.setValoracionGlobal(valoracion);
				} else {
					Double nota = r.getValoracionGlobal();
					double nuevaGlobal = ((nota * numeroValoraciones) + valoracion) / nuevoNumValoraciones;
					r.setValoracionGlobal(nuevaGlobal);
				}
				em.getTransaction().commit();
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			} finally {
				if (em.getTransaction().isActive()) {
					em.getTransaction().rollback();
				}
				em.close();
			}
			return true;
		} else
			return false;
	}

	public List<OpinionDTO> findByUsuario(Integer usuario) {
		OpinionDAO opinionDAO = OpinionDAO.getOpinionDAO();
		List<Opinion> opiniones = opinionDAO.findByUsuario(usuario);
		List<OpinionDTO> opinionesDTO = new ArrayList<>();

		for (Opinion o : opiniones) {
			Restaurante r = RestauranteDAO.getRestauranteDAO().findById(o.getRestaurante());
			OpinionDTO opinionDTO = new OpinionDTO();
			opinionDTO.setNombreRestaurante(r.getNombre());
			opinionDTO.setValoracion(o.getValor());
			opinionDTO.setComentario(o.getOpinion());
			opinionesDTO.add(opinionDTO);
		}
		return opinionesDTO;
	}

	public List<OpinionDTO> findByRestaurante(Integer restaurante) {
		OpinionDAO opinionDAO = OpinionDAO.getOpinionDAO();
		List<Opinion> opiniones = opinionDAO.findByRestaurante(restaurante);
		List<OpinionDTO> opinionesDTO = new ArrayList<>();

		for (Opinion o : opiniones) {
			Usuario u = UsuarioDAO.getUsuarioDAO().findById(o.getUsuario());
			OpinionDTO opinionDTO = new OpinionDTO();
			opinionDTO.setNombreUsuario(u.getNombre());
			opinionDTO.setValoracion(o.getValor());
			opinionDTO.setComentario(o.getOpinion());
			opinionesDTO.add(opinionDTO);
		}
		return opinionesDTO;
	}

	public boolean crearPedido(Integer cliente, Integer repartidor, Integer restaurante, String direccion,
			String comentario, Double importe, List<ItemPedido> listaItemPedidos) {
		PedidoDAO pedidoDAO = PedidoDAO.getPedidoDAO();
		EstadoPedidoDAO estadoPedidoDAO = EstadoPedidoDAO.getEstadopedidoDAO();
		ItemPedidoDAO itemPedidoDAO = ItemPedidoDAO.getItemPedidoDAO();
		Date fechaHora = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fechaHora);
		calendar.add(Calendar.HOUR, 1);
		Date fechaEsperado = calendar.getTime();

		Pedido pedido = pedidoDAO.crearPedido(cliente, repartidor, restaurante, direccion, fechaHora, fechaEsperado,
				comentario, importe);

		estadoPedidoDAO.crearEstadoPedido(pedido.getId(), fechaHora, TipoEstado.ACEPTADO);

		for (ItemPedido itemPedido : listaItemPedidos) {
			itemPedidoDAO.crearItemPedido(itemPedido.getPlato(), pedido.getId(), itemPedido.getCantidad(),
					itemPedido.getCantidad() * itemPedido.getPrecioTotal());
		}
		zeppelinumRemoto.pedidoIniciado(pedido.toString());
		return true;
	}

	public List<PedidoDTO> findByCliente(Integer cliente) {
		PedidoDAO pedidoDAO = PedidoDAO.getPedidoDAO();
		List<Pedido> pedidos = pedidoDAO.findByCliente(cliente);
		List<PedidoDTO> pedidosDTO = new ArrayList<>();
		int cont = 1;
		for (Pedido p : pedidos) {
			Restaurante r = RestauranteDAO.getRestauranteDAO().findById(p.getRestaurante());
			PedidoDTO pedidoDTO = new PedidoDTO();
			pedidoDTO.setId(cont);
			pedidoDTO.setNombreRestaurante(r.getNombre());
			pedidoDTO.setFechaHora(p.getFechaHora());
			pedidoDTO.setDireccion(p.getDireccion());
			pedidoDTO.setImporte(p.getImporte());
			cont++;
			pedidosDTO.add(pedidoDTO);
		}
		return pedidosDTO;
	}
}
