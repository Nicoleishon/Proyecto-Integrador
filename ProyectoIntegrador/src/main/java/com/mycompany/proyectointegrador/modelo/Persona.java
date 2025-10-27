package com.mycompany.proyectointegrador.modelo;

import java.time.LocalDate;
import java.time.Period;


public abstract class Persona {

    private int idPersona;
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento; 
    private String direccion;
    private String telefono;
    private String dni;

    public Persona(int idPersona, String nombre, String apellido, LocalDate fechaNacimiento, String direccion, String telefono, String dni) {
        this.idPersona = idPersona;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.direccion = direccion;
        this.telefono = telefono;
        this.dni = dni;
    }

    public int getId() {
        return idPersona;
    }

    public void setId(int id) {
        this.idPersona = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    public String obtenerNombreCompleto(){
       return nombre + " " + apellido;
    }
    
    public int obtenerEdad() {
        return Period.between(fechaNacimiento, LocalDate.now()).getYears();
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }
    
    public void actualizarDatos(
        String nombre,
        String apellido,
        String direccion,
        String telefono,
        String dni
    ) {
        if (nombre != null && !nombre.isBlank()) {
            this.nombre = nombre;
        }
        if (apellido != null && !apellido.isBlank()) {
            this.apellido = apellido;
        }
        if (direccion != null && !direccion.isBlank()) {
            this.direccion = direccion;
        }
        if (telefono != null && !telefono.isBlank()) {
            this.telefono = telefono;
        }
        if (dni != null && !dni.isBlank()) {
            this.dni = dni;
        }
    }

    
}
