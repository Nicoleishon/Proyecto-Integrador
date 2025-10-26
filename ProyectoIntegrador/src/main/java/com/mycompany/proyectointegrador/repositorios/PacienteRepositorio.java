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
import com.mycompany.proyectointegrador.persistencias.ConexionDB; 

abstract class PacienteRepositorio implements IRepositorio <Paciente> {
    
    @Override
    public void crear(Paciente paciente) throws SQLException {
        
        String sqlPersona = "INSERT INTO personas (idPersona, nombre, apellido, fechaNacimiento, direccion, telefono, dni) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        String sqlPaciente = "INSERT INTO pacientes (idPaciente, fechaRegistro) VALUES (?, ?)";
      
        try (Connection conn = ConexionDB.conectar()) {
         
            try (PreparedStatement stmt = conn.prepareStatement(sqlPersona, Statement.RETURN_GENERATED_KEYS)) {
                
                
                // mapeo de atributos de Persona
                stmt.setInt(1, 0); // configurado como autoincrement
                stmt.setString(2, paciente.getNombre());
                stmt.setString(3, paciente.getApellido());
                stmt.setString(4, paciente.getFechaNacimiento().toString());
                stmt.setString(5, paciente.getDireccion());
                stmt.setString(6, paciente.getTelefono());
                stmt.setString(7, paciente.getDni());
                stmt.executeUpdate();
               
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        int idPersonaGenerado = rs.getInt(1);
                        paciente.setId(idPersonaGenerado); // Actualizamos el objeto Paciente

                        try (PreparedStatement stmtPaciente = conn.prepareStatement(sqlPaciente)) {
                            
                            // Usamos el ID generado como FK y PK de la tabla Paciente
                            stmtPaciente.setInt(1, idPersonaGenerado);
                            
                            // Mapeo de atributo específico de Paciente
                            stmtPaciente.setString(2, paciente.getFechaRegistro().toString()); 
                            
                            stmtPaciente.executeUpdate();
                        }
                    } else {
                         throw new SQLException("La inserción de la persona falló, no se pudo obtener el ID.");
                    }
                }
            }
        } catch (SQLException e) {
            // se puede lanzar una excepción personalizada 
            System.err.println("Error SQL al guardar el paciente: " + e.getMessage());
            throw new SQLException("Error de base de datos al crear el paciente.", e);
        }
    }
    
  @Override
  
    public Paciente obtenerPorId(int id) throws SQLException {
    String sql = "SELECT p.idPersona, p.nombre, p.apellido, p.fechaNacimiento, p.direccion, p.telefono, p.dni, pa.fechaRegistro " +
                 "FROM personas p JOIN pacientes pa ON p.idPersona = pa.idPaciente " +
                 "WHERE p.idPersona = ?";
    
    try (Connection conn = ConexionDB.conectar();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setInt(1, id); 
        
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                Paciente paciente = new Paciente();
                
                // Mapeo de columnas a atributos
                paciente.setId(rs.getInt("idPersona"));
                paciente.setNombre(rs.getString("nombre"));
                paciente.setApellido(rs.getString("apellido"));
                paciente.setFechaNacimiento(LocalDate.parse(rs.getString("fechaNacimiento")));
                paciente.setDireccion(rs.getString("direccion"));
                paciente.setTelefono(rs.getString("telefono"));
                paciente.setDni(rs.getString("dni"));
                paciente.setIdPaciente(rs.getInt("idPaciente"));
                paciente.setFechaRegistro(LocalDate.parse(rs.getString("fechaRegistro")));
                
                return paciente;
            }
        }
    } catch (SQLException e) {
        System.err.println("Error al obtener paciente por ID: " + e.getMessage());
        throw e;
    }
    return null; // si no se encuentra el paciente
}


    @Override
    public List<Paciente> obtenerTodos() throws SQLException {
        // ... lógica para leer todos
        return null;
    }

    @Override
    public void actualizar(Paciente paciente) throws SQLException {
        // ... lógica para actualizar
    }

    @Override
    public void eliminar(int id) throws SQLException {
        // ... lógica para eliminar
    }
}