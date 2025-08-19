package Personas;

public class Persona {
    private double id;
    private String nombre;
    private String apellido;
    private String email;

    public Persona() {}
    public Persona(double id, String nombre, String apellido, String email) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public String toString(){
        return "ID:" + this.id + " " + " Nombre: " + this.nombre + " Apellido: " + this.apellido + " Email: " + this.email;
    }


}

