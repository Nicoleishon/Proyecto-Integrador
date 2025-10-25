package com.mycompany.proyectointegrador;

import java.util.Date; // Importamos la clase Date

public class MedicamentoPrescrito {

    private int idMedicamento;
    private String nombre;
    private String dosis;
    private String frecuencia;
    private Date fechaInicio; // <-- Cambiado a Date
    private Date fechaFin; // <-- Cambiado a Date

    // Constructor actualizado para recibir Date
    public MedicamentoPrescrito(int idMedicamento, String nombre, String dosis, String frecuencia, Date fechaInicio, Date fechaFin) {
        this.idMedicamento = idMedicamento;
        this.nombre = nombre;
        this.dosis = dosis;
        this.frecuencia = frecuencia;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public int getIdMedicamento() {
        return idMedicamento;
    }

    public void setIdMedicamento(int idMedicamento) {
        this.idMedicamento = idMedicamento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDosis() {
        return dosis;
    }

    public void setDosis(String dosis) {
        this.dosis = dosis;
    }

    public String getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(String frecuencia) {
        this.frecuencia = frecuencia;
    }

    // Getter y Setter actualizados para Date
    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    // Getter y Setter actualizados para Date
    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }
}