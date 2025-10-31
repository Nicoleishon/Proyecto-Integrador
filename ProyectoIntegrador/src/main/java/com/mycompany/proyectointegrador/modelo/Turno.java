

package com.mycompany.proyectointegrador.modelo;
import java.time.LocalDateTime;


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
        this.estado = validarEstadoTurno(estado);
    }
    
    public Turno(LocalDateTime fechaHora, String motivoConsulta) {
        this.idTurno = -1;
        this.fechaHora = fechaHora;
        this.motivoConsulta = motivoConsulta;
        this.estado = EstadoTurno.PENDIENTE;
    }

    public Turno() {}

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
    
    public static EstadoTurno validarEstadoTurno(String estado) {
        try {
            return EstadoTurno.valueOf(estado.toUpperCase());
        } catch (IllegalArgumentException e) {
            // Manejar el error, por ejemplo, asignar un valor por defecto
            System.out.println("Estado inválido, asignando PENDIENTE por defecto.");
            return EstadoTurno.PENDIENTE;
        }
    }
    
    
}
