package com.ejemplo.Modelos.Personas;

import com.ejemplo.Modelos.Universidad.Programa;

public class Estudiante extends Persona{

    private double codigo;
    private Programa programa;
    private boolean activo;
    private double promedio;

    public Estudiante() {}
    
    public Estudiante( String nombre, String apellido, String email, Programa programa,  double codigo, boolean activo, double promedio) {
        super(nombre, apellido, email);
        this.codigo = codigo;
        this.programa = programa;
        this.activo = activo;
        this.promedio = promedio;
    }

    @Override
    public String toString() {
        return super.toString() + "Codigo: " + this.codigo  + " Programa: " + this.programa.toString()  + " Activo: " + this.activo + " Promedio: " + this.promedio;
    }

    public double getCodigo() {
        return codigo;
    }

    public void setCodigo(double codigo) {
        this.codigo = codigo;
    }

    public Programa getPrograma() {
        return programa;
    }

    public void setPrograma(Programa programa) {
        this.programa = programa;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public double getPromedio() {
        return promedio;
    }

    public void setPromedio(double promedio) {
        this.promedio = promedio;
    }
}
