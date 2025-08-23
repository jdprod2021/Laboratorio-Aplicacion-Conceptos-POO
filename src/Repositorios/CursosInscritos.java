package Repositorios;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import Modelos.Cursos.CursoProfesor;
import Modelos.Universidad.Inscripcion;

public class CursosInscritos implements Servicios {

    private List<Inscripcion> inscripcion;

    public CursosInscritos() {}
    public CursosInscritos(List<Inscripcion> inscripcion) {
        this.inscripcion = inscripcion;
    }

    @Override
    public String imprimirPosicion(int posicion){
        if (posicion >= 0 && posicion < inscripcion.size()) {
            return inscripcion.get(posicion).toString();
        }
        return "Posición inválida";
    }

    @Override
    public int cantidadActual() {
        return inscripcion.size();
    }

    @Override
    public List<String> imprimirListado() {
        List<String> listado = new ArrayList<>();
        for (Inscripcion I : inscripcion) {
            listado.add(I.toString());
        }
        return listado;
    }

    public void inscribirCurso(Inscripcion inscripcionadd){
        if(inscripcion != null){
            inscripcion.add(inscripcionadd);
        }
    }
    public void eliminar(Inscripcion inscripciondel){
        if((inscripcion != null) && inscripcion.contains(inscripciondel)){
            inscripcion.remove(inscripciondel);
        }else {
            System.out.println("No se puede eliminar una persona nula o que no está inscrita.");
        }
    }
    public void actualizar(Inscripcion inscripcionact){
        if (inscripcionact != null && inscripcion.contains(inscripcionact)) {
            int index = inscripcion.indexOf(inscripcionact);
            inscripcion.set(index, inscripcionact);
        } else {
            System.out.println("No se puede actualizar una persona nula o que no está inscrita.");
        }
    }
    public void guardarInformacion(Inscripcion inscripcion){

    }
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (Inscripcion I : inscripcion) {
            sb.append(I.toString()).append("\n");
        }
        return sb.toString();

    }

    public void CargarDatos(Inscripcion inscripcion){

    }

}
