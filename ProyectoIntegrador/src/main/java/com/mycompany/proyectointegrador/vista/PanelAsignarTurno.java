package com.mycompany.proyectointegrador.vista;

import com.mycompany.proyectointegrador.modelo.*;
import com.mycompany.proyectointegrador.repositorios.*;
import com.mycompany.proyectointegrador.repositorios.MedicoRepositorio;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class PanelAsignarTurno extends JPanel {

    private final VentanaPrincipal ventana;
    private final PacienteRepositorio pacienteRepo = new PacienteRepositorio();
    private final MedicoRepositorio medicoRepo = new MedicoRepositorio();
    private final TurnoRepositorio turnoRepo = new TurnoRepositorio();

    
    private JTextField campoBuscarPaciente, campoFechaHora, campoMotivo;
    private JButton botonBuscarPaciente, botonAsignarTurno, botonVolver;
    private JComboBox<Especialidad> comboEspecialidades;
    private JComboBox<Medico> comboMedicos;
    private JLabel labelPacienteEncontrado;

    // modelos
    private Paciente pacienteSeleccionado;
    private Medico medicoSeleccionado;

    public PanelAsignarTurno(VentanaPrincipal ventana) {
        this.ventana = ventana;
        setLayout(new BorderLayout());
        
        JLabel titulo = new JLabel("Asignar Nuevo Turno", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        add(titulo, BorderLayout.NORTH);

        // Panel principal con GridBagLayout
        JPanel panelForm = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // buscar paciente
        gbc.gridx = 0; gbc.gridy = 0;
        panelForm.add(new JLabel("Buscar Paciente (por ID o DNI):"), gbc);
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
        gbc.gridwidth = 1; // Reset

        // buscar médico
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

        // datos del turno
        gbc.gridx = 0; gbc.gridy = 4;
        panelForm.add(new JLabel("Fecha y Hora:"), gbc);
        campoFechaHora = new JTextField("2025-11-20T10:00");
        gbc.gridx = 1; gbc.gridwidth = 2;
        panelForm.add(campoFechaHora, gbc);
        
        gbc.gridx = 0; gbc.gridy = 5;
        panelForm.add(new JLabel("Motivo de Consulta:"), gbc);
        campoMotivo = new JTextField();
        gbc.gridx = 1; gbc.gridwidth = 2;
        panelForm.add(campoMotivo, gbc);

        add(panelForm, BorderLayout.CENTER);

        // botones
        JPanel panelBotones = new JPanel();
        botonAsignarTurno = new JButton("Asignar Turno");
        botonVolver = new JButton("Volver");
        panelBotones.add(botonAsignarTurno);
        panelBotones.add(botonVolver);
        add(panelBotones, BorderLayout.SOUTH);

        configurarAcciones();
    }

    private void configurarAcciones() {
        botonVolver.addActionListener(e -> {
            limpiarCampos();
            ventana.mostrarVista("panelRecepcionista");
        });

        botonBuscarPaciente.addActionListener(e -> buscarPaciente());
        
        // se filtran los médicos según especialidad
        comboEspecialidades.addActionListener(e -> filtrarMedicosPorEspecialidad());

        // cuando cambia el médico, se guarda
        comboMedicos.addActionListener(e -> {
            medicoSeleccionado = (Medico) comboMedicos.getSelectedItem();
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
            } catch (NumberFormatException e) {
                
            }
        }
       
        if (pacienteSeleccionado != null) {
            labelPacienteEncontrado.setText("Paciente: " + pacienteSeleccionado.getNombre() + " " + pacienteSeleccionado.getApellido() + " (ID: " + pacienteSeleccionado.getIdPaciente() + ")");
            labelPacienteEncontrado.setForeground(Color.BLACK);
            
            System.out.println("Paciente encontrado: " + pacienteSeleccionado.getNombreCompleto() + " DNI: " + pacienteSeleccionado.getDni());
        } else {
            labelPacienteEncontrado.setText("Paciente no encontrado (ID o DNI: " + busqueda + ")");
            labelPacienteEncontrado.setForeground(Color.RED);
            System.out.println("Búsqueda fallida para: " + busqueda);
        }
        
    } catch (SQLException ex) {
        labelPacienteEncontrado.setText("Error de base de datos.");
        labelPacienteEncontrado.setForeground(Color.RED);
        System.err.println("Error SQL al buscar paciente: " + ex.getMessage());
    }
}

        private void filtrarMedicosPorEspecialidad() {
        Especialidad especialidad = (Especialidad) comboEspecialidades.getSelectedItem();
        comboMedicos.removeAllItems(); 

        try {
            List<Medico> medicoPorEspecialidad = medicoRepo.buscarMedicoPorEspecialidad(especialidad);

            for (Medico medico : medicoPorEspecialidad) {
                comboMedicos.addItem(medico); 
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar médicos: " + e.getMessage());
        }
    }

    private void asignarTurno() {
        try {
            
            if (pacienteSeleccionado == null) {
                JOptionPane.showMessageDialog(this, "Se debe seleccionar un paciente.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (medicoSeleccionado == null) {
                medicoSeleccionado = (Medico) comboMedicos.getSelectedItem(); 
                if (medicoSeleccionado == null) {
                    JOptionPane.showMessageDialog(this, "Se debe seleccionar un médico.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            
            LocalDateTime fechaHora = LocalDateTime.parse(campoFechaHora.getText());
            String motivo = campoMotivo.getText();

            Turno nuevoTurno = new Turno(fechaHora, motivo);
            nuevoTurno.setPaciente(pacienteSeleccionado);
            nuevoTurno.setMedico(medicoSeleccionado);
            // estado por defecto: "PENDIENTE"
            
            turnoRepo.crear(nuevoTurno);

            JOptionPane.showMessageDialog(this, "Turno asignado con éxito. ID Turno: " + nuevoTurno.getIdTurno());
            limpiarCampos();
            ventana.mostrarVista("panelRecepcionista");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al asignar turno: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void limpiarCampos() {
        
        campoBuscarPaciente.setText("");
        campoFechaHora.setText("2025-11-20T10:00"); 
        campoMotivo.setText("");
        
        labelPacienteEncontrado.setText("Paciente: (Ninguno seleccionado)");
        labelPacienteEncontrado.setForeground(Color.BLACK); 
        
        comboEspecialidades.setSelectedIndex(0); 
        comboMedicos.removeAllItems();

        pacienteSeleccionado = null;
        medicoSeleccionado = null;
    }
}