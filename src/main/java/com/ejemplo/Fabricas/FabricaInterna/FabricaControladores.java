package com.ejemplo.Fabricas.FabricaInterna;

import com.ejemplo.Controladores.CursoControlador;
import com.ejemplo.Controladores.EstudianteControlador;
import com.ejemplo.Controladores.FacultadControlador;
import com.ejemplo.Controladores.ProfesorControlador;
import com.ejemplo.Controladores.ProgramaControlador;

public class FabricaControladores {
    
    private final FabricaDAO fabricaDAO;

    public FabricaControladores(FabricaDAO fabricaDAO) {
        this.fabricaDAO = fabricaDAO;
    }

    public ProfesorControlador crearControladorProfesor() {
        return new ProfesorControlador(fabricaDAO.crearProfesorDAO(), fabricaDAO.crearPersonaDAO());
    }

    public FacultadControlador crearControladorFacultad() {
        return new FacultadControlador(fabricaDAO.crearFacultadDAO(), fabricaDAO.crearPersonaDAO());
    }

    public ProgramaControlador crearControladorPrograma() {
        return new ProgramaControlador(fabricaDAO.crearProgramaDAO(), fabricaDAO.crearFacultadDAO());
    }

    public CursoControlador crearControladorCurso() {
        return new CursoControlador(fabricaDAO.crearCursoDAO(), fabricaDAO.crearProgramaDAO());
    }

    public EstudianteControlador crearControladorEstudiante() {
        return new EstudianteControlador(fabricaDAO.crearEstudianteDAO(), fabricaDAO.crearPersonaDAO(), fabricaDAO.crearProgramaDAO());
    }

}
