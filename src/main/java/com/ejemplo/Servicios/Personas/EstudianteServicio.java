package com.ejemplo.Servicios.Personas;

import java.util.List;

import com.ejemplo.Modelos.Personas.Estudiante;

public interface EstudianteServicio {

    String inscribir(Estudiante estudiante);
    
    List<Estudiante> listarEstudiantes();

}
