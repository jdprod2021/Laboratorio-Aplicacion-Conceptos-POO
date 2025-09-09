package com.ejemplo.Repositorios.Personas;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.ejemplo.DAOs.Personas.ProfesorDAO;
import com.ejemplo.Modelos.Personas.Profesor;
import com.ejemplo.Repositorios.DB;

public class ProfesorRepo {

    // Caché en memoria (opcional)
    private final List<Profesor> listado = new ArrayList<>();

    private final ProfesorDAO profesorDAO = new ProfesorDAO();

    /* =================== CREATE / UPSERT =================== */
    public void save(Profesor profesor) {
        if (profesor == null) {
            System.out.println("No se puede guardar un profesor nulo.");
            return;
        }
        try (Connection conn = DB.get()) {
            profesorDAO.guardar(conn, profesor);
            listado.removeIf(p -> p.getId() == profesor.getId());
            listado.add(profesor);
            System.out.println("Información guardada correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al guardar profesor: " + e.getMessage());
        }
    }

    /* =================== READ =================== */
    public Optional<Profesor> findById(long id) {
        try (Connection conn = DB.get()) {
            return profesorDAO.cargarPorId(conn, id);
        } catch (SQLException e) {
            System.err.println("Error al cargar profesor: " + e.getMessage());
            return Optional.empty();
        }
    }

    public List<Profesor> findAll() {
        try (Connection conn = DB.get()) {
            List<Profesor> result = profesorDAO.listar(conn);
            listado.clear();
            listado.addAll(result);
            return result;
        } catch (SQLException e) {
            System.err.println("Error al listar profesores: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    /* =================== UPDATE =================== */
    
    public void update(Profesor profesor) {
        if (profesor == null) {
            System.out.println("No se puede actualizar un profesor nulo.");
            return;
        }
        // Opción A (explícito):
        try (Connection conn = DB.get()) {
            profesorDAO.actualizar(conn, profesor);
            listado.removeIf(p -> p.getId() == profesor.getId());
            listado.add(profesor);
            System.out.println("Información actualizada correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al actualizar profesor: " + e.getMessage());
        }
        
    }

    /* =================== DELETE =================== */
    public void deleteById(long id) {
        try (Connection conn = DB.get()) {
            int rows = profesorDAO.eliminar(conn, id);
            if (rows > 0) {
                listado.removeIf(p -> p.getId() == id);
                System.out.println("Información eliminada correctamente.");
            } else {
                System.out.println("No se encontró el profesor para eliminar.");
            }
        } catch (SQLException e) {
            System.err.println("Error al eliminar profesor: " + e.getMessage());
        }
    }

    public void delete(Profesor profesor) {
        if (profesor == null) {
            System.out.println("No se puede eliminar un profesor nulo.");
            return;
        }
        deleteById((long)profesor.getId());
    }

    /* =================== CACHE HELPERS (opcionales) =================== */
    
    public List<Profesor> getListado() {
        return Collections.unmodifiableList(listado);
    }

    
    public void reloadCache() {
        findAll();
    }
}
