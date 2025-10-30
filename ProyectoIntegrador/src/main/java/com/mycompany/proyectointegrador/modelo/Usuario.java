package com.mycompany.proyectointegrador.modelo;

import com.mycompany.proyectointegrador.excepciones.CredencialesInvalidasException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;

public class Usuario extends Persona {
    private int idUsuario;
    private String nombreUsuario;
    private String hashContraseña;
    private Boolean sesionIniciada;
    
    public Usuario(){}

    public Usuario(int idUsuario, String nombreUsuario, String hashContraseña, int idPersona, String nombre, String apellido, LocalDate fechaNacimiento, String direccion, String telefono, String dni) {
        super(idPersona, nombre, apellido, fechaNacimiento, direccion, telefono, dni);
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.hashContraseña = hashContraseña;
        this.sesionIniciada = false;
    }

    public Usuario(String nombreUsuario, String hashContraseña, Persona persona) {
        super(persona.getIdPersona(), persona.getNombre(), persona.getApellido(), persona.getFechaNacimiento(), persona.getDireccion(), persona.getTelefono(), persona.getDni());
        this.idUsuario = persona.getIdPersona();
        this.nombreUsuario = nombreUsuario;
        this.hashContraseña = hashContraseña;
        this.sesionIniciada = false;
    }
    
    public Usuario(String nombreUsuario, String hashContraseña) {
        this.idUsuario = -1;
        this.nombreUsuario = nombreUsuario;
        this.hashContraseña = hashContraseña;
        this.sesionIniciada = false;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getHashContraseña() {
        return hashContraseña;
    }

    public void setHashContraseña(String hashContraseña) {
        this.hashContraseña = hashContraseña;
    }
    
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Boolean getSesionIniciada() {
        return sesionIniciada;
    }

    public void setSesionIniciada(Boolean sesionIniciada) {
        this.sesionIniciada = sesionIniciada;
    }
    
    
    public void iniciarSesion(String contraseñaHasheada) throws CredencialesInvalidasException {
        if (this.hashContraseña.equals(contraseñaHasheada)) {
            this.sesionIniciada = true;
        }
    }

    public void cerrarSesion() {
        sesionIniciada = false;
    }

    public boolean estaSesionIniciada() {
        return sesionIniciada;
    }
    
    public static String generarHash(String contraseña) throws NoSuchAlgorithmException {
        // Se obtiene una instancia de SHA-256, que es un algoritmo de hashing seguro
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Se convierte la contraseña en un arreglo de bytes y se calcula su hash
            byte[] hashBytes = digest.digest(contraseña.getBytes());

            // Se crea un StringBuilder para construir la representación en hexadecimal
            StringBuilder sb = new StringBuilder();

            // Se recorre cada byte del hash
            for (byte b : hashBytes) {
                // Se convierte cada byte en dos dígitos hexadecimales y se añade al StringBuilder
                sb.append(String.format("%02x", b)); 
                // "%02x" asegura que siempre haya dos dígitos por byte, añadiendo un 0 inicial si es necesario
            }

            // Se devuelve la cadena hexadecimal completa, que es el hash de la contraseña
            return sb.toString();
    }


    
}
