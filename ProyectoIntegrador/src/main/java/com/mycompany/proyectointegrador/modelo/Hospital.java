package com.mycompany.proyectointegrador.modelo;
import java.util.ArrayList;

import java.util.List;

class Hospital {
    private int idHospital;
    private String nombre;
    private String direccion;
    private List<PersonalHospital> listaPersonal;
    private List<Paciente> listaPacientes;
    private List<Turno> listaTurnos;

    public void registrarPaciente(Paciente paciente) {
        // implementación
    }

    public List<Medico> buscarMedicoPorEspecialidad(Especialidad especialidad) {
        // implementación
        return null;
    }

    public String generarReporteDiario() {
        // implementación
        return null;
    }

    public int getIdHospital() {
        return idHospital;
    }

    public void setIdHospital(int idHospital) {
        this.idHospital = idHospital;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public List<PersonalHospital> getListaPersonal() {
        return listaPersonal;
    }

    public void setListaPersonal(List<PersonalHospital> listaPersonal) {
        this.listaPersonal = listaPersonal;
    }

    public List<Paciente> getListaPacientes() {
        return listaPacientes;
    }

    public void setListaPacientes(List<Paciente> listaPacientes) {
        this.listaPacientes = listaPacientes;
    }

    public List<Turno> getListaTurnos() {
        return listaTurnos;
    }

    public void setListaTurnos(List<Turno> listaTurnos) {
        this.listaTurnos = listaTurnos;
    }
}

