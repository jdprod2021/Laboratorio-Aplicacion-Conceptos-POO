package com.ejemplo.Modelos.Universidad;

public class Curso {

    private int ID;
    private String nombre;
    private Programa programa;
    private boolean activo;

    public Curso() {}

    public Curso(int ID, String nombre, Programa programa, boolean activo) {
        this.ID = ID;
        this.nombre = nombre;
        this.programa = programa;
        this.activo = activo;
    }

    public Curso(int ID) {
        this.ID = ID;
    }

    @Override
    public String toString() {
        return "ID: " + this.ID +  ", Nombre: " + this.nombre + ", Activo: " + this.activo + ", Programa: " + this.programa.toString();
    }
    
    public int getId() {
        return ID;
    }

    public void setId(int iD) {
        ID = iD;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
}
