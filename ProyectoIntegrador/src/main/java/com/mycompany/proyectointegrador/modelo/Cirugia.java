
package com.mycompany.proyectointegrador.modelo;

import java.time.LocalDate;

public class Cirugia {
    private int idCirugia;
    private String nombre;
    private LocalDate fecha;
    private String descripcion;

    public Cirugia(int idCirugia, String nombre, LocalDate fecha, String descripcion) {
        this.idCirugia = idCirugia;
        this.nombre = nombre;
        this.fecha = fecha;
        this.descripcion = descripcion;
    }

    public int getIdCirugia() {
        return idCirugia;
    }

    public void setIdCirugia(int idCirugia) {
        this.idCirugia = idCirugia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}

