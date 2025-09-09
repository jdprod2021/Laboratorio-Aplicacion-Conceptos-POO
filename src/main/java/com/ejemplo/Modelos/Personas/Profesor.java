package com.ejemplo.Modelos.Personas;

public class Profesor extends Persona{

    private String TipoContrato;

    public Profesor() {}

    public Profesor (String nombre, String apellido, String email, String TipoContrato) {
        super(nombre, apellido, email);
        this.TipoContrato = TipoContrato;
    }

    public Profesor (long id) {
        super(id);
    }

    @Override
    public String toString(){
        return super.toString() + " TipoContrato: " + this.TipoContrato;
    }

    public String getTipoContrato() {
        return TipoContrato;
    }

    public void setTipoContrato(String tipoContrato) {
        TipoContrato = tipoContrato;
    }
}
