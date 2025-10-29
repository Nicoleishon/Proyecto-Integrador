// Lo he movido a un paquete 'persistencia' para mejor orden
package com.mycompany.proyectointegrador.persistencias;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexionDB {

    
    private static final String URL_DB = "jdbc:sqlite:gestor_turnos.sqlite";
    
    private static Connection connection = null;

    private ConexionDB() throws SQLException {
        try {
            connection = DriverManager.getConnection(URL_DB);
            System.out.println("Conexión a SQLite establecida con éxito.");
            
            try (Statement stmt = connection.createStatement()) {
                stmt.execute("PRAGMA foreign_keys = ON;");
            }
            
        } catch (SQLException e) {
            System.err.println("Error al conectar con la base de datos SQLite: " + e.getMessage());
            // lanzar excepción personalizada en caso de que se requiera
            throw e;
        }
    }
    
    public static Connection conectar() throws SQLException {
        if (connection == null || connection.isClosed()) {
            new ConexionDB(); 
        }
        return connection;
    }

    public static void cerrar() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Conexión a SQLite cerrada.");
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }

    public static void inicializarBaseDeDatos() throws SQLException {
        try (Connection conn = conectar();
             Statement stmt = conn.createStatement()) {

            stmt.execute("PRAGMA foreign_keys = ON;");

            // Eliminar tablas en orden descendente de dependencias
            stmt.execute("DROP TABLE IF EXISTS turnos;");
            stmt.execute("DROP TABLE IF EXISTS medicos;");
            stmt.execute("DROP TABLE IF EXISTS pacientes;");
            stmt.execute("DROP TABLE IF EXISTS usuarios;");
            stmt.execute("DROP TABLE IF EXISTS personas;");
            stmt.execute("DROP TABLE IF EXISTS hospitales;");
            stmt.execute("DROP TABLE IF EXISTS recepcionistas;");
            stmt.execute("DROP TABLE IF EXISTS horarios;");
            System.out.println("Tablas antiguas eliminadas (si existían).");
            
            // Tabla Hospitales
            stmt.execute("""
                CREATE TABLE hospitales (
                idHospital INTEGER PRIMARY KEY,
                nombre TEXT NOT NULL,
                direccion TEXT NOT NULL
                );""");

            // Tabla Personas: LocalDate (fechaNacimiento)
            stmt.execute("""
                CREATE TABLE personas (
                idPersona INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT NOT NULL,
                apellido TEXT NOT NULL,
                fechaNacimiento TEXT NOT NULL,       -- YYYY-MM-DD para LocalDate
                direccion TEXT,
                telefono TEXT,
                dni TEXT NOT NULL UNIQUE
                );""");

            // Tabla Usuarios
            stmt.execute("""
                CREATE TABLE usuarios (
                idUsuario INTEGER PRIMARY KEY,
                nombreUsuario TEXT NOT NULL UNIQUE,
                hashContraseña TEXT NOT NULL,
                FOREIGN KEY (idUsuario) REFERENCES personas(idPersona)
                );""");

            // Tabla Pacientes: fechaRegistro (LocalDate)
            stmt.execute("""
                CREATE TABLE pacientes (
                idPaciente INTEGER PRIMARY KEY,
                idHospital INTEGER NOT NULL,
                fechaRegistro TEXT NOT NULL,  -- YYYY-MM-DD para LocalDate
                FOREIGN KEY (idHospital) REFERENCES hospitales(idHospital),
                FOREIGN KEY (idPaciente) REFERENCES usuarios(idUsuario)
                );""");


            // Tabla Medicos
            stmt.execute("""
                CREATE TABLE medicos (
                idMedico INTEGER PRIMARY KEY,
                idHospital INTEGER NOT NULL,
                matricula TEXT NOT NULL UNIQUE,
                especialidad TEXT NOT NULL,
                FOREIGN KEY (idHospital) REFERENCES hospitales(idHospital),
                FOREIGN KEY (idMedico) REFERENCES personas(idPersona)
                );""");
            
            // Tabla Recepcionistas
            stmt.execute("""
                CREATE TABLE recepcionistas (
                idRecepcionista INTEGER PRIMARY KEY,
                idHospital INTEGER NOT NULL,
                FOREIGN KEY (idHospital) REFERENCES hospitales(idHospital),
                FOREIGN KEY (idRecepcionista) REFERENCES usuarios(idUsuario)
                );""");

            // Tabla Turnos: fechaHora (LocalDateTime)
            // Turnos también deberia de tener idHospital si se quiere escalar a un sistema de multihospitales
            stmt.execute("""
                CREATE TABLE turnos (
                idTurno INTEGER PRIMARY KEY AUTOINCREMENT,
                fechaHora TEXT NOT NULL,      -- YYYY-MM-DDTHH:MM:SS para LocalDateTime
                estado TEXT,
                motivoConsulta TEXT,
                idMedico INTEGER NOT NULL,
                idPaciente INTEGER NOT NULL,
                FOREIGN KEY (idMedico) REFERENCES medicos(idMedico),
                FOREIGN KEY (idPaciente) REFERENCES pacientes(idPaciente)
                );""");
            
            // Tabla horarios
            stmt.execute("""
                CREATE TABLE horarios (
                idHorario INTEGER PRIMARY KEY AUTOINCREMENT,
                diaSemana TEXT NOT NULL,
                horaInicio TEXT NOT NULL,  -- formato HH:MM
                horaFin TEXT NOT NULL,
                idMedico INTEGER NOT NULL,
                FOREIGN KEY (idMedico) REFERENCES medicos(idMedico)
                );""");

            System.out.println("Base de datos inicializada. Tablas creadas con éxito.");
        } catch (SQLException e) {
            System.err.println("Error al inicializar la base de datos: " + e.getMessage());
            throw e;  // Lanzar la excepción para que la capa de interfaz pueda capturarla
        }
    }

}