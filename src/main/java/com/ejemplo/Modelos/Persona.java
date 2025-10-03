package com.ejemplo.Modelos;

public class Persona {

    private double id;
    private String nombre;
    private String apellido;
    private String email;

    public Persona() {
        nombre = "";
        apellido = "";
        email = "a@";
    }

    public Persona(String nombre, String apellido, String email) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
    }

    public Persona (long id) {
        this.id = id;
    }

    @Override
    public String toString(){
        return "ID:" + this.id + " " + " Nombre: " + this.nombre + " Apellido: " + this.apellido + " Email: " + this.email;
    }

    public double getId() {
        return id;
    }

    public void setId(double id) {
        this.id = id;
    }

    public String getNombres() {
        return nombre;
    }

    public void setNombres(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellido;
    }

    public void setApellidos(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

