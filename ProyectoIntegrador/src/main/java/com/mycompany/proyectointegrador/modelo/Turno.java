

package com.mycompany.proyectointegrador.modelo;
import java.time.LocalDateTime;
import com.mycompany.proyectointegrador.servicios.ServicioTurnos;

public class Turno {
    private String idTurno;
    private LocalDateTime fechaHora;
    private EstadoTurno estado = EstadoTurno.PENDIENTE;
    private String motivoConsulta;
    private Medico medico;
    private Paciente paciente;

    
    public Turno(String idTurno, LocalDateTime fechaHora, String motivoConsulta, String estado) {
        this.idTurno = idTurno;
        this.fechaHora = fechaHora;
        this.motivoConsulta = motivoConsulta;
        this.estado = ServicioTurnos.validarEstadoTurno(estado);
    }
    
    public Turno(String idTurno, LocalDateTime fechaHora, String motivoConsulta) {
        this.idTurno = idTurno;
        this.fechaHora = fechaHora;
        this.motivoConsulta = motivoConsulta;
        this.estado = EstadoTurno.PENDIENTE;
    }
    
    public void cambiarEstadoTurno(String nuevoEstado){
        this.estado = ServicioTurnos.validarEstadoTurno(nuevoEstado);
    }
    
}
