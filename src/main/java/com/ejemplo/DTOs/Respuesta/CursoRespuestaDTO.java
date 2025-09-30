package com.ejemplo.DTOs.Respuesta;

public class CursoRespuestaDTO {

    private long id;
    private String nombre;
    private boolean activo;

    public CursoRespuestaDTO(long id, String nombre, boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.activo = activo;
    }

    @Override
    public String toString() {
        return "id=" + id + ", nombre=" + nombre + ", activo=" + activo;
    }

    public long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean isActivo() {
        return activo;
    }

}
