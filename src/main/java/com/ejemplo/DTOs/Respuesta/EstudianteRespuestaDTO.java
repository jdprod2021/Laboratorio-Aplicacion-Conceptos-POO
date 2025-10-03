package com.ejemplo.DTOs.Respuesta;

public class EstudianteRespuestaDTO {

    private long ID;
    private String nombres;
    private String apellidos;
    private String email;
    private String NombrePrograma;

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

    public long getID() {
        return ID;
    }

    public String getNombres() {
        return nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getEmail() {
        return email;
    }

    public String getNombrePrograma() {
        return NombrePrograma;
    }

    

}
