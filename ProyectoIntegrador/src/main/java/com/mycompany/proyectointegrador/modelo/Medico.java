package com.mycompany.proyectointegrador.modelo;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;


class Medico extends PersonalHospital {
    private int idMedico;
    private String matricula;
    private Especialidad especialidad;
    private List<Turno> agendaTurnos;
    private Turno turnoEnCurso;

    public Medico(int idMedico, String matricula, Especialidad especialidad, List<Turno> agendaTurnos, Turno turnoEnCurso, int idPersonalHospital, Date fechaIngreso, String departamento, List<Horario> horarios) {
        super(idPersonalHospital, fechaIngreso, departamento, horarios);
        this.idMedico = idMedico;
        this.matricula = matricula;
        this.especialidad = especialidad;
        this.agendaTurnos = (agendaTurnos != null) ? agendaTurnos : new ArrayList<>();
        this.turnoEnCurso = turnoEnCurso;
    }
   
    public int getIdMedico() {
        return idMedico;
    }

    public String getMatricula() {
        return matricula;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public List<Turno> getAgendaTurnos() {
        return agendaTurnos;
    }

    public Turno getTurnoEnCurso() {
        return turnoEnCurso;
    }

    public void setIdMedico(int idMedico) {
        this.idMedico = idMedico;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }

    public void setAgendaTurnos(List<Turno> agendaTurnos) {
        this.agendaTurnos = agendaTurnos != null ? agendaTurnos : new ArrayList<>();
    }

    public void setTurnoEnCurso(Turno turnoEnCurso) {
        this.turnoEnCurso = turnoEnCurso;
    }
    
    public void iniciarAtencion() {
        if (turnoEnCurso != null) {
            turnoEnCurso.setEstado(EstadoTurno.INICIALIZADO);
        }
    }

    public void finalizarAtencion(Turno turno, String resumen) {
        // implementación
    }

    public void registrarReceta(Paciente paciente, MedicamentoPrescrito medicamento) {
        // implementación
    }

    public void registrarEstudio(Paciente paciente, Estudio estudio) {
        // implementación
    }

    public HistorialMedico consultarHistorial(Paciente paciente) {
        // implementación
        return null;
    }

    public void actualizarHistorial(Paciente paciente, HistorialMedico historial) {
        this.turnoEnCurso.getPaciente().consultarHistorialMedico()
    }

    public Boolean puedeAtender() {
        return turnoEnCurso != null &&
               EstadoTurno.CONFIRMADO.equals(turnoEnCurso.getEstado());
    }
    
}


