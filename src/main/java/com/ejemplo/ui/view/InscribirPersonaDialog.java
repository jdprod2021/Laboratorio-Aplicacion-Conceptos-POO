package com.ejemplo.ui.view;

import javax.swing.*;
import java.awt.*;

public class InscribirPersonaDialog extends JDialog {

    private final JTextField txtNombre = new JTextField(20);
    private final JTextField txtApellido = new JTextField(20);
    private final JTextField txtEmail = new JTextField(20);
    private boolean accepted = false;

    public InscribirPersonaDialog(Window owner) {
        super(owner, "Inscribir Persona", ModalityType.APPLICATION_MODAL);

        var form = new JPanel(new GridLayout(0, 2, 8, 8));
        form.add(new JLabel("Nombre:"));   form.add(txtNombre);
        form.add(new JLabel("Apellido:")); form.add(txtApellido);
        form.add(new JLabel("Email:"));    form.add(txtEmail);

        var btnOk = new JButton("Guardar");
        var btnCancel = new JButton("Cancelar");

        btnOk.addActionListener(e -> { accepted = true; setVisible(false); });
        btnCancel.addActionListener(e -> setVisible(false));

        var south = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        south.add(btnCancel); south.add(btnOk);

        getContentPane().add(form, BorderLayout.CENTER);
        getContentPane().add(south, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(owner);
    }

    public boolean isAccepted() { return accepted; }
    public String getNombre() { return txtNombre.getText(); }
    public String getApellido() { return txtApellido.getText(); }
    public String getEmail() { return txtEmail.getText(); }







    //nombre apellidos email
    //si es estudiante
    //codigo programa activo promedio
    //        si es profesor
    //tipo contrato
}
