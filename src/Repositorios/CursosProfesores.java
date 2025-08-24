package Repositorios;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Modelos.Cursos.CursoProfesor;

public class CursosProfesores implements Servicios {

    private List<CursoProfesor> cursoProfesores;

    public CursosProfesores() {
        this.cursoProfesores = new ArrayList<>();
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

    public CursosProfesores(List<CursoProfesor> cursoProfesores) {
        this.cursoProfesores = cursoProfesores;
        System.out.println("creamos todo");
    }

    public void inscribir(CursoProfesor cursoProfesor) {
        if (cursoProfesor != null) {
            cursoProfesores.add(cursoProfesor);
        } else {
            System.out.println("No se puede inscribir un curso nulo.");
        }
    }

    public void guardarInformacion(){

        try (Connection conn = DB.get()) {
           
            for (CursoProfesor curso : cursoProfesores) {
                curso.guardar(conn);
            }
            System.out.println("Información guardada correctamente.");

        } catch (SQLException e) {
            System.err.println("Error al guardar información: " + e.getMessage());
        }

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (CursoProfesor cp : cursoProfesores) {
            sb.append(cp.toString()).append("\n");
        }
        return sb.toString();
    }

    public void cargarDatos() {
        
        try (Connection conn = DB.get()) {
            var rs = conn.createStatement().executeQuery("SELECT id FROM CURSO_PROFESOR");
            while (rs.next()) {
               
                CursoProfesor cp = new CursoProfesor();
                cp.cargar(conn, rs.getInt("id"));
                cursoProfesores.add(cp);

            }
            System.out.println("Datos cargados correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al cargar datos: " + e.getMessage());
        }

    }
}
