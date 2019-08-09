package com.udg.mx.rass.ejb;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.annotation.PostConstruct;
import com.udg.mx.rass.dao.AlumnoDAO;
import com.udg.mx.rass.model.Alumno;





@LocalBean
@Named
@ViewScoped
public class AlumnoEjb implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8525306496002767622L;
	
	
	@EJB
	private AlumnoDAO dao;
	private Alumno estudiante;
	
	public List<Alumno> getAlumnos() {
		return dao.findAll();
	}
	
	@PostConstruct
	
	public void init() {
		estudiante = new Alumno();
	}
	
	public void guardar() {
		dao.insertar(estudiante);
	}

	public Alumno getAlumno() {
		return estudiante;
	}

	public void setAlumno(Alumno estudiante) {
		this.estudiante = estudiante;
	}

}
