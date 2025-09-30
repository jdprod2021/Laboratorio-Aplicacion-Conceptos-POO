package com.ejemplo.DTOs.Respuesta;

public class FacultadRespuestaDTO {

    private long ID;
    private String nombre;

    public FacultadRespuestaDTO(long ID, String nombre) {
        this.ID = ID;
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "ID=" + ID +
                ", nombre='" + nombre + '\'';
    }

    public long getID() {
        return ID;
    }

    public String getNombre() {
        return nombre;
    }

}
