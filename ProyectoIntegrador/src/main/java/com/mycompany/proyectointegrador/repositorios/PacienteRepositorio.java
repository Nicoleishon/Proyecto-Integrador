package com.mycompany.proyectointegrador.repositorios;

import com.mycompany.proyectointegrador.modelo.Paciente;
import com.mycompany.proyectointegrador.persistencias.ConexionDB;
import com.mycompany.proyectointegrador.utils.DBUtils;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PacienteRepositorio implements IRepositorio<Paciente> {

    @Override
    public void crear(Paciente paciente) throws SQLException {
        String sqlPaciente = """
            INSERT INTO pacientes (idPaciente, idHospital, fechaRegistro)
            VALUES (?, ?, ?)
        """;

        try (Connection conn = ConexionDB.conectar()) {
            conn.setAutoCommit(false);

            PersonaRepositorio personaRepo = new PersonaRepositorio();
            UsuarioRepositorio usuarioRepo = new UsuarioRepositorio();

            try {
                // Crear persona
                int idPersona = personaRepo.crearPersona(paciente, conn);
                paciente.setIdPaciente(idPersona);

                // Crear usuario asociado a la persona
                usuarioRepo.crearUsuario(paciente, conn);

                // Crear paciente
                try (PreparedStatement stmtPaciente = conn.prepareStatement(sqlPaciente)) {
                    stmtPaciente.setInt(1, paciente.getIdPaciente());
                    stmtPaciente.setInt(2, paciente.getIdHospital());
                    stmtPaciente.setString(3, paciente.getFechaRegistro().toString());
                    stmtPaciente.executeUpdate();
                }

                // Confirmar si todo salió bien
                conn.commit();

            } catch (SQLException e) {
                // Revertir todo si ocurre cualquier error
                DBUtils.rollback(conn);
                throw new SQLException("Error al crear el paciente: " + e.getMessage(), e);
            } finally {
                // Restaurar el comportamiento por defecto
                DBUtils.restaurarAutoCommit(conn);
            }

        } catch (SQLException e) {
            throw e;
        }
    }


    // En un futuro se tendría que pasar el Paciente para obtener idPaciente y idHospital para sistema multihospital 
    @Override
    public Paciente obtenerPorId(int id) throws SQLException {
        String sql = """
            SELECT 
                per.idPersona, per.nombre, per.apellido, per.fechaNacimiento, per.direccion, per.telefono, per.dni,
                u.idUsuario, u.nombreUsuario, u.hashContraseña,
                pa.idPaciente, pa.idHospital, pa.fechaRegistro
            FROM personas per
            JOIN usuarios u ON per.idPersona = u.idUsuario
            JOIN pacientes pa ON u.idUsuario = pa.idPaciente
            WHERE pa.idPaciente = ?
        """;

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearPaciente(rs);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error al obtener el paciente por ID: " + e.getMessage(), e);
        }

        return null;
    }

    @Override
    public List<Paciente> obtenerTodos() throws SQLException {
        String sql = """
            SELECT 
                per.idPersona, per.nombre, per.apellido, per.fechaNacimiento, per.direccion, per.telefono, per.dni,
                u.idUsuario, u.nombreUsuario, u.hashContraseña,
                pa.idPaciente, pa.idHospital, pa.fechaRegistro
            FROM personas per
            JOIN usuarios u ON per.idPersona = u.idUsuario
            JOIN pacientes pa ON u.idUsuario = pa.idPaciente
        """;

        List<Paciente> pacientes = new ArrayList<>();

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                pacientes.add(mapearPaciente(rs));
            }

        } catch (SQLException e) {
            throw new SQLException("Error al obtener todos los pacientes: " + e.getMessage(), e);
        }

        return pacientes;
    }

    @Override
    public void actualizar(Paciente paciente) throws SQLException {
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
        
        String sqlPaciente = """
            UPDATE pacientes 
            SET fechaRegistro = ?
            WHERE idPaciente = ?
        """;

            try (Connection conn = ConexionDB.conectar()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmtPersona = conn.prepareStatement(sqlPersona);
                 PreparedStatement stmtUsuario = conn.prepareStatement(sqlUsuario);
                 PreparedStatement stmtPaciente = conn.prepareStatement(sqlPaciente)) {

                // --- Actualizar Persona ---
                stmtPersona.setString(1, paciente.getNombre());
                stmtPersona.setString(2, paciente.getApellido());
                stmtPersona.setString(3, paciente.getFechaNacimiento().toString());
                stmtPersona.setString(4, paciente.getDireccion());
                stmtPersona.setString(5, paciente.getTelefono());
                stmtPersona.setString(6, paciente.getDni());
                stmtPersona.setInt(7, paciente.getIdPersona());
                stmtPersona.executeUpdate();

                // --- Actualizar Usuario ---
                stmtUsuario.setString(1, paciente.getNombreUsuario());
                stmtUsuario.setString(2, paciente.getHashContraseña());
                stmtUsuario.setInt(3, paciente.getIdUsuario());
                stmtUsuario.executeUpdate();
                
                // --- Actualizar Paciente ---
                stmtPaciente.setString(1, paciente.getFechaRegistro().toString());
                stmtPaciente.setInt(2, paciente.getIdPaciente());
                stmtPaciente.executeUpdate();

                conn.commit();
              
            } catch (SQLException e) {
                DBUtils.rollback(conn);
                throw new SQLException("Error al actualizar el paciente.", e);
            } finally {
                DBUtils.restaurarAutoCommit(conn);
            }
        }
    }

    @Override
    public void eliminar(int idPaciente) throws SQLException {
        String sqlPersona = "DELETE FROM personas WHERE idPersona = ?";
        String sqlUsuario = "DELETE FROM usuarios WHERE idUsuario = ?";
        String sqlPaciente = "DELETE FROM pacientes WHERE idPaciente = ?";
        String sqlTurnos = "DELETE FROM turnos WHERE idPaciente = ?";

        try (Connection conn = ConexionDB.conectar()) {
            conn.setAutoCommit(false); // Iniciar transacción
        
            try (PreparedStatement stmtPersona = conn.prepareStatement(sqlPersona);
                 PreparedStatement stmtUsuario = conn.prepareStatement(sqlUsuario);
                 PreparedStatement stmtPaciente = conn.prepareStatement(sqlPaciente);
                 PreparedStatement stmtTurnos = conn.prepareStatement(sqlTurnos)) {

                stmtTurnos.setInt(1, idPaciente);
                stmtTurnos.executeUpdate();
                
                stmtPaciente.setInt(1, idPaciente);
                stmtPaciente.executeUpdate();
                
                stmtUsuario.setInt(1, idPaciente);
                stmtUsuario.executeUpdate();
                
                stmtPersona.setInt(1, idPaciente);
                stmtPersona.executeUpdate();  
                
                conn.commit();

            } catch (SQLException e) {
                DBUtils.rollback(conn);
                throw new SQLException("Error al eliminar al paciente.", e);
            } finally {
                DBUtils.restaurarAutoCommit(conn);
            }
        }
    }

    
    private Paciente mapearPaciente(ResultSet rs) throws SQLException {
        Paciente paciente = new Paciente();

        // Persona
        paciente.setIdPersona(rs.getInt("idPersona"));
        paciente.setNombre(rs.getString("nombre"));
        paciente.setApellido(rs.getString("apellido"));
        paciente.setFechaNacimiento(LocalDate.parse(rs.getString("fechaNacimiento")));
        paciente.setDireccion(rs.getString("direccion"));
        paciente.setTelefono(rs.getString("telefono"));
        paciente.setDni(rs.getString("dni"));

        // Usuario
        paciente.setIdUsuario(rs.getInt("idUsuario"));
        paciente.setNombreUsuario(rs.getString("nombreUsuario"));
        paciente.setHashContraseña(rs.getString("hashContraseña"));
        paciente.setSesionIniciada(Boolean.FALSE);

        // Paciente
        paciente.setIdPaciente(rs.getInt("idPaciente"));
        paciente.setIdHospital(rs.getInt("idHospital"));
        paciente.setFechaRegistro(LocalDate.parse(rs.getString("fechaRegistro")));

        return paciente;
    }

}
