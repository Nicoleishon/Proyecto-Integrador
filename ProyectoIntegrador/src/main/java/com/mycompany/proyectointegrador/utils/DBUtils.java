package com.mycompany.proyectointegrador.utils;

import java.sql.Connection;
import java.sql.SQLException;

public class DBUtils {

    // Rollback seguro
    public static void rollback(Connection conn) {
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                System.err.println("Error al hacer rollback: " + ex.getMessage());
            }
        }
    }

    // Restaurar autoCommit y cerrar conexión de manera segura
    public static void close(Connection conn) {
        if (conn != null) {
            try { conn.close(); } 
            catch (SQLException ex) { System.err.println("No se pudo cerrar la conexión: " + ex.getMessage()); }
        }
    }

    // Restaurar autoCommit de manera segura    
    public static void restaurarAutoCommit(Connection conn) {
        if (conn != null) {
            try { conn.setAutoCommit(true); } 
            catch (SQLException ex) { System.err.println("No se pudo restaurar autoCommit: " + ex.getMessage()); }
        }
    }
    
}
