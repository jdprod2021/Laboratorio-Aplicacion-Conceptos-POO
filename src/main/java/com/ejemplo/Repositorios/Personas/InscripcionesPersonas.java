package com.ejemplo.Repositorios.Personas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.ejemplo.DAOs.Personas.PersonaDAO;
import com.ejemplo.Modelos.Personas.Persona;
import com.ejemplo.Repositorios.DB;

public class InscripcionesPersonas{

    private List<Persona> listado;
    private PersonaDAO personaDAO = new PersonaDAO();

    public InscripcionesPersonas(){}

    public InscripcionesPersonas(List<Persona> listado){
        this.listado = listado;
    }


    public void inscribir(Persona persona){

        if (persona != null) {
            listado.add(persona);
            guardarInformacion(persona);
        } else {
            System.out.println("No se puede inscribir una persona nula.");
        }
    }

    public void eliminar(Persona persona){
        if ((persona != null) && listado.contains(persona)) {

            listado.remove(persona);

            try(Connection conn = DB.get()){
                personaDAO.eliminar(conn, persona);
                System.out.println("Información eliminada correctamente.");
            }catch(SQLException e){
                System.err.println("Error al eliminar información: " + e.getMessage());
            }

        } else {
            System.out.println("No se puede eliminar una persona nula o que no está inscrita.");
        }
    }

    public void actualizar(Persona persona){
        if (persona != null && listado.contains(persona)) {
            int index = listado.indexOf(persona);
            listado.set(index, persona);
            guardarInformacion(persona);
        } else {
            System.out.println("No se puede actualizar una persona nula o que no está inscrita.");
        }
    }

    public void guardarInformacion(Persona persona){

        try(Connection conn = DB.get()){
            personaDAO.guardar(conn, persona);
            System.out.println("Información guardada correctamente.");
        }catch(SQLException e){
            System.err.println("Error al guardar información: " + e.getMessage());
        }

    }

    public void cargarDatos(){

        try(Connection conn = DB.get()){
            String sql = "SELECT id FROM PERSONA";
            try(PreparedStatement ps = conn.prepareStatement(sql)){
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    Persona p = personaDAO.cargar(conn, rs.getInt("id")).orElse(null);
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

    public List<Persona> getListado() {
        return listado;
    }

}
