package com.mycompany.proyectointegrador.vista;

import com.mycompany.proyectointegrador.modelo.Medico;
import com.mycompany.proyectointegrador.modelo.Paciente;
import com.mycompany.proyectointegrador.modelo.Turno;
import com.mycompany.proyectointegrador.repositorios.MedicoRepositorio;
import com.mycompany.proyectointegrador.repositorios.PacienteRepositorio;
import com.mycompany.proyectointegrador.repositorios.TurnoRepositorio;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class PanelRecepcionista extends JPanel {

    private final VentanaPrincipal ventana;
    private JTable tablaTurnos;
    private DefaultTableModel modeloTabla;
    private final TurnoRepositorio turnoRepo = new TurnoRepositorio();
    private final MedicoRepositorio medicoRepo = new MedicoRepositorio();
    private final PacienteRepositorio pacienteRepo = new PacienteRepositorio();

    public PanelRecepcionista(VentanaPrincipal ventana) {
        this.ventana = ventana;

        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(245, 245, 245)); // Fondo suave

        // Título
        JLabel titulo = new JLabel("Panel de Recepcionista", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titulo.setBorder(BorderFactory.createEmptyBorder(30, 0, 20, 0));
        add(titulo, BorderLayout.NORTH);

        // Panel lateral de botones
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

        JButton btnRegistrarPaciente = new JButton("Registrar Paciente");
        JButton btnAsignarTurno = new JButton("Asignar Turno");
        JButton btnCancelarTurno = new JButton("Cancelar Turno");
        JButton btnReprogramarTurno = new JButton("Reprogramar Turno");
        JButton btnVerListados = new JButton("Ver Directorio");
        JButton btnCerrarSesion = new JButton("Cerrar Sesión");

        JButton[] botones = {btnRegistrarPaciente, btnAsignarTurno, btnCancelarTurno,
                btnReprogramarTurno, btnVerListados, btnCerrarSesion};
        Color[] colores = {new Color(33, 150, 243), new Color(33, 150, 243),
                new Color(244, 67, 54), new Color(255, 152, 0), new Color(76, 175, 80),
                new Color(158, 158, 158)};

        for (int i = 0; i < botones.length; i++) {
            botones[i].setBackground(colores[i]);
            botones[i].setForeground(Color.WHITE);
            botones[i].setFont(new Font("Segoe UI", Font.BOLD, 14));
            gbc.gridy = i;
            panelOpciones.add(botones[i], gbc);
        }

        add(panelOpciones, BorderLayout.EAST);

        // Tabla de turnos
        String[] columnas = {"ID", "Paciente", "Médico", "Fecha/Hora", "Estado", "Motivo"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaTurnos = new JTable(modeloTabla);
        tablaTurnos.setFillsViewportHeight(true);
        tablaTurnos.setRowHeight(28);
        tablaTurnos.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tablaTurnos.setSelectionBackground(new Color(33, 150, 243));
        tablaTurnos.setSelectionForeground(Color.WHITE);
        tablaTurnos.setGridColor(new Color(220, 220, 220));

        // Encabezado
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
        btnRegistrarPaciente.addActionListener(e -> ventana.mostrarVista("panelRegistro"));
        btnAsignarTurno.addActionListener(e -> ventana.mostrarVista("panelAsignarTurno"));
        btnCancelarTurno.addActionListener(e -> cancelarTurnoSeleccionado());
        btnReprogramarTurno.addActionListener(e -> reprogramarTurnoSeleccionado());
        btnVerListados.addActionListener(e -> ventana.mostrarVista("panelListados"));
        btnCerrarSesion.addActionListener(e -> cerrarSesion());
    }

    public void cargarTurnos() {
        modeloTabla.setRowCount(0);
        try {
            List<Turno> turnos = turnoRepo.obtenerTodos();
            for (Turno turno : turnos) {
                String pacienteNombre = "N/A";
                Paciente paciente = pacienteRepo.obtenerPorId(turno.getIdPaciente());
                if (paciente != null) pacienteNombre = paciente.obtenerNombreCompleto();

                String medicoNombre = "N/A";
                Medico medico = medicoRepo.obtenerPorId(turno.getIdMedico());
                if (medico != null) medicoNombre = medico.toString();

                modeloTabla.addRow(new Object[]{
                        turno.getIdTurno(),
                        pacienteNombre,
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
                    "Por favor, seleccione un turno de la tabla para cancelar.",
                    "Ningún turno seleccionado",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idTurno = (int) tablaTurnos.getModel().getValueAt(fila, 0);
        String pacienteNombre = tablaTurnos.getModel().getValueAt(fila, 1).toString();
        String fecha = tablaTurnos.getModel().getValueAt(fila, 3).toString();

        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de cancelar el turno de " + pacienteNombre + "\nPara: " + fecha + "?",
                "Confirmar cancelación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                ventana.getControladorTurno().cancelarTurno(idTurno);
                JOptionPane.showMessageDialog(this,
                        "Turno (ID: " + idTurno + ") cancelado correctamente.",
                        "Cancelación exitosa",
                        JOptionPane.INFORMATION_MESSAGE);
                cargarTurnos();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Error al cancelar el turno:\n" + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void reprogramarTurnoSeleccionado() {
        int fila = tablaTurnos.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this,
                    "Seleccione un turno para reprogramar.",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            int idTurno = (int) modeloTabla.getValueAt(fila, 0);
            Turno turno = turnoRepo.obtenerPorId(idTurno);
            if (turno == null) return;

            PanelReprogramarTurno panel = new PanelReprogramarTurno(ventana, turno, ventana.getControladorTurno());
            ventana.getContenedorVistas().add(panel, "panelReprogramarTurno");
            CardLayout cl = (CardLayout) ventana.getContenedorVistas().getLayout();
            cl.show(ventana.getContenedorVistas(), "panelReprogramarTurno");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "Error de base de datos: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cerrarSesion() {
        int opcion = JOptionPane.showConfirmDialog(ventana,
                "¿Está seguro que desea cerrar sesión?",
                "Confirmar cierre de sesión",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if (opcion == JOptionPane.YES_OPTION) {
            ventana.getControladorIniciarSesion().cerrarSesion();
            ventana.mostrarVista("panelIniciarSesion");
        }
    }
}
