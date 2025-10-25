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
}

