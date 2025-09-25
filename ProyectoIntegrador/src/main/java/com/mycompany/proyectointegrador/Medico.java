package com.mycompany.proyectointegrador;
import java.util.ArrayList;

public class Medico {
    private String matricula;
    private String especialidad;
    private ArrayList<String> horarios;

    public Medico(String matricula, String especialidad, ArrayList<String> horarios) {
        this.matricula = matricula;
        this.especialidad = especialidad;
        this.horarios = horarios;
    }
    
    public void atenderPaciente(Paciente paciente, Turno turno){
        
    }
    
    public String emitirReceta(Paciente paciente, String receta){
        
    }
    
    public HistorialMedico verHistorial(Paciente paciente){
        
    }
    
}

