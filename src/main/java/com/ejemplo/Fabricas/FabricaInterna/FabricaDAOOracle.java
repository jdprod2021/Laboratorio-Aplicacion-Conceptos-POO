package com.ejemplo.Fabricas.FabricaInterna;

import com.ejemplo.DAOs.Implementaciones.Oracle.CursoDAOOracle;
import com.ejemplo.DAOs.Implementaciones.Oracle.EstudianteDAOOracle;
import com.ejemplo.DAOs.Implementaciones.Oracle.FacultadDAOOracle;
import com.ejemplo.DAOs.Implementaciones.Oracle.PersonaDAOOracle;
import com.ejemplo.DAOs.Implementaciones.Oracle.ProfesorDAOOracle;
import com.ejemplo.DAOs.Implementaciones.Oracle.ProgramaDAOOracle;
import com.ejemplo.DAOs.Interfaces.CursoDAO;
import com.ejemplo.DAOs.Interfaces.EstudianteDAO;
import com.ejemplo.DAOs.Interfaces.FacultadDAO;
import com.ejemplo.DAOs.Interfaces.PersonaDAO;
import com.ejemplo.DAOs.Interfaces.ProfesorDAO;
import com.ejemplo.DAOs.Interfaces.ProgramaDAO;

public class FabricaDAOOracle extends FabricaDAO {

    public FabricaDAOOracle(javax.sql.DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public ProfesorDAO crearProfesorDAO() {
        return new ProfesorDAOOracle(dataSource);
    }

    @Override
    public FacultadDAO crearFacultadDAO() {
        return new FacultadDAOOracle(dataSource);
    }

    @Override
    public ProgramaDAO crearProgramaDAO() {
        return new ProgramaDAOOracle(dataSource);
    }

    @Override
    public CursoDAO crearCursoDAO() {
        return new CursoDAOOracle(dataSource);
    }

    @Override
    public PersonaDAO crearPersonaDAO() {
        return new PersonaDAOOracle(dataSource);
    }

    @Override
    public EstudianteDAO crearEstudianteDAO() {
        return new EstudianteDAOOracle(dataSource);
    }

}
