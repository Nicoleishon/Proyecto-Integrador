package com.mycompany.proyectointegrador.modelo;

import java.time.LocalDate;


public class Paciente extends Usuario {
    private int idPaciente;
    private LocalDate fechaRegistro;

    public Paciente(
            // Parametros de Paciente
            int idPaciente, LocalDate fechaRegistro,
            
            // Parametros de Usuario
            int idUsuario, String nombreUsuario, String hashContraseña,
            
            // Parametros de Persona
            int idPersona, String nombre, String apellido, LocalDate fechaNacimiento, String direccion, String telefono, String dni
    ) {
        super(idUsuario, nombreUsuario, hashContraseña, idPersona, nombre, apellido, fechaNacimiento, direccion, telefono, dni);
        this.idPaciente = idPaciente;
        this.fechaRegistro = fechaRegistro;
    }
    
    public Paciente(LocalDate fechaRegistro, Usuario usuario, Persona persona) {
        super(usuario.getIdUsuario(), usuario.getNombreUsuario(), usuario.getHashContraseña(), persona);
        this.idPaciente = usuario.getIdUsuario();
        this.fechaRegistro = fechaRegistro;
    }
    
    public Paciente(Usuario usuario, Persona persona) {
        super(usuario.getIdUsuario(), usuario.getNombreUsuario(), usuario.getHashContraseña(), persona);
        this.idPaciente = usuario.getIdUsuario();
        this.fechaRegistro = LocalDate.now();
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
    

    public Turno solicitarTurno(Medico medico, LocalDate fechaHora) {
        // implementación
        return null;
    }

    public void cancelarTurno(Turno turno) {
        // implementación
    }


}
