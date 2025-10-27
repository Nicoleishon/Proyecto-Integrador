package com.mycompany.proyectointegrador.modelo;

import java.time.LocalDateTime;
import java.time.LocalDate;


public class Recepcionista extends Usuario {
    private final int idRecepcionista;

    public Recepcionista(int idRecepcionista, int idUsuario, String nombreUsuario, String hashContraseña, int idPersona, String nombre, String apellido, LocalDate fechaNacimiento, String direccion, String telefono, String dni) {
        super(idUsuario, nombreUsuario, hashContraseña, idPersona, nombre, apellido, fechaNacimiento, direccion, telefono, dni);
        this.idRecepcionista = idRecepcionista;
    }
    
  public void registrarPaciente(Paciente datos){
      return;
  }
  
  public Turno crearTurno(Paciente paciente, Medico medico, LocalDateTime fechaHora, String motivoConsulta){
      Turno nuevoTurno = new Turno(fechaHora, motivoConsulta);
      nuevoTurno.setMedico(medico);
      nuevoTurno.setPaciente(paciente);
      return nuevoTurno;
  }
  
  public void agendarTurno(Turno turno){
      return;
  }
  

  public void cancelarTurno(Turno turno){
      return;
  }
  
  public void reprogramarTurno(Turno turno, LocalDateTime nuevaFecha){
      return;
  }
    
    
    
}