package com.mycompany.proyectointegrador.vista;

import com.mycompany.proyectointegrador.controlador.ControladorIniciarSesion;
import com.mycompany.proyectointegrador.controlador.ControladorRegistrarPaciente;
import com.mycompany.proyectointegrador.controlador.ControladorTurno;
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
    private final ControladorTurno controladorTurno = new ControladorTurno();
    private PanelAsignarTurno panelAsignarTurno;
    private PanelListados panelListados;

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
        panelAsignarTurno = new PanelAsignarTurno(this);
        panelListados = new PanelListados(this);

        // Agregar las vistas al contenedor
        contenedorVistas.add(panelIniciarSesion, "panelIniciarSesion");
        contenedorVistas.add(panelRegistro, "panelRegistro");
        contenedorVistas.add(panelPaciente, "panelPaciente");
        contenedorVistas.add(panelRecepcionista, "panelRecepcionista");
        contenedorVistas.add(panelAsignarTurno, "panelAsignarTurno");
        contenedorVistas.add(panelListados, "panelListados");

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
        
        if (nombreVista.equals("panelAsignarTurno")) {
            // Eliminar el panel anterior y crear uno nuevo
            contenedorVistas.remove(panelAsignarTurno);
            panelAsignarTurno = new PanelAsignarTurno(this);
            contenedorVistas.add(panelAsignarTurno, "panelAsignarTurno");
        }
        
        layout.show(contenedorVistas, nombreVista);

        // Limpieza automática de campos si corresponde
        if (nombreVista.equals("panelIniciarSesion")) {
            panelIniciarSesion.limpiarCampos();
        }
        
        if (nombreVista.equals("panelRecepcionista")) {
            panelRecepcionista.cargarTurnos();
        }
        
        if (nombreVista.equals("panelListados")) {
        panelListados.cargarListas();
    }
    }

    public ControladorIniciarSesion getControladorIniciarSesion(){
        return this.controladorIniciarSesion;
    }

    public ControladorRegistrarPaciente getControladorRegistrar() {
        return controladorRegistrar;
    }
    
    public ControladorTurno getControladorTurno() {
        return controladorTurno;
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
