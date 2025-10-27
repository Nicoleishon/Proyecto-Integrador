package com.mycompany.proyectointegrador.vista;

import com.mycompany.proyectointegrador.modelo.Paciente;
import com.mycompany.proyectointegrador.modelo.Recepcionista;
import com.mycompany.proyectointegrador.modelo.Usuario;

import javax.swing.*;
import java.awt.*;

public class PanelIniciarSesion extends JPanel {

    private final JTextField campoUsuario;
    private final JPasswordField campoContraseña;
    private final JButton botonIniciarSesion;
    private final JButton botonRegistrarse;
    private final VentanaPrincipal ventana;

    public PanelIniciarSesion(VentanaPrincipal ventana) {
        this.ventana = ventana;
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 245)); // color de fondo suave

        // Título
        JLabel titulo = new JLabel("Sistema Gestor de Turnos", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titulo.setBorder(BorderFactory.createEmptyBorder(30, 0, 20, 0));
        add(titulo, BorderLayout.NORTH);

        // Panel central con campos
        JPanel panelCampos = new JPanel(new GridBagLayout());
        panelCampos.setBackground(Color.WHITE);
        panelCampos.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
            BorderFactory.createEmptyBorder(20, 30, 20, 30)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Etiqueta y campo usuario
        gbc.gridx = 0; gbc.gridy = 0;
        panelCampos.add(new JLabel("Usuario:"), gbc);
        campoUsuario = new JTextField(15);
        gbc.gridx = 1;
        panelCampos.add(campoUsuario, gbc);

        // Etiqueta y campo contraseña
        gbc.gridx = 0; gbc.gridy = 1;
        panelCampos.add(new JLabel("Contraseña:"), gbc);
        campoContraseña = new JPasswordField(15);
        gbc.gridx = 1;
        panelCampos.add(campoContraseña, gbc);

        add(panelCampos, BorderLayout.CENTER);

        // Panel inferior con botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBotones.setBackground(new Color(245, 245, 245));
        botonIniciarSesion = new JButton("Iniciar Sesión");
        botonRegistrarse = new JButton("Registrarse");

        // Personalizar botones
        botonIniciarSesion.setBackground(new Color(33, 150, 243));
        botonIniciarSesion.setForeground(Color.WHITE);
        botonRegistrarse.setBackground(new Color(76, 175, 80));
        botonRegistrarse.setForeground(Color.WHITE);

        panelBotones.add(botonIniciarSesion);
        panelBotones.add(botonRegistrarse);
        add(panelBotones, BorderLayout.SOUTH);

        configurarAcciones();
    }

    private void configurarAcciones() {
        botonIniciarSesion.addActionListener(e -> {

            String usuario = getUsuario();
            String contraseña = getContraseña();

            if (usuario.equals("admin") && contraseña.equals("1234")) {
                limpiarCampos();
                JOptionPane.showMessageDialog(this, "Inicio de sesión exitoso");
                ventana.mostrarVista("sistema");
            } else {
                JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos",
                                              "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        botonRegistrarse.addActionListener(e -> {
            limpiarCampos();
            ventana.mostrarVista("registro");
        });
    }

    public void limpiarCampos() {
        campoUsuario.setText("");
        campoContraseña.setText("");
    }


    public String getUsuario() {
        return campoUsuario.getText();
    }

    public String getContraseña() {
        return new String(campoContraseña.getPassword());
    }
    
}
