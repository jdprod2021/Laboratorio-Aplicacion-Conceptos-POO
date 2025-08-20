package Modelos.Universidad;
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

}
