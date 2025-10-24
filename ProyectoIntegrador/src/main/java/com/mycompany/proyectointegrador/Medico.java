package com.mycompany.proyectointegrador;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


class Medico extends PersonalHospital {
    private int idMedico;
    private String matricula;
    private String especialidad;
    private List<Turno> agendaTurnos;
    private Turno turnoEnCurso;

 
    public Medico(int idPersonalHospital, Date fechaIngreso, String departamento,
                  List<Horario> horarios, int idMedico, String matricula,
                  String especialidad, List<Turno> agendaTurnos) {
        super(idPersonalHospital, fechaIngreso, departamento, horarios);
        this.idMedico = idMedico;
        this.matricula = matricula;
        this.especialidad = especialidad;
        this.agendaTurnos = agendaTurnos != null ? agendaTurnos : new ArrayList<>();
        this.turnoEnCurso = null;
    }

   
    public int getIdMedico() {
        return idMedico;
    }

    public String getMatricula() {
        return matricula;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public List<Turno> getAgendaTurnos() {
        return agendaTurnos;
    }

    public Turno getTurnoEnCurso() {
        return turnoEnCurso;
    }

    // Setters
    public void setIdMedico(int idMedico) {
        this.idMedico = idMedico;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public void setAgendaTurnos(List<Turno> agendaTurnos) {
        this.agendaTurnos = agendaTurnos != null ? agendaTurnos : new ArrayList<>();
    }

    public void setTurnoEnCurso(Turno turnoEnCurso) {
        this.turnoEnCurso = turnoEnCurso;
    }



