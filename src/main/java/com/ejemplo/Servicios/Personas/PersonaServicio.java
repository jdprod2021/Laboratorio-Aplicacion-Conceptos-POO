package com.ejemplo.Servicios.Personas;

import java.util.List;

import com.ejemplo.Modelos.Personas.Persona;

public interface PersonaServicio {

    String inscribir(Persona persona);

    List<Persona> listar();

}

