package com.ejemplo.Repositorios.Universidad;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.ejemplo.DAOs.Universidad.FacultadDAO;
import com.ejemplo.Modelos.Universidad.Facultad;
import com.ejemplo.Repositorios.DB;

public class FacultadRepo {

    // Caché en memoria (opcional)
    private final List<Facultad> listado = new ArrayList<>();
    private final FacultadDAO facultadDAO;

    public FacultadRepo() {
        this.facultadDAO = new FacultadDAO();
    }

    /* =================== CREATE / UPSERT =================== */
    public void save(Facultad facultad) {
        if (facultad == null) {
            System.out.println("No se puede guardar una facultad nula.");
            return;
        }
        try (Connection conn = DB.get()) {
            facultadDAO.guardar(conn, facultad); 
            // sincroniza caché por id
            listado.removeIf(f -> f.getID() == facultad.getID());
            listado.add(facultad);
            System.out.println("FACULTAD guardada correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al guardar FACULTAD: " + e.getMessage());
        }
    }

    /* =================== READ =================== */
    public Optional<Facultad> findById(long id) {
        try (Connection conn = DB.get()) {
            return facultadDAO.cargar(conn, id);
        } catch (SQLException e) {
            System.err.println("Error al cargar FACULTAD: " + e.getMessage());
            return Optional.empty();
        }
    }

    public List<Facultad> findAll() {
        try (Connection conn = DB.get()) {
            List<Facultad> result = facultadDAO.listar(conn);
            listado.clear();
            listado.addAll(result);
            return result;
        } catch (SQLException e) {
            System.err.println("Error al listar FACULTADES: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    /* =================== UPDATE =================== */
    public void update(Facultad facultad) {
        if (facultad == null) {
            System.out.println("No se puede actualizar una facultad nula.");
            return;
        }
        try (Connection conn = DB.get()) {
            facultadDAO.actualizar(conn, facultad);
            listado.removeIf(f -> f.getID() == facultad.getID());
            listado.add(facultad);
            System.out.println("FACULTAD actualizada correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al actualizar FACULTAD: " + e.getMessage());
        }
    }

    /* =================== DELETE =================== */
    public void deleteById(long id) {
        try (Connection conn = DB.get()) {
            int rows = facultadDAO.eliminar(conn, id);
            if (rows > 0) {
                listado.removeIf(f -> f.getID() == id);
                System.out.println("FACULTAD eliminada correctamente.");
            } else {
                System.out.println("No se encontró la facultad para eliminar.");
            }
        } catch (SQLException e) {
            System.err.println("Error al eliminar FACULTAD: " + e.getMessage());
        }
    }

    public void delete(Facultad facultad) {
        if (facultad == null) {
            System.out.println("No se puede eliminar una facultad nula.");
            return;
        }
        deleteById((long)facultad.getID());
    }

    /* =================== CACHE HELPERS =================== */
    
    public List<Facultad> getListado() {
        return Collections.unmodifiableList(listado);
    }

    public void reloadCache() {
        findAll();
    }
}