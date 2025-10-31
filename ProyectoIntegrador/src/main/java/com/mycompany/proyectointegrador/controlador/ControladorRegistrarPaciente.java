package com.mycompany.proyectointegrador.controlador;

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
                                  String nombreUsuario, String contraseña, String confirmar) throws SQLException, IllegalArgumentException, IllegalStateException, NoSuchAlgorithmException {
        
        
        if (!contraseña.contentEquals(confirmar)) {
            throw new IllegalArgumentException("Las contraseñas no coinciden.");
        }

        LocalDate fechaNacimientoConvertida;
        
        try {
            fechaNacimientoConvertida = LocalDate.parse(fechaNacimiento, DateTimeFormatter.ISO_DATE);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("La fecha de nacimiento debe tener el formato YYYY-MM-DD.");
        }

        
        // Crear la persona
        Persona persona = new Persona(nombre, apellido, fechaNacimientoConvertida, direccion, telefono, dni);

        String contraseñaHasheada = Usuario.generarHash(contraseña);
        
        // Crear el usuario
        Usuario usuario = new Usuario(nombreUsuario, contraseñaHasheada);
        
        
        Hospital hospital = hospitalRepo.obtenerHospitalUnico();
        if (hospital == null) {
            throw new IllegalStateException("No se encontró un hospital en la base de datos.");
        }

        // Crear el paciente asociado
        Paciente paciente = new Paciente(hospital.getIdHospital(), usuario, persona);
        pacienteRepo.crear(paciente);
    }
    

    
}
