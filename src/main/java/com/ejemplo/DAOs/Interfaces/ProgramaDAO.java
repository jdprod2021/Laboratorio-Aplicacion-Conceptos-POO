package com.ejemplo.DAOs.Interfaces;

import java.util.Optional;

import com.ejemplo.Modelos.Programa;

public interface ProgramaDAO extends DAOGeneral<Long, Programa>{

    Optional<Programa> buscarPorId(Long id);
}
