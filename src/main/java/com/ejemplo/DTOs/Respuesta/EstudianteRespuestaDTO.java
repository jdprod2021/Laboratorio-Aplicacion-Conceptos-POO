package com.ejemplo.DTOs.Respuesta;

public class EstudianteRespuestaDTO {

    public long ID;
    public String nombres;
    public String apellidos;
    public String email;
    public String NombrePrograma;

    public EstudianteRespuestaDTO(long ID, String nombres, String apellidos, String email, String nombrePrograma) {
        this.ID = ID;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.email = email;
        this.NombrePrograma = nombrePrograma;
    }

    @Override
    public String toString() {
        return "ID=" + ID +
                ", nombres='" + nombres + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", email='" + email + '\'' +
                ", NombrePrograma='" + NombrePrograma;
    }

}
