
package com.mycompany.proyectointegrador;


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

    public String getHistorialId() {
        return historialId;
    }

    public void setHistorialId(String historialId) {
        this.historialId = historialId;
    }

    public String getAlergias() {
        return alergias;
    }

    public void setAlergias(String alergias) {
        this.alergias = alergias;
    }

    public String getMedicamentos() {
        return medicamentos;
    }

    public void setMedicamentos(String medicamentos) {
        this.medicamentos = medicamentos;
    }

    public String getCirugias() {
        return cirugias;
    }

    public void setCirugias(String cirugias) {
        this.cirugias = cirugias;
    }

    public String getHistorialVacunacion() {
        return historialVacunacion;
    }

    public void setHistorialVacunacion(String historialVacunacion) {
        this.historialVacunacion = historialVacunacion;
    }
    
    
    
}
