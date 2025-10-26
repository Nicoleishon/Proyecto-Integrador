
package com.mycompany.proyectointegrador.modelo;

// Refactorizar

public class HistorialMedico {
    private String historialId;
    private String alergias;
    private String medicamentos;
    private String cirugias;
    private String historialVacunacion;

    public HistorialMedico(String historialId, String alergias, String medicamentos, String cirugias, String historialVacunacion) {
        this.historialId = historialId;
        this.alergias = alergias;
        this.medicamentos = medicamentos;
        this.cirugias = cirugias;
        this.historialVacunacion = historialVacunacion;
    }

    
    
}
