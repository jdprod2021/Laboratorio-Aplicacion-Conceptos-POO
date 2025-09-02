package com.ejemplo.Repositorios.Universidad;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.ejemplo.DAOs.Universidad.ProgramaDAO;
import com.ejemplo.Modelos.Universidad.Programa;
import com.ejemplo.Repositorios.DB;

public class ProgramaRepo {

    List<Programa> listado;
    private final ProgramaDAO programaDAO;

    public ProgramaRepo(ProgramaDAO programaDAO) {
        this.programaDAO = programaDAO;
    }

    public List<Programa> getListado() {
        return listado;
    }

    public void Crear(Programa programa) {
        if (programa != null) {
            listado.add(programa);
            guardarInformacion(programa);
        } else {
            System.out.println("No se puede crear un programa nulo.");
        }
    }

    public void eliminar(Programa programa) {
        
        if (programa != null) {

            listado.remove(programa);

            try(Connection conn = DB.get()){
                programaDAO.eliminar(conn, (int)programa.getID());
                System.out.println("Información eliminada correctamente.");
            }catch(SQLException e){
                System.err.println("Error al eliminar información: " + e.getMessage());
            }
        } else {
            System.out.println("No se puede eliminar un programa nulo.");
        }
    }

    public void guardarInformacion(Programa programa) {
        try (Connection conn = DB.get()) {
            programaDAO.guardar(conn, programa);
        } catch (SQLException e) {
            System.err.println("Error al guardar PROGRAMA: " + e.getMessage());
        }
    }

    public Optional<Programa> cargarInformacion(int id) {
        try (Connection conn = DB.get()) {
            return programaDAO.cargar(conn, id);
        } catch (SQLException e) {
            System.err.println("Error al cargar PROGRAMA: " + e.getMessage());
        }
        return Optional.empty();
    }

}
