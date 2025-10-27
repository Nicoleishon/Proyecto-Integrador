package com.mycompany.proyectointegrador.repositorios;

import com.mycompany.proyectointegrador.modelo.DiaSemana;
import com.mycompany.proyectointegrador.modelo.Horario;
import com.mycompany.proyectointegrador.persistencias.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class HorarioRepositorio implements IRepositorio<Horario> {
    
    private Horario mapearHorario(ResultSet rs) throws SQLException {
        int idHorario = rs.getInt("idHorario");
        DiaSemana dia = DiaSemana.valueOf(rs.getString("diaSemana"));
        LocalTime inicio = LocalTime.parse(rs.getString("horaInicio"));
        LocalTime fin = LocalTime.parse(rs.getString("horaFin"));
        int idPersonal = rs.getInt("idPersonalHospital");

        Horario horario = new Horario(dia, inicio, fin, idPersonal);
        horario.setIdHorario(idHorario);
        return horario;
    }

    @Override
    public void crear(Horario horario) throws SQLException {
        // se asume que idHorario es AUTOINCREMENT
        String sql = "INSERT INTO horarios (diaSemana, horaInicio, horaFin, idPersonalHospital) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, horario.getDiaSemana().toString());
            stmt.setString(2, horario.getHoraInicio().toString());
            stmt.setString(3, horario.getHoraFin().toString());
            stmt.setInt(4, horario.getIdPersonalHospital());
            
            stmt.executeUpdate();
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    horario.setIdHorario(rs.getInt(1));
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error al crear el horario: " + e.getMessage());
            throw e;
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
            System.err.println("Error al obtener horario por ID: " + e.getMessage());
            throw e;
        }
        return null; // No encontrado
    }

    public List<Horario> obtenerPorIdPersonal(int idPersonal) throws SQLException {
        String sql = "SELECT * FROM horarios WHERE idPersonalHospital = ?";
        List<Horario> horarios = new ArrayList<>();
        
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idPersonal);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    horarios.add(mapearHorario(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener horarios por ID de personal: " + e.getMessage());
            throw e;
        }
        return horarios; // Devuelve la lista (puede estar vacía)
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
            System.err.println("Error al obtener todos los horarios: " + e.getMessage());
            throw e;
        }
        return horarios;
    }

    @Override
    public void actualizar(Horario horario) throws SQLException {
        String sql = "UPDATE horarios SET diaSemana = ?, horaInicio = ?, horaFin = ?, idPersonalHospital = ? " +
                     "WHERE idHorario = ?";
        
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, horario.getDiaSemana().toString());
            stmt.setString(2, horario.getHoraInicio().toString());
            stmt.setString(3, horario.getHoraFin().toString());
            stmt.setInt(4, horario.getIdPersonalHospital());
            stmt.setInt(5, horario.getIdHorario()); // WHERE
            
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Error al actualizar el horario: " + e.getMessage());
            throw e;
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
            System.err.println("Error al eliminar el horario: " + e.getMessage());
            throw e;
        }
    }
    
    public void eliminarPorIdPersonal(int idPersonal) throws SQLException {
        String sql = "DELETE FROM horarios WHERE idPersonalHospital = ?";
        
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idPersonal);
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Error al eliminar horarios por ID de personal: " + e.getMessage());
            throw e;
        }
    }
}