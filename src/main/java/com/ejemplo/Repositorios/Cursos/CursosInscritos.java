package com.ejemplo.Repositorios.Cursos;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ejemplo.DAOs.Universidad.InscripcionDAO;
import com.ejemplo.Modelos.Universidad.Inscripcion;
import com.ejemplo.Repositorios.DB;
import com.ejemplo.Servicios.Servicios;

public class CursosInscritos implements Servicios {

    private List<Inscripcion> inscripcion;
    private InscripcionDAO inscripcionDAO = new InscripcionDAO();

    public CursosInscritos() {}
    public CursosInscritos(List<Inscripcion> inscripcion) {
        this.inscripcion = inscripcion;
    }

    @Override
    public String imprimirPosicion(int posicion){
        if (posicion >= 0 && posicion < inscripcion.size()) {
            return inscripcion.get(posicion).toString();
        }
        return "Posición inválida";
    }

    @Override
    public int cantidadActual() {
        return inscripcion.size();
    }

    @Override
    public List<String> imprimirListado() {
        List<String> listado = new ArrayList<>();
        for (Inscripcion I : inscripcion) {
            listado.add(I.toString());
        }
        return listado;
    }

    public void inscribirCurso(Inscripcion inscripcionAdd){
        if(inscripcionAdd != null){
            inscripcion.add(inscripcionAdd);
        }
        else{
            System.out.println("No se puede inscribir una inscripción nula.");
        }
    }

    public void eliminar(Inscripcion inscripcionDel){
        if((inscripcionDel != null) && inscripcion.contains(inscripcionDel)){
            inscripcion.remove(inscripcionDel);
        }else {
            System.out.println("No se puede eliminar una inscripción nula o que no está inscrita.");
        }
    }

    public void actualizar(Inscripcion inscripcionact){
        if (inscripcionact != null && inscripcion.contains(inscripcionact)) {
            int index = inscripcion.indexOf(inscripcionact);
            inscripcion.set(index, inscripcionact);
        } else {
            System.out.println("No se puede actualizar una persona nula o que no está inscrita.");
        }
    }

    public void guardarInformacion(Inscripcion inscripcionAdd){
        try (Connection conn = DB.get()){
            inscripcionDAO.guardar(conn, inscripcionAdd);
            System.out.println("Información guardada correctamente.");
        } catch (SQLException e){
            System.err.println("Error al guardar informacion: " + e.getMessage());
        }
    }
    
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (String I : imprimirListado()) {
            sb.append(I).append("\n");
        }
        return sb.toString();

    }

    public void CargarDatos(){

        try (Connection conn = DB.get()){
            ResultSet rs = conn.createStatement().executeQuery("SELECT id FROM INSCRIPCION");
            while(rs.next()) {

                Inscripcion I = inscripcionDAO.cargar(conn, rs.getInt("id")).orElse(null);

                if (I != null) {
                    inscripcion.add(I);
                }

            }
        } catch (SQLException e){
                System.err.println("Error al cargar datos: " + e.getMessage());
        }
    }
}
