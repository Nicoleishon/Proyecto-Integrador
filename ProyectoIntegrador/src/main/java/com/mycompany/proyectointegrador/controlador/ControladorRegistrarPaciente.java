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

        // Validar formato de DNI: solo números positivos
        if (!dni.matches("\\d+")) {
            throw new IllegalArgumentException("El DNI debe contener solo números enteros positivos.");
        }

        // Validar nombre de usuario: al menos una letra
        if (!nombreUsuario.matches(".*[a-zA-Z].*")) {
            throw new IllegalArgumentException("El nombre de usuario debe contener al menos una letra.");
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

    

    
}
