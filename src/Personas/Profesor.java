package Personas;

public class Profesor extends Persona{
    private String TipoContrato;
    public Profesor() {
    }
    public Profesor (double id, String nombre, String apellido, String email, String TipoContrato) {
        super(id,nombre, apellido, email);
        this.TipoContrato = TipoContrato;
    }
    public String toString(){
        return super.toString() + " TipoContrato: " + this.TipoContrato;
    }
}
