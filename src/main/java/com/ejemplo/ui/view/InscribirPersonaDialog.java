package com.ejemplo.ui.view;

import com.ejemplo.Modelos.Personas.Estudiante;
import com.ejemplo.Modelos.Personas.Profesor;

import javax.swing.*;
import java.awt.*;

public class InscribirPersonaDialog extends JDialog {

    private JTextField txtNombre, txtApellidos, txtEmail;
    private JComboBox<String> cmbRol;
    private JPanel panelRol;
    private CardLayout cardLayout;

    public InscribirPersonaDialog(JFrame parent) {
        super(parent, "Inscribir Persona", true);
        setSize(450, 400);
        setLocationRelativeTo(parent);

        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout(10,10));
        getContentPane().add(mainPanel);

        // --- Datos básicos ---
        JPanel datosBasicos = new JPanel(new GridLayout(3, 2, 5, 5));
        datosBasicos.setBorder(BorderFactory.createTitledBorder("Datos básicos"));

        datosBasicos.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        datosBasicos.add(txtNombre);

        datosBasicos.add(new JLabel("Apellidos:"));
        txtApellidos = new JTextField();
        datosBasicos.add(txtApellidos);

        datosBasicos.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        datosBasicos.add(txtEmail);

        mainPanel.add(datosBasicos, BorderLayout.NORTH);

        // --- Panel central con rol + panel dinámico ---
        JPanel centro = new JPanel(new BorderLayout(5,5));

        // Selector de rol
        JPanel rolPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rolPanel.add(new JLabel("Rol:"));
        cmbRol = new JComboBox<>(new String[]{"Estudiante", "Profesor"});
        rolPanel.add(cmbRol);
        centro.add(rolPanel, BorderLayout.NORTH);

        // Panel dinámico según rol
        cardLayout = new CardLayout();
        panelRol = new JPanel(cardLayout);

        // Panel Estudiante
        JPanel estudiantePanel = new JPanel(new GridLayout(4, 2, 5, 5));
        estudiantePanel.setBorder(BorderFactory.createTitledBorder("Datos de Estudiante"));
        estudiantePanel.add(new JLabel("Código:"));
        estudiantePanel.add(new JTextField());
        estudiantePanel.add(new JLabel("Programa:"));
        estudiantePanel.add(new JTextField());
        estudiantePanel.add(new JLabel("Activo (sí/no):"));
        estudiantePanel.add(new JTextField());
        estudiantePanel.add(new JLabel("Promedio:"));
        estudiantePanel.add(new JTextField());

        // Panel Profesor
        JPanel profesorPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        profesorPanel.setBorder(BorderFactory.createTitledBorder("Datos de Profesor"));
        profesorPanel.add(new JLabel("Tipo de contrato:"));
        profesorPanel.add(new JTextField());

        // Añadir al CardLayout
        panelRol.add(estudiantePanel, "Estudiante");
        panelRol.add(profesorPanel, "Profesor");

        centro.add(panelRol, BorderLayout.CENTER);

        mainPanel.add(centro, BorderLayout.CENTER);

        // --- Botonera ---
        JPanel botonesPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnGuardar = new JButton("Guardar");
        JButton btnVolver = new JButton("Volver al menú principal");

        btnGuardar.addActionListener(e -> {
            String nombre = txtNombre.getText();
            String apellidos = txtApellidos.getText();
            String email = txtEmail.getText();
            String rol = (String) cmbRol.getSelectedItem();

            JOptionPane.showMessageDialog(
                    this,
                    "Datos guardados:\n" +
                            "Nombre: " + nombre + "\n" +
                            "Apellidos: " + apellidos + "\n" +
                            "Email: " + email + "\n" +
                            "Rol: " + rol,
                    "Confirmación",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });

        btnVolver.addActionListener(e -> dispose());

        botonesPanel.add(btnGuardar);
        botonesPanel.add(btnVolver);

        mainPanel.add(botonesPanel, BorderLayout.SOUTH);

        // --- Listener para cambiar entre roles ---
        cmbRol.addActionListener(e -> {
            String rol = (String) cmbRol.getSelectedItem();
            cardLayout.show(panelRol, rol);
        });

        // Mostrar por defecto "Estudiante"
        cardLayout.show(panelRol, "Estudiante");
    }

    // Para probarlo rápido
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame dummy = new JFrame();
            InscribirPersonaDialog dialog = new InscribirPersonaDialog(dummy);
            dialog.setVisible(true);
        });
    }
}



//nombre apellidos email
//si es estudiante
//codigo programa activo promedio
//        si es profesor
//tipo contrato