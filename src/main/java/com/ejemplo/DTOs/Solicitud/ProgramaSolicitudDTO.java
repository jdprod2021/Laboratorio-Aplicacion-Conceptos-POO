package com.ejemplo.DTOs.Solicitud;

import java.sql.Date;

public class ProgramaSolicitudDTO {
    
    public String nombre;
    public double duracion;
    public Date registro;
    public long facultadId;

    public ProgramaSolicitudDTO(String nombre, double duracion, Date registro, long facultadId) {
        this.nombre = nombre;
        this.duracion = duracion;
        this.registro = registro;
        this.facultadId = facultadId;
    }
}
