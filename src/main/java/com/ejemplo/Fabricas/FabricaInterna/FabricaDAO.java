package com.ejemplo.Fabricas.FabricaInterna;

import javax.sql.DataSource;

import com.ejemplo.DAOs.Interfaces.CursoDAO;
import com.ejemplo.DAOs.Interfaces.EstudianteDAO;
import com.ejemplo.DAOs.Interfaces.FacultadDAO;
import com.ejemplo.DAOs.Interfaces.HoraDAO;
import com.ejemplo.DAOs.Interfaces.PersonaDAO;
import com.ejemplo.DAOs.Interfaces.ProfesorDAO;
import com.ejemplo.DAOs.Interfaces.ProgramaDAO;

public abstract class FabricaDAO {
    
    protected final DataSource dataSource;
    private static FabricaDAO fabricaDAO;

    protected FabricaDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    abstract public ProfesorDAO crearProfesorDAO();
    abstract public FacultadDAO crearFacultadDAO();
    abstract public ProgramaDAO crearProgramaDAO();
    abstract public CursoDAO crearCursoDAO();
    abstract public PersonaDAO crearPersonaDAO();
    abstract public EstudianteDAO crearEstudianteDAO();
    abstract public HoraDAO crearHoraDAO();

    public static FabricaDAO of(String vendor, DataSource dataSource) {
        if(fabricaDAO == null){
            synchronized (FabricaDAO.class) {
                if(fabricaDAO == null){
                    fabricaDAO = switch (vendor.toUpperCase()) {
                        case "H2" -> new FabricaDAOH2(dataSource);
                        case "MYSQL" -> new FabricaDAOMySQL(dataSource);
                        case "ORACLE" -> new FabricaDAOOracle(dataSource);
                        default -> throw new IllegalArgumentException("Vendor no soportado: " + vendor);
                    };   
                } 
            }
        }
        
        return fabricaDAO;
    }

}
