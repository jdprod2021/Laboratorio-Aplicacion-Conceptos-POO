package com.ejemplo.DTOs.Solicitud;

public class ProfesorSolicitudDTO{

    public String nombres;
    public String apellidos;
    public String email;
    public String TipoContrato;

    public ProfesorSolicitudDTO(String nombres, String apellidos, String email, String tipoContrato) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.email = email;
        this.TipoContrato = tipoContrato;
    }
}
