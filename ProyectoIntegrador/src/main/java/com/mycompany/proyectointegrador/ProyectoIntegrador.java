

package com.mycompany.proyectointegrador;

import com.mycompany.proyectointegrador.vista.VentanaPrincipal;
import javax.swing.SwingUtilities;

public class ProyectoIntegrador {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaPrincipal ventana = new VentanaPrincipal();
            ventana.setVisible(true);
        });
    }
}
