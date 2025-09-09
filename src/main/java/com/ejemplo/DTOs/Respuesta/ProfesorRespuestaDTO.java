package com.ejemplo.DTOs.Respuesta;

public class ProfesorRespuestaDTO {

    public long ID;
    public String nombres;
    public String apellidos;
    public String email;
    public String TipoContrato;

    public ProfesorRespuestaDTO(long ID, String nombres, String apellidos, String email, String TipoContrato) {
        this.ID = ID;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.email = email;
        this.TipoContrato = TipoContrato;
    }
}
