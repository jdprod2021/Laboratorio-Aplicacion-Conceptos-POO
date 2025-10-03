package com.ejemplo.DTOs.Mappers;

import com.ejemplo.DTOs.Respuesta.EstudianteRespuestaDTO;
import com.ejemplo.DTOs.Solicitud.EstudianteSolicitudDTO;
import com.ejemplo.Modelos.Estudiante;
import com.ejemplo.Modelos.Programa;

public final class EstudianteMapper {

    public static Estudiante toEntity(EstudianteSolicitudDTO dto, Programa prog, long id) {
        Estudiante e = new Estudiante();
        e.setId(id);
        e.setNombres(dto.nombres);
        e.setApellidos(dto.apellidos);
        e.setEmail(dto.email);
        e.setCodigo(dto.codigo);
        e.setActivo(dto.activo);
        e.setPromedio(dto.promedio);
        e.setPrograma(prog);
        return e;
    }

    public static EstudianteRespuestaDTO toDTO(Estudiante e, Programa prog) {
        return new EstudianteRespuestaDTO(
            (long)e.getId(),
            e.getNombres(),
            e.getApellidos(),
            e.getEmail(),
            prog.getNombre()
        );
    }
}
