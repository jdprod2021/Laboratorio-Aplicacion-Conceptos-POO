package com.ejemplo.Vistas.Implementaciones;

import com.ejemplo.Controladores.CursoControlador;
import com.ejemplo.Observador.Observador;
import com.ejemplo.Vistas.Interface.Vista;

public class VistaCursoCreado implements Observador, Vista{
    
    private CursoControlador cursoControlador;

    public VistaCursoCreado(CursoControlador cursoControlador){
        this.cursoControlador = cursoControlador;
    }

    @Override
    public void actualizar(){
        System.out.println("");
        System.out.println("\u001B[32m¡Se ha creado un curso!\u001B[0m");
        System.out.println("");
    }

    @Override
    public void inicializar(){
        this.cursoControlador.adicionarObservador(this);
    }

}
