package com.ejemplo.Repositorios.Universidad;

import java.util.List;

import com.ejemplo.DAOs.Universidad.FacultadDAO;
import com.ejemplo.Modelos.Universidad.Facultad;
import com.ejemplo.Repositorios.DB;

public class FacultadRepo {

    private List<Facultad> listado;
    private final FacultadDAO facultadDAO;

    public FacultadRepo(FacultadDAO facultadDAO) {
        this.facultadDAO = facultadDAO;
    }

    public List<Facultad> getListado() {
        return listado;
    }

    public void crearFacultad(Facultad facultad) {
        if (facultad != null) {
            listado.add(facultad);
            guardarInformacion(facultad);
        } else {
            System.out.println("No se puede crear una facultad nula.");
        }
    }

    public void guardarInformacion(Facultad facultad) {
        try (var conn = DB.get()) {
            facultadDAO.guardar(conn, facultad);
        } catch (Exception e) {
            System.err.println("Error al guardar FACULTAD: " + e.getMessage());
        }
    }

    public void eliminar(Facultad facultad) {

        if (facultad != null) {

            listado.remove(facultad);

            try (var conn = DB.get()) {
                facultadDAO.eliminar(conn, (int) facultad.getID());
                System.out.println("Información eliminada correctamente.");
            } catch (Exception e) {
                System.err.println("Error al eliminar información: " + e.getMessage());
            }
        } else {
            System.out.println("No se puede eliminar una facultad nula.");
        }
    }

    public Facultad cargarInformacion(int id) {
        try (var conn = DB.get()) {
            return facultadDAO.cargar(conn, id).orElse(null);
        } catch (Exception e) {
            System.err.println("Error al cargar FACULTAD: " + e.getMessage());
            return null;
        }
    }

}
