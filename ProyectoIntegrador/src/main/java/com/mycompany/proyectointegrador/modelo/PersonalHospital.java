package com.mycompany.proyectointegrador.modelo;

import java.util.ArrayList;
import java.time.LocalDate;
import java.util.List;


abstract class PersonalHospital extends Persona {
    private int idPersonalHospital;
    private Hospital hospital;
    private LocalDate fechaIngreso;
    private String departamento;
    public  List<Horario> horarios;

    public PersonalHospital(int idPersonalHospital, LocalDate fechaIngreso, String departamento, List<Horario> horarios, int idPersona, String nombre, String apellido, LocalDate fechaNacimiento, String direccion, String telefono, String dni) {
        super(idPersona, nombre, apellido, fechaNacimiento, direccion, telefono, dni);
        this.idPersonalHospital = idPersonalHospital;
        this.fechaIngreso = fechaIngreso;
        this.departamento = departamento;
        this.horarios = horarios != null ? horarios : new ArrayList<>();
        
    }
    
    public int getIdPersonalHospital() {
        return idPersonalHospital;
    }

    public LocalDate getFechaIngreso() {
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

    public void setFechaIngreso(LocalDate fechaIngreso) {
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
