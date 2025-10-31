package com.mycompany.proyectointegrador.vista;

import com.mycompany.proyectointegrador.controlador.ControladorIniciarSesion;
import com.mycompany.proyectointegrador.controlador.ControladorRegistrarPaciente;
import com.mycompany.proyectointegrador.modelo.*;
import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {

    private final CardLayout layout;
    private final JPanel contenedorVistas;

    // Paneles base
    private final PanelIniciarSesion panelIniciarSesion;
    private PanelRegistro panelRegistro;
    private final PanelRecepcionista panelRecepcionista;
    private final PanelPaciente panelPaciente;
    private final ControladorIniciarSesion controladorIniciarSesion = new ControladorIniciarSesion();
    private final ControladorRegistrarPaciente controladorRegistrar = new ControladorRegistrarPaciente();

    public VentanaPrincipal() {
        setTitle("Sistema Gestor de Turnos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // CardLayout para cambiar paneles dinámicamente
        layout = new CardLayout();
        contenedorVistas = new JPanel(layout);

        // Crear vistas base
        panelIniciarSesion = new PanelIniciarSesion(this);
        panelRegistro = new PanelRegistro(this);
        panelPaciente = new PanelPaciente(this);
        panelRecepcionista = new PanelRecepcionista(this);

        // Agregar las vistas al contenedor
        contenedorVistas.add(panelIniciarSesion, "panelIniciarSesion");
        contenedorVistas.add(panelRegistro, "panelRegistro");
        contenedorVistas.add(panelPaciente, "panelPaciente");
        contenedorVistas.add(panelRecepcionista, "panelRecepcionista");

        add(contenedorVistas);
        mostrarVista("panelIniciarSesion"); // vista inicial
    }


    public void mostrarVista(String nombreVista) {
        
        if (nombreVista.equals("panelRegistro")) {
            // Eliminar el panel anterior y crear uno nuevo
            contenedorVistas.remove(panelRegistro);
            panelRegistro = new PanelRegistro(this);
            contenedorVistas.add(panelRegistro, "panelRegistro");
        }
        
        layout.show(contenedorVistas, nombreVista);

        // Limpieza automática de campos si corresponde
        if (nombreVista.equals("panelIniciarSesion")) {
            panelIniciarSesion.limpiarCampos();
        }
    }

    public ControladorIniciarSesion getControladorIniciarSesion(){
        return this.controladorIniciarSesion;
    }

    public ControladorRegistrarPaciente getControladorRegistrar() {
        return controladorRegistrar;
    }
    
    
    public String obtenerPanelSesion() {
        Usuario usuarioActual = controladorIniciarSesion.getUsuarioActual();
        if (usuarioActual instanceof Paciente)
            return "panelPaciente";
        else if (usuarioActual instanceof Recepcionista)
            return "panelRecepcionista";
        else
            return "panelIniciarSesion";
    }

}
