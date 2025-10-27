package com.mycompany.proyectointegrador.vista;

import com.mycompany.proyectointegrador.modelo.*;
import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {

    private final CardLayout layout;
    private final JPanel contenedorVistas;

    // Paneles base
    private final PanelIniciarSesion panelLogin;
    private final PanelRegistro panelRegistro;

    public VentanaPrincipal() {
        setTitle("Sistema Gestor de Turnos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // CardLayout para cambiar paneles dinámicamente
        layout = new CardLayout();
        contenedorVistas = new JPanel(layout);

        // Crear vistas base
        panelLogin = new PanelIniciarSesion(this);
        panelRegistro = new PanelRegistro(this);

        // Agregar las vistas al contenedor
        contenedorVistas.add(panelLogin, "login");
        contenedorVistas.add(panelRegistro, "registro");

        add(contenedorVistas);
        mostrarVista("login"); // vista inicial
    }


    public void mostrarVista(String nombreVista) {
        layout.show(contenedorVistas, nombreVista);

        // Limpieza automática de campos si corresponde
        if (nombreVista.equals("login")) {
            panelLogin.limpiarCampos();
        }
    }


    public void mostrarPanelPorRol(Usuario usuario) {
        JPanel panelRol = null;

        if (usuario instanceof Paciente) {
            panelRol = new PanelPaciente(this, (Paciente) usuario);
        } else if (usuario instanceof Recepcionista) {
            panelRol = new PanelRecepcionista(this, (Recepcionista) usuario);
        }

        if (panelRol != null) {
            contenedorVistas.add(panelRol, "rol");
            layout.show(contenedorVistas, "rol");
        } else {
            JOptionPane.showMessageDialog(this, 
                "Error: tipo de usuario no reconocido.", 
                "Error", JOptionPane.ERROR_MESSAGE);
            mostrarVista("login");
        }
    }
}
