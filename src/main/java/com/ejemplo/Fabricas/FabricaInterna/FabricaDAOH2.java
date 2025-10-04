package com.ejemplo.Fabricas.FabricaInterna;

import javax.sql.DataSource;

import com.ejemplo.DAOs.Implementaciones.H2.CursoDAOH2;
import com.ejemplo.DAOs.Implementaciones.H2.EstudianteDAOH2;
import com.ejemplo.DAOs.Implementaciones.H2.FacultadDAOH2;
import com.ejemplo.DAOs.Implementaciones.H2.PersonaDAOH2;
import com.ejemplo.DAOs.Implementaciones.H2.ProfesorDAOH2;
import com.ejemplo.DAOs.Implementaciones.H2.ProgramaDAOH2;
import com.ejemplo.DAOs.Interfaces.CursoDAO;
import com.ejemplo.DAOs.Interfaces.EstudianteDAO;
import com.ejemplo.DAOs.Interfaces.FacultadDAO;
import com.ejemplo.DAOs.Interfaces.PersonaDAO;
import com.ejemplo.DAOs.Interfaces.ProfesorDAO;
import com.ejemplo.DAOs.Interfaces.ProgramaDAO;

public class FabricaDAOH2 extends FabricaDAO{

    FabricaDAOH2(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public ProfesorDAO crearProfesorDAO() {
        return ProfesorDAOH2.crearProfesorDAOH2(dataSource);
    }

    @Override
    public FacultadDAO crearFacultadDAO() {
        return FacultadDAOH2.crearFacultadDAOH2(dataSource);
    }

    @Override
    public ProgramaDAO crearProgramaDAO() {
        return ProgramaDAOH2.crearProgramaDAOH2(dataSource);
    }

    @Override
    public CursoDAO crearCursoDAO() {
        return CursoDAOH2.crearCursoDAOH2(dataSource);
    }

    @Override
    public PersonaDAO crearPersonaDAO() {
        return PersonaDAOH2.crearPersonaDAOH2(dataSource);
    }

    @Override
    public EstudianteDAO crearEstudianteDAO() {
        return EstudianteDAOH2.crearEstudianteDAOH2(dataSource);
    }

}
