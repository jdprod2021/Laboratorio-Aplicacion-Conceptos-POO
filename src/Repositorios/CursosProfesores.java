package Repositorios;

import java.util.ArrayList;
import java.util.List;

import Modelos.Cursos.CursoProfesor;

public class CursosProfesores implements Servicios {

    private List<CursoProfesor> cursoProfesores;

    public CursosProfesores() {}

    @Override
    public String imprimirPosicion(int posicion) {
        if (posicion >= 0 && posicion < cursoProfesores.size()) {
            return cursoProfesores.get(posicion).toString();
        }
        return "Posición inválida";
    }

    @Override
    public int cantidadActual() {
        return cursoProfesores.size();
    }

    @Override
    public List<String> imprimirListado() {
        List<String> listado = new ArrayList<>();
        for (CursoProfesor cp : cursoProfesores) {
            listado.add(cp.toString());
        }
        return listado;
    }

    public CursosProfesores(List<CursoProfesor> cursoProfesores) {
        this.cursoProfesores = cursoProfesores;
    }

    public void inscribir(CursoProfesor cursoProfesor) {
        if (cursoProfesor != null) {
            cursoProfesores.add(cursoProfesor);
        } else {
            System.out.println("No se puede inscribir un curso nulo.");
        }
    }

    public void guardarInformacion(){

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (CursoProfesor cp : cursoProfesores) {
            sb.append(cp.toString()).append("\n");
        }
        return sb.toString();
    }

    public void cargarDatos() {
    
    }
}
