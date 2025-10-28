package com.mycompany.proyectointegrador.modelo;

import com.mycompany.proyectointegrador.repositorios.TurnoRepositorio;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalDate;


public class Recepcionista extends Usuario {
    private int idRecepcionista;
    private Hospital hospital;
    private TurnoRepositorio turnoRepositorio;

    public Recepcionista(int idRecepcionista, int idUsuario, String nombreUsuario, String hashContraseña, int idPersona, String nombre, String apellido, LocalDate fechaNacimiento, String direccion, String telefono, String dni) {
        super(idUsuario, nombreUsuario, hashContraseña, idPersona, nombre, apellido, fechaNacimiento, direccion, telefono, dni);
        this.idRecepcionista = idRecepcionista;
        this.turnoRepositorio = new TurnoRepositorio();
    }
    
    public Recepcionista(int idRecepcionista, Usuario usuario, Persona persona) {
        super(idUsuario, nombreUsuario, hashContraseña, idPersona, nombre, apellido, fechaNacimiento, direccion, telefono, dni);
        this.idRecepcionista = idRecepcionista;
        this.turnoRepositorio = new TurnoRepositorio();
    }
    
  public void registrarPaciente(Paciente paciente){
      return;
  }
  
  public Turno crearTurno(Paciente paciente, Medico medico, LocalDateTime fechaHora, String motivoConsulta){
      Turno nuevoTurno = new Turno(fechaHora, motivoConsulta);
      nuevoTurno.setMedico(medico);
      nuevoTurno.setPaciente(paciente);
      return nuevoTurno;
  }
  
    public void agendarTurno(Turno turno) throws SQLException{
        try {
            turnoRepositorio.crear(turno);
        } catch (SQLException e){
            throw e;
        }
    }

  public void cancelarTurno(Turno turno) throws SQLException{
      turno.setEstado(EstadoTurno.CANCELADO);
      try {
          turnoRepositorio.actualizar(turno);
      } catch (SQLException e){
          throw e;
      }
  }
  
  public void reprogramarTurno(Turno turno, LocalDateTime nuevaFecha) throws SQLException{
      turno.setFechaHora(nuevaFecha);
      try {
          turnoRepositorio.actualizar(turno);
      } catch (SQLException e){
          throw e;
      }
  }
    
    
    
}