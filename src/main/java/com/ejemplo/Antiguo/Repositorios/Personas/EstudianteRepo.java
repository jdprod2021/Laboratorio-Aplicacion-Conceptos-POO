package com.ejemplo.Repositorios.Personas;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.ejemplo.DAOs.Personas.EstudianteDAO;
import com.ejemplo.Modelos.Personas.Estudiante;
import com.ejemplo.Repositorios.DB;

public class EstudianteRepo {

    private final List<Estudiante> listado = new ArrayList<>();

    private final EstudianteDAO estudianteDAO = new EstudianteDAO();

    /* =================== CREATE / UPSERT =================== */
    public void save(Estudiante estudiante) {
        if (estudiante == null) {
            System.out.println("No se puede guardar un estudiante nulo.");
            return;
        }
        try (Connection conn = DB.get()) {
            estudianteDAO.guardar(conn, estudiante);   
            listado.removeIf(e -> e.getId() == estudiante.getId());
            listado.add(estudiante);
            System.out.println("Estudiante guardado correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al guardar estudiante: " + e.getMessage());
        }
    }

    /* =================== READ =================== */
    public Optional<Estudiante> findById(long id) {
        try (Connection conn = DB.get()) {
            return estudianteDAO.cargar(conn, id);
        } catch (SQLException e) {
            System.err.println("Error al cargar estudiante: " + e.getMessage());
            return Optional.empty();
        }
    }

    public List<Estudiante> findAll() {
        try (Connection conn = DB.get()) {
            List<Estudiante> result = estudianteDAO.listar(conn);
            
            listado.clear();
            listado.addAll(result);
            return result;
        } catch (SQLException e) {
            System.err.println("Error al listar estudiantes: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    /* =================== UPDATE =================== */

    public void update(Estudiante estudiante) {
        if (estudiante == null) {
            System.out.println("No se puede actualizar un estudiante nulo.");
            return;
        }
        save(estudiante);
    }

    /* =================== DELETE =================== */
    public void deleteById(long id) {
        try (Connection conn = DB.get()) {
            estudianteDAO.eliminar(conn, id);
            listado.removeIf(e -> e.getId() == id);
            System.out.println("Estudiante eliminado correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al eliminar estudiante: " + e.getMessage());
        }
    }

    public void delete(Estudiante estudiante) {
        if (estudiante == null) {
            System.out.println("No se puede eliminar un estudiante nulo.");
            return;
        }
        deleteById((long)estudiante.getId());
    }

    /* =================== CACHE HELPERS (opcionales) =================== */

    public List<Estudiante> getListadoCache() {
        return Collections.unmodifiableList(listado);
    }


    public void reloadCache() {
        findAll();
    }
}