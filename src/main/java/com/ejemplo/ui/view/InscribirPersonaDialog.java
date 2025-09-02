package com.ejemplo.ui.view;

import com.ejemplo.Modelos.Personas.Estudiante;
import com.ejemplo.Modelos.Personas.Profesor;
import com.ejemplo.Modelos.Universidad.Programa;
import com.ejemplo.Servicios.Personas.EstudianteServicio;
import com.ejemplo.Servicios.Personas.ProfesorServicio;

import javax.swing.*;
import java.awt.*;

public class InscribirPersonaDialog extends JDialog {

    private JTextField txtNombre, txtApellidos, txtEmail;
    private JComboBox<String> cmbRol;
    private JPanel panelRol;
    private CardLayout cardLayout;

    // Campos estudiante
    private JTextField txtCodigo, txtPrograma, txtActivo, txtPromedio;

    // Campos profesor
    private JTextField txtTipoContrato;

    // Servicios
    private final EstudianteServicio estudianteServicio;
    private final ProfesorServicio profesorServicio;

    public InscribirPersonaDialog(JFrame parent, EstudianteServicio estudianteServicio, ProfesorServicio profesorServicio) {
        super(parent, "Inscribir Persona", true);
        this.estudianteServicio = estudianteServicio;
        this.profesorServicio = profesorServicio;

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

        JPanel rolPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rolPanel.add(new JLabel("Rol:"));
        cmbRol = new JComboBox<>(new String[]{"Estudiante", "Profesor"});
        rolPanel.add(cmbRol);
        centro.add(rolPanel, BorderLayout.NORTH);

        cardLayout = new CardLayout();
        panelRol = new JPanel(cardLayout);

        // Panel Estudiante
        JPanel estudiantePanel = new JPanel(new GridLayout(4, 2, 5, 5));
        estudiantePanel.setBorder(BorderFactory.createTitledBorder("Datos de Estudiante"));
        estudiantePanel.add(new JLabel("Código:"));
        txtCodigo = new JTextField();
        estudiantePanel.add(txtCodigo);
        estudiantePanel.add(new JLabel("Programa:"));
        txtPrograma = new JTextField();
        estudiantePanel.add(txtPrograma);
        estudiantePanel.add(new JLabel("Activo (sí/no):"));
        txtActivo = new JTextField();
        estudiantePanel.add(txtActivo);
        estudiantePanel.add(new JLabel("Promedio:"));
        txtPromedio = new JTextField();
        estudiantePanel.add(txtPromedio);

        // Panel Profesor
        JPanel profesorPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        profesorPanel.setBorder(BorderFactory.createTitledBorder("Datos de Profesor"));
        profesorPanel.add(new JLabel("Tipo de contrato:"));
        txtTipoContrato = new JTextField();
        profesorPanel.add(txtTipoContrato);

        panelRol.add(estudiantePanel, "Estudiante");
        panelRol.add(profesorPanel, "Profesor");

        centro.add(panelRol, BorderLayout.CENTER);
        mainPanel.add(centro, BorderLayout.CENTER);

        // --- Botonera ---
        JPanel botonesPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnGuardar = new JButton("Guardar");
        JButton btnVolver = new JButton("Volver al menú principal");

        btnGuardar.addActionListener(e -> guardarPersona());
        btnVolver.addActionListener(e -> dispose());

        botonesPanel.add(btnGuardar);
        botonesPanel.add(btnVolver);

        mainPanel.add(botonesPanel, BorderLayout.SOUTH);

        // --- Listener para cambiar entre roles ---
        cmbRol.addActionListener(e -> {
            String rol = (String) cmbRol.getSelectedItem();
            cardLayout.show(panelRol, rol);
        });

        cardLayout.show(panelRol, "Estudiante");
    }

    private void guardarPersona() {
        try {
            String nombre = txtNombre.getText();
            String apellidos = txtApellidos.getText();
            String email = txtEmail.getText();
            String rol = (String) cmbRol.getSelectedItem();

            String mensaje;

            if ("Estudiante".equals(rol)) {
                Estudiante est = new Estudiante();
                est.setNombres(nombre);
                est.setApellidos(apellidos);
                est.setEmail(email);
                est.setCodigo(Double.parseDouble(txtCodigo.getText()));
                //est.setPrograma(new Programa());
                est.setActivo("si".equalsIgnoreCase(txtActivo.getText()));
                est.setPromedio(Double.parseDouble(txtPromedio.getText()));

                mensaje = estudianteServicio.inscribir(est);

            } else {
                Profesor prof = new Profesor();
                prof.setNombres(nombre);
                prof.setApellidos(apellidos);
                prof.setEmail(email);
                prof.setTipoContrato(txtTipoContrato.getText());

                mensaje = profesorServicio.inscribir(prof);
            }

            JOptionPane.showMessageDialog(this, mensaje, "Confirmación", JOptionPane.INFORMATION_MESSAGE);
            dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}


//nombre apellidos email
//si es estudiante
//codigo programa activo promedio
//        si es profesor
//tipo contrato