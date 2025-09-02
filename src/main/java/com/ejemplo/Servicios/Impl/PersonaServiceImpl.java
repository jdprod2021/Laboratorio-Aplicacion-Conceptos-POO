package com.ejemplo.Servicios.Impl;

import java.util.List;

import com.ejemplo.Modelos.Personas.Persona;
import com.ejemplo.Repositorios.InscripcionesPersonas;
import com.ejemplo.Servicios.PersonaServicio;
import com.ejemplo.Servicios.PersonaValidator;

public class PersonaServiceImpl implements PersonaServicio{

    private final InscripcionesPersonas inscripcionesPersonas;
    private final PersonaValidator personaValidator;

    public PersonaServiceImpl(InscripcionesPersonas inscripcionesPersonas, PersonaValidator personaValidator) {
        this.inscripcionesPersonas = inscripcionesPersonas;
        this.personaValidator = personaValidator;
    }

    @Override
    public String inscribir(String nombres, String apellidos, String email) {
        
        Persona persona = new Persona(nombres, apellidos, email);

        personaValidator.validarCamposBasicos(persona);

        inscripcionesPersonas.inscribir(persona);

        return "Persona inscrita exitosamente";
    }
    
    @Override
    public List<Persona> listar() {
        return inscripcionesPersonas.getListado();
    }

}
