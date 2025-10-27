package com.mycompany.proyectointegrador.repositorios;

import com.mycompany.proyectointegrador.modelo.EstadoTurno;
import com.mycompany.proyectointegrador.modelo.Medico;
import com.mycompany.proyectointegrador.modelo.Paciente;
import com.mycompany.proyectointegrador.modelo.Turno;
import com.mycompany.proyectointegrador.persistencias.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TurnoRepositorio implements IRepositorio<Turno> {

    
    private MedicoRepositorio medicoRepo = new MedicoRepositorio();
    private PacienteRepositorio pacienteRepo = new PacienteRepositorio();
    
    @Override 
    public void crear(Turno turno) throws SQLException {
        String sql = "INSERT INTO turnos (fechaHora, estado, motivoConsulta, idMedico, idPaciente) " +
             "VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, turno.getFechaHora().toString());
            stmt.setString(2, turno.getEstado().toString());
            stmt.setString(3, turno.getMotivoConsulta());
            stmt.setInt(4, turno.getMedico().getIdMedico());
            stmt.setInt(5, turno.getPaciente().getIdPaciente());

            stmt.executeUpdate();

            // Obtener el id generado automáticamente
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int idGenerado = rs.getInt(1);
                    // Actualizar el objeto turno con el id generado
                    turno.setIdTurno(idGenerado);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al crear el turno: " + e.getMessage());
            throw e;
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
                    
                    int idTurno = rs.getInt("idTurno");
                    LocalDateTime fechaHora = LocalDateTime.parse(rs.getString("fechaHora"));
                    String estadoStr = rs.getString("estado");
                    String motivo = rs.getString("motivoConsulta");
                    int idMedico = rs.getInt("idMedico");
                    int idPaciente = rs.getInt("idPaciente");
                   
                    Turno turno = new Turno(idTurno, fechaHora, motivo, estadoStr);

                    Medico medico = medicoRepo.obtenerPorId(idMedico);
                    Paciente paciente = pacienteRepo.obtenerPorId(idPaciente);
                    
                    turno.setMedico(medico);
                    turno.setPaciente(paciente);
                    
                    return turno;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener turno por ID: " + e.getMessage());
            throw e;
        }
        return null; // No encontrado
    }

    @Override
    public List<Turno> obtenerTodos() throws SQLException {
        String sql = "SELECT * FROM turnos";
        List<Turno> turnos = new ArrayList<>();
        
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                // 1. Extraer datos
                int idTurno = rs.getInt("idTurno");
                LocalDateTime fechaHora = LocalDateTime.parse(rs.getString("fechaHora"));
                String estadoStr = rs.getString("estado");
                String motivo = rs.getString("motivoConsulta");
                int idMedico = rs.getInt("idMedico");
                int idPaciente = rs.getInt("idPaciente");

                Turno turno = new Turno(idTurno, fechaHora, motivo, estadoStr);

                turno.setMedico(medicoRepo.obtenerPorId(idMedico));
                turno.setPaciente(pacienteRepo.obtenerPorId(idPaciente));
                
                turnos.add(turno);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener todos los turnos: " + e.getMessage());
            throw e;
        }
        return turnos;
    }

     @Override
    public void actualizar(Turno turno) throws SQLException {
        String sql = "UPDATE turnos SET fechaHora = ?, estado = ?, motivoConsulta = ?, " +
                     "idMedico = ?, idPaciente = ? WHERE idTurno = ?";
        
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, turno.getFechaHora().toString());
            stmt.setString(2, turno.getEstado().toString());
            stmt.setString(3, turno.getMotivoConsulta());
            stmt.setInt(4, turno.getMedico().getIdMedico());
            stmt.setInt(5, turno.getPaciente().getIdPaciente());
            stmt.setInt(6, turno.getIdTurno()); // WHERE
            
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Error al actualizar el turno: " + e.getMessage());
            throw e;
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
            System.err.println("Error al eliminar el turno: " + e.getMessage());
            throw e;
        }
    }
}