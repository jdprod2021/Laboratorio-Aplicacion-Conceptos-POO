package com.ejemplo.Repositorios;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ejemplo.DAOs.Cursos.CursoProfesorDAO;
import com.ejemplo.Modelos.Cursos.CursoProfesor;
import com.ejemplo.Servicios.Servicios;

public class CursosProfesores implements Servicios {

    private List<CursoProfesor> cursoProfesores;
    private CursoProfesorDAO cursoProfesorDAO = new CursoProfesorDAO();

    public CursosProfesores() {
        this.cursoProfesores = new ArrayList<>();
    }

    public CursosProfesores(List<CursoProfesor> cursoProfesores) {
        this.cursoProfesores = cursoProfesores;
    }

    @Override
    public String imprimirPosicion(int posicion) {
        if (posicion >= 0 && posicion < cursoProfesores.size()) {
            return cursoProfesores.get(posicion).toString();
        }
        return "Posición inválida";
    }

    @Override
    public int cantidadActual() {
        return cursoProfesores.size();
    }

    @Override
    public List<String> imprimirListado() {
        List<String> listado = new ArrayList<>();
        for (CursoProfesor cp : cursoProfesores) {
            listado.add(cp.toString());
        }
        return listado;
    }

    public void inscribir(CursoProfesor cursoProfesor) {
        if (cursoProfesor != null) {
            cursoProfesores.add(cursoProfesor);
        } else {
            System.out.println("No se puede inscribir un curso nulo.");
        }
    }

    public void guardarInformacion(CursoProfesor cursoProfesor){

        try (Connection conn = DB.get()) {

            cursoProfesorDAO.guardar(conn, cursoProfesor);

            System.out.println("Información guardada correctamente.");

        } catch (SQLException e) {
            System.err.println("Error al guardar información: " + e.getMessage());
        }

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String cp : imprimirListado()) {
            sb.append(cp).append("\n");
        }
        return sb.toString();
    }

    public void cargarDatos() {
        
        try (Connection conn = DB.get()) {
            ResultSet rs = conn.createStatement().executeQuery("SELECT id FROM CURSO_PROFESOR");
            while (rs.next()) {

                CursoProfesor cp = cursoProfesorDAO.cargar(conn, rs.getInt("id")).orElse(null);

                if (cp != null) {
                    cursoProfesores.add(cp);
                }

            }
            System.out.println("Datos cargados correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al cargar datos: " + e.getMessage());
        }

    }

    public List<CursoProfesor> getCursoProfesores() {
        return cursoProfesores;
    }
}
