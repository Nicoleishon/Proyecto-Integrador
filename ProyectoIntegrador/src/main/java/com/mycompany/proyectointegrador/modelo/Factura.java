
package com.mycompany.proyectointegrador.modelo;

import java.time.LocalDate;
import java.util.List;

public class Factura {
    private int idFactura;
    private LocalDate fechaEmision;
    private Paciente paciente;
    private Turno turno;
    private List<ItemFactura> items;
    private Double montoTotal;
    private Double montoCubierto;  // lo que cubre la obra social o seguro
    private Double montoPaciente;  // lo que debe pagar el paciente
    private FormaPago formaPago;   // ENUM: OBRA_SOCIAL, SEGURO_MEDICO, PARTICULAR
    private Object cobertura;      // ObraSocial | SeguroMedico | null
    private Boolean pagada;

    // Métodos
    public void generar() {
        // implementación pendiente
    }

    public Boolean pagar() {
        // implementación pendiente
        return null;
    }

    public int getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
    }

    public LocalDate getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDate fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Turno getTurno() {
        return turno;
    }

    public void setTurno(Turno turno) {
        this.turno = turno;
    }

    public List<ItemFactura> getItems() {
        return items;
    }

    public void setItems(List<ItemFactura> items) {
        this.items = items;
    }

    public Double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(Double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public Double getMontoCubierto() {
        return montoCubierto;
    }

    public void setMontoCubierto(Double montoCubierto) {
        this.montoCubierto = montoCubierto;
    }

    public Double getMontoPaciente() {
        return montoPaciente;
    }

    public void setMontoPaciente(Double montoPaciente) {
        this.montoPaciente = montoPaciente;
    }

    public FormaPago getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(FormaPago formaPago) {
        this.formaPago = formaPago;
    }

    public Object getCobertura() {
        return cobertura;
    }

    public void setCobertura(Object cobertura) {
        this.cobertura = cobertura;
    }

    public Boolean getPagada() {
        return pagada;
    }

    public void setPagada(Boolean pagada) {
        this.pagada = pagada;
    }
}

