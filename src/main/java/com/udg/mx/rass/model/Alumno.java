package com.udg.mx.rass.model;

import java.io.Serializable;

public class Alumno implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long codigoAlumno;
    private String nombreAlumno;
    private String carreraAlumno;
    private String calendarioAlumno;
    
    
    public Alumno() {
    	
    }
    
    
	public Long getCodigoAlumno() {
		return codigoAlumno;
	}
	public void setCodigoAlumno(Long codigoAlumno) {
		this.codigoAlumno = codigoAlumno;
	}
	public String getNombreAlumno() {
		return nombreAlumno;
	}
	public void setNombreAlumno(String nombreAlumno) {
		this.nombreAlumno = nombreAlumno;
	}
	public String getCarreraAlumno() {
		return carreraAlumno;
	}
	public void setCarreraAlumno(String carreraAlumno) {
		this.carreraAlumno = carreraAlumno;
	}
	public String getCalendarioAlumno() {
		return calendarioAlumno;
	}
	public void setCalendarioAlumno(String calendarioAlumno) {
		this.calendarioAlumno = calendarioAlumno;
	}
  
    
    

}
