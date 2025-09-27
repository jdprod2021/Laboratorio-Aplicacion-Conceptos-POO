package com.ejemplo.DTOs.Mappers;

import com.ejemplo.DTOs.Respuesta.CursoRespuestaDTO;
import com.ejemplo.DTOs.Solicitud.CursoSolicitudDTO;
import com.ejemplo.Modelos.Curso;
import com.ejemplo.Modelos.Programa;

public class CursoMapper {
    
    public static Curso toEntity(CursoSolicitudDTO dto, Programa programa) {
        Curso c = new Curso();
        c.setNombre(dto.nombre);
        c.setPrograma(programa);
        c.setActivo(dto.activo);
        return c;
    }

    public static CursoRespuestaDTO toDTO(Curso c) {
        return new CursoRespuestaDTO(
            (long)c.getId(),
            c.getNombre(),
            c.isActivo()
        );
    }

}
