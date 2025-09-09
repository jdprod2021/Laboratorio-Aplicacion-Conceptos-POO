package com.ejemplo.Repositorios.Universidad;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.ejemplo.DAOs.Universidad.CursoDAO;
import com.ejemplo.Modelos.Universidad.Curso;
import com.ejemplo.Repositorios.DB;

public class CursoRepo {

    // Caché en memoria (opcional)
    private final List<Curso> listado = new ArrayList<>();

    private final CursoDAO cursoDAO;

    public CursoRepo(CursoDAO cursoDAO) {
        this.cursoDAO = cursoDAO;
    }

    /* =================== CREATE / UPSERT =================== */
    public void save(Curso curso) {
        if (curso == null) {
            System.out.println("No se puede guardar un curso nulo.");
            return;
        }
        try (Connection conn = DB.get()) {
            cursoDAO.guardar(conn, curso); 
        
            listado.removeIf(c -> c.getId() == curso.getId());
            listado.add(curso);
            System.out.println("CURSO guardado correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al guardar CURSO: " + e.getMessage());
        }
    }

    /* =================== READ =================== */
    public Optional<Curso> findById(long id) {
        try (Connection conn = DB.get()) {
            return cursoDAO.cargar(conn, id);
        } catch (SQLException e) {
            System.err.println("Error al cargar CURSO: " + e.getMessage());
            return Optional.empty();
        }
    }

    public List<Curso> findAll() {
        try (Connection conn = DB.get()) {
            List<Curso> result = cursoDAO.listar(conn);
            listado.clear();
            listado.addAll(result);
            return result;
        } catch (SQLException e) {
            System.err.println("Error al listar CURSOS: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public List<Curso> findByPrograma(long programaId) {
        try (Connection conn = DB.get()) {
            List<Curso> result = cursoDAO.listarPorPrograma(conn, programaId);
        
            return result;
        } catch (SQLException e) {
            System.err.println("Error al listar CURSOS por programa: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    /* =================== UPDATE =================== */

    public void update(Curso curso) {
        if (curso == null) {
            System.out.println("No se puede actualizar un curso nulo.");
            return;
        }
        try (Connection conn = DB.get()) {
            cursoDAO.actualizar(conn, curso);
            listado.removeIf(c -> c.getId() == curso.getId());
            listado.add(curso);
            System.out.println("CURSO actualizado correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al actualizar CURSO: " + e.getMessage());
        }
        
    }

    /* =================== DELETE =================== */
    public void deleteById(long id) {
        try (Connection conn = DB.get()) {
            int rows = cursoDAO.eliminar(conn, id);
            if (rows > 0) {
                listado.removeIf(c -> c.getId() == id);
                System.out.println("CURSO eliminado correctamente.");
            } else {
                System.out.println("No se encontró el curso para eliminar.");
            }
        } catch (SQLException e) {
            System.err.println("Error al eliminar CURSO: " + e.getMessage());
        }
    }

    public void delete(Curso curso) {
        if (curso == null) {
            System.out.println("No se puede eliminar un curso nulo.");
            return;
        }
        deleteById(curso.getId());
    }

    /* =================== CACHE HELPERS =================== */
    
    public List<Curso> getListado() {
        return Collections.unmodifiableList(listado);
    }

    
    public void reloadCache() {
        findAll();
    }
}