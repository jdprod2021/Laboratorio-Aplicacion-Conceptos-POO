package Modelos.Personas;

import Modelos.Universidad.Programa;

public class Estudiante extends Persona{

    private double codigo;
    private Programa programa;
    private boolean activo;
    private double promedio;

    public Estudiante() {}
    
    public Estudiante(double id, String nombre, String apellido, String email, Programa programa,  double codigo, boolean activo, double promedio) {
        super(id, nombre, apellido, email);
        this.codigo = codigo;
        this.programa = programa;
        this.activo = activo;
        this.promedio = promedio;
    }

    @Override
    public String toString() {
        return super.toString() + "Codigo: " + this.codigo  + " Programa: " + this.programa.toString()  + " Activo: " + this.activo + " Promedio: " + this.promedio;
    }

}
