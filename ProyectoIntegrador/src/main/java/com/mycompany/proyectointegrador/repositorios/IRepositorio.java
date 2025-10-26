package com.mycompany.proyectointegrador.repositorios;

import java.sql.SQLException;
import java.util.List;

public interface IRepositorio<T>{
    
    void crear(T t) throws SQLException;
    T leer(int id) throws SQLException;
    void actualizar(T t) throws SQLException;
    void eliminar(int id) throws SQLException;
    List<T> listarTodos() throws SQLException;
}
    

