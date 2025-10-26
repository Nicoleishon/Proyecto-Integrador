package com.mycompany.proyectointegrador.modelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// Clase abstracta PersonalHospital
abstract class PersonalHospital {
    private int idPersonalHospital;
    private Hospital hospital;
    private Date fechaIngreso;
    private String departamento;
    private List <Horario> horarios;

    // Constructor
    public PersonalHospital(int idPersonalHospital, Date fechaIngreso, 
                            String departamento, List<Horario> horarios) {
        this.idPersonalHospital = idPersonalHospital;
        this.fechaIngreso = fechaIngreso;
        this.departamento = departamento;
        this.horarios = horarios != null ? horarios : new ArrayList<>();
    }

    public int getIdPersonalHospital() {
        return idPersonalHospital;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public String getDepartamento() {
        return departamento;
    }

    public List<Horario> getHorarios() {
        return horarios;
    }

    
    public void setIdPersonalHospital(int idPersonalHospital) {
        this.idPersonalHospital = idPersonalHospital;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public void setHorarios(List<Horario> horarios) {
        this.horarios = horarios != null ? horarios : new ArrayList<>();
    }

    
    public void agregarHorario(Horario horario) {
        if (horario != null) {
            this.horarios.add(horario);
        }
    }

    public void removerHorario(Horario horario) {
        this.horarios.remove(horario);
    }

    @Override
    public String toString() {
        return "PersonalHospital{" +
                "idPersonalHospital=" + idPersonalHospital +
                ", fechaIngreso=" + fechaIngreso +
                ", departamento='" + departamento + '\'' +
                ", horarios=" + horarios +
                '}';
    }
}
