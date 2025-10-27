package com.mycompany.proyectointegrador.vista;

import javax.swing.*;

public class VentanaLogin extends JFrame {

    public VentanaLogin() {
        setTitle("Sistema Gestor de Turnos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        
        PanelLogin panelLogin = new PanelLogin();
        add(panelLogin);
    }

}
