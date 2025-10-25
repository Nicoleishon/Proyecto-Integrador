

package com.mycompany.proyectointegrador.modelo;

public class Alergia {
    private int idAlergia;
    private String nombre;
    private String descripcion;
    private String gravedad;

    public Alergia(int idAlergia, String nombre, String descripcion, String gravedad) {
        this.idAlergia = idAlergia;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.gravedad = gravedad;
    }

    public int getIdAlergia() {
        return idAlergia;
    }

    public void setIdAlergia(int idAlergia) {
        this.idAlergia = idAlergia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getGravedad() {
        return gravedad;
    }

    public void setGravedad(String gravedad) {
        this.gravedad = gravedad;
    }

}

