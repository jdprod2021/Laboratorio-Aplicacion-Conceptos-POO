package com.ejemplo.DAOs.Interfaces;

import java.util.List;

public interface DAOGeneral <K, T>{
    public T guardar(T entidad);
    public List<T> listar();
    public void actualizar(T entidad);
    public void eliminar(K id);
}