package com.mycompany.proyectointegrador.modelo;

import java.time.LocalDate;
import java.util.List;



public class Medico extends Persona {
    private int idMedico;
    private final int idHospital;
    private LocalDate fechaIngreso;
    private String departamento;
    private String matricula;
    private Turno turnoEnCurso;
    private Especialidad especialidad;

    public Medico(int idHospital, LocalDate fechaIngreso, String departamento, String matricula, Turno turnoEnCurso, Especialidad especialidad, Persona persona) {
        super(persona.getNombre(), persona.getApellido(), persona.getFechaNacimiento(), persona.getDireccion(), persona.getTelefono(), persona.getDni());
        this.idMedico = persona.getIdPersona();
        this.idHospital = idHospital;
        this.fechaIngreso = fechaIngreso;
        this.departamento = departamento;
        this.matricula = matricula;
        this.turnoEnCurso = turnoEnCurso;
        this.especialidad = especialidad;
    }
    
    
    
    public void iniciarAtencion() {
        if (turnoEnCurso != null) {
            turnoEnCurso.setEstado(EstadoTurno.INICIALIZADO);
        }
    }

    public void finalizarAtencion(Turno turno) {
        // implementación
    }

    public Boolean puedeAtender() {
        return turnoEnCurso != null &&
               EstadoTurno.CONFIRMADO.equals(turnoEnCurso.getEstado());
    }

    public int getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(int idMedico) {
        this.idMedico = idMedico;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public Turno getTurnoEnCurso() {
        return turnoEnCurso;
    }

    public void setTurnoEnCurso(Turno turnoEnCurso) {
        this.turnoEnCurso = turnoEnCurso;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }

    
}


