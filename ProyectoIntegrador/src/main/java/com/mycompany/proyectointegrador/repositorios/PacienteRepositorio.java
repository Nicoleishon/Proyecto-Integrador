package com.mycompany.proyectointegrador.repositorios;

import com.mycompany.proyectointegrador.modelo.Paciente;
import com.mycompany.proyectointegrador.persistencias.ConexionDB;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PacienteRepositorio implements IRepositorio<Paciente> {

    @Override
    public void crear(Paciente paciente) throws SQLException {
        String sqlPersona = """
            INSERT INTO personas (nombre, apellido, fechaNacimiento, direccion, telefono, dni)
            VALUES (?, ?, ?, ?, ?, ?)
        """;

        String sqlPaciente = """
            INSERT INTO pacientes (idPaciente, fechaRegistro)
            VALUES (?, ?)
        """;

        try (Connection conn = ConexionDB.conectar()) {
            conn.setAutoCommit(false);

            try (
                PreparedStatement stmtPersona = conn.prepareStatement(sqlPersona, Statement.RETURN_GENERATED_KEYS);
                PreparedStatement stmtPaciente = conn.prepareStatement(sqlPaciente)
            ) {
                // Inserción en personas
                stmtPersona.setString(1, paciente.getNombre());
                stmtPersona.setString(2, paciente.getApellido());
                stmtPersona.setString(3, paciente.getFechaNacimiento() != null ? paciente.getFechaNacimiento().toString() : null);
                stmtPersona.setString(4, paciente.getDireccion());
                stmtPersona.setString(5, paciente.getTelefono());
                stmtPersona.setString(6, paciente.getDni());
                stmtPersona.executeUpdate();

                try (ResultSet rs = stmtPersona.getGeneratedKeys()) {
                    if (rs.next()) {
                        int idGenerado = rs.getInt(1);
                        paciente.setIdPaciente(idGenerado);

                        stmtPaciente.setInt(1, idGenerado);
                        stmtPaciente.setString(2, paciente.getFechaRegistro().toString());
                        stmtPaciente.executeUpdate();
                    } else {
                        throw new SQLException("No se pudo obtener el ID generado para la persona.");
                    }
                }

                conn.commit();

            } catch (SQLException e) {
                conn.rollback();
                throw new SQLException("Error al crear el paciente: " + e.getMessage(), e);
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }

    @Override
    public Paciente obtenerPorId(int id) throws SQLException {
        String sql = """
            SELECT per.idPersona, per.nombre, per.apellido, per.fechaNacimiento, per.direccion, per.telefono, per.dni,
                   pa.idPaciente, pa.fechaRegistro
            FROM personas per
            JOIN pacientes pa ON per.idPersona = pa.idPaciente
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
            SELECT per.idPersona, per.nombre, per.apellido, per.fechaNacimiento, per.direccion, per.telefono, per.dni,
                   pa.idPaciente, pa.fechaRegistro
            FROM personas per
            JOIN pacientes pa ON per.idPersona = pa.idPaciente
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
        String sql = """
            UPDATE personas 
            SET nombre = ?, apellido = ?, fechaNacimiento = ?, direccion = ?, telefono = ?, dni = ? 
            WHERE idPersona = ?
        """;

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, paciente.getNombre());
            stmt.setString(2, paciente.getApellido());
            stmt.setString(3, paciente.getFechaNacimiento().toString());
            stmt.setString(4, paciente.getDireccion());
            stmt.setString(5, paciente.getTelefono());
            stmt.setString(6, paciente.getDni());
            stmt.setInt(7, paciente.getIdPaciente());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new SQLException("Error al actualizar el paciente: " + e.getMessage(), e);
        }
    }

    @Override
    public void eliminar(int idPaciente) throws SQLException {
        String sql = "DELETE FROM pacientes WHERE idPaciente = ?";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPaciente);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new SQLException("Error al eliminar el paciente: " + e.getMessage(), e);
        }
    }


    private Paciente mapearPaciente(ResultSet rs) throws SQLException {
        Paciente paciente = new Paciente();

        paciente.setIdPaciente(rs.getInt("idPaciente"));
        paciente.setFechaRegistro(LocalDate.parse(rs.getString("fechaRegistro")));

        paciente.setIdPersona(rs.getInt("idPersona"));
        paciente.setNombre(rs.getString("nombre"));
        paciente.setApellido(rs.getString("apellido"));

        String fechaNacimientoStr = rs.getString("fechaNacimiento");
        paciente.setFechaNacimiento(fechaNacimientoStr != null ? LocalDate.parse(fechaNacimientoStr) : null);

        paciente.setDireccion(rs.getString("direccion"));
        paciente.setTelefono(rs.getString("telefono"));
        paciente.setDni(rs.getString("dni"));

        return paciente;
    }
}
