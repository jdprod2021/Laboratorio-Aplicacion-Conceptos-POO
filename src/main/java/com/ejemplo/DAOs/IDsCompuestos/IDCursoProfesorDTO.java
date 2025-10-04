package com.ejemplo.DAOs.IDsCompuestos;

public class IDCursoProfesorDTO {
    public long profesorId;
    public Integer anio;
    public Integer semestre;
    public long cursoId;

    public IDCursoProfesorDTO(long profesorId, Integer anio, Integer semestre, long cursoId) {
        this.profesorId = profesorId;
        this.anio = anio;
        this.semestre = semestre;
        this.cursoId = cursoId;
    }
}
