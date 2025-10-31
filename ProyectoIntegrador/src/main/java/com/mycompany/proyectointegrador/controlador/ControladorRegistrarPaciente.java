package com.mycompany.proyectointegrador.controlador;

import com.mycompany.proyectointegrador.excepciones.NombreUsuarioOcupadoException;
import com.mycompany.proyectointegrador.excepciones.SesionInvalidaException;
import com.mycompany.proyectointegrador.modelo.*;
import com.mycompany.proyectointegrador.repositorios.HospitalRepositorio;
import com.mycompany.proyectointegrador.repositorios.PacienteRepositorio;
import com.mycompany.proyectointegrador.repositorios.PersonaRepositorio;
import com.mycompany.proyectointegrador.repositorios.UsuarioRepositorio;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ControladorRegistrarPaciente {

    private final PacienteRepositorio pacienteRepo;
    private final UsuarioRepositorio usuarioRepo;
    private final PersonaRepositorio personaRepo;
    private final HospitalRepositorio hospitalRepo;
    private final ControladorIniciarSesion controladorSesion = new ControladorIniciarSesion();

     
    
    public ControladorRegistrarPaciente() {
        this.pacienteRepo = new PacienteRepositorio();
        this.usuarioRepo = new UsuarioRepositorio();
        this.personaRepo = new PersonaRepositorio();
        this.hospitalRepo = new HospitalRepositorio();
    }

    public void registrarPaciente(String nombre, String apellido, String fechaNacimiento,
                                  String direccion, String telefono, String dni,
                                  String nombreUsuario, String contraseña, String confirmar)
            throws SQLException, IllegalArgumentException, IllegalStateException,
                   NoSuchAlgorithmException, NombreUsuarioOcupadoException {

        // Validar contraseñas
        if (!contraseña.equals(confirmar)) {
            throw new IllegalArgumentException("Las contraseñas no coinciden.");
        }

        // Validar formato de fecha
        LocalDate fechaNacimientoConvertida;
        try {
            fechaNacimientoConvertida = LocalDate.parse(fechaNacimiento, DateTimeFormatter.ISO_DATE);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("La fecha de nacimiento debe tener el formato YYYY-MM-DD.");
        }

        
        if (!dni.matches("\\d+")) {
                    throw new IllegalArgumentException("El DNI debe contener solo números enteros positivos.");
        }
        // Validar formato de nombre de usuario si no es por recepcionista, usuarios registrados por recepcionista tienen nombre usuario como dni
        if (controladorSesion.getUsuarioActual() != null && !controladorSesion.esRecepcionista()) {
            // Validar nombre de usuario: al menos una letra
            if (!nombreUsuario.matches(".*[a-zA-Z].*")) {
                throw new IllegalArgumentException("El nombre de usuario debe contener al menos una letra.");
            }    
        }


        // Validar nombre de usuario ocupado
        if (usuarioRepo.existeNombreUsuario(nombreUsuario)) {
            throw new NombreUsuarioOcupadoException("El nombre de usuario '" + nombreUsuario + "' ya está en uso.");
        }

        // Crear persona
        Persona persona = new Persona(nombre, apellido, fechaNacimientoConvertida, direccion, telefono, dni);

        // Hashear contraseña
        String contraseñaHasheada = Usuario.generarHash(contraseña);

        // Crear usuario
        Usuario usuario = new Usuario(nombreUsuario, contraseñaHasheada);

        // Verificar existencia de hospital
        Hospital hospital = hospitalRepo.obtenerHospitalUnico();
        if (hospital == null) {
            throw new IllegalStateException("No se encontró un hospital en la base de datos.");
        }

        // Crear paciente asociado
        Paciente paciente = new Paciente(hospital.getIdHospital(), usuario, persona);
        pacienteRepo.crear(paciente);
    }

    private ControladorIniciarSesion ControladorIniciarSesion() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    

    
}
