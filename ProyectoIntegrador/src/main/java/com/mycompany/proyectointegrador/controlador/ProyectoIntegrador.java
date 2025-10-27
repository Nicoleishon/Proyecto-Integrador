

package com.mycompany.proyectointegrador.controlador;

import com.mycompany.proyectointegrador.vista.VentanaLogin;
import javax.swing.SwingUtilities;

public class ProyectoIntegrador {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaLogin ventana = new VentanaLogin();
            ventana.setVisible(true);
        });
    }
}
