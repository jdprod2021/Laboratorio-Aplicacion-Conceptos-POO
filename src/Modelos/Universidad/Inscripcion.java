package Modelos.Universidad;

import Modelos.Personas.Estudiante;

public class Inscripcion {
    private Curso curso;
    private int año;
    private int semestre;
    private Estudiante estudiante;

    public Inscripcion(){}
    
    public Inscripcion(Curso curso, int año, int semestre, Estudiante estudiante){
        this.curso = curso;
        this.año = año;
        this.semestre = semestre;
        this.estudiante = estudiante;
    }

    @Override
    public String toString() {
        return "Año: " + this.año + ", Semestre: " + this.semestre + ", Curso: " + this.curso.toString() + ", Estudiante: " + this.estudiante.toString();
    }
}
