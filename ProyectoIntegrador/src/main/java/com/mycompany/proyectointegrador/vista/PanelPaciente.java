package com.mycompany.proyectointegrador.vista;

import com.mycompany.proyectointegrador.controlador.ControladorIniciarSesion;
import com.mycompany.proyectointegrador.modelo.Medico;
import com.mycompany.proyectointegrador.modelo.Paciente;
import com.mycompany.proyectointegrador.modelo.Turno;
import com.mycompany.proyectointegrador.repositorios.MedicoRepositorio;
import com.mycompany.proyectointegrador.repositorios.TurnoRepositorio;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PanelPaciente extends JPanel {

    private final VentanaPrincipal ventana;
    private JTable tablaTurnos;
    private DefaultTableModel modeloTabla;
    private final TurnoRepositorio turnoRepo = new TurnoRepositorio();
    private final MedicoRepositorio medicoRepo = new MedicoRepositorio();

    public PanelPaciente(VentanaPrincipal ventana) {
        this.ventana = ventana;

        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(245, 245, 245)); // Fondo suave

        // Título
        JLabel titulo = new JLabel("Panel del Paciente", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titulo.setBorder(BorderFactory.createEmptyBorder(30, 0, 20, 0));
        add(titulo, BorderLayout.NORTH);

        // Panel lateral con botones
        JPanel panelOpciones = new JPanel(new GridBagLayout());
        panelOpciones.setBackground(Color.WHITE);
        panelOpciones.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        JButton btnSolicitarTurno = crearBoton("Solicitar Turno", new Color(33, 150, 243));
        JButton btnCancelarTurno = crearBoton("Cancelar Turno", new Color(244, 67, 54));
        JButton btnCerrarSesion = crearBoton("Cerrar Sesión", new Color(158, 158, 158));

        JButton[] botones = {btnSolicitarTurno, btnCancelarTurno, btnCerrarSesion};
        for (int i = 0; i < botones.length; i++) {
            gbc.gridy = i;
            panelOpciones.add(botones[i], gbc);
        }

        add(panelOpciones, BorderLayout.EAST);

        // Tabla de turnos
        String[] columnas = {"ID", "Médico", "Fecha/Hora", "Estado", "Motivo"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tablaTurnos = new JTable(modeloTabla);
        tablaTurnos.setFillsViewportHeight(true);
        tablaTurnos.setRowHeight(28);
        tablaTurnos.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tablaTurnos.setSelectionBackground(new Color(33, 150, 243));
        tablaTurnos.setSelectionForeground(Color.WHITE);
        tablaTurnos.setGridColor(new Color(220, 220, 220));

        tablaTurnos.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tablaTurnos.getTableHeader().setBackground(new Color(33, 150, 243));
        tablaTurnos.getTableHeader().setForeground(Color.WHITE);
        tablaTurnos.getTableHeader().setReorderingAllowed(false);

        // Filas alternadas
        tablaTurnos.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    setBackground(row % 2 == 0 ? Color.WHITE : new Color(245, 245, 245));
                }
                return this;
            }
        });

        JScrollPane scrollPane = new JScrollPane(tablaTurnos);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        add(scrollPane, BorderLayout.CENTER);

        // Acciones de botones
        btnSolicitarTurno.addActionListener(e -> ventana.mostrarVista("panelAsignarTurno"));
        btnCancelarTurno.addActionListener(e -> cancelarTurnoSeleccionado());
        btnCerrarSesion.addActionListener(e -> cerrarSesion());
    }

    private JButton crearBoton(String texto, Color color) {
        JButton btn = new JButton(texto);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        return btn;
    }

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

    private void cancelarTurnoSeleccionado() {
        int fila = tablaTurnos.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this,
                    "Seleccione un turno para cancelar.",
                    "Ningún turno seleccionado",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idTurno = (int) tablaTurnos.getModel().getValueAt(fila, 0);
        String medicoNombre = tablaTurnos.getModel().getValueAt(fila, 1).toString();
        String fecha = tablaTurnos.getModel().getValueAt(fila, 2).toString();

        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Desea cancelar el turno con el Dr. " + medicoNombre + "\nFecha: " + fecha + "?",
                "Confirmar cancelación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                ventana.getControladorTurno().cancelarTurno(idTurno);
                JOptionPane.showMessageDialog(this,
                        "Turno cancelado correctamente.",
                        "Cancelación exitosa",
                        JOptionPane.INFORMATION_MESSAGE);
                cargarTurnosPaciente();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Error al cancelar el turno:\n" + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

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
