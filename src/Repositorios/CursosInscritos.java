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

    public void inscribirCurso(Inscripcion inscripcion){
        if(inscripcion != null){
            this.inscripcion.add(inscripcion);
        }
    }
    public void eliminar(Inscripcion inscripcion){

    }
    public void actualizar(Inscripcion inscripcion){

    }
    public void guardarInformacion(Inscripcion inscripcion){

    }
    public String toString(){

        return "hi";
    }

    public void CargarDatos(Inscripcion inscripcion){

    }

}
