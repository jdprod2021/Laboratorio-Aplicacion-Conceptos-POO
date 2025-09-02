package com.ejemplo.Repositorios.Personas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ejemplo.DAOs.Personas.EstudianteDAO;
import com.ejemplo.Modelos.Personas.Estudiante;
import com.ejemplo.Repositorios.DB;

public class EstudianteRepo {

    private List<Estudiante> listado;
    private EstudianteDAO estudianteDAO = new EstudianteDAO();

    public EstudianteRepo() {
        this.listado = new ArrayList<>();
    }

    public EstudianteRepo(List<Estudiante> listado) {
        this.listado = listado;
    }

    public List<Estudiante> getListado() {
        return listado;
    }

    public void inscribir(Estudiante estudiante) {
        if (estudiante != null) {
            listado.add(estudiante);
            guardarInformacion(estudiante);
        } else {
            System.out.println("No se puede inscribir un estudiante nulo.");
        }
    }

    public void eliminar(Estudiante estudiante) {
        if ((estudiante != null) && listado.contains(estudiante)) {

            listado.remove(estudiante);

            try(Connection conn = DB.get()){
                estudianteDAO.eliminar(conn, estudiante);
                System.out.println("Información eliminada correctamente.");
            }catch(SQLException e){
                System.err.println("Error al eliminar información: " + e.getMessage());
            }

        } else {
            System.out.println("No se puede eliminar un estudiante nulo o que no está inscrito.");
        }
    }

    public void actualizar(Estudiante estudiante) {
        if (estudiante != null && listado.contains(estudiante)) {
            int index = listado.indexOf(estudiante);
            listado.set(index, estudiante);
            guardarInformacion(estudiante);
        } else {
            System.out.println("No se puede actualizar un estudiante nulo o que no está inscrito.");
        }
    }

    public void guardarInformacion(Estudiante estudiante) {

        try(Connection conn = DB.get()){
            estudianteDAO.guardar(conn, estudiante);
            System.out.println("Información guardada correctamente.");
        }catch(SQLException e){
            System.err.println("Error al guardar información: " + e.getMessage());
        }

    }

    public void cargarDatos() {

        try(Connection conn = DB.get()){
            String sql = "SELECT id FROM ESTUDIANTE";
            try(PreparedStatement ps = conn.prepareStatement(sql)){
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    Estudiante e = estudianteDAO.cargar(conn, rs.getInt("id")).orElse(null);
                    if (e != null) {
                        listado.add(e);
                    }
                }
            }catch(SQLException e){
                System.err.println("Error al cargar datos de ESTUDIANTE: " + e.getMessage());
            }
        }catch(SQLException e){
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
        }
    }

}
