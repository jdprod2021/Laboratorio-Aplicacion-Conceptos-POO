package com.ejemplo.Modelos.Universidad;
import java.util.Date;

public class Programa {
    
    private double id;
    private String nombre;
    private double duracion;
    private Date registro;
    private Facultad facultad; // ASOCIACION FACULTAD

    public Programa(){}

    public Programa(double id, String nombre, double duracion, Date registro, Facultad facultad){
        this.id = id;
        this.nombre = nombre;
        this.duracion = duracion;
        this.registro = registro;
        this.facultad = facultad;
    }

    @Override
    public String toString(){
        return id + " " + nombre + " " + duracion + " " + registro + " " + facultad.toString();
    }

    public double getID() {
        return id;
    }

    public void setID(double id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getDuracion() {
        return duracion;
    }

    public void setDuracion(double duracion) {
        this.duracion = duracion;
    }

    public Date getRegistro() {
        return registro;
    }

    public void setRegistro(Date registro) {
        this.registro = registro;
    }

    public Facultad getFacultad() {
        return facultad;
    }

    public void setFacultad(Facultad facultad) {
        this.facultad = facultad;
    }

}
