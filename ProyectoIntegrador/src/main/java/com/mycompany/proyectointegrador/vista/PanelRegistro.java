package com.mycompany.proyectointegrador.vista;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class PanelRegistro extends JPanel {

    private final VentanaPrincipal ventana;
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel panelPrincipal = new JPanel(cardLayout);
    private final JButton botonSiguiente = new JButton("Siguiente");
    private final JButton botonAtras = new JButton("Atrás");
    private final JButton botonRegistrar = new JButton("Registrar");
    private int pasoActual = 0;
    private List<JPanel> pasos; // Cada paso contiene un grupo de campos

    // Campos de entrada
    private JTextField campoUsuario, campoNombre, campoApellido, campoDireccion, campoTelefono, campoDni, campoFechaNacimiento;
    private JPasswordField campoContraseña, campoConfirmar;

    public PanelRegistro(VentanaPrincipal ventana) {
        this.ventana = ventana;
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 245));

        JLabel titulo = new JLabel("Registro de Usuario", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titulo, BorderLayout.NORTH);

        panelPrincipal.setBackground(Color.WHITE);
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        add(panelPrincipal, BorderLayout.CENTER);

        inicializarCampos();
        generarPasosDinamicos();
        configurarBotonera();

        cardLayout.show(panelPrincipal, "paso0");
    }

    private void inicializarCampos() {
        campoUsuario = new JTextField(20);
        campoContraseña = new JPasswordField(20);
        campoConfirmar = new JPasswordField(20);
        campoNombre = new JTextField(20);
        campoApellido = new JTextField(20);
        campoFechaNacimiento = new JTextField(20);
        campoDireccion = new JTextField(20);
        campoTelefono = new JTextField(20);
        campoDni = new JTextField(20);
    }

    private void generarPasosDinamicos() {
        pasos = new ArrayList<>();

        // Lista completa de etiquetas y campos
        String[] etiquetas = {
                "Usuario:", "Contraseña:", "Confirmar Contraseña:",
                "Nombre:", "Apellido:", "Fecha Nacimiento (yyyy-MM-dd):",
                "Dirección:", "Teléfono:", "DNI:"
        };

        JComponent[] campos = {
                campoUsuario, campoContraseña, campoConfirmar,
                campoNombre, campoApellido, campoFechaNacimiento,
                campoDireccion, campoTelefono, campoDni
        };

        // Determinar cantidad de campos por paso según tamaño disponible
        int camposPorPaso = (getToolkit().getScreenSize().height < 700) ? 3 : 5;

        for (int i = 0; i < etiquetas.length; i += camposPorPaso) {
            JPanel panel = new JPanel(new GridBagLayout());
            panel.setBackground(Color.WHITE);
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.weightx = 1.0;

            for (int j = 0; j < camposPorPaso && (i + j) < etiquetas.length; j++) {
                gbc.gridx = 0;
                gbc.gridy = j;
                panel.add(new JLabel(etiquetas[i + j]), gbc);
                gbc.gridx = 1;
                panel.add(campos[i + j], gbc);
            }

            pasos.add(panel);
            panelPrincipal.add(panel, "paso" + (pasos.size() - 1));
        }
    }

    private void configurarBotonera() {
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBotones.setBackground(new Color(245, 245, 245));

        JButton botonVolver = new JButton("Volver");
        botonSiguiente.setBackground(new Color(33, 150, 243));
        botonSiguiente.setForeground(Color.WHITE);
        botonAtras.setBackground(new Color(158, 158, 158));
        botonAtras.setForeground(Color.WHITE);
        botonRegistrar.setBackground(new Color(76, 175, 80));
        botonRegistrar.setForeground(Color.WHITE);

        botonRegistrar.setEnabled(false);
        botonAtras.setEnabled(false);

        botonSiguiente.addActionListener(e -> avanzar());
        botonAtras.addActionListener(e -> retroceder());
        botonRegistrar.addActionListener(e -> registrar());
        botonVolver.addActionListener(e -> ventana.mostrarVista("login"));

        panelBotones.add(botonAtras);
        panelBotones.add(botonSiguiente);
        panelBotones.add(botonRegistrar);
        panelBotones.add(botonVolver);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private void avanzar() {
        if (!validarCampos(pasoActual)) return;

        if (pasoActual < pasos.size() - 1) {
            pasoActual++;
            cardLayout.show(panelPrincipal, "paso" + pasoActual);
            botonAtras.setEnabled(true);
        }

        if (pasoActual == pasos.size() - 1) {
            botonSiguiente.setEnabled(false);
            botonRegistrar.setEnabled(true);
        }
    }

    private void retroceder() {
        if (pasoActual > 0) {
            pasoActual--;
            cardLayout.show(panelPrincipal, "paso" + pasoActual);
            botonSiguiente.setEnabled(true);
            botonRegistrar.setEnabled(false);
        }

        if (pasoActual == 0) botonAtras.setEnabled(false);
    }

    private boolean validarCampos(int paso) {
        Component[] comps = pasos.get(paso).getComponents();
        for (Component c : comps) {
            if (c instanceof JTextField && ((JTextField) c).getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Complete todos los campos visibles antes de continuar.",
                        "Campos incompletos", JOptionPane.WARNING_MESSAGE);
                return false;
            }
        }
        return true;
    }

    private void registrar() {
        try {
            LocalDate.parse(campoFechaNacimiento.getText().trim());
            JOptionPane.showMessageDialog(this,
                    "Usuario registrado correctamente: " + campoUsuario.getText(),
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);

            limpiarCampos();
            pasoActual = 0;
            cardLayout.show(panelPrincipal, "paso0");
            botonAtras.setEnabled(false);
            botonRegistrar.setEnabled(false);
            botonSiguiente.setEnabled(true);
            // Redirigir automáticamente al login
            ventana.mostrarVista("login");

        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this,
                    "Formato de fecha incorrecto. Use yyyy-MM-dd",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCampos() {
        for (JTextField campo : new JTextField[]{
                campoUsuario, campoNombre, campoApellido, campoFechaNacimiento,
                campoDireccion, campoTelefono, campoDni
        }) {
            campo.setText("");
        }
        campoContraseña.setText("");
        campoConfirmar.setText("");
    }
}
