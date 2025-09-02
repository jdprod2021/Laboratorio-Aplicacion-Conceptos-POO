package com.ejemplo.Servicios.Universidad;

import java.util.List;

import com.ejemplo.Modelos.Universidad.Programa;

public interface ProgramaServicio {

    String crearPrograma(Programa programa);
    
    List<Programa> listarProgramas();
}