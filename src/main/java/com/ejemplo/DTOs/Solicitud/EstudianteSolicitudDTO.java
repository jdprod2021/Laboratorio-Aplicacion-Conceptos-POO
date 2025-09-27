package com.ejemplo.DTOs.Solicitud;

public class EstudianteSolicitudDTO{

    public String nombres;
    public String apellidos;
    public String email;
    public long codigo;
    public boolean activo;
    public double promedio;
    public long programaId;

    public EstudianteSolicitudDTO(String nombres, String apellidos, String email, long codigo, boolean activo,
            double promedio, long programaId) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.email = email;
        this.codigo = codigo;
        this.activo = activo;
        this.promedio = promedio;
        this.programaId = programaId;
    }
}
