package com.ejemplo.DTOs.Mappers;

import com.ejemplo.DTOs.Respuesta.ProfesorRespuestaDTO;
import com.ejemplo.DTOs.Solicitud.ProfesorSolicitudDTO;
import com.ejemplo.Modelos.Personas.Profesor;

public class ProfesorMapper {

   public static Profesor toEntity(ProfesorSolicitudDTO dto) {
       Profesor p = new Profesor();
       p.setNombres(dto.nombres);
       p.setApellidos(dto.apellidos);
       p.setEmail(dto.email);
       p.setTipoContrato(dto.TipoContrato);
       return p;
   }

   public static ProfesorRespuestaDTO toDTO(Profesor p) {
       return new ProfesorRespuestaDTO(
           (long)p.getId(),
           p.getNombres(),
           p.getApellidos(),
           p.getEmail(),
           p.getTipoContrato()
       );
   }
}
