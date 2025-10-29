package com.mycompany.proyectointegrador.repositorios;

import com.mycompany.proyectointegrador.modelo.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UsuarioRepositorio {

    public void crearUsuario(Usuario usuario, Connection conn) throws SQLException {
        String sql = "INSERT INTO usuarios (idUsuario, nombreUsuario, hashContraseña) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, usuario.getIdPersona());
            stmt.setString(2, usuario.getNombreUsuario());
            stmt.setString(3, usuario.getHashContraseña());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new SQLException ("Error al crear usuario.", e);
        }
    }
}
