package Repositorios;

import java.util.ArrayList;
import java.util.List;

import Modelos.Personas.Persona;

public class InscripcionesPersonas implements Servicios {

    private List<Persona> listado;

    public InscripcionesPersonas(){
        cargarDatos();
    }

    public InscripcionesPersonas(List<Persona> listado){
        this.listado = listado;
    }

    @Override
    public String imprimirPosicion(int posicion) {
        if (posicion >= 0 && posicion < listado.size()) {
            return listado.get(posicion).toString();
        }
        return "Posición inválida";
    }

    @Override
    public int cantidadActual() {
        return listado.size();
    }

    @Override
    public List<String> imprimirListado() {
        List<String> retorno = new ArrayList<>();
        for (Persona cp : listado) {
            retorno.add(cp.toString());
        }
        return retorno;
    }

    public void inscribir(Persona persona){
        if (persona != null) {
            listado.add(persona);
        } else {
            System.out.println("No se puede inscribir una persona nula.");
        }
    }

    public void eliminar(Persona persona){
        if (persona != null && listado.contains(persona)) {
            listado.remove(persona);
        } else {
            System.out.println("No se puede eliminar una persona nula o que no está inscrita.");
        }
    }

    public void actualizar(Persona persona){
        if (persona != null && listado.contains(persona)) {
            int index = listado.indexOf(persona);
            listado.set(index, persona);
        } else {
            System.out.println("No se puede actualizar una persona nula o que no está inscrita.");
        }
    }

    public void guardarInformacion(){}

    public void cargarDatos(){}


}
