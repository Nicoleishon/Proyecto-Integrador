
package com.mycompany.proyectointegrador.servicio;

import com.mycompany.proyectointegrador.modelo.EstadoTurno;

public class ServicioTurno {

    public static EstadoTurno validarEstadoTurno(String estado) {
        try {
            return EstadoTurno.valueOf(estado.toUpperCase());
        } catch (IllegalArgumentException e) {
            // Manejar el error, por ejemplo, asignar un valor por defecto
            System.out.println("Estado inválido, asignando PENDIENTE por defecto.");
            return EstadoTurno.PENDIENTE;
        }
    }
}

