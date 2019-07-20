package com.udg.mx.rass.dao;

import com.udg.mx.rass.model.Alumno;
import com.udg.mx.rass.dao.DAOFactory;
import com.udg.mx.rass.dao.AlumnoDAO;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;


	//La subclase AlumnoImplDAO Hereda todas las variables y métodos definidos por la 
	//superclase DAOFactory y agrega sus propios elementos únicos, tambien implementa la interface
	//AlumnoDAO
@ManagedBean(name="db")
public class AlumnoImplDAO extends DAOFactory<Alumno> implements AlumnoDAO {

	//Consultas de SQL y metodos
	    private static final String SQL_INSERT 
	    		= "";//"INSERT INTO alumno (codigoalumno, nombrealumno, carreraalumno, calendarioalumno) VALUES(?, ?, ?, ?)";
	    private static final String SQL_UPDATE
	            = "";
	    private static final String SQL_DELETE
	            = "";
	    private static final String SQL_ALL
	            = " SELECT codigoalumno,nombrealumno,carreraalumno,calendarioalumno"
	            + " FROM alumno order by codigoalumno";
	    private static final String SQL_FIND_BY_DESCRIPCION
	            = " SELECT codigoalumno,nombrealumno,carreraalumno,calendarioalumno"
	            + " FROM alumno WHERE nombrealumno = :nombrealumno";
	    private static final String SQL_FIND_BY_ID
	            = " SELECT codigoalumno,nombrealumno,carreraalumno,calendarioalumno"
	            + " FROM alumno WHERE codigoalumno = :codigoalumno";

	    public AlumnoImplDAO() {
	        super(SQL_ALL, SQL_INSERT, SQL_UPDATE, SQL_DELETE);
	    }

	    @Override
	    Map<String, Object> convertObjToParam(Alumno object) {
	        Map<String, Object> params = new HashMap<>();
	        params.put("codigoalumno", object.getCodigoAlumno());
	        params.put("nombrealumno", object.getNombreAlumno());
	        params.put("carreraalumno", object.getCarreraAlumno());
	        params.put("calendarioalumno", object.getCalendarioAlumno());
	        return params;
	    }

	    @Override
	    Alumno convertDbToOjb(ResultSet resultSet) throws SQLException {
	        Alumno unity = new Alumno();
	        unity.setCodigoAlumno(resultSet.getLong("codigoalumno"));
	        unity.setNombreAlumno(resultSet.getString("nombrealumno"));
	        unity.setCarreraAlumno(resultSet.getString("carreraalumno"));
	        unity.setCalendarioAlumno(resultSet.getString("calendarioalumno"));
	        return unity;
	    }

// Se sobrescriben los metodos de la interface AlumnoDAO
		@Override
		public void insertar(Alumno estudiante) {
			// Se usan los metodos de DAOFactory y se manda el objeto estudiante al metodo
			insert(estudiante);
			
		}
		
// Se sobrescriben los metodos de la interface AlumnoDAO
		@Override
		public void editar(Alumno estudiante) {
			// Se usan los metodos de DAOFactory y se manda el objeto estudiante al metodo
			update(estudiante);
			
		}

		@Override
		public void eliminar(Alumno estudiante) {
			delete(estudiante);
			
		}

		@Override
		public Alumno buscar(Object id) {
			return find(SQL_FIND_BY_ID, id);
		}


		@Override
		public Alumno buscarPorNombreAlumno(String nombreAlumno) {
			
			return find(SQL_FIND_BY_DESCRIPCION, nombreAlumno);
		}

		@Override
		public List<Alumno> findAll() {
			
			return list(SQL_ALL);
		}

		
}
