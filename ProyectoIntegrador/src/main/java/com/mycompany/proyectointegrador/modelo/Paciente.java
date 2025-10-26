package com.mycompany.proyectointegrador.modelo;


import java.util.List;
import java.time.LocalDate;
import java.time.Period;

public class Paciente extends Persona {
    private int idPaciente;
    private LocalDate fechaRegistro;

    public Paciente(int idPaciente, LocalDate fechaRegistro, int idPersona, String nombre, String apellido, String fechaNacimiento, String direccion, String telefono, String dni) {
        super(idPersona, nombre, apellido, fechaNacimiento, direccion, telefono, dni);
        this.idPaciente = idPaciente;
        this.fechaRegistro = fechaRegistro;
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

    public HistorialMedico consultarHistorialMedico() {
        // implementación
        return null;
    }
}
