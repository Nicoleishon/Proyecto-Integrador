package com.mycompany.proyectointegrador.repositorios;

import com.mycompany.proyectointegrador.modelo.Especialidad;
import com.mycompany.proyectointegrador.modelo.Horario;
import com.mycompany.proyectointegrador.modelo.Medico;
import com.mycompany.proyectointegrador.persistencias.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MedicoRepositorio implements IRepositorio<Medico> {
    
    private HorarioRepositorio horarioRepo = new HorarioRepositorio();

    @Override
    public void crear(Medico medico) throws SQLException {
        
        String sqlPersonal = "INSERT INTO personal_hospital (fechaIngreso, departamento) VALUES (?, ?)";
        String sqlMedico = "INSERT INTO medicos (idMedico, matricula, especialidad) VALUES (?, ?, ?)";
        
        Connection conn = null;
        PreparedStatement stmtPersonal = null;
        PreparedStatement stmtMedico = null;
        ResultSet rs = null;

        try {
            conn = ConexionDB.conectar();
            conn.setAutoCommit(false); // Iniciar transacción

            
            stmtPersonal = conn.prepareStatement(sqlPersonal, Statement.RETURN_GENERATED_KEYS);
            
            
            if (medico.getFechaIngreso() != null) {
                stmtPersonal.setDate(1, new java.sql.Date(medico.getFechaIngreso().getTime()));
            } else {
                stmtPersonal.setNull(1, Types.DATE);
            }
            stmtPersonal.setString(2, medico.getDepartamento());
            stmtPersonal.executeUpdate();
            
            
            rs = stmtPersonal.getGeneratedKeys();
            int idPersonalGenerado;
            if (rs.next()) {
                idPersonalGenerado = rs.getInt(1);
                medico.setIdPersonalHospital(idPersonalGenerado); // 
                medico.setIdMedico(idPersonalGenerado); 
            } else {
                throw new SQLException("Falló la inserción en personal_hospital, no se obtuvo ID.");
            }

            
            stmtMedico = conn.prepareStatement(sqlMedico);
            stmtMedico.setInt(1, idPersonalGenerado); 
            stmtMedico.setString(2, medico.getMatricula());
            stmtMedico.setString(3, medico.getEspecialidad().toString());
            stmtMedico.executeUpdate();
            
            // (aca también iría la lógica para guardar los horarios si es necesario)

            conn.commit(); // 

        } catch (SQLException e) {
            if (conn != null) conn.rollback(); // Revertir en caso de error
            System.err.println("Error en transacción al crear médico: " + e.getMessage());
            throw new SQLException("Error al crear médico.", e);
        } finally {
            if (rs != null) rs.close();
            if (stmtPersonal != null) stmtPersonal.close();
            if (stmtMedico != null) stmtMedico.close();
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

    @Override
    public Medico obtenerPorId(int id) throws SQLException {
        String sql = "SELECT p.idPersonalHospital, p.fechaIngreso, p.departamento, " +
                     "m.matricula, m.especialidad " +
                     "FROM personal_hospital p JOIN medicos m ON p.idPersonalHospital = m.idMedico " +
                     "WHERE m.idMedico = ?";
        
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    
                    int idMedico = id; // O rs.getInt("idMedico") si se selecciona
                    String matricula = rs.getString("matricula");
                    Especialidad especialidad = Especialidad.valueOf(rs.getString("especialidad"));
                    int idPersonalHospital = rs.getInt("idPersonalHospital");
                    Date fechaIngreso = rs.getDate("fechaIngreso");
                    String departamento = rs.getString("departamento");
                    List<Horario> horarios = horarioRepo.obtenerPorIdPersonal(id);

                    // Usar el constructor de persistencia
                    return new Medico(idMedico, matricula, especialidad, idPersonalHospital, fechaIngreso, departamento, horarios);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener médico por ID: " + e.getMessage());
            throw e;
        }
        return null; 
    }

    // @Override
    public List<Medico> obtenerTodos() throws SQLException {
        String sql = "SELECT p.idPersonalHospital, p.fechaIngreso, p.departamento, " +
                     "m.idMedico, m.matricula, m.especialidad " + // Añadido m.idMedico
                     "FROM personal_hospital p JOIN medicos m ON p.idPersonalHospital = m.idMedico";
        
        List<Medico> medicos = new ArrayList<>();
        
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                // Extraer datos
                int idMedico = rs.getInt("idMedico");
                int idPersonalHospital = rs.getInt("idPersonalHospital");
                Date fechaIngreso = rs.getDate("fechaIngreso");
                String departamento = rs.getString("departamento");
                String matricula = rs.getString("matricula");
                Especialidad especialidad = Especialidad.valueOf(rs.getString("especialidad"));

                // Cargar horarios (igual que en obtenerPorId)
                List<Horario> horarios = new ArrayList<>(); // Simplificado

                // Añadir a la lista
                medicos.add(new Medico(idMedico, matricula, especialidad, idPersonalHospital, fechaIngreso, departamento, horarios));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener todos los médicos: " + e.getMessage());
            throw e;
        }
        return medicos;
    }

    @Override
    public void actualizar(Medico medico) throws SQLException {
        String sqlPersonal = "UPDATE personal_hospital SET fechaIngreso = ?, departamento = ? WHERE idPersonalHospital = ?";
        String sqlMedico = "UPDATE medicos SET matricula = ?, especialidad = ? WHERE idMedico = ?";

        Connection conn = null;
        try {
            conn = ConexionDB.conectar();
            conn.setAutoCommit(false); // Transacción

            // Actualizar PersonalHospital
            try (PreparedStatement stmtPersonal = conn.prepareStatement(sqlPersonal)) {
                stmtPersonal.setDate(1, new java.sql.Date(medico.getFechaIngreso().getTime()));
                stmtPersonal.setString(2, medico.getDepartamento());
                stmtPersonal.setInt(3, medico.getIdPersonalHospital());
                stmtPersonal.executeUpdate();
            }

            // Actualizar Medico
            try (PreparedStatement stmtMedico = conn.prepareStatement(sqlMedico)) {
                stmtMedico.setString(1, medico.getMatricula());
                stmtMedico.setString(2, medico.getEspecialidad().toString());
                stmtMedico.setInt(3, medico.getIdMedico());
                stmtMedico.executeUpdate();
            }
            
            // la lógica para actualizar los horarios)

            conn.commit();

        } catch (SQLException e) {
            if (conn != null) conn.rollback();
            System.err.println("Error al actualizar médico: " + e.getMessage());
            throw e;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

    @Override
    public void eliminar(int id) throws SQLException {
        String sqlMedico = "DELETE FROM medicos WHERE idMedico = ?";
        String sqlPersonal = "DELETE FROM personal_hospital WHERE idPersonalHospital = ?";
        
        Connection conn = null;
        try {
            conn = ConexionDB.conectar();
            conn.setAutoCommit(false); // Transacción

            //  Eliminar de medicos
            try (PreparedStatement stmtMedico = conn.prepareStatement(sqlMedico)) {
                stmtMedico.setInt(1, id);
                stmtMedico.executeUpdate();
            }
            
            //  la lógica para eliminar horarios

            //  Eliminar de personal_hospital 
            try (PreparedStatement stmtPersonal = conn.prepareStatement(sqlPersonal)) {
                stmtPersonal.setInt(1, id);
                stmtPersonal.executeUpdate();
            }

            conn.commit();

        } catch (SQLException e) {
            if (conn != null) conn.rollback();
            System.err.println("Error al eliminar médico: " + e.getMessage());
            // Manejar violaciones de FK (ej. si el médico tiene turnos)
            throw e;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }
}