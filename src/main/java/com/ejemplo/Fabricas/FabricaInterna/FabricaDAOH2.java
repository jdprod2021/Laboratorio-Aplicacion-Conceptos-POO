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
        return new ProfesorDAOH2(dataSource);
    }

    @Override
    public FacultadDAO crearFacultadDAO() {
        return new FacultadDAOH2(dataSource);
    }

    @Override
    public ProgramaDAO crearProgramaDAO() {
        return new ProgramaDAOH2(dataSource);
    }

    @Override
    public CursoDAO crearCursoDAO() {
        return new CursoDAOH2(dataSource);
    }

    @Override
    public PersonaDAO crearPersonaDAO() {
        return new PersonaDAOH2(dataSource);
    }

    @Override
    public EstudianteDAO crearEstudianteDAO() {
        return new EstudianteDAOH2(dataSource);
    }

}
