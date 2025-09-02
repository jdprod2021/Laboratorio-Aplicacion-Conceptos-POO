package com.ejemplo.Servicios.Impl.Personas;

import java.util.List;

import com.ejemplo.Modelos.Personas.Persona;
import com.ejemplo.Repositorios.Personas.InscripcionesPersonas;
import com.ejemplo.Servicios.Personas.PersonaServicio;
import com.ejemplo.Servicios.Personas.PersonaValidator;

public class PersonaServiceImpl implements PersonaServicio{

    private final InscripcionesPersonas inscripcionesPersonas;
    private final PersonaValidator personaValidator = new PersonaValidatorImpl();

    public PersonaServiceImpl(InscripcionesPersonas inscripcionesPersonas) {
        this.inscripcionesPersonas = inscripcionesPersonas;
    }

    @Override
    public String inscribir(Persona persona) {

        personaValidator.validarCamposBasicos(persona);

        inscripcionesPersonas.inscribir(persona);

        return "Persona inscrita exitosamente";
    }
    
    @Override
    public List<Persona> listar() {
        return inscripcionesPersonas.getListado();
    }

}
