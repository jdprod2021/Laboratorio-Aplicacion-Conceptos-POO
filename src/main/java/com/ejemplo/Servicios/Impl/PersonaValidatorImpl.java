package com.ejemplo.Servicios.Impl;

import com.ejemplo.Modelos.Personas.Persona;
import com.ejemplo.Servicios.PersonaValidator;

public class PersonaValidatorImpl implements PersonaValidator {

   @Override
   public void validarCamposBasicos(Persona p) {
        if (p == null) throw new IllegalArgumentException("Persona requerida");
        if (p.getNombres() == null || p.getNombres().isBlank()) throw new IllegalArgumentException("Nombre requerido");
        if (p.getApellidos() == null || p.getApellidos().isBlank()) throw new IllegalArgumentException("Apellido requerido");
        if (p.getEmail() == null || p.getEmail().isBlank() || !p.getEmail().contains("@")) throw new IllegalArgumentException("Email inválido");
   }

}
