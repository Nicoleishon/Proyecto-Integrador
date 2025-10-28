package com.mycompany.proyectointegrador.modelo;

import java.time.LocalTime;

public class Horario {
    private int idHorario;
    private DiaSemana diaSemana;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private int idPersonalHospital; // clave foránea para vincular al personal
    
    public Horario(DiaSemana diaSemana, LocalTime horaInicio, LocalTime horaFin, int idPersonalHospital) {
        this.idHorario = -1;
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.idPersonalHospital = idPersonalHospital;
    }

    
    public int getIdHorario() {
        return idHorario;
    }

    public DiaSemana getDiaSemana() {
        return diaSemana;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public int getIdPersonalHospital() {
        return idPersonalHospital;
    }

    public void setIdHorario(int idHorario) {
        this.idHorario = idHorario;
    }

    public void setDiaSemana(DiaSemana diaSemana) {
        this.diaSemana = diaSemana;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    public void setIdPersonalHospital(int idPersonalHospital) {
        this.idPersonalHospital = idPersonalHospital;
    }

    @Override
    public String toString() {
        return "Horario{" +
                "id=" + idHorario +
                ", dia=" + diaSemana +
                ", inicio=" + horaInicio +
                ", fin=" + horaFin +
                ", idPersonal=" + idPersonalHospital +
                '}';
    }
}