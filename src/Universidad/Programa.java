package Universidad;
import java.util.Date;

public class Programa {
    double id;
    String nombre;
    double duracion;
    Date registro;
    //ASOCIACION FACULTAD

    public Programa(){}
    public Programa(double id, String nombre, double duracion, Date registro){
        this.id = id;
        this.nombre = nombre;
        this.duracion = duracion;
        this.registro = registro;
        //ASOCIACION FACULTAD
    }
    public String toString(){
        return id + " " + nombre + " " + duracion + " " + registro;
    }

}
