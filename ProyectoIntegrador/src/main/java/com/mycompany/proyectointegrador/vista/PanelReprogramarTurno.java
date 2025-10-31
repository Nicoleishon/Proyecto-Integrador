package com.mycompany.proyectointegrador.vista;

import com.mycompany.proyectointegrador.controlador.ControladorTurno;
import com.mycompany.proyectointegrador.modelo.Turno;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.util.List;

public class PanelReprogramarTurno extends JPanel {

    private final JComboBox<LocalDate> comboFechas;
    private final JComboBox<LocalTime> comboHoras;
    private final JButton btnReprogramar;
    private final JButton btnVolver;
    private final Turno turno;
    private final ControladorTurno controlador;
    private final VentanaPrincipal ventana;

    public PanelReprogramarTurno(VentanaPrincipal ventana, Turno turno, ControladorTurno controlador) throws SQLException {
        this.ventana = ventana;
        this.turno = turno;
        this.controlador = controlador;

        setLayout(new BorderLayout(20, 20));
        setBackground(new Color(245, 245, 245));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título
        JLabel titulo = new JLabel("Reprogramar Turno", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        add(titulo, BorderLayout.NORTH);

        // Panel central
        JPanel panelCentro = new JPanel(new GridBagLayout());
        panelCentro.setBackground(Color.WHITE);
        panelCentro.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
                BorderFactory.createEmptyBorder(20, 30, 20, 30)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Etiquetas y combos
        JLabel lblFecha = new JLabel("Nueva fecha:");
        JLabel lblHora = new JLabel("Nueva hora:");

        List<LocalDate> fechasDisponibles = controlador.obtenerDiasLaboralesDisponibles(turno.getIdMedico());
        comboFechas = new JComboBox<>(fechasDisponibles.toArray(new LocalDate[0]));
        comboFechas.setSelectedItem(turno.getFechaHora().toLocalDate());

        comboHoras = new JComboBox<>();
        actualizarHoras();

        comboFechas.addActionListener(e -> actualizarHoras());

        gbc.gridx = 0; gbc.gridy = 0;
        panelCentro.add(lblFecha, gbc);
        gbc.gridx = 1;
        panelCentro.add(comboFechas, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panelCentro.add(lblHora, gbc);
        gbc.gridx = 1;
        panelCentro.add(comboHoras, gbc);

        add(panelCentro, BorderLayout.CENTER);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBotones.setBackground(new Color(245, 245, 245));

        btnReprogramar = new JButton("Reprogramar");
        btnReprogramar.setBackground(new Color(33, 150, 243));
        btnReprogramar.setForeground(Color.WHITE);

        btnVolver = new JButton("Volver");
        btnVolver.setBackground(new Color(158, 158, 158));
        btnVolver.setForeground(Color.WHITE);

        btnReprogramar.addActionListener(e -> reprogramarTurno());
        btnVolver.addActionListener(e -> volverPanelRecepcionista());

        panelBotones.add(btnReprogramar);
        panelBotones.add(btnVolver);

        add(panelBotones, BorderLayout.SOUTH);
    }

    private void actualizarHoras() {
        try {
            LocalDate fecha = (LocalDate) comboFechas.getSelectedItem();
            List<LocalTime> horarios = controlador.obtenerHorariosDisponibles(turno.getIdMedico(), fecha);
            horarios.remove(turno.getFechaHora().toLocalTime());
            comboHoras.setModel(new DefaultComboBoxModel<>(horarios.toArray(new LocalTime[0])));
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar horarios: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void reprogramarTurno() {
        LocalDate fecha = (LocalDate) comboFechas.getSelectedItem();
        LocalTime hora = (LocalTime) comboHoras.getSelectedItem();

        if (fecha == null || hora == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar fecha y hora válidas.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            controlador.reprogramarTurno(turno, LocalDateTime.of(fecha, hora));
            JOptionPane.showMessageDialog(this, "Turno reprogramado correctamente.");
            volverPanelRecepcionista();
            ventana.getPanelRecepcionista().cargarTurnos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al reprogramar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void volverPanelRecepcionista() {
        CardLayout cl = (CardLayout) ventana.getContenedorVistas().getLayout();
        cl.show(ventana.getContenedorVistas(), "panelRecepcionista");
        ventana.getContenedorVistas().remove(this); // limpiar panel dinámico
    }
}
