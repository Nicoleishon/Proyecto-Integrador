

package com.mycompany.proyectointegrador;

import com.mycompany.proyectointegrador.repositorios.ConexionDB;
import com.mycompany.proyectointegrador.utils.InicializadorSistema;
import com.mycompany.proyectointegrador.vista.VentanaPrincipal;
import java.sql.SQLException;
import javax.swing.SwingUtilities;

public class ProyectoIntegrador {

    public static void main(String[] args) {
        
        try {
            // Crear las tablas
            ConexionDB.inicializarBaseDeDatos();

            // Crear hospital y recepcionista inicial si no existen
            InicializadorSistema.inicializarSistema();

            // Levantar la interfaz
            VentanaPrincipal ventana = new VentanaPrincipal();
            ventana.setVisible(true);

        } catch (SQLException e) {
            System.err.println("Error al inicializar la base de datos: " + e.getMessage());
        }
    }
}
