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
             crearMedicosIniciales(hospitalRepo.obtenerHospitalUnico());
            }
            
            if (!existe) {
            crearPacientesIniciales(hospitalRepo.obtenerHospitalUnico());
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
    
    

    private static void crearPacientesIniciales(Hospital hospitalDefault) throws SQLException, NoSuchAlgorithmException {
    
    // paciente inicial 1
    Persona p1 = new Persona("Juan", "Perez", LocalDate.of(1990, 1, 1), "San Martín 123", "261444111", "35111222");
    Usuario u1 = new Usuario("jperez", Usuario.generarHash("1234"));
    
    Paciente pac1 = new Paciente(hospitalDefault.getIdHospital(), u1, p1);
    pacienteRepo.crear(pac1); 

    // paciente inicial 2
    Persona p2 = new Persona("Maria", "Lopez", LocalDate.of(1985, 6, 15), "Las Heras 456", "261444222", "30222333");
    Usuario u2 = new Usuario("mlopez", Usuario.generarHash("1234"));
    Paciente pac2 = new Paciente(hospitalDefault.getIdHospital(), u2, p2);
    pacienteRepo.crear(pac2);

    // paciente inicial 3
    Persona p3 = new Persona("Roberto", "Sanchez", LocalDate.of(2005, 11, 30), "Belgrano 789", "261444333", "45333444");
    Usuario u3 = new Usuario("rsanchez", Usuario.generarHash("1234"));
    Paciente pac3 = new Paciente(hospitalDefault.getIdHospital(), u3, p3);
    pacienteRepo.crear(pac3);

    // paciente inicial 4
    Persona p4 = new Persona("Lucia", "Gomez", LocalDate.of(1998, 3, 22), "Patricias 101", "261444555", "40444555");
    Usuario u4 = new Usuario("lgomez", Usuario.generarHash("1234"));
    Paciente pac4 = new Paciente(hospitalDefault.getIdHospital(), u4, p4);
    pacienteRepo.crear(pac4);

    System.out.println("Pacientes iniciales creados correctamente.");
}
    
    private static void crearMedicosIniciales(Hospital hospitalDefault) throws SQLException {
    
    
    Persona personaMedico1 = new Persona("Ana", "Gutiérrez", LocalDate.of(1985, 5, 15), "Colón 1073", "261555111", "30111222");
    Medico medico1 = new Medico(hospitalDefault.getIdHospital(), LocalDate.now(), "Pediatría", "ANA123", null, Especialidad.PEDIATRÍA, personaMedico1);
    
    medicoRepo.crear(medico1);
    
    Horario horarioM1_Lunes = new Horario(DiaSemana.LUNES, LocalTime.of(8, 0), LocalTime.of(16, 0), medico1.getIdMedico());
    Horario horarioM1_Mierc = new Horario(DiaSemana.MIERCOLES, LocalTime.of(8, 0), LocalTime.of(16, 0), medico1.getIdMedico());
    horarioRepo.crear(horarioM1_Lunes);
    horarioRepo.crear(horarioM1_Mierc);

    
    // CARDIOLOGÍA - Médico 1
    Persona personaMedico2 = new Persona("Carlos", "Martínez", LocalDate.of(1978, 10, 20), "Av. Liberman 742", "261555222", "25333444");
    Medico medico2 = new Medico(hospitalDefault.getIdHospital(), LocalDate.now(), "Cardiología", "CAR456", null, Especialidad.CARDIOLOGÍA, personaMedico2);
    
    medicoRepo.crear(medico2);
    
    Horario horarioM2_Martes = new Horario(DiaSemana.MARTES, LocalTime.of(15, 0), LocalTime.of(18, 0), medico2.getIdMedico());
    Horario horarioM2_Jueves = new Horario(DiaSemana.JUEVES, LocalTime.of(15, 0), LocalTime.of(18, 0), medico2.getIdMedico());
    horarioRepo.crear(horarioM2_Martes);
    horarioRepo.crear(horarioM2_Jueves);
    
    // ODONTOLOGÍA - Médico 1
    Persona personaMedico3 = new Persona("Laura", "Pérez", LocalDate.of(1980, 3, 10), "San Martín 456", "261555333", "30444555");
    Medico medico3 = new Medico(hospitalDefault.getIdHospital(), LocalDate.now(), "Odontología", "LAU789", null, Especialidad.ODONTOLOGÍA, personaMedico3);
    medicoRepo.crear(medico3);

    Horario horarioM3_Lunes = new Horario(DiaSemana.LUNES, LocalTime.of(9, 0), LocalTime.of(17, 0), medico3.getIdMedico());
    Horario horarioM3_Jueves = new Horario(DiaSemana.JUEVES, LocalTime.of(9, 0), LocalTime.of(17, 0), medico3.getIdMedico());
    horarioRepo.crear(horarioM3_Lunes);
    horarioRepo.crear(horarioM3_Jueves);

    // ODONTOLOGÍA - Médico 2
    Persona personaMedico4 = new Persona("Diego", "Rodríguez", LocalDate.of(1982, 7, 22), "Belgrano 789", "261555444", "30555666");
    Medico medico4 = new Medico(hospitalDefault.getIdHospital(), LocalDate.now(), "Odontología", "DIE012", null, Especialidad.ODONTOLOGÍA, personaMedico4);
    medicoRepo.crear(medico4);

    Horario horarioM4_Martes = new Horario(DiaSemana.MARTES, LocalTime.of(8, 0), LocalTime.of(16, 0), medico4.getIdMedico());
    Horario horarioM4_Viernes = new Horario(DiaSemana.VIERNES, LocalTime.of(8, 0), LocalTime.of(16, 0), medico4.getIdMedico());
    horarioRepo.crear(horarioM4_Martes);
    horarioRepo.crear(horarioM4_Viernes);

    // OFTALMOLOGÍA - Médico 1
    Persona personaMedico5 = new Persona("María", "López", LocalDate.of(1975, 12, 5), "Av. San Juan 321", "261555555", "28666777");
    Medico medico5 = new Medico(hospitalDefault.getIdHospital(), LocalDate.now(), "Oftalmología", "MAR345", null, Especialidad.OFTALMOLOGÍA, personaMedico5);
    medicoRepo.crear(medico5);

    Horario horarioM5_Lunes = new Horario(DiaSemana.LUNES, LocalTime.of(10, 0), LocalTime.of(18, 0), medico5.getIdMedico());
    Horario horarioM5_Mierc = new Horario(DiaSemana.MIERCOLES, LocalTime.of(10, 0), LocalTime.of(18, 0), medico5.getIdMedico());
    horarioRepo.crear(horarioM5_Lunes);
    horarioRepo.crear(horarioM5_Mierc);

    // OFTALMOLOGÍA - Médico 2
    Persona personaMedico6 = new Persona("Javier", "Gómez", LocalDate.of(1983, 9, 18), "Pueyrredón 654", "261555666", "31888999");
    Medico medico6 = new Medico(hospitalDefault.getIdHospital(), LocalDate.now(), "Oftalmología", "JAV678", null, Especialidad.OFTALMOLOGÍA, personaMedico6);
    medicoRepo.crear(medico6);

    Horario horarioM6_Martes = new Horario(DiaSemana.MARTES, LocalTime.of(8, 30), LocalTime.of(16, 30), medico6.getIdMedico());
    Horario horarioM6_Jueves = new Horario(DiaSemana.JUEVES, LocalTime.of(8, 30), LocalTime.of(16, 30), medico6.getIdMedico());
    horarioRepo.crear(horarioM6_Martes);
    horarioRepo.crear(horarioM6_Jueves);

    // PEDIATRÍA - Médico 2 (complementando el que ya tienes)
    Persona personaMedico7 = new Persona("Lucía", "Fernández", LocalDate.of(1988, 4, 12), "Av. Las Heras 987", "261555777", "32111000");
    Medico medico7 = new Medico(hospitalDefault.getIdHospital(), LocalDate.now(), "Pediatría", "LUC901", null, Especialidad.PEDIATRÍA, personaMedico7);
    medicoRepo.crear(medico7);

    Horario horarioM7_Martes = new Horario(DiaSemana.MARTES, LocalTime.of(7, 0), LocalTime.of(15, 0), medico7.getIdMedico());
    Horario horarioM7_Viernes = new Horario(DiaSemana.VIERNES, LocalTime.of(7, 0), LocalTime.of(15, 0), medico7.getIdMedico());
    horarioRepo.crear(horarioM7_Martes);
    horarioRepo.crear(horarioM7_Viernes);

    // DERMATOLOGÍA - Médico 1
    Persona personaMedico8 = new Persona("Sofía", "Ramírez", LocalDate.of(1981, 6, 30), "Av. Godoy Cruz 234", "261555888", "33222111");
    Medico medico8 = new Medico(hospitalDefault.getIdHospital(), LocalDate.now(), "Dermatología", "SOF234", null, Especialidad.DERMATOLOGÍA, personaMedico8);
    medicoRepo.crear(medico8);

    Horario horarioM8_Lunes = new Horario(DiaSemana.LUNES, LocalTime.of(8, 0), LocalTime.of(14, 0), medico8.getIdMedico());
    Horario horarioM8_Jueves = new Horario(DiaSemana.JUEVES, LocalTime.of(8, 0), LocalTime.of(14, 0), medico8.getIdMedico());
    horarioRepo.crear(horarioM8_Lunes);
    horarioRepo.crear(horarioM8_Jueves);

    // DERMATOLOGÍA - Médico 2
    Persona personaMedico9 = new Persona("Andrés", "Silva", LocalDate.of(1979, 11, 8), "Catamarca 567", "261555999", "34333222");
    Medico medico9 = new Medico(hospitalDefault.getIdHospital(), LocalDate.now(), "Dermatología", "AND567", null, Especialidad.DERMATOLOGÍA, personaMedico9);
    medicoRepo.crear(medico9);

    Horario horarioM9_Mierc = new Horario(DiaSemana.MIERCOLES, LocalTime.of(12, 0), LocalTime.of(18, 0), medico9.getIdMedico());
    Horario horarioM9_Viernes = new Horario(DiaSemana.VIERNES, LocalTime.of(12, 0), LocalTime.of(18, 0), medico9.getIdMedico());
    horarioRepo.crear(horarioM9_Mierc);
    horarioRepo.crear(horarioM9_Viernes);

    // UROLOGÍA - Médico 1
    Persona personaMedico10 = new Persona("Roberto", "Mendoza", LocalDate.of(1976, 2, 25), "Av. Aristides 876", "261556000", "35444333");
    Medico medico10 = new Medico(hospitalDefault.getIdHospital(), LocalDate.now(), "Urología", "ROB890", null, Especialidad.UROLOGÍA, personaMedico10);
    medicoRepo.crear(medico10);

    Horario horarioM10_Martes = new Horario(DiaSemana.MARTES, LocalTime.of(9, 0), LocalTime.of(17, 0), medico10.getIdMedico());
    Horario horarioM10_Jueves = new Horario(DiaSemana.JUEVES, LocalTime.of(9, 0), LocalTime.of(17, 0), medico10.getIdMedico());
    horarioRepo.crear(horarioM10_Martes);
    horarioRepo.crear(horarioM10_Jueves);

    // UROLOGÍA - Médico 2
    Persona personaMedico11 = new Persona("Gabriela", "Castro", LocalDate.of(1984, 8, 14), "Av. España 432", "261556111", "36555444");
    Medico medico11 = new Medico(hospitalDefault.getIdHospital(), LocalDate.now(), "Urología", "GAB123", null, Especialidad.UROLOGÍA, personaMedico11);
    medicoRepo.crear(medico11);

    Horario horarioM11_Lunes = new Horario(DiaSemana.LUNES, LocalTime.of(10, 0), LocalTime.of(16, 0), medico11.getIdMedico());
    Horario horarioM11_Viernes = new Horario(DiaSemana.VIERNES, LocalTime.of(10, 0), LocalTime.of(16, 0), medico11.getIdMedico());
    horarioRepo.crear(horarioM11_Lunes);
    horarioRepo.crear(horarioM11_Viernes);

    // ONCOLOGÍA - Médico 1
    Persona personaMedico12 = new Persona("Patricia", "Vargas", LocalDate.of(1977, 1, 19), "Av. Boulogne 765", "261556222", "37666555");
    Medico medico12 = new Medico(hospitalDefault.getIdHospital(), LocalDate.now(), "Oncología", "PAT456", null, Especialidad.ONCOLOGÍA, personaMedico12);
    medicoRepo.crear(medico12);

    Horario horarioM12_Mierc = new Horario(DiaSemana.MIERCOLES, LocalTime.of(8, 0), LocalTime.of(15, 0), medico12.getIdMedico());
    Horario horarioM12_Viernes = new Horario(DiaSemana.VIERNES, LocalTime.of(8, 0), LocalTime.of(15, 0), medico12.getIdMedico());
    horarioRepo.crear(horarioM12_Mierc);
    horarioRepo.crear(horarioM12_Viernes);

    // ONCOLOGÍA - Médico 2
    Persona personaMedico13 = new Persona("Fernando", "Ríos", LocalDate.of(1986, 3, 7), "Av. Acceso Este 198", "261556333", "38777666");
    Medico medico13 = new Medico(hospitalDefault.getIdHospital(), LocalDate.now(), "Oncología", "FER789", null, Especialidad.ONCOLOGÍA, personaMedico13);
    medicoRepo.crear(medico13);

    Horario horarioM13_Lunes = new Horario(DiaSemana.LUNES, LocalTime.of(11, 0), LocalTime.of(18, 0), medico13.getIdMedico());
    Horario horarioM13_Jueves = new Horario(DiaSemana.JUEVES, LocalTime.of(11, 0), LocalTime.of(18, 0), medico13.getIdMedico());
    horarioRepo.crear(horarioM13_Lunes);
    horarioRepo.crear(horarioM13_Jueves);

    // CARDIOLOGÍA - Médico 2 
    Persona personaMedico14 = new Persona("Elena", "Morales", LocalDate.of(1980, 12, 3), "Av. San Martín 543", "261556444", "39888777");
    Medico medico14 = new Medico(hospitalDefault.getIdHospital(), LocalDate.now(), "Cardiología", "ELE012", null, Especialidad.CARDIOLOGÍA, personaMedico14);
    medicoRepo.crear(medico14);

    Horario horarioM14_Mierc = new Horario(DiaSemana.MIERCOLES, LocalTime.of(7, 30), LocalTime.of(13, 30), medico14.getIdMedico());
    Horario horarioM14_Viernes = new Horario(DiaSemana.VIERNES, LocalTime.of(7, 30), LocalTime.of(13, 30), medico14.getIdMedico());
    horarioRepo.crear(horarioM14_Mierc);
    horarioRepo.crear(horarioM14_Viernes);

    // CLÍNICA GENERAL - Médico 1
    Persona personaMedico15 = new Persona("Miguel", "Torres", LocalDate.of(1974, 5, 28), "Av. Mitre 321", "261556555", "40999888");
    Medico medico15 = new Medico(hospitalDefault.getIdHospital(), LocalDate.now(), "Clínica General", "MIG345", null, Especialidad.CLINICA_GENERAL, personaMedico15);
    medicoRepo.crear(medico15);

    Horario horarioM15_Lunes = new Horario(DiaSemana.LUNES, LocalTime.of(8, 0), LocalTime.of(17, 0), medico15.getIdMedico());
    Horario horarioM15_Martes = new Horario(DiaSemana.MARTES, LocalTime.of(8, 0), LocalTime.of(17, 0), medico15.getIdMedico());
    Horario horarioM15_Mierc = new Horario(DiaSemana.MIERCOLES, LocalTime.of(8, 0), LocalTime.of(17, 0), medico15.getIdMedico());
    horarioRepo.crear(horarioM15_Lunes);
    horarioRepo.crear(horarioM15_Martes);
    horarioRepo.crear(horarioM15_Mierc);

    // CLÍNICA GENERAL - Médico 2
    Persona personaMedico16 = new Persona("Carmen", "Ortega", LocalDate.of(1987, 7, 9), "Av. Belgrano 654", "261556666", "41100999");
    Medico medico16 = new Medico(hospitalDefault.getIdHospital(), LocalDate.now(), "Clínica General", "CAR678", null, Especialidad.CLINICA_GENERAL, personaMedico16);
    medicoRepo.crear(medico16);

    Horario horarioM16_Jueves = new Horario(DiaSemana.JUEVES, LocalTime.of(8, 0), LocalTime.of(17, 0), medico16.getIdMedico());
    Horario horarioM16_Viernes = new Horario(DiaSemana.VIERNES, LocalTime.of(8, 0), LocalTime.of(17, 0), medico16.getIdMedico());
    Horario horarioM16_Sabado = new Horario(DiaSemana.SABADO, LocalTime.of(8, 0), LocalTime.of(12, 0), medico16.getIdMedico());
    horarioRepo.crear(horarioM16_Jueves);
    horarioRepo.crear(horarioM16_Viernes);
    horarioRepo.crear(horarioM16_Sabado);

        System.out.println("Médicos iniciales creados correctamente.");
    }
}
