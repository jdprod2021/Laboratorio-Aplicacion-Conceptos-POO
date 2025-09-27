package com.ejemplo.DAOs.Interfaces;

import java.util.Optional;

import com.ejemplo.Modelos.Persona;

public interface PersonaDAO extends DAOGeneral<Long, Persona>{

    Optional<Persona> buscarPorCorreo(String correo);
    Optional<Persona> buscarPorId(Long id);
}
