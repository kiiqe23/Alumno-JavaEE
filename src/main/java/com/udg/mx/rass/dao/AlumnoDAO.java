package com.udg.mx.rass.dao;

import java.util.List;

import com.udg.mx.rass.model.Alumno;


//interface base que se va a usar, na clase interface puede ser implementada
//por cualquier número de clases, permitiendo a cada clase compartir el 
//interfaz de programación sin tener que ser consciente
//de la implementación que hagan las otras clases que implementen el interface.
public interface AlumnoDAO {

// create (clase identificador)
//         (clase Objeto)
    void insertar(Alumno estudiante);

    void editar(Alumno estudiante);

    void eliminar(Alumno estudiante);

    Alumno buscar(Object id);

    List<Alumno> buscartodo();

    Alumno buscarPorNombreAlumno(String nombreAlumno);
}
