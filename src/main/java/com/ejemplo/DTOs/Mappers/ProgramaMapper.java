package com.ejemplo.DTOs.Mappers;

import com.ejemplo.DTOs.Respuesta.ProgramaRespuestaDTO;
import com.ejemplo.DTOs.Solicitud.ProgramaSolicitudDTO;
import com.ejemplo.Modelos.Facultad;
import com.ejemplo.Modelos.Programa;

public class ProgramaMapper {
    
    public static Programa toEntity(ProgramaSolicitudDTO dto, Facultad fac) {
        Programa p = new Programa();
        p.setNombre(dto.nombre);
        p.setDuracion(dto.duracion);
        p.setRegistro(dto.registro);
        p.setFacultad(fac);
        return p;
    }

    public static ProgramaRespuestaDTO toDTO(Programa p) {
        return new ProgramaRespuestaDTO(
            (long)p.getID(),
            p.getNombre()
        );
    }

}
