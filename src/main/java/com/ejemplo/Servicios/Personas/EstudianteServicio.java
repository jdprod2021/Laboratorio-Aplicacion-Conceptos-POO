package com.ejemplo.Servicios.Personas;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.ejemplo.DTOs.Mappers.EstudianteMapper;
import com.ejemplo.DTOs.Respuesta.EstudianteRespuestaDTO;
import com.ejemplo.DTOs.Solicitud.EstudianteSolicitudDTO;
import com.ejemplo.Modelos.Personas.Estudiante;
import com.ejemplo.Modelos.Universidad.Programa;
import com.ejemplo.Repositorios.Personas.EstudianteRepo;
import com.ejemplo.Repositorios.Universidad.ProgramaRepo;

public class EstudianteServicio {
    private final EstudianteRepo estudianteRepo;
    private final ProgramaRepo programaRepo;

    public EstudianteServicio() {
        this.estudianteRepo = new EstudianteRepo();
        this.programaRepo = new ProgramaRepo();
    }

    public EstudianteServicio(EstudianteRepo estudianteRepo, ProgramaRepo programaRepo) {
        this.estudianteRepo = estudianteRepo;
        this.programaRepo = programaRepo;
    }

    /* ======================= CREATE ======================= */
    public EstudianteRespuestaDTO crear(EstudianteSolicitudDTO dto) {
        validar(dto);

        Programa programa = programaRepo.findById(dto.programaId)
            .orElseThrow(() -> new IllegalArgumentException("Programa no existe: " + dto.programaId));

        Estudiante entidad = EstudianteMapper.toEntity(dto, programa);
        estudianteRepo.save(entidad);

        return EstudianteMapper.toDTO(entidad, programa);
    }

    /* ======================= READ ======================= */
    public Optional<EstudianteRespuestaDTO> obtenerPorId(long id) {
        return estudianteRepo.findById(id)
            .map(est -> {
                Programa prog = null;
                if (est.getPrograma() != null) {
                    prog = programaRepo.findById((long)est.getPrograma().getID()).orElse(null);
                }
                return EstudianteMapper.toDTO(est, prog);
            });
    }

    public List<EstudianteRespuestaDTO> listar() {
        List<Estudiante> lista = estudianteRepo.findAll();

        // Pequeña optimización para evitar llamadas repetidas a ProgramaRepo
        Map<Long, Programa> cacheProgramas = new HashMap<>();
        return lista.stream()
            .map(est -> {
                Programa prog = null;
                if (est.getPrograma() != null) {
                    long pid = (long)est.getPrograma().getID();
                    prog = cacheProgramas.computeIfAbsent(
                        pid,
                        key -> programaRepo.findById(key).orElse(null)
                    );
                }
                return EstudianteMapper.toDTO(est, prog);
            })
            .collect(Collectors.toList());
    }

    /* ======================= UPDATE ======================= */
    // Usa el mismo DTO de solicitud; si prefieres campos opcionales, crea un EstudianteUpdateDTO.
    public EstudianteRespuestaDTO actualizar(long id, EstudianteSolicitudDTO dto) {
        validar(dto);

        Estudiante existente = estudianteRepo.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Estudiante no existe: " + id));

        Programa programa = programaRepo.findById(dto.programaId)
            .orElseThrow(() -> new IllegalArgumentException("Programa no existe: " + dto.programaId));

        // Actualiza campos (mapper tuyo no tiene "applyUpdate", así que lo hacemos aquí)
        existente.setNombres(dto.nombres);
        existente.setApellidos(dto.apellidos);
        existente.setEmail(dto.email);
        existente.setCodigo(dto.codigo);
        existente.setActivo(dto.activo);
        existente.setPromedio(dto.promedio);
        existente.setPrograma(programa);

        estudianteRepo.update(existente);

        return EstudianteMapper.toDTO(existente, programa);
    }

    /* ======================= DELETE ======================= */
    public void eliminar(long id) {
        // Podrías chequear existencia primero si quieres mensajes más claros
        estudianteRepo.deleteById(id);
    }

    /* ======================= VALIDACIONES ======================= */
    private void validar(EstudianteSolicitudDTO dto) {
        if (dto == null) throw new IllegalArgumentException("Solicitud requerida.");
        if (dto.nombres == null || dto.nombres.isBlank())
            throw new IllegalArgumentException("El nombre es requerido.");
        if (dto.apellidos == null || dto.apellidos.isBlank())
            throw new IllegalArgumentException("El apellido es requerido.");
        if (dto.email == null || !dto.email.contains("@"))
            throw new IllegalArgumentException("Email inválido.");
        if (dto.codigo <= 0)
            throw new IllegalArgumentException("Código inválido.");
        if (dto.promedio < 0.0)
            throw new IllegalArgumentException("Promedio inválido.");
        if (dto.programaId <= 0)
            throw new IllegalArgumentException("programaId inválido.");
    }
}
