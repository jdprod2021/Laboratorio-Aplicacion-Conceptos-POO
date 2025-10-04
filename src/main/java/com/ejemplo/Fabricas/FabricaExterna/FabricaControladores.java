package com.ejemplo.Fabricas.FabricaExterna;

import com.ejemplo.Controladores.CursoControlador;
import com.ejemplo.Controladores.EstudianteControlador;
import com.ejemplo.Controladores.FacultadControlador;
import com.ejemplo.Controladores.HoraControlador;
import com.ejemplo.Controladores.ProfesorControlador;
import com.ejemplo.Controladores.ProgramaControlador;
import com.ejemplo.Fabricas.FabricaInterna.FabricaDAO;

public class FabricaControladores {
    
    private final FabricaDAO fabricaDAO;
    private static FabricaControladores fabricaControladores;

    private FabricaControladores(FabricaDAO fabricaDAO) {
        this.fabricaDAO = fabricaDAO;
    }

    public static FabricaControladores crearFabricaControladores(FabricaDAO fabricaDAO){
        if (fabricaControladores == null) {
            synchronized (FabricaControladores.class) {
                if (fabricaControladores == null) {
                    fabricaControladores = new FabricaControladores(fabricaDAO);
                }
            }
        }
        return fabricaControladores;
    }

    public ProfesorControlador crearControladorProfesor() {
        return ProfesorControlador.crearProfesorControlador(fabricaDAO.crearProfesorDAO(), fabricaDAO.crearPersonaDAO());
    }

    public FacultadControlador crearControladorFacultad() {
        return FacultadControlador.crearControlador(fabricaDAO.crearFacultadDAO(), fabricaDAO.crearPersonaDAO());
    }

    public ProgramaControlador crearControladorPrograma() {
        return ProgramaControlador.crearProgramaControlador(fabricaDAO.crearProgramaDAO(), fabricaDAO.crearFacultadDAO());
    }

    public CursoControlador crearControladorCurso() {
        return CursoControlador.crearCursoControlador(fabricaDAO.crearCursoDAO(), fabricaDAO.crearProgramaDAO());
    }

    public EstudianteControlador crearControladorEstudiante() {
        return EstudianteControlador.crearEstudianteControlador(fabricaDAO.crearEstudianteDAO(), fabricaDAO.crearPersonaDAO(), fabricaDAO.crearProgramaDAO());
    }

    public HoraControlador crearControladorHora() {
        return HoraControlador.crearHoraControlador(fabricaDAO.crearHoraDAO());
    }

}
