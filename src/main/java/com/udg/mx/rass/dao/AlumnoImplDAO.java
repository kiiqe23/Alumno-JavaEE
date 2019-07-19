package com.udg.mx.rass.dao;

import com.udg.mx.rass.model.Alumno;

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
	            = "INSERT INTO alumno(codigo_alumno, nombre_alumno, carrera_alumno, calendario_alumno) VALUES(?, ?, ?, ?)";
	    private static final String SQL_UPDATE
	            = "";
	    private static final String SQL_DELETE
	            = "";
	    private static final String SQL_ALL
	            = " SELECT codigo_alumno,nombre_alumno,carrera_alumno,calendario_alumno"
	            + " FROM alumno order by codigo_alumno";
	    private static final String SQL_FIND_BY_DESCRIPCION
	            = " SELECT codigo_alumno,nombre_alumno,carrera_alumno,calendario_alumno"
	            + " FROM alumno WHERE nombre_alumno = :nombre_alumno";
	    private static final String SQL_FIND_BY_ID
	            = " SELECT codigo_alumno,nombre_alumno,carrera_alumno,calendario_alumno"
	            + " FROM alumno WHERE codigo_alumno = :codigo_alumno";

	    public AlumnoImplDAO() {
	        super(SQL_ALL, SQL_INSERT, SQL_UPDATE, SQL_DELETE);
	    }

	    @Override
	    Map<String, Object> convertObjToParam(Alumno object) {
	        Map<String, Object> params = new HashMap<>();
	        params.put("codigo_alumno", object.getCodigoAlumno());
	        params.put("nombre_alumno", object.getNombreAlumno());
	        params.put("carrera_alumno", object.getCarreraAlumno());
	        params.put("calendario_alumno", object.getCalendarioAlumno());
	        return params;
	    }

	    @Override
	    Alumno convertDbToOjb(ResultSet resultSet) throws SQLException {
	        Alumno unity = new Alumno();
	        unity.setCodigoAlumno(resultSet.getLong("codigo_alumno"));
	        unity.setNombreAlumno(resultSet.getString("nombre_alumno"));
	        unity.setCarreraAlumno(resultSet.getString("carrera_alumno"));
	        unity.setCalendarioAlumno(resultSet.getString("calendario_alumno"));
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
			// TODO Auto-generated method stub
			return find(SQL_FIND_BY_DESCRIPCION, nombreAlumno);
		}

		@Override
		public List<Alumno> buscartodo() {
			// TODO Auto-generated method stub
			return null;
		}

		
}
