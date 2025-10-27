

package com.mycompany.proyectointegrador.modelo;
import java.time.LocalDateTime;
import com.mycompany.proyectointegrador.servicios.ServicioTurnos;

public class Turno {
    private int idTurno;
    private LocalDateTime fechaHora;
    private EstadoTurno estado = EstadoTurno.PENDIENTE;
    private String motivoConsulta;
    private Medico medico;
    private Paciente paciente;

    
    public Turno(int idTurno, LocalDateTime fechaHora, String motivoConsulta, String estado) {
        this.idTurno = idTurno;
        this.fechaHora = fechaHora;
        this.motivoConsulta = motivoConsulta;
        this.estado = ServicioTurnos.validarEstadoTurno(estado);
    }
    
    public Turno(LocalDateTime fechaHora, String motivoConsulta) {
        this.idTurno = -1;
        this.fechaHora = fechaHora;
        this.motivoConsulta = motivoConsulta;
        this.estado = EstadoTurno.PENDIENTE;
    }

    public int getIdTurno() {
        return idTurno;
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

    public void setIdTurno(int idTurno) {
        this.idTurno = idTurno;
    }
    
    
    
}
