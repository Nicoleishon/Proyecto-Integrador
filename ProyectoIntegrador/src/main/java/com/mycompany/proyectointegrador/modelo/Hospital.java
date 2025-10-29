
package com.mycompany.proyectointegrador.modelo;


public class Hospital {

    private int idHospital;
    private String nombre;
    private String direccion;

    
    public Hospital(int idHospital, String nombre, String direccion) {
        this.idHospital = idHospital;
        this.nombre = nombre;
        this.direccion = direccion;
    }

    public Hospital() {}

    // Getters y setters
    public int getIdHospital() {
        return idHospital;
    }
    
    public void setIdHospital(int idHospital) {
        this.idHospital = idHospital;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    
}
