package com.ejemplo.Servicios.Universidad;

import java.util.List;

import com.ejemplo.Modelos.Universidad.Facultad;

public interface FacultadServicio {

    public void crearFacultad(Facultad facultad);

    public List<Facultad> listar();

}
