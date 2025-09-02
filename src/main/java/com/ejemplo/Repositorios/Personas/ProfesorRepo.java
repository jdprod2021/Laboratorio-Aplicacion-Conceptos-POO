package com.ejemplo.Repositorios.Personas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.ejemplo.DAOs.Personas.ProfesorDAO;
import com.ejemplo.Modelos.Personas.Profesor;
import com.ejemplo.Repositorios.DB;

public class ProfesorRepo {

    List<Profesor> listado;
    ProfesorDAO profesorDAO = new ProfesorDAO();


    public ProfesorRepo(){}

    public ProfesorRepo(List<Profesor> listado) {
        this.listado = listado;
    }

    public List<Profesor> getListado() {
        return listado;
    }

    public void inscribir(Profesor profesor) {
        if (profesor != null) {
            listado.add(profesor);
            guardarInformacion(profesor);
        } else {
            System.out.println("No se puede inscribir un profesor nulo.");
        }
    }

    public void eliminar(Profesor profesor) {
        if ((profesor != null) && listado.contains(profesor)) {

            listado.remove(profesor);

            try(Connection conn = DB.get()){
                profesorDAO.eliminar(conn, profesor);
                System.out.println("Información eliminada correctamente.");
            }catch(SQLException e){
                System.err.println("Error al eliminar información: " + e.getMessage());
            }

        } else {
            System.out.println("No se puede eliminar un profesor nulo o que no está inscrito.");
        }
    }

    public void actualizar(Profesor profesor) {
        if (profesor != null && listado.contains(profesor)) {
            int index = listado.indexOf(profesor);
            listado.set(index, profesor);
            guardarInformacion(profesor);
        } else {
            System.out.println("No se puede actualizar un profesor nulo o que no está inscrito.");
        }
    }

    public void guardarInformacion(Profesor profesor){

        try(Connection conn = DB.get()){
            profesorDAO.guardar(conn, profesor);
            System.out.println("Información guardada correctamente.");
        }catch(SQLException e){
            System.err.println("Error al guardar información: " + e.getMessage());
        }

    }

    public void cargarDatos(){

        try(Connection conn = DB.get()){
            String sql = "SELECT id FROM PROFESOR";
            try(PreparedStatement ps = conn.prepareStatement(sql)){
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    Profesor p = profesorDAO.cargar(conn, rs.getInt("id")).orElse(null);
                    if (p != null) {
                        listado.add(p);
                    }
                }
            }catch(SQLException e){
                System.err.println("Error al cargar datos de PERSONA: " + e.getMessage());
            }
        }catch(SQLException e){
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
        }
    }

}
