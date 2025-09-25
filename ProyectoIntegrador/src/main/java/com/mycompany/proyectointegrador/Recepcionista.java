
package com.mycompany.proyectointegrador;


public class Recepcionista {
    private String usuario;
    private String contraseña;
    private String turnoTrabajo;

    public Recepcionista(String usuario, String contraseña, String turnoTrabajo) {
        this.usuario = usuario;
        this.contraseña = contraseña;
        this.turnoTrabajo = turnoTrabajo;
    }
    
    public Turno crearTurno(Paciente paciente, Medico medico, String fecha){
        
    }
    
    public void cancelarTurno(Turno turno){
        
    }
    
    // buscar paciente por id
    public Paciente buscarPaciente(String id){
        
    }
    
    // buscar paciente por nombre
    public Paciente buscarPaciente(Paciente paciente){
        
    }
    
    public Factura facturar(Paciente paciente){
        
    }
}

