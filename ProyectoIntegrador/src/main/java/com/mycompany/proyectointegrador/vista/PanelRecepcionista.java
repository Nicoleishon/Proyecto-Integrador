package com.mycompany.proyectointegrador.vista;

import com.mycompany.proyectointegrador.controlador.ControladorIniciarSesion;
import com.mycompany.proyectointegrador.modelo.Medico;
import com.mycompany.proyectointegrador.modelo.Paciente;
import com.mycompany.proyectointegrador.modelo.Recepcionista;
import com.mycompany.proyectointegrador.modelo.Turno;
import com.mycompany.proyectointegrador.repositorios.MedicoRepositorio;
import com.mycompany.proyectointegrador.repositorios.PacienteRepositorio;
import com.mycompany.proyectointegrador.repositorios.TurnoRepositorio;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.time.*;
import javax.swing.table.DefaultTableModel;
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

        setLayout(new BorderLayout(10, 10)); // Nuevo BorderLayout
        setBackground(Color.WHITE);

        JLabel titulo = new JLabel("Panel de Recepcionista", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(titulo, BorderLayout.NORTH);

        JPanel panelOpciones = new JPanel(new GridLayout(6, 1, 10, 10));
        panelOpciones.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20)); // Padding
        panelOpciones.setBackground(Color.WHITE);

        JButton btnRegistrarPaciente = new JButton("Registrar Paciente");
        JButton btnAsignarTurno = new JButton("Asignar Turno");
        JButton btnCancelarTurno = new JButton("Cancelar Turno");
        JButton btnReprogramarTurno = new JButton("Reprogramar Turno");
        JButton btnVerListados = new JButton("Ver Directorio");
        JButton btnCerrarSesion = new JButton("Cerrar Sesión");

        panelOpciones.add(btnRegistrarPaciente);
        panelOpciones.add(btnAsignarTurno);
        panelOpciones.add(btnCancelarTurno);
        panelOpciones.add(btnReprogramarTurno);
        panelOpciones.add(btnVerListados);
        panelOpciones.add(btnCerrarSesion);

        add(panelOpciones, BorderLayout.EAST); 
        
        String[] columnas = {"ID", "Paciente", "Médico", "Fecha/Hora", "Estado", "Motivo"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hace que la tabla no sea editable
            }
        };
        tablaTurnos = new JTable(modeloTabla);
        
        JScrollPane scrollPane = new JScrollPane(tablaTurnos);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 0)); // Padding
        add(scrollPane, BorderLayout.CENTER);
       
        btnRegistrarPaciente.addActionListener(e -> {
            ventana.mostrarVista("panelRegistro");
        });

        btnAsignarTurno.addActionListener(e -> 
            ventana.mostrarVista("panelAsignarTurno"));

        
        btnCancelarTurno.addActionListener(e -> cancelarTurnoSeleccionado());


        btnReprogramarTurno.addActionListener(e -> {
           int filaSeleccionada = tablaTurnos.getSelectedRow();
           if (filaSeleccionada == -1) {
               JOptionPane.showMessageDialog(this, "Seleccione un turno para reprogramar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
               return;
           }

           try {
               int idTurno = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
               Turno turno = turnoRepo.obtenerPorId(idTurno);
               if (turno == null) {
                   JOptionPane.showMessageDialog(this, "No se encontró el turno seleccionado.", "Error", JOptionPane.ERROR_MESSAGE);
                   return;
               }

               Medico medico = medicoRepo.obtenerPorId(turno.getIdMedico());
               if (medico == null) {
                   JOptionPane.showMessageDialog(this, "No se encontró el médico asociado al turno.", "Error", JOptionPane.ERROR_MESSAGE);
                   return;
               }

               List<LocalDate> fechasDisponibles = ventana.getControladorTurno().obtenerDiasLaboralesDisponibles(medico.getIdMedico());
               if (fechasDisponibles.isEmpty()) {
                   JOptionPane.showMessageDialog(this, "El médico no tiene días laborales disponibles.", "Error", JOptionPane.ERROR_MESSAGE);
                   return;
               }

               LocalDate nuevaFecha = (LocalDate) JOptionPane.showInputDialog(
                       this,
                       "Seleccione nueva fecha:",
                       "Reprogramar Turno",
                       JOptionPane.QUESTION_MESSAGE,
                       null,
                       fechasDisponibles.toArray(),
                       turno.getFechaHora().toLocalDate()
               );

               if (nuevaFecha == null) return;

               List<LocalTime> horariosDisponibles = ventana.getControladorTurno().obtenerHorariosDisponibles(medico.getIdMedico(), nuevaFecha);
               horariosDisponibles.remove(turno.getFechaHora().toLocalTime()); // evitar mismo horario

               if (horariosDisponibles.isEmpty()) {
                   JOptionPane.showMessageDialog(this, "No hay horarios disponibles en esa fecha.", "Error", JOptionPane.ERROR_MESSAGE);
                   return;
               }

               LocalTime nuevaHora = (LocalTime) JOptionPane.showInputDialog(
                       this,
                       "Seleccione nueva hora:",
                       "Reprogramar Turno",
                       JOptionPane.QUESTION_MESSAGE,
                       null,
                       horariosDisponibles.toArray(),
                       turno.getFechaHora().toLocalTime()
               );

               if (nuevaHora == null) return;

               ventana.getControladorTurno().reprogramarTurno(turno, LocalDateTime.of(nuevaFecha, nuevaHora));
               JOptionPane.showMessageDialog(this, "Turno reprogramado correctamente.");
               cargarTurnos();

           } catch (SQLException ex) {
               JOptionPane.showMessageDialog(this, "Error de base de datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
           } catch (Exception ex) {
               JOptionPane.showMessageDialog(this, "Error al reprogramar turno: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
           }
       });

        
        btnVerListados.addActionListener(e -> 
            ventana.mostrarVista("panelListados"));


        btnCerrarSesion.addActionListener(e -> {
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
        });
    }
    
    
    public void cargarTurnos() {
        
        modeloTabla.setRowCount(0);

        try {
            
            List<Turno> turnos = turnoRepo.obtenerTodos(); 

            for (Turno turno : turnos) {
                String pacienteNombre = "N/A";
                Paciente paciente = pacienteRepo.obtenerPorId(turno.getIdPaciente());
                Medico medico = medicoRepo.obtenerPorId(turno.getIdMedico());
                if (paciente != null) {
                    
                    pacienteNombre = paciente.obtenerNombreCompleto(); 
                }
                
                String medicoNombre = "N/A";
                if (medico != null) {
                    
                    medicoNombre = medico.toString();
                }

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
        
        int filaSeleccionada = tablaTurnos.getSelectedRow();

        
        if (filaSeleccionada == -1) { // -1 = ninguna fila seleccionada
            JOptionPane.showMessageDialog(this,
                    "Por favor, seleccione un turno de la tabla para cancelar.",
                    "Ningún turno seleccionado",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idTurno = (int) tablaTurnos.getModel().getValueAt(filaSeleccionada, 0);
        String pacienteNombre = tablaTurnos.getModel().getValueAt(filaSeleccionada, 1).toString();
        String fecha = tablaTurnos.getModel().getValueAt(filaSeleccionada, 3).toString();

        
        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de cancelar el turno de " + pacienteNombre + "\nPara: " + fecha + "?",
                "Confirmar cancelación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        // el usuario presiona sí
        if (confirmacion == JOptionPane.YES_OPTION) {
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
    
} 

