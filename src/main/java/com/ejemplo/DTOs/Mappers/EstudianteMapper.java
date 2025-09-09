package com.ejemplo.DTOs.Mappers;

import com.ejemplo.DTOs.Respuesta.EstudianteRespuestaDTO;
import com.ejemplo.DTOs.Solicitud.EstudianteSolicitudDTO;
import com.ejemplo.Modelos.Personas.Estudiante;
import com.ejemplo.Modelos.Universidad.Programa;

public final class EstudianteMapper {
    private EstudianteMapper(){}

    public static Estudiante toEntity(EstudianteSolicitudDTO dto, Programa prog) {
        Estudiante e = new Estudiante();
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
            e.getNombres(),
            e.getApellidos(),
            e.getEmail(),
            prog.getNombre()
        );
    }
}
