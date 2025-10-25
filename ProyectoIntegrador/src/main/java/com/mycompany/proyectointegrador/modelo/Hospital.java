package com.mycompany.proyectointegrador.modelo;
import java.util.ArrayList;

public class Hospital {
    private String hospitalId;
    private String nombre;
    private String direccion;
    private ArrayList<PersonalHospital> personalHospital;
    private ArrayList<Paciente> listaPaciente;
    private ArrayList<Turno> listaTurnos;

    public Hospital(String hospitalId, String nombre, String direccion, ArrayList<PersonalHospital> personalHospital, ArrayList<Paciente> listaPaciente, ArrayList<Turno> listaTurnos) {
        this.hospitalId = hospitalId;
        this.nombre = nombre;
        this.direccion = direccion;
        this.personalHospital = personalHospital;
        this.listaPaciente = listaPaciente;
        this.listaTurnos = listaTurnos;
    }
    
    private List<PersonalHospital> personal;
    private List<Paciente> listaPacientes;
    
    public void agregarPersonal(personalHospital personal){
        personal.add(personal);
        System.out.println("´" + personal + "´" + " fue añadido a la lista de personal.");
    }
    
    public void registrarPaciente(Paciente paciente){
        listaPacientes.add(paciente);
        System.out.println("´" + paciente + "´" + " fue añadido a la lista de pacientes.");
    }
    
    public void buscarMedicoPorEspecialidad(String especialidad){
        
    }
    
    public void generarReporteDiario(String reporte){
        
    }
}
