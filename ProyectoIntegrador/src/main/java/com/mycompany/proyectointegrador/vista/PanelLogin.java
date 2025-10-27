package com.mycompany.proyectointegrador.vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class PanelLogin extends JPanel {

    private final JTextField campoUsuario;
    private final JPasswordField campoContraseña;
    private final JButton botonIniciarSesion;
    private final JButton botonRegistrarse;

    public PanelLogin() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        
        // Panel superior con el título
        JLabel titulo = new JLabel("Sistema Gestor de Turnos", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titulo, BorderLayout.NORTH);
        
        // Panel central con campos de entrada
        JPanel panelCampos = new JPanel(new GridLayout(2, 2, 10, 10));
        panelCampos.setBorder(BorderFactory.createEmptyBorder(20, 60, 20, 60));
        panelCampos.setBackground(Color.WHITE);

        JLabel etiquetaUsuario = new JLabel("Usuario:");
        campoUsuario = new JTextField(15);
        JLabel etiquetaContraseña = new JLabel("Contraseña:");
        campoContraseña = new JPasswordField(15);
        
        panelCampos.add(etiquetaUsuario);
        panelCampos.add(campoUsuario);
        panelCampos.add(etiquetaContraseña);
        panelCampos.add(campoContraseña);
        
        add(panelCampos, BorderLayout.CENTER);
        
        // Panel inferior con botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.setBackground(Color.WHITE);
        botonIniciarSesion = new JButton("Iniciar Sesión");
        botonRegistrarse = new JButton("Registrarse");
        
        panelBotones.add(botonIniciarSesion);
        panelBotones.add(botonRegistrarse);
        add(panelBotones, BorderLayout.SOUTH);
    }

    public String getUsuario() {
        return campoUsuario.getText();
    }

    public String getContraseña() {
        return new String(campoContraseña.getPassword());
    }

    public void addAccionIniciarSesion(ActionListener listener) {
        botonIniciarSesion.addActionListener(listener);
    }

    public void addAccionRegistrarse(ActionListener listener) {
        botonRegistrarse.addActionListener(listener);
    }
}
