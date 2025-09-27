package com.ejemplo.Servicios.Universidad;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.ejemplo.DTOs.Mappers.FacultadMapper;
import com.ejemplo.DTOs.Respuesta.FacultadRespuestaDTO;
import com.ejemplo.DTOs.Solicitud.FacultadSolicitudDTO;
import com.ejemplo.Modelos.Personas.Persona;
import com.ejemplo.Modelos.Universidad.Facultad;
import com.ejemplo.Repositorios.Personas.InscripcionesPersonas;
import com.ejemplo.Repositorios.Universidad.FacultadRepo;

public class FacultadServicio {

    private final FacultadRepo facultadRepo;
    private final InscripcionesPersonas personaRepo;

    public FacultadServicio(FacultadRepo facultadRepo, InscripcionesPersonas personaRepo) {
        this.facultadRepo = facultadRepo;
        this.personaRepo = personaRepo;
    }

    /* ======================= CREATE ======================= */
    public FacultadRespuestaDTO crear(FacultadSolicitudDTO dto) {
        validar(dto);

        // Validar FK del decano
        Persona decano = personaRepo.findById(dto.decanoId)
            .orElseThrow(() -> new IllegalArgumentException("El decano no existe: " + dto.decanoId));

        // Mapear DTO -> Entidad
        Facultad entidad = FacultadMapper.toEntity(dto, decano);

        // Persistir
        facultadRepo.save(entidad);

        // Salida
        return FacultadMapper.toDTO(entidad);
    }

    /* ======================= READ ======================= */
    public Optional<FacultadRespuestaDTO> obtenerPorId(long id) {
        return facultadRepo.findById(id).map(FacultadMapper::toDTO);
    }

    public List<FacultadRespuestaDTO> listar() {
        return facultadRepo.findAll().stream()
                .map(FacultadMapper::toDTO)
                .collect(Collectors.toList());
    }

    /* ======================= UPDATE (reemplazo completo) ======================= */
    public FacultadRespuestaDTO actualizar(long id, FacultadSolicitudDTO dto) {
        validar(dto);

        // Cargar existente
        Facultad existente = facultadRepo.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Facultad no existe: " + id));

        // Validar nuevo decano
        Persona nuevoDecano = personaRepo.findById(dto.decanoId)
            .orElseThrow(() -> new IllegalArgumentException("El decano no existe: " + dto.decanoId));

        // Actualizar campos
        existente.setNombre(dto.nombre);
        existente.setDecano(nuevoDecano);

        // Persistir
        facultadRepo.update(existente);

        return FacultadMapper.toDTO(existente);
    }

    /* ======================= DELETE ======================= */
    public void eliminar(long id) {
        facultadRepo.deleteById(id);
    }

    /* ======================= VALIDACIÓN ======================= */
    private void validar(FacultadSolicitudDTO dto) {
        if (dto == null) throw new IllegalArgumentException("Solicitud requerida.");
        if (dto.nombre == null || dto.nombre.isBlank())
            throw new IllegalArgumentException("El nombre es requerido.");
        if (dto.decanoId <= 0)
            throw new IllegalArgumentException("decanoId inválido.");
    }
}
