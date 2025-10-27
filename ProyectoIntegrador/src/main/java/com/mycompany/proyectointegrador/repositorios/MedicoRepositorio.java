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
import java.time.LocalDate;
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
                // Convierte LocalDate a java.sql.Date, que es lo que JDBC necesita
                stmtPersonal.setDate(1, java.sql.Date.valueOf(medico.getFechaIngreso()));
            } else {
                // Si es null, le indica a la base de datos que es NULL
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
        String sql = """
            SELECT 
                per.idPersona, per.nombre, per.apellido, per.fechaNacimiento, per.direccion, per.telefono, per.dni,
                ph.idPersonalHospital, ph.fechaIngreso, ph.departamento,
                m.idMedico, m.matricula, m.especialidad
            FROM personal_hospital ph
            JOIN medicos m ON ph.idPersonalHospital = m.idMedico
            JOIN personas per ON ph.idPersonalHospital = per.idPersona
            WHERE m.idMedico = ?
            """;

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {

                    // Datos de Persona
                    int idPersona = rs.getInt("idPersona");
                    String nombre = rs.getString("nombre");
                    String apellido = rs.getString("apellido");
                    String fechaNacimientoStr = rs.getString("fechaNacimiento");
                    LocalDate fechaNacimiento = (fechaNacimientoStr != null) 
                        ? LocalDate.parse(fechaNacimientoStr) 
                        : null;
                    String direccion = rs.getString("direccion");
                    String telefono = rs.getString("telefono");
                    String dni = rs.getString("dni");

                    // Datos de PersonalHospital
                    int idPersonalHospital = rs.getInt("idPersonalHospital");
                    String fechaIngresoStr = rs.getString("fechaIngreso");
                    LocalDate fechaIngreso = (fechaIngresoStr != null)
                        ? LocalDate.parse(fechaIngresoStr)
                        : null;
                    String departamento = rs.getString("departamento");

                    // Datos de Medico
                    int idMedico = rs.getInt("idMedico");
                    String matricula = rs.getString("matricula");
                    Especialidad especialidad = Especialidad.valueOf(rs.getString("especialidad"));

                    // Cargar horarios
                    List<Horario> horarios = horarioRepo.obtenerPorIdPersonal(idPersonalHospital);

                    // Crear objeto Medico usando el constructor completo
                    return new Medico(
                        idMedico, matricula, new ArrayList<>(), null, especialidad,  // Medico
                        idPersonalHospital, fechaIngreso, departamento, horarios,     // PersonalHospital
                        idPersona, nombre, apellido, fechaNacimiento, // Persona
                        direccion, telefono, dni
                    );
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
        String sql = """
            SELECT 
                ph.idPersonalHospital, ph.fechaIngreso, ph.departamento,
                m.idMedico, m.matricula, m.especialidad,
                per.idPersona, per.nombre, per.apellido, per.fechaNacimiento, per.direccion, per.telefono, per.dni
            FROM personal_hospital ph
            JOIN medicos m ON ph.idPersonalHospital = m.idMedico
            JOIN personas per ON ph.idPersonalHospital = per.idPersona
            """;

        List<Medico> medicos = new ArrayList<>();

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                // Datos de Medico
                int idMedico = rs.getInt("idMedico");
                String matricula = rs.getString("matricula");
                Especialidad especialidad = Especialidad.valueOf(rs.getString("especialidad"));

                // Datos de PersonalHospital
                int idPersonalHospital = rs.getInt("idPersonalHospital");
                LocalDate fechaIngreso = rs.getString("fechaIngreso") != null
                    ? LocalDate.parse(rs.getString("fechaIngreso"))
                    : null;
                String departamento = rs.getString("departamento");

                // Datos de Persona
                int idPersona = rs.getInt("idPersona");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                LocalDate fechaNacimiento = rs.getString("fechaNacimiento") != null
                    ? LocalDate.parse(rs.getString("fechaNacimiento"))
                    : null;
                String direccion = rs.getString("direccion");
                String telefono = rs.getString("telefono");
                String dni = rs.getString("dni");

                // Horarios (si tienes un repositorio de horarios)
                List<Horario> horarios = new ArrayList<>(); // o cargar con horarioRepo.obtenerPorIdPersonal(idPersonalHospital)

                // Crear objeto Medico
                Medico medico = new Medico(
                    idMedico, matricula, new ArrayList<>(), null, especialidad, 
                    idPersonalHospital, fechaIngreso, departamento, horarios, 
                    idPersona, nombre, apellido, fechaNacimiento,
                    direccion, telefono, dni
                );

                medicos.add(medico);
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
                if (medico.getFechaIngreso() != null) {
                    stmtPersonal.setDate(1, java.sql.Date.valueOf(medico.getFechaIngreso()));
                } else {
                    stmtPersonal.setNull(1, java.sql.Types.DATE);
                }
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