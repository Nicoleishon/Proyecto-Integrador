package com.mycompany.proyectointegrador.modelo;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;


class Medico extends PersonalHospital {
    private int idMedico;
    private String matricula;
    private List<Turno> agendaTurnos;
    private Turno turnoEnCurso;
    private Especialidad especialidad;

    public Medico(int idMedico, String matricula, List<Turno> agendaTurnos, Turno turnoEnCurso, Especialidad especialidad, int idPersonalHospital, Date fechaIngreso, String departamento, List<Horario> horarios) {
        super(idPersonalHospital, fechaIngreso, departamento, horarios);
        this.idMedico = idMedico;
        this.matricula = matricula;
        this.agendaTurnos = agendaTurnos;
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


