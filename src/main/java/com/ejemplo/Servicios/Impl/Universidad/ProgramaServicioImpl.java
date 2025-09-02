package com.ejemplo.Servicios.Impl.Universidad;

import java.util.List;

import com.ejemplo.Modelos.Universidad.Programa;
import com.ejemplo.Repositorios.Universidad.ProgramaRepo;
import com.ejemplo.Servicios.Universidad.ProgramaServicio;

public class ProgramaServicioImpl implements ProgramaServicio{

   private final ProgramaRepo programaRepo;

   public ProgramaServicioImpl(ProgramaRepo programaRepo) {
       this.programaRepo = programaRepo;
   }

   @Override
    public String crearPrograma(Programa programa) {
        programaRepo.Crear(programa);
        return "Programa creado exitosamente";
    }

    @Override
    public List<Programa> listarProgramas() {
        return programaRepo.getListado();
    }

}
