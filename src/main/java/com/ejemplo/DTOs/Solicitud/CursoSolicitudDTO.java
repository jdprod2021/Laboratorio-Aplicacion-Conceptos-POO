package com.ejemplo.DTOs.Solicitud;

public class CursoSolicitudDTO {

    public String nombre;
    public long programaId;
    public boolean activo;

    public CursoSolicitudDTO(String nombre, long programaId, boolean activo) {
        this.nombre = nombre;
        this.programaId = programaId;
        this.activo = activo;
    }

}
