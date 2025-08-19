package Universidad;

public class Facultad {
    private double id;
    private String nombre;

    //ASOCIACION PERSONA

    public Facultad(){}
    public Facultad(double id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        //ASOCIACION
    }
    public String toString() {
        return "Facultad{" + "id=" + id + ", nombre=" + nombre + '}';
    }

}
