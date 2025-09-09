package com.ejemplo.Servicios.Universidad;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.ejemplo.DTOs.Mappers.ProgramaMapper;
import com.ejemplo.DTOs.Respuesta.ProgramaRespuestaDTO;
import com.ejemplo.DTOs.Solicitud.ProgramaSolicitudDTO;
import com.ejemplo.Modelos.Universidad.Facultad;
import com.ejemplo.Modelos.Universidad.Programa;
import com.ejemplo.Repositorios.Universidad.FacultadRepo;
import com.ejemplo.Repositorios.Universidad.ProgramaRepo;

public class ProgramaServicio {
    private final ProgramaRepo programaRepo;
    private final FacultadRepo facultadRepo;

    public ProgramaServicio() {
        this.programaRepo = new ProgramaRepo();
        this.facultadRepo = new FacultadRepo();
    }

    /* ======================= CREATE ======================= */
    public ProgramaRespuestaDTO crear(ProgramaSolicitudDTO dto) {
        validar(dto);

        // Validar FK (facultad)
        Facultad fac = facultadRepo.findById(dto.facultadId)
            .orElseThrow(() -> new IllegalArgumentException("La facultad no existe: " + dto.facultadId));

        // Mapear DTO -> Entidad
        Programa entidad = ProgramaMapper.toEntity(dto, fac);

        // Persistir
        programaRepo.save(entidad);

        // Responder DTO salida
        return ProgramaMapper.toDTO(entidad);
    }

    /* ======================= READ ======================= */
    public Optional<ProgramaRespuestaDTO> obtenerPorId(long id) {
        return programaRepo.findById(id).map(ProgramaMapper::toDTO);
    }

    public List<ProgramaRespuestaDTO> listar() {
        return programaRepo.findAll().stream()
                .map(ProgramaMapper::toDTO)
                .collect(Collectors.toList());
    }

    /* ======================= UPDATE ======================= */
    public ProgramaRespuestaDTO actualizar(long id, ProgramaSolicitudDTO dto) {
        validar(dto);

        Programa existente = programaRepo.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Programa no existe: " + id));

        Facultad fac = facultadRepo.findById(dto.facultadId)
            .orElseThrow(() -> new IllegalArgumentException("La facultad no existe: " + dto.facultadId));

        // Actualizar campos (tu mapper no tiene applyUpdate; lo hacemos aquí)
        existente.setNombre(dto.nombre);
        existente.setDuracion(dto.duracion);
        existente.setRegistro(dto.registro);
        existente.setFacultad(fac);

        programaRepo.update(existente);
        return ProgramaMapper.toDTO(existente);
    }

    /* ======================= DELETE ======================= */
    public void eliminar(long id) {
        programaRepo.deleteById(id);
    }

    /* ======================= VALIDACIONES ======================= */
    private void validar(ProgramaSolicitudDTO dto) {
        if (dto == null) throw new IllegalArgumentException("Solicitud requerida.");
        if (dto.nombre == null || dto.nombre.isBlank())
            throw new IllegalArgumentException("El nombre es requerido.");
        if (dto.duracion <= 0)
            throw new IllegalArgumentException("Duración inválida.");
        if (dto.registro == null)
            throw new IllegalArgumentException("Fecha de registro requerida.");
        if (dto.facultadId <= 0)
            throw new IllegalArgumentException("facultadId inválido.");
    }
}
