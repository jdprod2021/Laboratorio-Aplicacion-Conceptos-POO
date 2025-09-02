package com.ejemplo.Servicios.Personas;

import java.util.List;

import com.ejemplo.Modelos.Personas.Profesor;

public interface ProfesorServicio {

    String inscribir(Profesor profesor);
    
    List<Profesor> listar();

}
