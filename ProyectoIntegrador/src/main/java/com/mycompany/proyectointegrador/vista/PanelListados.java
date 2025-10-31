package com.mycompany.proyectointegrador.vista;

import com.mycompany.proyectointegrador.modelo.*;
import com.mycompany.proyectointegrador.repositorios.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PanelListados extends JPanel {

    private final VentanaPrincipal ventana;

    
    private JTable tablaPacientes;
    private DefaultTableModel modeloPacientes;
    private JTable tablaMedicos;
    private DefaultTableModel modeloMedicos;
    
    
    private final PacienteRepositorio pacienteRepo = new PacienteRepositorio();
    private final MedicoRepositorio medicoRepo = new MedicoRepositorio();

    public PanelListados(VentanaPrincipal ventana) {
        this.ventana = ventana;
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        
        JLabel titulo = new JLabel("Directorio del Hospital", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(titulo, BorderLayout.NORTH);

        
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        
        
        tabbedPane.addTab("Pacientes", crearPanelPacientes());
        
        
        tabbedPane.addTab("Médicos", crearPanelMedicos());

        add(tabbedPane, BorderLayout.CENTER);

        
        JPanel panelSur = new JPanel();
        panelSur.setBackground(Color.WHITE);
        JButton btnVolver = new JButton("Volver");
        panelSur.add(btnVolver);
        add(panelSur, BorderLayout.SOUTH);

        
        btnVolver.addActionListener(e -> {
            ventana.mostrarVista("panelRecepcionista");
        });
    }
    
    
    private JScrollPane crearPanelPacientes() {
        String[] columnas = {"ID", "Nombre", "Apellido", "DNI", "Teléfono"};
        modeloPacientes = new DefaultTableModel(columnas, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        tablaPacientes = new JTable(modeloPacientes);
        return new JScrollPane(tablaPacientes);
    }

    private JScrollPane crearPanelMedicos() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] columnas = {"ID", "Nombre", "Apellido", "Especialidad", "Matrícula"};
        modeloMedicos = new DefaultTableModel(columnas, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        tablaMedicos = new JTable(modeloMedicos);
        return new JScrollPane(tablaMedicos);
    }

    
    public void cargarListas() {
        
        modeloPacientes.setRowCount(0);
        modeloMedicos.setRowCount(0);

        try {
            
            List<Paciente> pacientes = pacienteRepo.obtenerTodos(); 
            for (Paciente p : pacientes) {
                modeloPacientes.addRow(new Object[]{
                    p.getIdPaciente(),
                    p.getNombre(),
                    p.getApellido(),
                    p.getDni(),
                    p.getTelefono()
                });
            }

           
            List<Medico> medicos = medicoRepo.obtenerTodos(); 
            for (Medico m : medicos) {
                modeloMedicos.addRow(new Object[]{
                    m.getIdMedico(),
                    m.getNombre(),
                    m.getApellido(),
                    m.getEspecialidad(), //
                    m.getMatricula()
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar los listados: " + e.getMessage(),
                    "Error de Base de Datos",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}