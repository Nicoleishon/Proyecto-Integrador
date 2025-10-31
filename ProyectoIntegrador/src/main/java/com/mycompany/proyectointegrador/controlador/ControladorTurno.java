package com.mycompany.proyectointegrador.controlador;

import com.mycompany.proyectointegrador.modelo.*;
import com.mycompany.proyectointegrador.repositorios.*;

import java.sql.SQLException;
import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

public class ControladorTurno {

    private final TurnoRepositorio turnoRepo;
    private final HorarioRepositorio horarioRepo;

    // duración fija de un turno (ej. 30 minutos)
    private static final int DURACION_TURNO_MINUTOS = 30;

    public ControladorTurno() {
        this.turnoRepo = new TurnoRepositorio();
        this.horarioRepo = new HorarioRepositorio();
    }

    // Devuelve una lista de horarios disponibles para un médico en una fecha determinada.

    public List<LocalTime> obtenerHorariosDisponibles(int idMedico, LocalDate fecha) throws SQLException {
        
        // Obtener los horarios del médico para ese día de la semana
        DiaSemana dia = convertirADiaSemana(fecha);
        List<Horario> horarios = horarioRepo.obtenerPorMedicoYDia(idMedico, dia);
        if (horarios.isEmpty()) return Collections.emptyList();

        // Obtener los turnos ya asignados para esa fecha
        List<Turno> turnosAsignados = turnoRepo.obtenerPorMedicoYFecha(idMedico, fecha);

        // Generar intervalos libres de 30 minutos
        Set<LocalTime> ocupados = turnosAsignados.stream()
            .filter(t -> t.getEstado() != EstadoTurno.CANCELADO)
            .map(t -> t.getFechaHora().toLocalTime())
            .collect(Collectors.toSet());


        List<LocalTime> disponibles = new ArrayList<>();
        LocalTime ahora = (fecha.equals(LocalDate.now())) ? LocalTime.now() : LocalTime.MIN;

        for (Horario horario : horarios) {
            LocalTime hora = horario.getHoraInicio();
            while (!hora.plusMinutes(DURACION_TURNO_MINUTOS).isAfter(horario.getHoraFin())) {
                if (!ocupados.contains(hora) && !hora.isBefore(ahora)) {
                    disponibles.add(hora);
                }
                hora = hora.plusMinutes(DURACION_TURNO_MINUTOS);
            }
        }
        
        return disponibles;
    }


    // Crea un nuevo turno si el horario sigue disponible.

    public void asignarTurno(Paciente paciente, Medico medico, LocalDateTime fechaHora, String motivo) throws Exception {
        
        if (fechaHora.isBefore(LocalDateTime.now())) {
            throw new Exception("No se puede asignar un turno en el pasado.");
        }
        
        if (paciente == null && medico == null) {
            throw new Exception("Paciente y médico son obligatorios.");
        }
        
        if (paciente == null) throw new Exception("Paciente es obligatorio.");
        if (medico == null) throw new Exception("Médico es obligatorio.");

        

        boolean disponible = estaDisponible(medico.getIdMedico(), fechaHora);
        if (!disponible) {
            throw new Exception("El médico no tiene disponibilidad en ese horario.");
        }

        Turno nuevo = new Turno(fechaHora, motivo);
        nuevo.setPaciente(paciente);
        nuevo.setMedico(medico);
        turnoRepo.crear(nuevo);
    }

    private boolean estaDisponible(int idMedico, LocalDateTime fechaHora) throws SQLException {
        List<Turno> turnos = turnoRepo.obtenerPorMedicoYFecha(idMedico, fechaHora.toLocalDate());
        return turnos.stream()
             .filter(t -> t.getEstado() != EstadoTurno.CANCELADO)
             .noneMatch(t -> t.getFechaHora().equals(fechaHora));
    }
    
    public DiaSemana convertirADiaSemana(LocalDate fecha) {
        switch (fecha.getDayOfWeek()) {
            case MONDAY: return DiaSemana.LUNES;
            case TUESDAY: return DiaSemana.MARTES;
            case WEDNESDAY: return DiaSemana.MIERCOLES;
            case THURSDAY: return DiaSemana.JUEVES;
            case FRIDAY: return DiaSemana.VIERNES;
            case SATURDAY: return DiaSemana.SABADO;
            case SUNDAY: return DiaSemana.DOMINGO;
            default: throw new IllegalArgumentException("Día inválido");
        }
    }
    
    public List<DiaSemana> obtenerDiasLaboralesMedico(int idMedico) throws SQLException {
        List<Horario> horarios = horarioRepo.obtenerPorIdMedico(idMedico);
        // Usar un Set para eliminar duplicados
        Set<DiaSemana> diasUnicos = new HashSet<>();
        for (Horario h : horarios) {
            diasUnicos.add(h.getDiaSemana());
        }
        // Convertir a lista y ordenar por el enum
        List<DiaSemana> dias = new ArrayList<>(diasUnicos);
        dias.sort(Comparator.comparing(Enum::ordinal));
        return dias;
    }


    
}
