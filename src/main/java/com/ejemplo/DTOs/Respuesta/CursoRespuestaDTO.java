package com.ejemplo.DTOs.Respuesta;

public class CursoRespuestaDTO {

    public long id;
    public String nombre;
    public boolean activo;

    public CursoRespuestaDTO(long id, String nombre, boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.activo = activo;
    }

    @Override
    public String toString() {
        return "id=" + id + ", nombre=" + nombre + ", activo=" + activo;
    }

}
