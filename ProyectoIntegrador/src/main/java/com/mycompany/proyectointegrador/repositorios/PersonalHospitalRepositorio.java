package com.mycompany.proyectointegrador.repositorios;

import com.mycompany.proyectointegrador.modelo.PersonalHospital;
import com.mycompany.proyectointegrador.persistencias.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PersonalHospitalRepositorio {

    public void crear(PersonalHospital personal, int idPersona) throws SQLException {
        String sql = "INSERT INTO personal_hospital (idPersonalHospital, fechaIngreso, departamento) " +
                     "VALUES (?, ?, ?)";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPersona); // mismo ID que persona
            stmt.setString(2, personal.getFechaIngreso() != null ? personal.getFechaIngreso().toString() : null);
            stmt.setString(3, personal.getDepartamento());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error al crear personal hospitalario: " + e.getMessage());
            throw e;
        }
    }
}
