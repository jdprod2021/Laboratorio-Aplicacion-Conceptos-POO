package Modelos.Universidad;

import Modelos.Personas.Estudiante;

public class Inscripcion {
    private Curso curso;
    private int anio;
    private int semestre;
    private Estudiante estudiante;

    public Inscripcion(){}
    
    public Inscripcion(Curso curso, int año, int semestre, Estudiante estudiante){
        this.curso = curso;
        this.anio = anio;
        this.semestre = semestre;
        this.estudiante = estudiante;
    }

    @Override
    public String toString() {
        return "Año: " + this.anio + ", Semestre: " + this.semestre + ", Curso: " + this.curso.toString() + ", Estudiante: " + this.estudiante.toString();
    }
}
