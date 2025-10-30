package com.mycompany.proyectointegrador.utils;

import com.mycompany.proyectointegrador.modelo.*;
import com.mycompany.proyectointegrador.repositorios.*;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.time.LocalDate;

public class InicializadorSistema {

    private static final UsuarioRepositorio usuarioRepo = new UsuarioRepositorio();
    private static final RecepcionistaRepositorio recepcionistaRepo = new RecepcionistaRepositorio();
    private static final HospitalRepositorio hospitalRepo = new HospitalRepositorio();

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
}
