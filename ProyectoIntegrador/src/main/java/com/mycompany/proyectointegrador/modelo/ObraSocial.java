package com.mycompany.proyectointegrador.modelo;


public class ObraSocial {
    private int idObraSocial;
    private String nombre;
    private String numeroAfiliado; // no seria int?
    private String cobertura;

    public ObraSocial(int idObraSocial, String nombre, String numeroAfiliado, String cobertura) {
        this.idObraSocial = idObraSocial;
        this.nombre = nombre;
        this.numeroAfiliado = numeroAfiliado;
        this.cobertura = cobertura;
    }

    public int getIdObraSocial() {
        return idObraSocial;
    }

    public void setIdObraSocial(int idObraSocial) {
        this.idObraSocial = idObraSocial;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNumeroAfiliado() {
        return numeroAfiliado;
    }

    public void setNumeroAfiliado(String numeroAfiliado) {
        this.numeroAfiliado = numeroAfiliado;
    }

    public String getCobertura() {
        return cobertura;
    }

    public void setCobertura(String cobertura) {
        this.cobertura = cobertura;
    }
    
    
    
}
