
package com.mycompany.proyectointegrador;


public class Paciente {
    private String pacienteId;
    private SeguroMedico seguro;
    private String fechaRegristo;

    public Paciente(String pacienteId, int nroSeguroSocial, String fechaRegristo) {
        this.pacienteId = pacienteId;
        this.seguro = seguro;
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
