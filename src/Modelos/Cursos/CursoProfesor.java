package Modelos.Cursos;

import Modelos.Personas.Profesor;
import Modelos.Universidad.Curso;

public class CursoProfesor {

    private Profesor profesor;
    private int anio;
    private int semestre;
    private Curso curso;

    public CursoProfesor(){}

    public CursoProfesor(Profesor profesor, int anio, int semestre, Curso curso) {
        this.profesor = profesor;
        this.anio = anio;
        this.semestre = semestre;
        this.curso = curso;
    }

    @Override
    public String toString() {
        return "CursoProfesor{" +
                "profesor=" + profesor +
                ", anio=" + anio +
                ", semestre=" + semestre +
                ", curso=" + curso +
                '}';
    }

}
