package com.mycompany.proyectointegrador.modelo;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;


public class Medico extends PersonalHospital {
    private int idMedico;
    private String matricula;
    private List<Turno> agendaTurnos;
    private Turno turnoEnCurso;
    private Especialidad especialidad;

    public Medico(
            // Parámetros de Medico
            int idMedico, String matricula, List<Turno> agendaTurnos, 
            Turno turnoEnCurso, Especialidad especialidad, 
            
            // Parámetros de PersonalHospital
            int idPersonalHospital, Date fechaIngreso, String departamento, List<Horario> horarios,
            
            // Parámetros de Persona (que se pasan a PersonalHospital)
            int idPersona, String nombre, String apellido, String fechaNacimiento, 
            String direccion, String telefono, String dni
    ) {
        super(idPersonalHospital, fechaIngreso, departamento, horarios, 
              idPersona, nombre, apellido, fechaNacimiento, direccion, telefono, dni);
        this.idMedico = idMedico;
        this.matricula = matricula;
        this.agendaTurnos = (agendaTurnos != null) ? agendaTurnos : new ArrayList<>();
        this.turnoEnCurso = turnoEnCurso;
        this.especialidad = especialidad;
    }

    public int getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(int idMedico) {
        this.idMedico = idMedico;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public List<Turno> getAgendaTurnos() {
        return agendaTurnos;
    }

    public void setAgendaTurnos(List<Turno> agendaTurnos) {
        this.agendaTurnos = agendaTurnos;
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
    
}


