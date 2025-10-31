package com.mycompany.proyectointegrador.vista;

import com.mycompany.proyectointegrador.controlador.ControladorIniciarSesion;
import com.mycompany.proyectointegrador.modelo.Medico;
import com.mycompany.proyectointegrador.modelo.Paciente;
import com.mycompany.proyectointegrador.modelo.Turno;
import com.mycompany.proyectointegrador.repositorios.MedicoRepositorio;
import com.mycompany.proyectointegrador.repositorios.TurnoRepositorio;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PanelPaciente extends JPanel {

    private final VentanaPrincipal ventana;
    private JTable tablaTurnos;
    private DefaultTableModel modeloTabla;
    private final TurnoRepositorio turnoRepo = new TurnoRepositorio();
    private final MedicoRepositorio medicoRepo = new MedicoRepositorio();

    public PanelPaciente(VentanaPrincipal ventana) {
        this.ventana = ventana;

        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);

        JLabel titulo = new JLabel("Panel del Paciente", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titulo, BorderLayout.NORTH);

        // Panel lateral con opciones
        JPanel panelOpciones = new JPanel(new GridLayout(3, 1, 10, 10));
        panelOpciones.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
        panelOpciones.setBackground(Color.WHITE);

        JButton btnSolicitarTurno = new JButton("Solicitar Turno");
        JButton btnCancelarTurno = new JButton("Cancelar Turno");
        JButton btnCerrarSesion = new JButton("Cerrar Sesión");

        panelOpciones.add(btnSolicitarTurno);
        panelOpciones.add(btnCancelarTurno);
        panelOpciones.add(btnCerrarSesion);

        add(panelOpciones, BorderLayout.EAST);

        // Tabla de turnos del paciente
        String[] columnas = {"ID", "Médico", "Fecha/Hora", "Estado", "Motivo"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaTurnos = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaTurnos);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 0));
        add(scrollPane, BorderLayout.CENTER);

        // Acciones de botones
        btnSolicitarTurno.addActionListener(e -> ventana.mostrarVista("panelAsignarTurno"));
        btnCancelarTurno.addActionListener(e -> cancelarTurnoSeleccionado());
        btnCerrarSesion.addActionListener(e -> cerrarSesion());
    }

    // Carga los turnos solo del paciente logueado
    public void cargarTurnosPaciente() {
        modeloTabla.setRowCount(0);

        try {
            ControladorIniciarSesion controladorSesion = ventana.getControladorIniciarSesion();
            Paciente pacienteActual = (Paciente) controladorSesion.getUsuarioActual();

            if (pacienteActual == null) {
                JOptionPane.showMessageDialog(this,
                        "Error: No hay un paciente con sesión iniciada.",
                        "Error de sesión",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            List<Turno> turnos = turnoRepo.obtenerPorIdPaciente(pacienteActual.getIdPaciente());

            for (Turno turno : turnos) {
                Medico medico = medicoRepo.obtenerPorId(turno.getIdMedico());
                String medicoNombre = (medico != null) ? medico.toString() : "N/A";

                modeloTabla.addRow(new Object[]{
                    turno.getIdTurno(),
                    medicoNombre,
                    turno.getFechaHora().toString(),
                    turno.getEstado().toString(),
                    turno.getMotivoConsulta()
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar los turnos: " + e.getMessage(),
                    "Error de Base de Datos",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // Cancela el turno seleccionado de la tabla
    private void cancelarTurnoSeleccionado() {
        int filaSeleccionada = tablaTurnos.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this,
                    "Seleccione un turno para cancelar.",
                    "Ningún turno seleccionado",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idTurno = (int) tablaTurnos.getModel().getValueAt(filaSeleccionada, 0);
        String medicoNombre = tablaTurnos.getModel().getValueAt(filaSeleccionada, 1).toString();
        String fecha = tablaTurnos.getModel().getValueAt(filaSeleccionada, 2).toString();

        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Desea cancelar el turno con el Dr. " + medicoNombre + "\nFecha: " + fecha + "?",
                "Confirmar cancelación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                ventana.getControladorTurno().cancelarTurno(idTurno);
                JOptionPane.showMessageDialog(this,
                        "Turno cancelado correctamente.",
                        "Cancelación exitosa",
                        JOptionPane.INFORMATION_MESSAGE);
                cargarTurnosPaciente(); // Recargar tabla
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Error al cancelar el turno:\n" + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Cierra la sesión actual
    private void cerrarSesion() {
        int opcion = JOptionPane.showConfirmDialog(
                ventana,
                "¿Está seguro que desea cerrar sesión?",
                "Confirmar cierre de sesión",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (opcion == JOptionPane.YES_OPTION) {
            ventana.getControladorIniciarSesion().cerrarSesion();
            ventana.mostrarVista("panelIniciarSesion");
        }
    }
}
