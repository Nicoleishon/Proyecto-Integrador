package com.mycompany.proyectointegrador.modelo;

import java.time.LocalDate;
import java.time.Period;

abstract class Persona {
    private int idPersona;
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento; 
    private String direccion;
    private String telefono;
    private String dni;

    public Persona(int idPersona, String nombre, String apellido, String fechaNacimiento, String direccion, String telefono, String dni) {
        this.idPersona = idPersona;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = LocalDate.parse(fechaNacimiento);
        this.direccion = direccion;
        this.telefono = telefono;
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

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }
    
    public String obtenerNombreCompleto(){
       return nombre + " " + apellido;
    }
    
    public int obtenerEdad() {
        return Period.between(fechaNacimiento, LocalDate.now()).getYears();
    }
    
    public void actualizarDatos(
        String nombre,
        String apellido,
        String direccion,
        Integer telefono,
        Integer dni
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
        if (telefono != null && telefono > 0) {
            this.telefono = telefono;
        }
        if (dni != null && dni > 0) {
            this.dni = dni;
        }
    }

    
}
