package com.mycompany.proyectointegrador.repositorios;

import com.mycompany.proyectointegrador.modelo.Hospital;

import java.sql.*;

public class HospitalRepositorio {

    public void crearHospital(Hospital hospital) throws SQLException {
        String sql = """
            INSERT INTO hospitales (nombre, direccion)
            VALUES (?, ?)
        """;

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, hospital.getNombre());
            stmt.setString(2, hospital.getDireccion());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new SQLException("Error al crear el hospital: " + e.getMessage(), e);
        }
    }

    public Hospital obtenerHospitalPorId(int idHospital) throws SQLException {
        String sql = """
            SELECT idHospital, nombre, direccion
            FROM hospitales
            WHERE idHospital = ?
        """;

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idHospital);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Hospital hospital = new Hospital();
                    hospital.setIdHospital(rs.getInt("idHospital"));
                    hospital.setNombre(rs.getString("nombre"));
                    hospital.setDireccion(rs.getString("direccion"));
                    return hospital;
                }
            }

        } catch (SQLException e) {
            throw new SQLException("Error al obtener el hospital por ID: " + e.getMessage(), e);
        }

        return null;
    }

    public Hospital obtenerHospitalUnico() throws SQLException {
        String sql = """
            SELECT idHospital, nombre, direccion
            FROM hospitales
            LIMIT 1
        """;

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                Hospital hospital = new Hospital();
                hospital.setIdHospital(rs.getInt("idHospital"));
                hospital.setNombre(rs.getString("nombre"));
                hospital.setDireccion(rs.getString("direccion"));
                return hospital;
            }

        } catch (SQLException e) {
            throw new SQLException("Error al obtener el hospital: " + e.getMessage(), e);
        }

        return null;
    }

    public boolean existeHospital() throws SQLException {
        String sql = "SELECT COUNT(*) FROM hospitales";

        try (Connection conn = ConexionDB.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            return rs.next() && rs.getInt(1) > 0;

        } catch (SQLException e) {
            throw new SQLException("Error al verificar existencia del hospital: " + e.getMessage(), e);
        }
    }
}
