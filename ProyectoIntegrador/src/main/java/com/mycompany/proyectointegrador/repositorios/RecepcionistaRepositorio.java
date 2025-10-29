package com.mycompany.proyectointegrador.repositorios;

import com.mycompany.proyectointegrador.modelo.Recepcionista;
import com.mycompany.proyectointegrador.persistencias.ConexionDB;
import com.mycompany.proyectointegrador.utils.DBUtils;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RecepcionistaRepositorio implements IRepositorio<Recepcionista> {

    @Override
    public void crear(Recepcionista recepcionista) throws SQLException {
        String sqlRecepcionista = """
            INSERT INTO recepcionistas (idRecepcionista, idHospital)
            VALUES (?, ?)
        """;

        try (Connection conn = ConexionDB.conectar()) {
            conn.setAutoCommit(false);

            PersonaRepositorio personaRepo = new PersonaRepositorio();
            UsuarioRepositorio usuarioRepo = new UsuarioRepositorio();

            try {
                // Crear persona
                int idPersona = personaRepo.crearPersona(recepcionista, conn);
                recepcionista.setIdRecepcionista(idPersona);

                // Crear usuario
                usuarioRepo.crearUsuario(recepcionista, conn);

                // Crear recepcionista
                try (PreparedStatement stmt = conn.prepareStatement(sqlRecepcionista)) {
                    stmt.setInt(1, recepcionista.getIdRecepcionista());
                    stmt.setInt(2, recepcionista.getIdHospital());
                    stmt.executeUpdate();
                }

                conn.commit();

            } catch (SQLException e) {
                DBUtils.rollback(conn);
                throw new SQLException("Error al crear el recepcionista: " + e.getMessage(), e);
            } finally {
                DBUtils.restaurarAutoCommit(conn);
            }

        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public Recepcionista obtenerPorId(int id) throws SQLException {
        String sql = """
            SELECT 
                per.idPersona, per.nombre, per.apellido, per.fechaNacimiento, per.direccion, per.telefono, per.dni,
                u.idUsuario, u.nombreUsuario, u.hashContraseña,
                r.idRecepcionista, r.idHospital
            FROM personas per
            JOIN usuarios u ON per.idPersona = u.idUsuario
            JOIN recepcionistas r ON u.idUsuario = r.idRecepcionista
            WHERE r.idRecepcionista = ?
        """;

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearRecepcionista(rs);
                }
            }

        } catch (SQLException e) {
            throw new SQLException("Error al obtener el recepcionista por ID: " + e.getMessage(), e);
        }

        return null;
    }

    @Override
    public List<Recepcionista> obtenerTodos() throws SQLException {
        String sql = """
            SELECT 
                per.idPersona, per.nombre, per.apellido, per.fechaNacimiento, per.direccion, per.telefono, per.dni,
                u.idUsuario, u.nombreUsuario, u.hashContraseña,
                r.idRecepcionista, r.idHospital
            FROM personas per
            JOIN usuarios u ON per.idPersona = u.idUsuario
            JOIN recepcionistas r ON u.idUsuario = r.idRecepcionista
        """;

        List<Recepcionista> recepcionistas = new ArrayList<>();

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                recepcionistas.add(mapearRecepcionista(rs));
            }

        } catch (SQLException e) {
            throw new SQLException("Error al obtener los recepcionistas: " + e.getMessage(), e);
        }

        return recepcionistas;
    }

    @Override
    public void actualizar(Recepcionista recepcionista) throws SQLException {
        String sqlPersona = """
            UPDATE personas 
            SET nombre = ?, apellido = ?, fechaNacimiento = ?, direccion = ?, telefono = ?, dni = ? 
            WHERE idPersona = ?
        """;

        String sqlUsuario = """
            UPDATE usuarios 
            SET nombreUsuario = ?, hashContraseña = ?
            WHERE idUsuario = ?
        """;

        String sqlRecepcionista = """
            UPDATE recepcionistas 
            SET idHospital = ?
            WHERE idRecepcionista = ?
        """;

        try (Connection conn = ConexionDB.conectar()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmtPersona = conn.prepareStatement(sqlPersona);
                 PreparedStatement stmtUsuario = conn.prepareStatement(sqlUsuario);
                 PreparedStatement stmtRecepcionista = conn.prepareStatement(sqlRecepcionista)) {

                // Persona
                stmtPersona.setString(1, recepcionista.getNombre());
                stmtPersona.setString(2, recepcionista.getApellido());
                stmtPersona.setString(3, recepcionista.getFechaNacimiento().toString());
                stmtPersona.setString(4, recepcionista.getDireccion());
                stmtPersona.setString(5, recepcionista.getTelefono());
                stmtPersona.setString(6, recepcionista.getDni());
                stmtPersona.setInt(7, recepcionista.getIdPersona());
                stmtPersona.executeUpdate();

                // Usuario
                stmtUsuario.setString(1, recepcionista.getNombreUsuario());
                stmtUsuario.setString(2, recepcionista.getHashContraseña());
                stmtUsuario.setInt(3, recepcionista.getIdUsuario());
                stmtUsuario.executeUpdate();

                // Recepcionista
                stmtRecepcionista.setInt(1, recepcionista.getIdHospital());
                stmtRecepcionista.setInt(2, recepcionista.getIdRecepcionista());
                stmtRecepcionista.executeUpdate();

                conn.commit();

            } catch (SQLException e) {
                DBUtils.rollback(conn);
                throw new SQLException("Error al actualizar el recepcionista.", e);
            } finally {
                DBUtils.restaurarAutoCommit(conn);
            }
        }
    }

    @Override
    public void eliminar(int idRecepcionista) throws SQLException {
        String sqlPersona = "DELETE FROM personas WHERE idPersona = ?";
        String sqlUsuario = "DELETE FROM usuarios WHERE idUsuario = ?";
        String sqlRecepcionista = "DELETE FROM recepcionistas WHERE idRecepcionista = ?";

        try (Connection conn = ConexionDB.conectar()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmtPersona = conn.prepareStatement(sqlPersona);
                 PreparedStatement stmtUsuario = conn.prepareStatement(sqlUsuario);
                 PreparedStatement stmtRecepcionista = conn.prepareStatement(sqlRecepcionista)) {

                stmtRecepcionista.setInt(1, idRecepcionista);
                stmtRecepcionista.executeUpdate();

                stmtUsuario.setInt(1, idRecepcionista);
                stmtUsuario.executeUpdate();

                stmtPersona.setInt(1, idRecepcionista);
                stmtPersona.executeUpdate();

                conn.commit();

            } catch (SQLException e) {
                DBUtils.rollback(conn);
                throw new SQLException("Error al eliminar el recepcionista.", e);
            } finally {
                DBUtils.restaurarAutoCommit(conn);
            }

        }
    }

    private Recepcionista mapearRecepcionista(ResultSet rs) throws SQLException {
        Recepcionista recepcionista = new Recepcionista();

        // Persona
        recepcionista.setIdPersona(rs.getInt("idPersona"));
        recepcionista.setNombre(rs.getString("nombre"));
        recepcionista.setApellido(rs.getString("apellido"));
        recepcionista.setFechaNacimiento(LocalDate.parse(rs.getString("fechaNacimiento")));
        recepcionista.setDireccion(rs.getString("direccion"));
        recepcionista.setTelefono(rs.getString("telefono"));
        recepcionista.setDni(rs.getString("dni"));

        // Usuario
        recepcionista.setIdUsuario(rs.getInt("idUsuario"));
        recepcionista.setNombreUsuario(rs.getString("nombreUsuario"));
        recepcionista.setHashContraseña(rs.getString("hashContraseña"));
        recepcionista.setSesionIniciada(false);

        // Recepcionista
        recepcionista.setIdRecepcionista(rs.getInt("idRecepcionista"));
        recepcionista.setIdHospital(rs.getInt("idHospital"));

        return recepcionista;
    }
}
