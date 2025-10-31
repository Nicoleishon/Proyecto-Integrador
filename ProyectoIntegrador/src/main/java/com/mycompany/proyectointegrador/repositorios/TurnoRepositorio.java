package com.mycompany.proyectointegrador.repositorios;

import com.mycompany.proyectointegrador.modelo.*;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TurnoRepositorio implements IRepositorio<Turno> {

    private final MedicoRepositorio medicoRepo = new MedicoRepositorio();
    private final PacienteRepositorio pacienteRepo = new PacienteRepositorio();

    private Turno mapearTurno(ResultSet rs) throws SQLException {
        Turno turno = new Turno();
        turno.setIdTurno(rs.getInt("idTurno"));
        turno.setFechaHora(LocalDateTime.parse(rs.getString("fechaHora")));
        turno.setMotivoConsulta(rs.getString("motivoConsulta"));
        turno.setEstado(EstadoTurno.valueOf(rs.getString("estado")));

        turno.setIdMedico(rs.getInt("idMedico"));
        turno.setIdPaciente(rs.getInt("idPaciente"));

        return turno;
    }

    @Override
    public void crear(Turno turno) throws SQLException {
        String sql = "INSERT INTO turnos (fechaHora, estado, motivoConsulta, idMedico, idPaciente) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, turno.getFechaHora().toString());
            stmt.setString(2, turno.getEstado().toString());
            stmt.setString(3, turno.getMotivoConsulta());
            stmt.setInt(4, turno.getIdMedico());
            stmt.setInt(5, turno.getIdPaciente());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    turno.setIdTurno(rs.getInt(1));
                } else {
                    throw new SQLException("No se pudo obtener el ID generado para el turno.");
                }
            }

        } catch (SQLException e) {
            throw new SQLException("Error al crear el turno: " + e.getMessage(), e);
        }
    }

    @Override
    public Turno obtenerPorId(int id) throws SQLException {
        String sql = "SELECT * FROM turnos WHERE idTurno = ?";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearTurno(rs);
                }
            }

        } catch (SQLException e) {
            throw new SQLException("Error al obtener el turno por ID: " + e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<Turno> obtenerTodos() throws SQLException {
        String sql = "SELECT * FROM turnos";
        List<Turno> turnos = new ArrayList<>();

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                turnos.add(mapearTurno(rs));
            }

        } catch (SQLException e) {
            throw new SQLException("Error al obtener todos los turnos: " + e.getMessage(), e);
        }
        return turnos;
    }

    @Override
    public void actualizar(Turno turno) throws SQLException {
        String sql = "UPDATE turnos SET fechaHora = ?, estado = ?, motivoConsulta = ?, idMedico = ?, idPaciente = ? WHERE idTurno = ?";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, turno.getFechaHora().toString());
            stmt.setString(2, turno.getEstado().toString());
            stmt.setString(3, turno.getMotivoConsulta());
            stmt.setInt(4, turno.getIdMedico());
            stmt.setInt(5, turno.getIdPaciente());
            stmt.setInt(6, turno.getIdTurno());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new SQLException("Error al actualizar el turno: " + e.getMessage(), e);
        }
    }

    @Override
    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM turnos WHERE idTurno = ?";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new SQLException("Error al eliminar el turno: " + e.getMessage(), e);
        }
    }

    public List<Turno> obtenerPorIdMedico(int idMedico) throws SQLException {
        String sql = "SELECT * FROM turnos WHERE idMedico = ?";
        List<Turno> turnos = new ArrayList<>();

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idMedico);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    turnos.add(mapearTurno(rs));
                }
            }

        } catch (SQLException e) {
            throw new SQLException("Error al obtener turnos por ID de médico: " + e.getMessage(), e);
        }
        return turnos;
    }

    public List<Turno> obtenerPorIdPaciente(int idPaciente) throws SQLException {
        String sql = "SELECT * FROM turnos WHERE idPaciente = ?";
        List<Turno> turnos = new ArrayList<>();

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPaciente);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    turnos.add(mapearTurno(rs));
                }
            }

        } catch (SQLException e) {
            throw new SQLException("Error al obtener turnos por ID de paciente: " + e.getMessage(), e);
        }
        return turnos;
    }
    
    public List<Turno> obtenerPorMedicoYFecha(int idMedico, LocalDate fecha) throws SQLException {
        String sql = "SELECT * FROM turnos WHERE idMedico = ? AND fechaHora LIKE ?";
        List<Turno> turnos = new ArrayList<>();

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idMedico);
            // convertir LocalDate a string YYYY-MM-DD y añadir '%' para buscar cualquier hora
            stmt.setString(2, fecha.toString() + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    turnos.add(mapearTurno(rs));
                }
            }
            
        } catch (SQLException e) {
            throw new SQLException("Error al obtener turnos por médico y fecha: " + e.getMessage(), e);
        }

        return turnos;
    }

    
}
        