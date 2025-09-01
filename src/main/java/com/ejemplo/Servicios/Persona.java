package com.ejemplo.Servicios;

import java.util.List;

public interface Persona {

    Persona inscribir(String nombres, String apellidos, String email);

    List<Persona> listar();

}

