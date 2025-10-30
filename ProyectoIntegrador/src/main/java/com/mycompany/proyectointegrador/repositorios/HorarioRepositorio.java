package com.mycompany.proyectointegrador.repositorios;

import com.mycompany.proyectointegrador.modelo.DiaSemana;
import com.mycompany.proyectointegrador.modelo.Horario;
import com.mycompany.proyectointegrador.utils.DBUtils;

import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class HorarioRepositorio implements IRepositorio<Horario> {

    private Horario mapearHorario(ResultSet rs) throws SQLException {
        Horario horario = new Horario();
        horario.setIdHorario(rs.getInt("idHorario"));
        horario.setDiaSemana(DiaSemana.valueOf(rs.getString("diaSemana")));
        horario.setHoraInicio(LocalTime.parse(rs.getString("horaInicio")));
        horario.setHoraFin(LocalTime.parse(rs.getString("horaFin")));
        horario.setIdMedico(rs.getInt("idMedico"));
        return horario;
    }

    @Override
    public void crear(Horario horario) throws SQLException {
        String sql = "INSERT INTO horarios (diaSemana, horaInicio, horaFin, idMedico) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, horario.getDiaSemana().toString());
            stmt.setString(2, horario.getHoraInicio().toString());
            stmt.setString(3, horario.getHoraFin().toString());
            stmt.setInt(4, horario.getIdMedico());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    horario.setIdHorario(rs.getInt(1));
                } else {
                    throw new SQLException("No se pudo insertar el horario.");
                }
            }

        } catch (SQLException e) {
            throw new SQLException("Error al crear el horario: " + e.getMessage(), e);
        }
    }

    @Override
    public Horario obtenerPorId(int id) throws SQLException {
        String sql = "SELECT * FROM horarios WHERE idHorario = ?";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearHorario(rs);
                }
            }

        } catch (SQLException e) {
            throw new SQLException("Error al obtener horario por ID: " + e.getMessage(), e);
        }
        return null;
    }

    public List<Horario> obtenerPorIdMedico(int idMedico) throws SQLException {
        String sql = "SELECT * FROM horarios WHERE idMedico = ?";
        List<Horario> horarios = new ArrayList<>();

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idMedico);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    horarios.add(mapearHorario(rs));
                }
            }

        } catch (SQLException e) {
            throw new SQLException("Error al obtener horarios por ID de medico: " + e.getMessage(), e);
        }
        return horarios;
    }

    @Override
    public List<Horario> obtenerTodos() throws SQLException {
        String sql = "SELECT * FROM horarios";
        List<Horario> horarios = new ArrayList<>();

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                horarios.add(mapearHorario(rs));
            }

        } catch (SQLException e) {
            throw new SQLException("Error al obtener todos los horarios: " + e.getMessage(), e);
        }
        return horarios;
    }

    @Override
    public void actualizar(Horario horario) throws SQLException {
        String sql = "UPDATE horarios SET diaSemana = ?, horaInicio = ?, horaFin = ?, idMedico = ? WHERE idHorario = ?";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, horario.getDiaSemana().toString());
            stmt.setString(2, horario.getHoraInicio().toString());
            stmt.setString(3, horario.getHoraFin().toString());
            stmt.setInt(4, horario.getIdMedico());
            stmt.setInt(5, horario.getIdHorario());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new SQLException("Error al actualizar el horario: " + e.getMessage(), e);
        }
    }

    @Override
    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM horarios WHERE idHorario = ?";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new SQLException("Error al eliminar el horario: " + e.getMessage(), e);
        }
    }

    public void eliminarPorIdMedico(int idMedico) throws SQLException {
        String sql = "DELETE FROM horarios WHERE idMedico = ?";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idMedico);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new SQLException("Error al eliminar horarios por ID de medico: " + e.getMessage(), e);
        }
    }
}
