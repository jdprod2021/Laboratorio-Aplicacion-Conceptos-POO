package com.ejemplo.Servicios.Impl.Personas;

import java.util.List;

import com.ejemplo.Modelos.Personas.Estudiante;
import com.ejemplo.Repositorios.Personas.EstudianteRepo;
import com.ejemplo.Servicios.Personas.EstudianteServicio;
import com.ejemplo.Servicios.Personas.PersonaValidator;

public class EstudianteImpl implements EstudianteServicio{


    private final EstudianteRepo estudianteRepo;
    private final PersonaValidator personaValidator = new PersonaValidatorImpl();


    public EstudianteImpl(EstudianteRepo estudianteRepo) {
        this.estudianteRepo = estudianteRepo;
    }

    @Override
    public String inscribir(Estudiante estudiante) {
        
        personaValidator.validarCamposBasicos(estudiante);
        estudianteRepo.guardarInformacion(estudiante);
        return "Estudiante inscrito exitosamente";
    }

    @Override
    public List<Estudiante> listarEstudiantes() {
        return estudianteRepo.getListado();
    }
}
