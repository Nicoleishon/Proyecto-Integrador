package com.mycompany.proyectointegrador.modelo;

import java.util.Date;
import java.util.List;

public class Paciente {
    private int idPaciente;
    private Date fechaRegistro;
    private ObraSocial obraSocial;
    private List<SeguroMedico> segurosMedicos;
    private HistorialMedico historial;

    public Turno solicitarTurno(Medico medico, Date fechaHora) {
        // implementación
        return null;
    }

    public void cancelarTurno(Turno turno) {
        // implementación
    }

    public HistorialMedico consultarHistorialMedico() {
        // implementación
        return null;
    }

    public List<Factura> verFacturas() {
        // implementación
        return null;
    }
}
