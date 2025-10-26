package com.mycompany.proyectointegrador.repositorios;

import java.sql.SQLException;
import java.util.List;


public interface IRepositorio<T> { 
    T obtenerPorId(int id) throws SQLException; 
    void crear(T t) throws SQLException;
    List<T> obtenerTodos() throws SQLException;
    void actualizar(T t) throws SQLException;
    void eliminar(int id) throws SQLException;
}