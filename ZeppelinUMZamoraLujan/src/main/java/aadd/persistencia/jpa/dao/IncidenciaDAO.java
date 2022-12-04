package aadd.persistencia.jpa.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import aadd.persistencia.dto.IncidenciaDTO;
import aadd.persistencia.dto.UsuarioDTO;
import aadd.persistencia.jpa.EntityManagerHelper;
import aadd.persistencia.jpa.bean.Incidencia;
import aadd.persistencia.jpa.bean.TipoUsuario;
import aadd.persistencia.jpa.bean.Usuario;

public class IncidenciaDAO extends ExtensionDAO<Incidencia> {

    public IncidenciaDAO(Class<Incidencia> persistedClass) {
        super(persistedClass);
    }

    private static IncidenciaDAO incidenciaDAO;

    public static IncidenciaDAO getIncidenciaDAO() {
        if (incidenciaDAO == null)
            incidenciaDAO = new IncidenciaDAO(Incidencia.class);
        return incidenciaDAO;
    }
    
    public List<IncidenciaDTO> transformarToDTO(List<Incidencia> incidencias) {
        List<IncidenciaDTO> inc = new ArrayList<IncidenciaDTO>();
        for (Incidencia i : incidencias) {
        	inc.add(new IncidenciaDTO(i.getId(), i.getFechaCreacion(), i.getDescripcion(), i.getFechaCierre(), i.getComentarioCierre()));
        }
        return inc;
    }
  
    public List<UsuarioDTO> findByFechaCreacion(LocalDate fechaCreacion) {
        try {
            Query query = EntityManagerHelper.getEntityManager().createNamedQuery("Incidencia.findByFechaCreacion");
            query.setParameter("fechaCreacion", fechaCreacion);
            return transformarToDTO(query.getResultList());
        } catch (RuntimeException re) {
            throw re;
        }
    }

   
}