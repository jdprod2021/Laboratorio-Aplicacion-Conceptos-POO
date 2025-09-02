package com.ejemplo.Servicios.Impl.Personas;

import java.util.List;

import com.ejemplo.Modelos.Personas.Profesor;
import com.ejemplo.Repositorios.Personas.ProfesorRepo;
import com.ejemplo.Servicios.Personas.PersonaValidator;
import com.ejemplo.Servicios.Personas.ProfesorServicio;

public class ProfesorImpl implements ProfesorServicio{

    private final ProfesorRepo profesorRepo;
    private final PersonaValidator personaValidator = new PersonaValidatorImpl();

    public ProfesorImpl(ProfesorRepo profesorRepo) {
        this.profesorRepo = profesorRepo;
    }

    @Override
    public String inscribir(Profesor profesor) {

        personaValidator.validarCamposBasicos(profesor);
        profesorRepo.guardarInformacion(profesor);
        return "Profesor inscrito exitosamente";
    }

    @Override
    public List<Profesor> listar() {
        return profesorRepo.getListado();
    }

}
