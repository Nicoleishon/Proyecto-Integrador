package com.mycompany.proyectointegrador.modelo;

import java.time.LocalDate;


public class Paciente extends Usuario {
    private int idPaciente;
    private LocalDate fechaRegistro;

    // Constructor para obtener paciente de db
    public Paciente(int idPaciente, LocalDate fechaRegistro, int idUsuario, String nombreUsuario, String hashContraseña, int idPersona, String nombre, String apellido, LocalDate fechaNacimiento, String direccion, String telefono, String dni) {
        super(idUsuario, nombreUsuario, hashContraseña, idPersona, nombre, apellido, fechaNacimiento, direccion, telefono, dni);
        this.idPaciente = idPaciente;
        this.fechaRegistro = fechaRegistro;
    }

    // Constructor para generar un paciente que no estaba antes en el sistema
    public Paciente(LocalDate fechaRegistro, Usuario usuario, Persona persona) {
        super(usuario.getNombreUsuario(), usuario.getHashContraseña(), persona);
        this.idPaciente = usuario.getIdUsuario();
        this.fechaRegistro = fechaRegistro;
    }
    
    // Constructor para generar un paciente en el momento
    public Paciente(Usuario usuario, Persona persona) {
        super(usuario.getNombreUsuario(), usuario.getHashContraseña(), persona);
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
