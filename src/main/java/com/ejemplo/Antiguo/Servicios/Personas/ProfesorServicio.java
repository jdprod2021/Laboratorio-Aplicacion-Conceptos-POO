package com.ejemplo.Servicios.Personas;

import java.util.List;
import java.util.Optional;

import com.ejemplo.DTOs.Mappers.ProfesorMapper;
import com.ejemplo.DTOs.Respuesta.ProfesorRespuestaDTO;
import com.ejemplo.DTOs.Solicitud.ProfesorSolicitudDTO;
import com.ejemplo.Modelos.Personas.Profesor;
import com.ejemplo.Repositorios.Personas.ProfesorRepo;

public class ProfesorServicio {
    private final ProfesorRepo profesorRepo;

    public ProfesorServicio() {
        this.profesorRepo = new ProfesorRepo();
    }

    /* ======================= CREATE ======================= */
    public ProfesorRespuestaDTO crear(ProfesorSolicitudDTO dto) {
        validar(dto);
        Profesor entidad = ProfesorMapper.toEntity(dto);
        profesorRepo.save(entidad);
        return ProfesorMapper.toDTO(entidad);
    }

    /* ======================= READ ======================= */
    public Optional<ProfesorRespuestaDTO> obtenerPorId(long id) {
        return profesorRepo.findById(id).map(ProfesorMapper::toDTO);
    }

    public List<ProfesorRespuestaDTO> listar() {
        return profesorRepo.findAll()
                .stream()
                .map(ProfesorMapper::toDTO)
                .toList();
    }

    /* ======================= UPDATE ======================= */
    // Reemplazo completo con el mismo DTO de solicitud
    public ProfesorRespuestaDTO actualizar(long id, ProfesorSolicitudDTO dto) {
        validar(dto);

        Profesor existente = profesorRepo.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Profesor no existe: " + id));

        existente.setNombres(dto.nombres);
        existente.setApellidos(dto.apellidos);
        existente.setEmail(dto.email);
        existente.setTipoContrato(dto.TipoContrato); // <-- asegúrate del nombre correcto en el DTO

        profesorRepo.update(existente);
        return ProfesorMapper.toDTO(existente);
    }

    /* ======================= DELETE ======================= */
    public void eliminar(long id) {
        profesorRepo.deleteById(id);
    }

    /* ======================= VALIDACIONES ======================= */
    private void validar(ProfesorSolicitudDTO dto) {
        if (dto == null) throw new IllegalArgumentException("Solicitud requerida.");
        if (dto.nombres == null || dto.nombres.isBlank())
            throw new IllegalArgumentException("Nombres requeridos.");
        if (dto.apellidos == null || dto.apellidos.isBlank())
            throw new IllegalArgumentException("Apellidos requeridos.");
        if (dto.email == null || !dto.email.contains("@"))
            throw new IllegalArgumentException("Email inválido.");
        if (dto.TipoContrato == null || dto.TipoContrato.isBlank())
            throw new IllegalArgumentException("Tipo de contrato requerido.");
    }
}
