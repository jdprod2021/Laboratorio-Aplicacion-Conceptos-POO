package com.ejemplo.Modelos;

import java.util.ArrayList;
import java.util.List;

import com.ejemplo.Observador.Observable;
import com.ejemplo.Observador.Observador;

public class CursosCreados implements Observable{
    
    private List<Curso> cursos;
    private static CursosCreados cursosCreados;
    private List<Observador> listadoDeObservadores;

    private CursosCreados(){
        this.cursos = new ArrayList<>();
        this.listadoDeObservadores = new ArrayList<>();
    }

    public static CursosCreados crearCursosCreados(){
        if(cursosCreados == null){
            synchronized (CursosCreados.class){
                if(cursosCreados == null){
                    cursosCreados = new CursosCreados();
                }
            }
        }
        return cursosCreados;
    }

    @Override
    public void notificar(){
        for (Observador observador : listadoDeObservadores) {
            observador.actualizar();
        }
    }

    @Override
    public void adicionarObservador(Observador observador){
        this.listadoDeObservadores.add(observador);
    }

    @Override
    public void removerObservador(Observador observador){
        this.listadoDeObservadores.remove(observador);
    }

    public void adicionarCurso(Curso curso){
        this.cursos.add(curso);
        notificar();
    }

    public void removerCurso(Long id){
        cursos.removeIf(c -> java.util.Objects.equals((long)c.getId(), id));
    }

}
