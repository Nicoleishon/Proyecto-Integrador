package com.mycompany.proyectointegrador.repositorios;

import com.mycompany.proyectointegrador.modelo.Persona;
import com.mycompany.proyectointegrador.persistencias.ConexionDB;
import com.mycompany.proyectointegrador.utils.DBUtils;

import java.sql.*;

public class PersonaRepositorio {


     // Inserta una persona en la tabla personas y devuelve el ID generado.
    public int crearPersona(Persona persona) throws SQLException {
        String sql = "INSERT INTO personas (nombre, apellido, fechaNacimiento, direccion, telefono, dni) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionDB.conectar()) {
            conn.setAutoCommit(false); // iniciar transacción

            try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, persona.getNombre());
                stmt.setString(2, persona.getApellido());
                stmt.setString(3, persona.getFechaNacimiento() != null ? persona.getFechaNacimiento().toString() : null);
                stmt.setString(4, persona.getDireccion());
                stmt.setString(5, persona.getTelefono());
                stmt.setString(6, persona.getDni());

                int filasAfectadas = stmt.executeUpdate();
                if (filasAfectadas == 0) {
                    throw new SQLException("No se pudo insertar la persona.");
                }

                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        int idGenerado = rs.getInt(1);
                        persona.setIdPersona(idGenerado);
                        conn.commit();
                        return idGenerado;
                    } else {
                        throw new SQLException("No se pudo obtener el ID generado para la persona.");
                    }
                }
            } catch (SQLException e) {
                DBUtils.rollback(conn);
                throw new SQLException(e.getMessage(), e);
            } finally {
                DBUtils.restaurarAutoCommit(conn);
            }
        }
    }


}
