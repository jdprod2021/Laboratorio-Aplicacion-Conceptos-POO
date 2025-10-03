package com.ejemplo.DAOs.IDsCompuestos;

public class IDCursoProfesor {
    public long profesorId;
    public Integer anio;
    public Integer semestre;
    public long cursoId;

    public IDCursoProfesor(long profesorId, Integer anio, Integer semestre, long cursoId) {
        this.profesorId = profesorId;
        this.anio = anio;
        this.semestre = semestre;
        this.cursoId = cursoId;
    }
}
