package com.mycompany.proyectointegrador.repositorios;

import java.sql.SQLException;
import java.util.List;

public interface IRepositorio<T, I> { // I es el tipo de ID (Integer o String)
    void crear(T t) throws SQLException;
    T obtenerPorId(I id) throws SQLException; // Usa el tipo I
    List<T> obtenerTodos() throws SQLException;
    void actualizar(T t) throws SQLException;
    void eliminar(I id) throws SQLException; // Usa el tipo I
}