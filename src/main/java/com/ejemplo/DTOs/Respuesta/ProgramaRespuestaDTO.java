package com.ejemplo.DTOs.Respuesta;

public class ProgramaRespuestaDTO {
    
    public long ID;
    public String nombre;

    public ProgramaRespuestaDTO(long ID, String nombre) {
        this.ID = ID;
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "ID=" + ID +
                ", nombre='" + nombre + '\'';
    }

}
