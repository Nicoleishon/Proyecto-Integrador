package com.mycompany.proyectointegrador.vista;

import com.mycompany.proyectointegrador.controlador.ControladorIniciarSesion;
import com.mycompany.proyectointegrador.modelo.Recepcionista;
import javax.swing.*;
import java.awt.*;

public class PanelRecepcionista extends JPanel {

    private final VentanaPrincipal ventana;

    public PanelRecepcionista(VentanaPrincipal ventana) {
        this.ventana = ventana;

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel titulo = new JLabel("Panel de Recepcionista", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titulo, BorderLayout.NORTH);

        JPanel panelOpciones = new JPanel(new GridLayout(5, 1, 10, 10));
        panelOpciones.setBorder(BorderFactory.createEmptyBorder(40, 200, 40, 200));

        JButton btnRegistrarPaciente = new JButton("Registrar Paciente");
        JButton btnAsignarTurno = new JButton("Asignar Turno");
        JButton btnCancelarTurno = new JButton("Cancelar Turno");
        JButton btnReprogramarTurno = new JButton("Reprogramar Turno");
        JButton btnCerrarSesion = new JButton("Cerrar Sesión");

        panelOpciones.add(btnRegistrarPaciente);
        panelOpciones.add(btnAsignarTurno);
        panelOpciones.add(btnCancelarTurno);
        panelOpciones.add(btnReprogramarTurno);
        panelOpciones.add(btnCerrarSesion);

        add(panelOpciones, BorderLayout.CENTER);

        // Acciones
        btnRegistrarPaciente.addActionListener(e -> 
            JOptionPane.showMessageDialog(this, "Funcionalidad pendiente: Registrar Paciente"));

        btnAsignarTurno.addActionListener(e -> 
            JOptionPane.showMessageDialog(this, "Funcionalidad pendiente: Asignar Turno"));

        btnCancelarTurno.addActionListener(e -> 
            JOptionPane.showMessageDialog(this, "Funcionalidad pendiente: Cancelar Turno"));

        btnReprogramarTurno.addActionListener(e -> 
            JOptionPane.showMessageDialog(this, "Funcionalidad pendiente: Reprogramar Turno"));

        btnCerrarSesion.addActionListener(e -> {
            ventana.getControladorIniciarSesion().cerrarSesion();
            ventana.mostrarVista("panelIniciarSesion");
        });

    }
}
