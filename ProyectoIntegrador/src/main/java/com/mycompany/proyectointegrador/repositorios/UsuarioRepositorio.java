package com.mycompany.proyectointegrador.repositorios;

import com.mycompany.proyectointegrador.excepciones.CredencialesInvalidasException;
import com.mycompany.proyectointegrador.excepciones.TipoUsuarioInvalidoException;
import com.mycompany.proyectointegrador.modelo.Paciente;
import com.mycompany.proyectointegrador.modelo.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class UsuarioRepositorio {
    
    private static PacienteRepositorio pacienteRepo = new PacienteRepositorio();
    private static RecepcionistaRepositorio recepcionistaRepo = new RecepcionistaRepositorio();

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
    
    public int buscarIdPorNombreUsuario(String nombreUsuario) throws SQLException, CredencialesInvalidasException {
        String sql = "SELECT idUsuario FROM usuarios WHERE nombreUsuario = ?";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombreUsuario);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt("idUsuario");
            throw new CredencialesInvalidasException();
        } catch (SQLException e) {
            throw new SQLException("Error al buscar idUsuario por nombre de usuario.", e);
        }
    }

    public Usuario buscarPorId(int idUsuario) throws SQLException, TipoUsuarioInvalidoException {
        String sql = "SELECT 1 FROM usuarios WHERE idUsuario = ?";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) {
                throw new SQLException("Error al buscar usuario por idUsuario.");
            }

            // Buscar tipo de usuario
            Usuario usuarioEncontrado = pacienteRepo.obtenerPorId(idUsuario);
            if (usuarioEncontrado != null) return usuarioEncontrado;

            usuarioEncontrado = recepcionistaRepo.obtenerPorId(idUsuario);
            if (usuarioEncontrado != null) return usuarioEncontrado;

            throw new TipoUsuarioInvalidoException("El usuario no pertenece a ningún tipo válido.");
            
        } catch (SQLException e) {
            throw new SQLException("Error al buscar usuario por ID.", e);
        }
    }
    
    public boolean existeNombreUsuario(String nombreUsuario) throws SQLException {
        String sql = "SELECT COUNT(*) FROM usuarios WHERE nombreUsuario = ?";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombreUsuario);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error al verificar existencia de nombre de usuario: " + e.getMessage(), e);
        }
        return false;
    }



}
