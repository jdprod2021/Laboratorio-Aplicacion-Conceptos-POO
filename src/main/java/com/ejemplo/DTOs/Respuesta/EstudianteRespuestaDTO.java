package com.ejemplo.DTOs.Respuesta;

public class EstudianteRespuestaDTO {

    public String nombres;
    public String apellidos;
    public String email;
    public String NombrePrograma;

    public EstudianteRespuestaDTO(String nombres, String apellidos, String email, String nombrePrograma) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.email = email;
        this.NombrePrograma = nombrePrograma;
    }
}
