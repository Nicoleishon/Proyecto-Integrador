

package com.mycompany.proyectointegrador.modelo;


import java.time.LocalDate;


public class Turno {
    private String idTurno;
    private LocalDate fechaHora;
    private String tipoConsulta;
    private EstadoTurno estado;
    private String motivoConsulta;
    private Medico medico;
    private Paciente paciente;

    public Turno(String turnoId, LocalDate fechaHora, String tipoConsulta, EstadoTurno estado) {
        this.idTurno = idTurno;
        this.fechaHora = fechaHora;
        this.tipoConsulta = tipoConsulta;
        this.estado = estado;
    }
    
    public void cambiarEstadoTurno(EstadoTurno nuevoEstado){
        this.estado = nuevoEstado;
    }
    
}
