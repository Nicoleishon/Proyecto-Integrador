package com.mycompany.proyectointegrador.modelo;

public class SeguroMedico {

    private String idSeguro;
    private String nombre;
    private String nroPoliza; // no seria int?
    private String plan;
    private String cobertura;

    public SeguroMedico(String idSeguro, String nombreAseguradora, String nroAfiliado, String plan) {
        this.idSeguro = idSeguro;
        this.nombre = nombreAseguradora;
        this.nroPoliza = nroAfiliado;
        this.plan = plan;
        this.cobertura = cobertura;
    }

  
    public double calcularCopago(double montoTotal) {
        // implementación por defecto (ej: un seguro genérico cubre el 80% aprox)
        return montoTotal * 0.20; 
    }

 
    public String getIdSeguro() {
        return idSeguro;
    }

    public void setIdSeguro(String idSeguro) {
        this.idSeguro = idSeguro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNroPoliza() {
        return nroPoliza;
    }

    public void setNroPoliza(String nroPoliza) {
        this.nroPoliza = nroPoliza;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getCobertura() {
        return cobertura;
    }

    public void setCobertura(String cobertura) {
        this.cobertura = cobertura;
    }
    
    
}
