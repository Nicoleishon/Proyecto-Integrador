package com.mycompany.proyectointegrador.repositorios;

import com.mycompany.proyectointegrador.modelo.Especialidad;
import com.mycompany.proyectointegrador.modelo.Medico;
import com.mycompany.proyectointegrador.persistencias.ConexionDB;
import com.mycompany.proyectointegrador.utils.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MedicoRepositorio implements IRepositorio<Medico> {

    @Override
    public void crear(Medico medico) throws SQLException {
        
        String sqlMedico = "INSERT INTO medicos (idMedico, idHospital, matricula, especialidad) VALUES (?, ?, ?, ?)";
        
        
        try (Connection conn = ConexionDB.conectar()) {
            conn.setAutoCommit(false); // Iniciar transacción
            
            try (PreparedStatement stmtMedico = conn.prepareStatement(sqlMedico);) {
                stmtMedico.setInt(1, medico.getIdPersona());
                stmtMedico.setInt(2, medico.getIdHospital());
                stmtMedico.setString(3, medico.getMatricula());
                stmtMedico.setString(4, medico.getEspecialidad().toString());
                stmtMedico.executeUpdate();
                
                conn.commit();
            } catch (SQLException e) {
                DBUtils.rollback(conn); // Revertir en caso de error
                throw new SQLException("Error al crear médico.", e);
            } finally {
                DBUtils.restaurarAutoCommit(conn);
            }
        }
    }

    
    @Override
    public Medico obtenerPorId(int id) throws SQLException {
        String sql = """
            SELECT 
                p.idPersona, p.nombre, p.apellido, p.fechaNacimiento, p.direccion, p.telefono, p.dni,
                m.idMedico, m.idHospital, m.matricula, m.especialidad
            FROM personas p
            JOIN medicos m ON p.idPersona = m.idMedico
            WHERE m.idMedico = ?
            """;
        
        Medico medico = new Medico();

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearMedico(rs);
                }
            }

        } catch (SQLException e) {
            throw new SQLException ("Error al obtener médico por ID.", e);
        }
        
        return null;
        
    }


    @Override
    public List<Medico> obtenerTodos() throws SQLException {
        String sql = """
            SELECT 
                p.idPersona, p.nombre, p.apellido, p.fechaNacimiento, p.direccion, p.telefono, p.dni,
                m.idMedico, m.idHospital, m.matricula, m.especialidad
            FROM personas p
            JOIN medicos m ON p.idPersona = m.idMedico
            """;

        List<Medico> medicos = new ArrayList<>();

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {                
                medicos.add(mapearMedico(rs));
            }

        } catch (SQLException e) {
            throw new SQLException ("Error al obtener todos los médicos.", e);
        }

        return medicos;
    }


    
    @Override
    public void actualizar(Medico medico) throws SQLException {
        String sqlPersona = """
            UPDATE personas
            SET nombre = ?, apellido = ?, fechaNacimiento = ?, direccion = ?, telefono = ?, dni = ?
            WHERE idPersona = ?
            """;

        String sqlMedico = """
            UPDATE medicos
            SET matricula = ?, especialidad = ?
            WHERE idMedico = ?
            """;

        try (Connection conn = ConexionDB.conectar()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmtPersona = conn.prepareStatement(sqlPersona);
                 PreparedStatement stmtMedico = conn.prepareStatement(sqlMedico)) {

                // --- Actualizar Persona ---
                stmtPersona.setString(1, medico.getNombre());
                stmtPersona.setString(2, medico.getApellido());
                if (medico.getFechaNacimiento() != null) {
                    stmtPersona.setDate(3, java.sql.Date.valueOf(medico.getFechaNacimiento()));
                } else {
                    stmtPersona.setNull(3, java.sql.Types.DATE);
                }
                stmtPersona.setString(4, medico.getDireccion());
                stmtPersona.setString(5, medico.getTelefono());
                stmtPersona.setString(6, medico.getDni());
                stmtPersona.setInt(7, medico.getIdPersona());
                stmtPersona.executeUpdate();

                // --- Actualizar Médico ---
                stmtMedico.setString(1, medico.getMatricula());
                stmtMedico.setString(2, medico.getEspecialidad().toString());
                stmtMedico.setInt(3, medico.getIdMedico());
                stmtMedico.executeUpdate();
                
                // Horarios se actualizan mediante otro método si es necesario

                conn.commit();
            } catch (SQLException e) {
                DBUtils.rollback(conn);
                throw new SQLException("Error al actualizar médico.", e);
            } finally {
                DBUtils.restaurarAutoCommit(conn);
            }
        }
    }

    
    @Override
    public void eliminar(int id) throws SQLException {
        String sqlMedico = "DELETE FROM medicos WHERE idMedico = ?";
        String sqlPersona = "DELETE FROM personas WHERE idPersona = ?";
        String sqlHorarios = "DELETE FROM horarios WHERE idMedico = ?";
        String sqlTurnos = "DELETE FROM turnos WHERE idMedico = ?";

        try (Connection conn = ConexionDB.conectar()) {
            conn.setAutoCommit(false); // Iniciar transacción

            try (PreparedStatement stmtTurnos = conn.prepareStatement(sqlTurnos);
                 PreparedStatement stmtHorarios = conn.prepareStatement(sqlHorarios);
                 PreparedStatement stmtMedico = conn.prepareStatement(sqlMedico);
                 PreparedStatement stmtPersona = conn.prepareStatement(sqlPersona)) {

                // Eliminar de turnos del medico
                stmtTurnos.setInt(1, id);
                stmtTurnos.executeUpdate();

                // Eliminar horarios del médico
                stmtHorarios.setInt(1, id);
                stmtHorarios.executeUpdate();

                // Eliminar medico de medicos
                stmtMedico.setInt(1, id);
                stmtMedico.executeUpdate();

                // Eliminar medico de personas
                stmtPersona.setInt(1, id);
                stmtPersona.executeUpdate();

                conn.commit();

            } catch (SQLException e) {
                DBUtils.rollback(conn);
                throw new SQLException("Error al eliminar médico.", e);
            } finally {
                DBUtils.restaurarAutoCommit(conn);
            }

        }
    }

    
    
    private Medico mapearMedico(ResultSet rs) throws SQLException {
        Medico medico = new Medico();

        // --- Datos de Persona ---
        medico.setIdPersona(rs.getInt("idPersona"));
        medico.setNombre(rs.getString("nombre"));
        medico.setApellido(rs.getString("apellido"));
        String fechaNacimientoStr = rs.getString("fechaNacimiento");
        if (fechaNacimientoStr != null) {
            medico.setFechaNacimiento(LocalDate.parse(fechaNacimientoStr));
        }
        medico.setDireccion(rs.getString("direccion"));
        medico.setTelefono(rs.getString("telefono"));
        medico.setDni(rs.getString("dni"));

        // --- Datos de Médico ---
        medico.setIdMedico(rs.getInt("idMedico"));
        medico.setIdHospital(rs.getInt("idHospital"));
        medico.setMatricula(rs.getString("matricula"));

        String especialidadStr = rs.getString("especialidad");
        if (especialidadStr != null) {
            medico.setEspecialidad(Especialidad.valueOf(especialidadStr));
        }

        return medico;
    }

    
}