
package com.mycompany.proyectointegrador.modelo;
import java.util.List;


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
}

