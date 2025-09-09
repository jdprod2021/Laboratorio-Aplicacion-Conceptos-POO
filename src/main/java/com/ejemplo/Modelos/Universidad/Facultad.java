package com.ejemplo.Modelos.Universidad;

import com.ejemplo.Modelos.Personas.Persona;

public class Facultad {

    private double id;
    private String nombre;
    private Persona decano;

    public Facultad() {
        nombre = "";
        decano = new Persona();
    }
    
    public Facultad( String nombre, Persona decano) {
        this.nombre = nombre;
        this.decano = decano;
    }

    @Override
    public String toString() {
        return "Facultad{" + "id=" + id + ", nombre=" + nombre + ", decano=" + decano.toString() + '}';
    }

    public double getID() {
        return id;
    }

    public Persona getDecano() {
        return decano;
    }

    public String getNombre() {
        return nombre;
    }

    public void setID(double id) {
        this.id = id;
    }

    public void setDecano(Persona decano) {
        this.decano = decano;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
