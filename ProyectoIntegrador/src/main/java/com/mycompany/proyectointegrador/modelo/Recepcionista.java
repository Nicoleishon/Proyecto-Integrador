package com.mycompany.proyectointegrador.modelo;

import com.mycompany.proyectointegrador.repositorios.TurnoRepositorio;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;


public class Recepcionista extends Usuario {
    private int idRecepcionista;
    private int idHospital;
    private TurnoRepositorio turnoRepositorio;

    public Recepcionista(int idRecepcionista, int idHospital, TurnoRepositorio turnoRepositorio, int idUsuario, String nombreUsuario, String hashContraseña, int idPersona, String nombre, String apellido, LocalDate fechaNacimiento, String direccion, String telefono, String dni) {
        super(idUsuario, nombreUsuario, hashContraseña, idPersona, nombre, apellido, fechaNacimiento, direccion, telefono, dni);
        this.idRecepcionista = idRecepcionista;
        this.idHospital = idHospital;
        this.turnoRepositorio = turnoRepositorio;
    }

    
    public Recepcionista(int idHospital, Usuario usuario, Persona persona) {
        super(usuario.getNombreUsuario(), usuario.getHashContraseña(), persona);
        this.idRecepcionista = usuario.getIdUsuario();
        this.idHospital = idHospital;
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

    public int getIdRecepcionista() {
        return idRecepcionista;
    }

    public void setIdRecepcionista(int idRecepcionista) {
        this.idRecepcionista = idRecepcionista;
    }

    public int getIdHospital() {
        return idHospital;
    }

    public void setIdHospital(int idHospital) {
        this.idHospital = idHospital;
    }

    public TurnoRepositorio getTurnoRepositorio() {
        return turnoRepositorio;
    }

    public void setTurnoRepositorio(TurnoRepositorio turnoRepositorio) {
        this.turnoRepositorio = turnoRepositorio;
    }
    
    
    
}