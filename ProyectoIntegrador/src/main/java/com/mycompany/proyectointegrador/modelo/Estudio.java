

package com.mycompany.proyectointegrador.modelo;

import java.time.LocalDate;

public class Estudio {
    private int idEstudio;
    private String descripcion;
    private LocalDate fechaSolicitud;
    private String resultados;
    private Medico medicoSolicitante;

    public Estudio(int idEstudio, String descripcion, LocalDate fechaSolicitud, String resultados, Medico medicoSolicitante) {
        this.idEstudio = idEstudio;
        this.descripcion = descripcion;
        this.fechaSolicitud = fechaSolicitud;
        this.resultados = resultados;
        this.medicoSolicitante = medicoSolicitante;
    }

    public int getIdEstudio() {
        return idEstudio;
    }

    public void setIdEstudio(int idEstudio) {
        this.idEstudio = idEstudio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(LocalDate fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public String getResultados() {
        return resultados;
    }

    public void setResultados(String resultados) {
        this.resultados = resultados;
    }

    public Medico getMedicoSolicitante() {
        return medicoSolicitante;
    }

    public void setMedicoSolicitante(Medico medicoSolicitante) {
        this.medicoSolicitante = medicoSolicitante;
    }

}
