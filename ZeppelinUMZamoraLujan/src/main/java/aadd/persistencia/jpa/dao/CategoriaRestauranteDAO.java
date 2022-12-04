package aadd.persistencia.jpa.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import aadd.persistencia.dto.IncidenciaDTO;
import aadd.persistencia.dto.UsuarioDTO;
import aadd.persistencia.jpa.EntityManagerHelper;
import aadd.persistencia.jpa.bean.CategoriaRestaurante;
import aadd.persistencia.jpa.bean.Incidencia;
import aadd.persistencia.jpa.bean.TipoUsuario;
import aadd.persistencia.jpa.bean.Usuario;

public class CategoriaRestauranteDAO extends ExtensionDAO<CategoriaRestaurante> {

    public CategoriaRestauranteDAO(Class<CategoriaRestaurante> persistedClass) {
        super(persistedClass);
    }

    private static CategoriaRestauranteDAO categoriaRestauranteDAO;

    public static CategoriaRestauranteDAO getIncidenciaDAO() {
        if (categoriaRestauranteDAO == null)
            categoriaRestauranteDAO = new CategoriaRestauranteDAO(CategoriaRestaurante.class);
        return categoriaRestauranteDAO;
    }
    

   
}