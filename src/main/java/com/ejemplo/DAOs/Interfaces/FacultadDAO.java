package com.ejemplo.DAOs.Interfaces;

import java.util.Optional;

import com.ejemplo.Modelos.Facultad;

public interface FacultadDAO extends DAOGeneral<Long, Facultad>{

    Optional<Facultad> buscarPorId(Long id);

}
