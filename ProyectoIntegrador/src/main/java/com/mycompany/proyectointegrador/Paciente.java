
package com.mycompany.proyectointegrador;


public class Paciente {
    private String pacienteId;
    private int nroSeguroSocial;
    private String fechaRegristo;

    public Paciente(String pacienteId, int nroSeguroSocial, String fechaRegristo) {
        this.pacienteId = pacienteId;
        this.nroSeguroSocial = nroSeguroSocial;
        this.fechaRegristo = fechaRegristo;
    }
    
    public Turno reservarTurno(Medico medico, String fecha){
        
    }
    
    public void cancelarTurno(Turno turno){
        
    }
    
    public HistorialMedico verHistorial(){
        
    }
    
    public List<Factura> verFacturas(){
        
    }
}
