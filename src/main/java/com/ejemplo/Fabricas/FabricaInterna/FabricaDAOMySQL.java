package com.ejemplo.Fabricas.FabricaInterna;

import javax.sql.DataSource;

import com.ejemplo.DAOs.Implementaciones.MySQL.CursoDAOMySQL;
import com.ejemplo.DAOs.Implementaciones.MySQL.EstudianteDAOMySQL;
import com.ejemplo.DAOs.Implementaciones.MySQL.FacultadDAOMySQL;
import com.ejemplo.DAOs.Implementaciones.MySQL.PersonaDAOMySQL;
import com.ejemplo.DAOs.Implementaciones.MySQL.ProfesorDAOMySQL;
import com.ejemplo.DAOs.Implementaciones.MySQL.ProgramaDAOMySQL;
import com.ejemplo.DAOs.Interfaces.CursoDAO;
import com.ejemplo.DAOs.Interfaces.EstudianteDAO;
import com.ejemplo.DAOs.Interfaces.FacultadDAO;
import com.ejemplo.DAOs.Interfaces.PersonaDAO;
import com.ejemplo.DAOs.Interfaces.ProfesorDAO;
import com.ejemplo.DAOs.Interfaces.ProgramaDAO;

public class FabricaDAOMySQL extends FabricaDAO {


    public FabricaDAOMySQL(DataSource dataSource) {
        super(dataSource);
    }
    
    @Override
    public ProfesorDAO crearProfesorDAO() {
        return new ProfesorDAOMySQL(dataSource);
    }

    @Override
    public FacultadDAO crearFacultadDAO() {
        return new FacultadDAOMySQL(dataSource);
    }

    @Override
    public ProgramaDAO crearProgramaDAO() {
        return new ProgramaDAOMySQL(dataSource);
    }
    
    @Override
    public CursoDAO crearCursoDAO() {
        return new CursoDAOMySQL(dataSource);
    }

    @Override
    public PersonaDAO crearPersonaDAO() {
        return new PersonaDAOMySQL(dataSource);
    }

    @Override
    public EstudianteDAO crearEstudianteDAO() {
        return new EstudianteDAOMySQL(dataSource);
    }
}
