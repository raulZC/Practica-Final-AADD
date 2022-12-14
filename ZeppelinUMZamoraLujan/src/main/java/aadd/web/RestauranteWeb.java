package aadd.web;

import java.io.IOException;
import java.io.Serializable;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.event.map.PointSelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import com.mongodb.client.model.geojson.Point;

import aadd.persistencia.dto.RestauranteDTO;
import aadd.persistencia.jpa.bean.CategoriaRestaurante;
import aadd.persistencia.jpa.dao.RestauranteDAO;
import aadd.persistencia.mongo.bean.Direccion;
import aadd.persistencia.mongo.dao.DireccionDAO;
import aadd.web.usuario.UserSessionWeb;
import aadd.zeppelinum.ServicioGestionPlataforma;

@Named
@ViewScoped
public class RestauranteWeb implements Serializable {

	private static final long serialVersionUID = 1L;

	private Double latitudSelected;
	private Double longitudSelected;
	private String nombreRestaurante;
	private String calle;
	private String codigoPostal;
	private Integer numero;
	private String ciudad;
	private List<CategoriaRestaurante> categoriasDisp;
	private List<Integer> categoriasSelec;
	private MapModel<Integer> simpleModel;
	@Inject
	private FacesContext facesContext;
	@Inject
	private UserSessionWeb usuarioSesion;
	private Integer responsableId;
	private ServicioGestionPlataforma servicio;

	public RestauranteWeb() {
		simpleModel = new DefaultMapModel<Integer>();
		servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();
	}

	@PostConstruct
	public void obtenerUsuarioSesion() {
		responsableId = usuarioSesion.getUsuario().getId();
		categoriasDisp = servicio.getAllCategoriasRest();
		
		List<RestauranteDTO> restaurantes = RestauranteDAO.getRestauranteDAO().findByUser(responsableId);
		// Añadimos las marcas de todos los restaurantes del usuario
		for (RestauranteDTO rest : restaurantes) {
			
			Point point = DireccionDAO.getDireccionDAO().findByRestaurante(rest.getId()).getCoordenadas();
			
			double longitud = point.getCoordinates().getValues().get(0); // primer elemento de la lista de coordenadas es la longitud
			double latitud = point.getCoordinates().getValues().get(1);// segundo elemento de la lista de coordenadas es la latitud
			simpleModel.addOverlay(new Marker<Integer>(new LatLng(latitud, longitud), rest.getNombre(), rest.getId()));
		}
		
	}

	public void onPointSelect(PointSelectEvent event) {
		LatLng latlng = event.getLatLng();
		latitudSelected = latlng.getLat();
		longitudSelected = latlng.getLng();
	}

	public void onMarkerRestauranteSelect(OverlaySelectEvent<Integer> event) {
		Marker<Integer> marker = (Marker<Integer>) event.getOverlay();
		Integer restauranteSelectedId = (Integer) marker.getData();
		try {
			String contextoURL = facesContext.getExternalContext().getApplicationContextPath();
			facesContext.getExternalContext()
					.redirect(contextoURL + "/restaurante/formPlatos.xhtml?id=" + restauranteSelectedId);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void crearRestaurante() {
		Integer restauranteId = servicio.registrarRestaurante(nombreRestaurante, responsableId, calle, codigoPostal,
				numero, ciudad, latitudSelected, longitudSelected,categoriasSelec);

		if (restauranteId == null) {
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "El restaurante no se ha podido crear", ""));
		} else {
			LatLng coord = new LatLng(latitudSelected, longitudSelected);
			simpleModel.addOverlay(new Marker<Integer>(coord, nombreRestaurante, restauranteId));
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Restaurante creado correctamente", ""));
		}
	}

	// getters y setters necesarios
	public String getNombreRestaurante() {
		return nombreRestaurante;
	}

	public void setNombreRestaurante(String nombreRestaurante) {
		this.nombreRestaurante = nombreRestaurante;
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public MapModel<Integer> getSimpleModel() {
		return simpleModel;
	}

	public void setSimpleModel(MapModel<Integer> simpleModel) {
		this.simpleModel = simpleModel;
	}

	public FacesContext getFacesContext() {
		return facesContext;
	}

	public void setFacesContext(FacesContext facesContext) {
		this.facesContext = facesContext;
	}

	public UserSessionWeb getUsuarioSesion() {
		return usuarioSesion;
	}

	public void setUsuarioSesion(UserSessionWeb usuarioSesion) {
		this.usuarioSesion = usuarioSesion;
	}


	public Double getLatitudSelected() {
		return latitudSelected;
	}

	public Double getLongitudSelected() {
		return longitudSelected;
	}

	public Integer getResponsableId() {
		return responsableId;
	}

	public void setResponsableId(Integer responsableId) {
		this.responsableId = responsableId;
	}



	public List<Integer> getCategoriasSelec() {
		return categoriasSelec;
	}

	public void setCategoriasSelec(List<Integer> categoriasSelec) {
		this.categoriasSelec = categoriasSelec;
	}

	public List<CategoriaRestaurante> getCategoriasDisp() {
		return categoriasDisp;
	}

	public void setCategoriasDisp(List<CategoriaRestaurante> categoriasDisp) {
		this.categoriasDisp = categoriasDisp;
	}

	
	
}