package com.ejemplo.Servicios;

import java.util.List;

import com.ejemplo.Modelos.Personas.Persona;

public interface PersonaServicio {

    String inscribir(String nombres, String apellidos, String email);

    List<Persona> listar();

}

