

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

    public String getIdTurno() {
        return idTurno;
    }

    public void setIdTurno(String idTurno) {
        this.idTurno = idTurno;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public EstadoTurno getEstado() {
        return estado;
    }

    public void setEstado(EstadoTurno estado) {
        this.estado = estado;
    }

    public String getMotivoConsulta() {
        return motivoConsulta;
    }

    public void setMotivoConsulta(String motivoConsulta) {
        this.motivoConsulta = motivoConsulta;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }
    
    
    
}
