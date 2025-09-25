package com.ejemplo.Repositorios.Universidad;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.ejemplo.DAOs.Universidad.ProgramaDAO;
import com.ejemplo.Modelos.Universidad.Programa;
import com.ejemplo.Repositorios.DB;

public class ProgramaRepo {

    // Caché en memoria (opcional)
    private final List<Programa> listado = new ArrayList<>();

    private final ProgramaDAO programaDAO;

    public ProgramaRepo() {
        this.programaDAO = new ProgramaDAO();
    }

    public ProgramaRepo(ProgramaDAO programaDAO) {
        this.programaDAO = programaDAO;
    }

    /* =================== CREATE / UPSERT =================== */
    public void save(Programa programa) {
        if (programa == null) {
            System.out.println("No se puede guardar un programa nulo.");
            return;
        }
        try (Connection conn = DB.get()) {
            programaDAO.guardar(conn, programa); // MERGE: inserta/actualiza
            // Sincroniza caché por id
            listado.removeIf(p -> p.getID() == programa.getID());
            listado.add(programa);
            System.out.println("PROGRAMA guardado correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al guardar PROGRAMA: " + e.getMessage());
        }
    }

    /* =================== READ =================== */
    public Optional<Programa> findById(long id) {
        try (Connection conn = DB.get()) {
            return programaDAO.cargarPorId(conn, id);
        } catch (SQLException e) {
            System.err.println("Error al cargar PROGRAMA: " + e.getMessage());
            return Optional.empty();
        }
    }

    public List<Programa> findAll() {
        try (Connection conn = DB.get()) {
            List<Programa> result = programaDAO.listar(conn);
            listado.clear();
            listado.addAll(result);
            return result;
        } catch (SQLException e) {
            System.err.println("Error al listar PROGRAMAS: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    /* =================== UPDATE =================== */
    // Si tienes actualizar explícito en ProgramaDAO, úsalo; de lo contrario, save(programa) con MERGE basta.
    public void update(Programa programa) {
        if (programa == null) {
            System.out.println("No se puede actualizar un programa nulo.");
            return;
        }
        try (Connection conn = DB.get()) {
            programaDAO.actualizar(conn, programa);
            listado.removeIf(p -> p.getID() == programa.getID());
            listado.add(programa);
            System.out.println("PROGRAMA actualizado correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al actualizar PROGRAMA: " + e.getMessage());
        }
        // Alternativa simple: save(programa);
    }

    /* =================== DELETE =================== */
    public void deleteById(long id) {
        try (Connection conn = DB.get()) {
            int rows = programaDAO.eliminar(conn, id);
            if (rows > 0) {
                listado.removeIf(p -> p.getID() == id);
                System.out.println("PROGRAMA eliminado correctamente.");
            } else {
                System.out.println("No se encontró el programa para eliminar.");
            }
        } catch (SQLException e) {
            System.err.println("Error al eliminar PROGRAMA: " + e.getMessage());
        }
    }

    public void delete(Programa programa) {
        if (programa == null) {
            System.out.println("No se puede eliminar un programa nulo.");
            return;
        }
        deleteById((long)programa.getID());
    }

    /* =================== CACHE HELPERS =================== */
    /** Vista de solo lectura del caché actual. */
    public List<Programa> getListado() {
        return Collections.unmodifiableList(listado);
    }

    /** Recarga el caché desde la BD. */
    public void reloadCache() {
        findAll();
    }
}
