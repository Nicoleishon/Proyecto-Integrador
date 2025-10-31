package com.mycompany.proyectointegrador.vista;

import com.mycompany.proyectointegrador.controlador.ControladorTurno;
import com.mycompany.proyectointegrador.modelo.*;
import com.mycompany.proyectointegrador.repositorios.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PanelAsignarTurno extends JPanel {

    private final VentanaPrincipal ventana;
    private final ControladorTurno controladorTurno = new ControladorTurno();
    private final PacienteRepositorio pacienteRepo = new PacienteRepositorio();
    private final MedicoRepositorio medicoRepo = new MedicoRepositorio();
    private Boolean esPaciente = null;
    private JPanel panelForm;

    private JTextField campoBuscarPaciente, campoMotivo;
    private JButton botonBuscarPaciente, botonAsignarTurno, botonVolver;
    private JComboBox<Especialidad> comboEspecialidades;
    private JComboBox<Medico> comboMedicos;
    private JLabel labelPacienteEncontrado;
    private JTable tablaHorarios;
    private DefaultTableModel modeloTabla;
    private JSpinner spinnerFecha;

    private Paciente pacienteSeleccionado;
    private Medico medicoSeleccionado;
    private LocalDate fechaSeleccionada;
    private List<LocalDate> fechasDisponibles;

    public PanelAsignarTurno(VentanaPrincipal ventana) {
        this.ventana = ventana;
        this.esPaciente = ventana.getControladorIniciarSesion().esPaciente();
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Asignar Nuevo Turno", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        add(titulo, BorderLayout.NORTH);

        crearPanelFormulario();
        crearTablaHorarios();
        crearPanelBotones();

        spinnerFecha.setModel(new SpinnerListModel(List.of(LocalDate.now())));
        fechaSeleccionada = LocalDate.now();
        spinnerFecha.setValue(fechaSeleccionada);

        configurarAcciones();
        inicializarPaciente();
    }

    private void crearPanelFormulario() {
        panelForm = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Buscar paciente
        gbc.gridx = 0; gbc.gridy = 0;
        panelForm.add(new JLabel("Buscar Paciente (ID o DNI):"), gbc);
        campoBuscarPaciente = new JTextField(15);
        gbc.gridx = 1;
        panelForm.add(campoBuscarPaciente, gbc);
        botonBuscarPaciente = new JButton("Buscar");
        gbc.gridx = 2;
        panelForm.add(botonBuscarPaciente, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 3;
        labelPacienteEncontrado = new JLabel("Paciente: (Ninguno seleccionado)");
        labelPacienteEncontrado.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        panelForm.add(labelPacienteEncontrado, gbc);
        gbc.gridwidth = 1;

        // Especialidad y médico
        gbc.gridx = 0; gbc.gridy = 2;
        panelForm.add(new JLabel("Especialidad:"), gbc);
        comboEspecialidades = new JComboBox<>(Especialidad.values());
        gbc.gridx = 1; gbc.gridwidth = 2;
        panelForm.add(comboEspecialidades, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        panelForm.add(new JLabel("Médico:"), gbc);
        comboMedicos = new JComboBox<>();
        gbc.gridx = 1; gbc.gridwidth = 2;
        panelForm.add(comboMedicos, gbc);
        gbc.gridwidth = 1;

        // Fecha
        gbc.gridx = 0; gbc.gridy = 4;
        panelForm.add(new JLabel("Fecha:"), gbc);
        spinnerFecha = new JSpinner(new SpinnerListModel(List.of(LocalDate.now())));
        gbc.gridx = 1; gbc.gridwidth = 2;
        panelForm.add(spinnerFecha, gbc);
        gbc.gridwidth = 1;

        // Motivo
        gbc.gridx = 0; gbc.gridy = 5;
        panelForm.add(new JLabel("Motivo:"), gbc);
        campoMotivo = new JTextField();
        gbc.gridx = 1; gbc.gridwidth = 2;
        panelForm.add(campoMotivo, gbc);
        gbc.gridwidth = 1;

        add(panelForm, BorderLayout.NORTH);
    }

    private void crearTablaHorarios() {
        modeloTabla = new DefaultTableModel(new Object[]{"Horario Disponible"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tablaHorarios = new JTable(modeloTabla);
        add(new JScrollPane(tablaHorarios), BorderLayout.CENTER);
    }

    private void crearPanelBotones() {
        JPanel panelBotones = new JPanel();
        botonAsignarTurno = new JButton("Asignar Turno");
        botonVolver = new JButton("Volver");
        panelBotones.add(botonAsignarTurno);
        panelBotones.add(botonVolver);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private void configurarAcciones() {
        botonVolver.addActionListener(e -> {
            limpiarCampos();
            if (esPaciente) {
                ventana.mostrarVista("panelPaciente");
            } else {
                ventana.mostrarVista("panelRecepcionista");
            }            
        });

        botonBuscarPaciente.addActionListener(e -> buscarPaciente());

        comboEspecialidades.addActionListener(e -> filtrarMedicosPorEspecialidad());

        comboMedicos.addActionListener(e -> {
            medicoSeleccionado = (Medico) comboMedicos.getSelectedItem();
            actualizarFechasDisponibles();
            actualizarTablaHorarios();
        });

        spinnerFecha.addChangeListener(e -> {
            if (spinnerFecha.getValue() instanceof LocalDate) {
                fechaSeleccionada = (LocalDate) spinnerFecha.getValue();
                actualizarTablaHorarios();
            }
        });

        botonAsignarTurno.addActionListener(e -> asignarTurno());
    }

    private void buscarPaciente() {
        String busqueda = campoBuscarPaciente.getText().trim();
        if (busqueda.isEmpty()) return;

        pacienteSeleccionado = null;

        try {
            pacienteSeleccionado = pacienteRepo.obtenerPorDni(busqueda);
            if (pacienteSeleccionado == null) {
                try {
                    int idPaciente = Integer.parseInt(busqueda);
                    pacienteSeleccionado = pacienteRepo.obtenerPorId(idPaciente);
                } catch (NumberFormatException ignored) {}
            }

            if (pacienteSeleccionado != null) {
                labelPacienteEncontrado.setText("Paciente: " + pacienteSeleccionado.getNombre() + " " + pacienteSeleccionado.getApellido());
                labelPacienteEncontrado.setForeground(Color.BLACK);
            } else {
                labelPacienteEncontrado.setText("Paciente no encontrado");
                labelPacienteEncontrado.setForeground(Color.RED);
            }
        } catch (SQLException ex) {
            labelPacienteEncontrado.setText("Error de base de datos");
            labelPacienteEncontrado.setForeground(Color.RED);
        }
    }

    private void filtrarMedicosPorEspecialidad() {
        Especialidad especialidad = (Especialidad) comboEspecialidades.getSelectedItem();
        comboMedicos.removeAllItems();
        try {
            List<Medico> medicos = medicoRepo.buscarMedicoPorEspecialidad(especialidad);
            for (Medico m : medicos) comboMedicos.addItem(m);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar médicos: " + e.getMessage());
        }
    }

    private void actualizarFechasDisponibles() {
        if (medicoSeleccionado == null) {
            spinnerFecha.setModel(new SpinnerListModel(List.of(LocalDate.now())));
            return;
        }

        try {
            List<DiaSemana> dias = controladorTurno.obtenerDiasLaboralesMedico(medicoSeleccionado.getIdMedico());
            fechasDisponibles = new ArrayList<>();
            LocalDate hoy = LocalDate.now();

            for (int i = 0; i < 21; i++) {
                LocalDate fecha = hoy.plusDays(i);
                if (dias.contains(controladorTurno.convertirADiaSemana(fecha))) {
                    fechasDisponibles.add(fecha);
                }
            }

            if (fechasDisponibles.isEmpty()) {
                fechasDisponibles.add(LocalDate.now());
            }

            spinnerFecha.setModel(new SpinnerListModel(fechasDisponibles));
            fechaSeleccionada = fechasDisponibles.get(0);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar fechas: " + ex.getMessage());
            spinnerFecha.setModel(new SpinnerListModel(List.of(LocalDate.now())));
            fechaSeleccionada = LocalDate.now();
        }
    }


    private void actualizarTablaHorarios() {
        modeloTabla.setRowCount(0);
        if (medicoSeleccionado == null || fechaSeleccionada == null) return;

        try {
            List<LocalTime> horarios = controladorTurno.obtenerHorariosDisponibles(
                    medicoSeleccionado.getIdMedico(), fechaSeleccionada
            );
            for (LocalTime h : horarios) {
                modeloTabla.addRow(new Object[]{h.toString()});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al obtener horarios: " + e.getMessage());
        }
    }

    private void asignarTurno() {
        int filaSeleccionada = tablaHorarios.getSelectedRow();
        if (pacienteSeleccionado == null || medicoSeleccionado == null || filaSeleccionada < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione paciente, médico y horario.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        LocalTime hora = LocalTime.parse(modeloTabla.getValueAt(filaSeleccionada, 0).toString());
        LocalDateTime fechaHora = LocalDateTime.of(fechaSeleccionada, hora);
        String motivo = campoMotivo.getText();

        try {
            controladorTurno.asignarTurno(pacienteSeleccionado, medicoSeleccionado, fechaHora, motivo);
            JOptionPane.showMessageDialog(this, "Turno asignado con éxito.");
            
            limpiarCampos();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al asignar turno: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void limpiarCampos() {
        campoBuscarPaciente.setText("");
        campoMotivo.setText("");
        if (!esPaciente){
            labelPacienteEncontrado.setText("Paciente: (Ninguno seleccionado)");
            labelPacienteEncontrado.setForeground(Color.BLACK);
            pacienteSeleccionado = null;
        }        
        comboEspecialidades.setSelectedIndex(0);
        comboMedicos.removeAllItems();
        modeloTabla.setRowCount(0);
        spinnerFecha.setModel(new SpinnerListModel(List.of(LocalDate.now())));        
        medicoSeleccionado = null;
        fechaSeleccionada = LocalDate.now();
    }

    public void setFechaSeleccionada(LocalDate fecha) {
        this.fechaSeleccionada = fecha;
        actualizarTablaHorarios();
    }
    
    private void inicializarPaciente() {
        if (esPaciente) {
            pacienteSeleccionado = (Paciente) ventana.getControladorIniciarSesion().getUsuarioActual();
            labelPacienteEncontrado.setText("Paciente: " + pacienteSeleccionado.getNombre() + " " + pacienteSeleccionado.getApellido());
            labelPacienteEncontrado.setForeground(Color.BLACK);

            // Ocultar búsqueda de paciente
            campoBuscarPaciente.setVisible(false);
            botonBuscarPaciente.setVisible(false);
            // Ocultar la etiqueta "Buscar Paciente (ID o DNI):"
            panelForm.getComponent(0).setVisible(false);

            // Cambiar texto de título y botón
            Component[] comps = getComponents();
            for (Component c : comps) {
                if (c instanceof JLabel) {
                    JLabel lbl = (JLabel) c;
                    if (lbl.getText().equals("Asignar Nuevo Turno")) {
                        lbl.setText("Solicitar Turno");
                        break;
                    }
                }
            }
            botonAsignarTurno.setText("Solicitar Turno");
        }
    }


    
}
