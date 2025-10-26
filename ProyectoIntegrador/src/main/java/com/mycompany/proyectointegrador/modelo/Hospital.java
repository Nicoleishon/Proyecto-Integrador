
package com.mycompany.proyectointegrador.modelo;

import java.util.ArrayList;
import java.util.List;


public class Hospital {

    private int idHospital;
    private String nombre;
    private String direccion;
    private List<PersonalHospital> listaPersonal;
    private List<Paciente> listaPacientes;
    private List<Turno> listaTurnos;

    public Hospital(int idHospital, String nombre, String direccion) {
        this.idHospital = idHospital;
        this.nombre = nombre;
        this.direccion = direccion;
        this.listaPersonal = new ArrayList<>();
        this.listaPacientes = new ArrayList<>();
        this.listaTurnos = new ArrayList<>();
    }

    // Métodos de consulta
    public List<Medico> buscarMedicoPorEspecialidad(Especialidad especialidad) {
        List<Medico> medicosPorEspecialidad = new ArrayList<>();
        for (PersonalHospital p : listaPersonal) {
            if (p instanceof Medico) {
                Medico m = (Medico) p;
                if (m.getEspecialidad().equals(especialidad)) {
                    medicosPorEspecialidad.add(m);
                }
            }
        }
        return medicosPorEspecialidad;
    }

    public List<Turno> obtenerTurnosPorPaciente(Paciente paciente) {
        List<Turno> turnosPorPaciente = new ArrayList<>();
        for (Turno t : listaTurnos) {
            if (t.getPaciente().equals(paciente)) {
                turnosPorPaciente.add(t);
            }
        }
        return turnosPorPaciente;
    }

    public List<Turno> obtenerTurnosPorMedico(Medico medico) {
        List<Turno> turnosPorMedico = new ArrayList<>();
        for (Turno t : listaTurnos) {
            if (t.getMedico().equals(medico)) {
                turnosPorMedico.add(t);
            }
        }
        return turnosPorMedico;
    }

    // Métodos de modificación de listas
    public void agregarMedico(Medico medico) {
        listaPersonal.add(medico);
    }

    public void eliminarMedico(Medico medico) {
        listaPersonal.remove(medico);
    }

    public void agregarPaciente(Paciente paciente) {
        listaPacientes.add(paciente);
    }

    public void eliminarPaciente(Paciente paciente) {
        listaPacientes.remove(paciente);
    }

    public void agregarTurno(Turno turno) {
        listaTurnos.add(turno);
    }

    public void eliminarTurno(Turno turno) {
        listaTurnos.remove(turno);
    }

    // Getters y setters
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
