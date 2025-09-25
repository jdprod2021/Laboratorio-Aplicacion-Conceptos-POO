package com.ejemplo.DTOs.Mappers;

import com.ejemplo.DTOs.Respuesta.FacultadRespuestaDTO;
import com.ejemplo.DTOs.Solicitud.FacultadSolicitudDTO;
import com.ejemplo.Modelos.Personas.Persona;
import com.ejemplo.Modelos.Universidad.Facultad;

public class FacultadMapper {

    public static Facultad toEntity(FacultadSolicitudDTO dto, Persona decano) {
        return new Facultad(dto.nombre, decano);
    }

    public static FacultadRespuestaDTO toDTO(Facultad entidad) {
        return new FacultadRespuestaDTO((long)entidad.getID(), entidad.getNombre());
    }

}
