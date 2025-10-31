

package com.mycompany.proyectointegrador.modelo;
import java.time.LocalDateTime;


public class Turno {
    private int idTurno;
    private LocalDateTime fechaHora;
    private EstadoTurno estado = EstadoTurno.PENDIENTE;
    private String motivoConsulta;
    private int idMedico;
    private int idPaciente;


    
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

    public int getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(int idMedico) {
        this.idMedico = idMedico;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }
    
    
}
