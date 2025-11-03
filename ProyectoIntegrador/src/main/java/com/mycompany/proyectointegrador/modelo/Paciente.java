package com.mycompany.proyectointegrador.modelo;

import java.time.LocalDate;


public class Paciente extends Usuario {
    private int idPaciente;
    private int idHospital;
    private LocalDate fechaRegistro;
    
    public Paciente(){}

    // Constructor para obtener paciente de db
    public Paciente(int idPaciente, int idHospital, LocalDate fechaRegistro, int idUsuario, String nombreUsuario, String hashContraseña, int idPersona, String nombre, String apellido, LocalDate fechaNacimiento, String direccion, String telefono, String dni) {
        super(idUsuario, nombreUsuario, hashContraseña, idPersona, nombre, apellido, fechaNacimiento, direccion, telefono, dni);
        this.idPaciente = idPaciente;
        this.fechaRegistro = fechaRegistro;
        this.idHospital = idHospital;
    }

    // Constructor para generar un paciente que no estaba antes en el sistema
    public Paciente(LocalDate fechaRegistro, int idHospital, Usuario usuario, Persona persona) {
        super(usuario.getNombreUsuario(), usuario.getHashContraseña(), persona);
        this.idPaciente = usuario.getIdUsuario();
        this.fechaRegistro = fechaRegistro;
        this.idHospital = idHospital;
    }
    
    // Constructor para generar un paciente en el momento
    public Paciente(int idHospital, Usuario usuario, Persona persona) {
        super(usuario.getNombreUsuario(), usuario.getHashContraseña(), persona);
        this.idPaciente = usuario.getIdUsuario();
        this.fechaRegistro = LocalDate.now();
        this.idHospital = idHospital;
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
    

    public int getIdHospital() {
        return idHospital;
    }

    public void setIdHospital(int idHospital) {
        this.idHospital = idHospital;
    }

    public String getNombreCompleto() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }


}
