package com.ejemplo.Servicios.Impl.Universidad;

import java.util.List;

import com.ejemplo.Modelos.Universidad.Facultad;
import com.ejemplo.Repositorios.Universidad.FacultadRepo;
import com.ejemplo.Servicios.Universidad.FacultadServicio;

public class FacultadSerivicioImpl implements FacultadServicio {


    private final FacultadRepo facultadRepo;
    
    public FacultadSerivicioImpl(FacultadRepo facultadRepo) {
        this.facultadRepo = facultadRepo;
    }

    @Override
    public void crearFacultad(Facultad facultad) {
        facultadRepo.crearFacultad(facultad);
    }

    @Override
    public List<Facultad> listar() {
        return facultadRepo.getListado();
    }
}
