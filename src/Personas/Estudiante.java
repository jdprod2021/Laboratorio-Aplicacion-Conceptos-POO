package Personas;

public class Estudiante extends Persona{
    private double codigo;
    //ASOCIACION programa;
    private boolean activo;
    private double promedio;

    public Estudiante() {}
    public Estudiante(double id, String nombre, String apellido, String email, double codigo, boolean activo, double promedio) {
        super(id, nombre, apellido, email);
        this.codigo = codigo;
        //ASOCIACION this.programa = programa;
        this.activo = activo;
        this.promedio = promedio;
    }

    @Override
    public String toString() {
        return super.toString() + "Codigo: " + this.codigo  + " Programa: "  + " Activo: " + this.activo + " Promedio: " + this.promedio;
    }

}
