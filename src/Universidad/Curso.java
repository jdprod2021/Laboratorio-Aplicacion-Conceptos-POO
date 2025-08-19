package Universidad;

public class Curso {
    private int ID;
    private String nombre;
    //ASOCIACION PROGRAMA
    private boolean activo;

    public Curso() {}
    public Curso(int ID, String nombre, boolean activo) {
        this.ID = ID;
        this.nombre = nombre;
        this.activo = activo;
    }
    public String toString() {
        return this.ID + " " +  " " + this.nombre + " " + this.activo;
    }

}
