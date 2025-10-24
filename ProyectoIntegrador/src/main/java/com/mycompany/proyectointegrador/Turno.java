package com.mycompany.proyectointegrador;
import java.util.Date;

public class Turno {
    private String idTurno;
    private Date fechaHora;
    private String tipoConsulta;
    private EstadoTurno estado;
    private String motivoConsulta;
    private Medico medico;
    private Paciente paciente;

    public Turno(String turnoId, Date fechaHora, String tipoConsulta, EstadoTurno estado) {
        this.idTurno = idTurno;
        this.fechaHora = fechaHora;
        this.tipoConsulta = tipoConsulta;
        this.estado = estado;
    }
    
    public void cambiarEstadoTurno(EstadoTurno nuevoEstado){
        this.estado = nuevoEstado;
    }
    
}
