package com.mycompany.proyectointegrador.repositorios;

import com.mycompany.proyectointegrador.modelo.Paciente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import com.mycompany.proyectointegrador.persistencias.ConexionDB; 


public class PacienteRepositorio implements IRepositorio<Paciente> { 

    @Override
    public void crear(Paciente paciente) throws SQLException {
        
        String sqlPersona = "INSERT INTO personas (nombre, apellido, fechaNacimiento, direccion, telefono, dni) VALUES (?, ?, ?, ?, ?, ?)";
        String sqlPaciente = "INSERT INTO pacientes (idPaciente, fechaRegistro) VALUES (?, ?)";
        
        Connection conn = null;
        PreparedStatement stmtPersona = null;
        PreparedStatement stmtPaciente = null;
        ResultSet rs = null;
        
        try {
            conn = ConexionDB.conectar();
            conn.setAutoCommit(false); 
            
            stmtPersona = conn.prepareStatement(sqlPersona, Statement.RETURN_GENERATED_KEYS);
            
            stmtPersona.setString(1, paciente.getNombre());
            stmtPersona.setString(2, paciente.getApellido());
            stmtPersona.setString(3, paciente.getFechaNacimiento().toString()); 
            stmtPersona.setString(4, paciente.getDireccion());
            stmtPersona.setString(5, paciente.getTelefono());
            stmtPersona.setString(6, paciente.getDni());
            stmtPersona.executeUpdate();
            
            rs = stmtPersona.getGeneratedKeys();
            int idPersonaGenerado;
            if (rs.next()) {
                idPersonaGenerado = rs.getInt(1);
                paciente.setIdPaciente(idPersonaGenerado); // Actualizamos el objeto Paciente
            } else {
                throw new SQLException("La inserción de la persona falló, no se pudo obtener el ID.");
            }

            stmtPaciente = conn.prepareStatement(sqlPaciente);
            
            stmtPaciente.setInt(1, idPersonaGenerado); 
            
            stmtPaciente.setString(2, paciente.getFechaRegistro().toString());  
            
            stmtPaciente.executeUpdate();

            conn.commit(); 
            
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    System.err.println("Error al hacer rollback: " + ex.getMessage());
                }
            }
            System.err.println("Error SQL al guardar el paciente: " + e.getMessage());
            throw new SQLException("Error de base de datos al crear el paciente.", e);
        } finally {
            
            if (rs != null) rs.close();
            if (stmtPersona != null) stmtPersona.close();
            if (stmtPaciente != null) stmtPaciente.close();
            if (conn != null) {
                conn.setAutoCommit(true); 
                conn.close();
            }
        }
    }
    
    @Override
    public Paciente obtenerPorId(int id) throws SQLException {
        String sql = """
            SELECT per.idPersona, per.nombre, per.apellido, per.fechaNacimiento, per.direccion, per.telefono, per.dni,
                   u.idUsuario, u.nombreUsuario, u.hashContraseña,
                   pa.idPaciente, pa.fechaRegistro
            FROM personas per
            JOIN usuarios u ON per.idPersona = u.idUsuario
            JOIN pacientes pa ON u.idUsuario = pa.idPaciente
            WHERE per.idPersona = ?
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
                    LocalDate fechaNacimiento = (fechaNacimientoStr != null) ? LocalDate.parse(fechaNacimientoStr) : null;
                    String direccion = rs.getString("direccion");
                    String telefono = rs.getString("telefono");
                    String dni = rs.getString("dni");

                    // Datos de Usuario
                    int idUsuario = rs.getInt("idUsuario");
                    String nombreUsuario = rs.getString("nombreUsuario");
                    String hashContraseña = rs.getString("hashContraseña");

                    // Datos de Paciente
                    int idPaciente = rs.getInt("idPaciente");
                    LocalDate fechaRegistro = LocalDate.parse(rs.getString("fechaRegistro"));

                    return new Paciente(
                        idPaciente,          // Paciente
                        fechaRegistro,       // fechaRegistro
                        idUsuario,           // Usuario
                        nombreUsuario,       // nombreUsuario
                        hashContraseña,      // hashContraseña
                        idPersona,           // Persona
                        nombre,
                        apellido,
                        fechaNacimiento,
                        direccion,
                        telefono,
                        dni
                    );
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener paciente por ID: " + e.getMessage());
            throw e;
        }

        return null;
    }

    
    @Override
    public List<Paciente> obtenerTodos() throws SQLException {
        String sql = """
            SELECT per.idPersona, per.nombre, per.apellido, per.fechaNacimiento, per.direccion, per.telefono, per.dni,
                   u.idUsuario, u.nombreUsuario, u.hashContraseña,
                   pa.idPaciente, pa.fechaRegistro
            FROM personas per
            JOIN usuarios u ON per.idPersona = u.idUsuario
            JOIN pacientes pa ON u.idUsuario = pa.idPaciente
        """;

        List<Paciente> pacientes = new ArrayList<>();

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                // Datos de Persona
                int idPersona = rs.getInt("idPersona");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String fechaNacimientoStr = rs.getString("fechaNacimiento");
                LocalDate fechaNacimiento = (fechaNacimientoStr != null) ? LocalDate.parse(fechaNacimientoStr) : null;
                String direccion = rs.getString("direccion");
                String telefono = rs.getString("telefono");
                String dni = rs.getString("dni");

                // Datos de Usuario
                int idUsuario = rs.getInt("idUsuario");
                String nombreUsuario = rs.getString("nombreUsuario");
                String hashContraseña = rs.getString("hashContraseña");

                // Datos de Paciente
                int idPaciente = rs.getInt("idPaciente");
                LocalDate fechaRegistro = LocalDate.parse(rs.getString("fechaRegistro"));

                Paciente paciente = new Paciente(
                    idPaciente,            // Paciente
                    fechaRegistro,         // fechaRegistro
                    idUsuario,             // Usuario
                    nombreUsuario,         // nombreUsuario
                    hashContraseña,        // hashContraseña
                    idPersona,             // Persona
                    nombre,
                    apellido,
                    fechaNacimiento,
                    direccion,
                    telefono,
                    dni
                );

                pacientes.add(paciente);
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener todos los pacientes: " + e.getMessage());
            throw e;
        }

        return pacientes;
    }


    @Override
    public void actualizar(Paciente paciente) throws SQLException {
        String sql = "UPDATE pacientes SET idPersona = ?, nombre = ?, apellido = ?, fechaNaciemiento = ?, direccion = ?, telefono = ?, dni = ?, fechaRegistro = ?, WHERE id = ?";
        
        try (Connection conn = ConexionDB.conectar();
        PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1, paciente.getId());
            pstmt.setString(2, paciente.getNombre());
            pstmt.setString(3, paciente.getApellido());
            pstmt.setString(4, paciente.getFechaNacimiento().toString());
            pstmt.setString(5, paciente.getDireccion());
            pstmt.setString(6, paciente.getTelefono());
            pstmt.setString(7, paciente.getDni());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar datos del paciente: " + e.getMessage());
            throw e;
        }
        System.out.println("Se actualizaron los datos del paciente con éxito.");
    }
    
    @Override
    public void eliminar(int idPaciente) throws SQLException {
        String sql = "DELETE FROM pacientes WHERE ID = ?";
        
        try (Connection conn = ConexionDB.conectar();
        PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1, idPaciente);
            pstmt.executeUpdate();
        } catch (SQLException e){
            System.err.println("Error al eliminar paciente: " + e.getMessage());
            throw e;
        }
        System.out.println("El paciente se eliminó de la lista con éxito.");
    }
    
}