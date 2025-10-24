package com.mycompany.proyectointegrador;

import java.util.Date;

abstract class Persona {
    private int id;
    private String nombre;
    private String apellido;
    private Date fechaNacimiento; 
    private String direccion;
    private int telefono;
    private int dni;

    public Persona(int id, String nombre, String apellido, String fechaNacimiento, String direccion, int telefono, int dni) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento; //preferiria colocar directamente la edad - tira un error
        this.direccion = direccion;
        this.telefono = telefono;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
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
    
    public String obtenerEdad(){
        return fechaNacimiento; // hay que hacer el calculo de la edad
    }
    
    public void actualizarDatos(){ // falta completar
        
    }
    
}
