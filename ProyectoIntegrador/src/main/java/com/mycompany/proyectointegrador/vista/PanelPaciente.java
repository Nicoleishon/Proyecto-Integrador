package com.mycompany.proyectointegrador.vista;

import com.mycompany.proyectointegrador.modelo.Paciente;
import javax.swing.*;
import java.awt.*;

public class PanelPaciente extends JPanel {

    private final VentanaPrincipal ventana;

    public PanelPaciente(VentanaPrincipal ventana) {
        this.ventana = ventana;

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Encabezado
        JLabel titulo = new JLabel("Panel del Paciente", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titulo, BorderLayout.NORTH);

        // Panel central con opciones
        JPanel panelOpciones = new JPanel(new GridLayout(3, 1, 10, 10));
        panelOpciones.setBorder(BorderFactory.createEmptyBorder(40, 200, 40, 200));

        JButton btnSolicitarTurno = new JButton("Solicitar Turno");
        JButton btnCancelarTurno = new JButton("Cancelar Turno");
        JButton btnCerrarSesion = new JButton("Cerrar Sesión");

        panelOpciones.add(btnSolicitarTurno);
        panelOpciones.add(btnCancelarTurno);
        panelOpciones.add(btnCerrarSesion);

        add(panelOpciones, BorderLayout.CENTER);

        // Acciones
        btnSolicitarTurno.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, 
                "Funcionalidad pendiente: Solicitar Turno");
        });

        btnCancelarTurno.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, 
                "Funcionalidad pendiente: Cancelar Turno");
        });

        btnCerrarSesion.addActionListener(e -> {
            ventana.getControladorIniciarSesion().cerrarSesion();
            ventana.mostrarVista("panelIniciarSesion");
        });
    }
}
