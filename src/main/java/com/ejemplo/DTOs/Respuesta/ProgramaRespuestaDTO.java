package com.ejemplo.DTOs.Respuesta;

public class ProgramaRespuestaDTO {

    private long ID;
    private String nombre;
    private double duracion;

    public ProgramaRespuestaDTO(long ID, String nombre, double duracion) {
        this.ID = ID;
        this.nombre = nombre;
        this.duracion = duracion;
    }

    @Override
    public String toString() {
        return "ID=" + ID +
                ", nombre='" + nombre + '\'' +
                ", duracion=" + duracion;
    }

    public long getID() {
        return ID;
    }
    public String getNombre() {
        return nombre;
    }

    public double getDuracion() {
        return duracion;
    }

}
