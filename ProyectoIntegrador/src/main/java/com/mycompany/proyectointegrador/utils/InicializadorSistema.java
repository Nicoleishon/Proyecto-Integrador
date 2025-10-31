package com.mycompany.proyectointegrador.utils;

import com.mycompany.proyectointegrador.modelo.*;
import com.mycompany.proyectointegrador.repositorios.*;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

public class InicializadorSistema {

    private static final UsuarioRepositorio usuarioRepo = new UsuarioRepositorio();
    private static final RecepcionistaRepositorio recepcionistaRepo = new RecepcionistaRepositorio();
    private static final HospitalRepositorio hospitalRepo = new HospitalRepositorio();
    private static final MedicoRepositorio medicoRepo = new MedicoRepositorio();
    private static final HorarioRepositorio horarioRepo = new HorarioRepositorio();
    private static final PacienteRepositorio pacienteRepo = new PacienteRepositorio();

    public static void inicializarSistema() {
        try {
            // Crear hospital principal si no existe
            Hospital hospitalPrincipal = hospitalRepo.obtenerHospitalUnico();
            if (hospitalPrincipal == null) {
                hospitalPrincipal = new Hospital();
                hospitalPrincipal.setNombre("Hospital Principal");
                hospitalPrincipal.setDireccion("Sede Central");
                hospitalRepo.crearHospital(hospitalPrincipal);
            }

            // Crear recepcionista inicial si no existe
            Boolean existe = recepcionistaRepo.existeRecepcionista();
            if (!existe) {
                crearRecepcionistaInicial(hospitalRepo.obtenerHospitalUnico());
            }
            
            if (!existe) {
               // crearMedicosIniciales(medicoRepo.buscarMedicoPorEspecialidad(Especialidad.PEDIATRÍA));
            }

        } catch (SQLException | NoSuchAlgorithmException e) {
            System.err.println("Error al inicializar el sistema: " + e.getMessage());
        }
    }

    private static void crearRecepcionistaInicial(Hospital hospitalDefault) throws SQLException, NoSuchAlgorithmException {
       
        Persona personaInicial = new Persona();
        personaInicial.setNombre("Administrador");
        personaInicial.setApellido("Principal");
        personaInicial.setDni("00000000");
        personaInicial.setDireccion("Sede Central");
        personaInicial.setTelefono("0000000000");
        personaInicial.setFechaNacimiento(LocalDate.now());
        personaInicial.setIdPersona(-1);
        
        
        String nombreUsuarioInicial = "admin";
        String contraseñaHasheadaInicial = Usuario.generarHash("1234"); // genera hash
        
        Usuario usuarioInicial = new Usuario(nombreUsuarioInicial, contraseñaHasheadaInicial);
        
        Recepcionista recepcionistaInicial = new Recepcionista(hospitalDefault.getIdHospital(), usuarioInicial, personaInicial);

        recepcionistaRepo.crear(recepcionistaInicial);

        System.out.println("Recepcionista inicial creada correctamente.");
    }
    
    private static void crearMedicosIniciales(Hospital hospitalDefault) throws SQLException {
    
    // médico 1 - pediatra
    Persona personaMedico1 = new Persona("Ana", "Gutiérrez", LocalDate.of(1985, 5, 15), "Colón 1073", "261555111", "30111222");
    Medico medico1 = new Medico(hospitalDefault.getIdHospital(), LocalDate.now(), "Pediatría", "ANA123", null, Especialidad.PEDIATRÍA, personaMedico1);
    
    medicoRepo.crear(medico1);
    
    Horario horarioM1_Lunes = new Horario(DiaSemana.LUNES, LocalTime.of(8, 0), LocalTime.of(16, 0), medico1.getIdMedico());
    Horario horarioM1_Mierc = new Horario(DiaSemana.MIERCOLES, LocalTime.of(8, 0), LocalTime.of(16, 0), medico1.getIdMedico());
    horarioRepo.crear(horarioM1_Lunes);
    horarioRepo.crear(horarioM1_Mierc);

    
    // --- Médico 2: Cardiólogo ---
    Persona personaMedico2 = new Persona("Carlos", "Martínez", LocalDate.of(1978, 10, 20), "Av. Liberman 742", "261555222", "25333444");
    Medico medico2 = new Medico(hospitalDefault.getIdHospital(), LocalDate.now(), "Cardiología", "CAR456", null, Especialidad.CARDIOLOGÍA, personaMedico2);
    
    medicoRepo.crear(medico2);
    
    Horario horarioM2_Martes = new Horario(DiaSemana.MARTES, LocalTime.of(15, 0), LocalTime.of(18, 0), medico2.getIdMedico());
    Horario horarioM2_Jueves = new Horario(DiaSemana.JUEVES, LocalTime.of(15, 0), LocalTime.of(18, 0), medico2.getIdMedico());
    horarioRepo.crear(horarioM2_Martes);
    horarioRepo.crear(horarioM2_Jueves);

    System.out.println("Médicos iniciales creados correctamente.");
}
}
