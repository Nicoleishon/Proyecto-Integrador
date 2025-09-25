package com.mycompany.proyectointegrador;

public class PersonalHospital {
    protected String empleadoId;
    protected String fechaIngreso; // buscar tipo de dato exacto para fechas
    protected String departamento;
    protected String turnoAsignado;

    public PersonalHospital(String empleadoId, String fechaIngreso, String departamento, String turnoAsignado) {
        this.empleadoId = empleadoId;
        this.fechaIngreso = fechaIngreso;
        this.departamento = departamento;
        this.turnoAsignado = turnoAsignado;
    }
    
    public void registrarEntrada(){
        
    }
    
    public void asignarTurno(){
        
    }
    
    public void generarReporte(String reporte){
        
    }
}
