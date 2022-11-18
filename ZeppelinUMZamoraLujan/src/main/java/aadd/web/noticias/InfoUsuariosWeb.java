package aadd.web.noticias;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.Push;
import org.omnifaces.cdi.PushContext;

import aadd.persistencia.jpa.bean.TipoUsuario;
import aadd.zeppelinum.ServicioGestionPlataforma;

@Named
@ViewScoped
public class InfoUsuariosWeb implements Serializable {
	@Inject
	@Push(channel = "canalNoticias")
	private PushContext canal;
	@Inject
	private FacesContext facesContext;

	protected String message;
	protected List<String> tipos;

	public void submit() {
		if (message == null || message.isBlank()) {
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Indique el mensaje que desea enviar", ""));
			return;
		}
		if (tipos == null || tipos.isEmpty()) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Debe indicar los tipos de usuarios que deben recibir el mensaje", ""));
			return;
		}
		List<TipoUsuario> enumtipos = new ArrayList<>();
		for (String t : tipos) {
			enumtipos.add(TipoUsuario.valueOf(t));
		}
		List<Integer> usuarios = ServicioGestionPlataforma.getServicioGestionPlataforma()
				.getIdUsuariosByTipo(enumtipos);
		canal.send(message, usuarios);
		message = null;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<String> getTipos() {
		return tipos;
	}

	public void setTipos(List<String> tipos) {
		this.tipos = tipos;
	}
}
