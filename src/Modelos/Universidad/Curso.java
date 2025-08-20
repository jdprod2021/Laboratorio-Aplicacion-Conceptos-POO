package Modelos.Universidad;

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
    
    @Override
    public String toString() {
        return this.ID + " " +  " " + this.nombre + " " + this.activo + " " + this.programa.toString();
    }

}
