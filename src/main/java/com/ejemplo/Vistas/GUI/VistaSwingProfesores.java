package com.ejemplo.Vistas.GUI;

import com.ejemplo.Controladores.ProfesorControlador;
import com.ejemplo.DTOs.Solicitud.ProfesorSolicitudDTO;

import javax.swing.*;
import java.awt.*;

public class VistaSwingProfesores extends JFrame {

    private ProfesorControlador controlador;

    public VistaSwingProfesores(ProfesorControlador controlador) {
        this.controlador = controlador;
        initUI();
}

private void initUI(){

    setTitle("📚 Menú Cursos");
    setSize(400, 300);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setLocationRelativeTo(null);

    // Layout principal
    JPanel panel = new JPanel();
    panel.setLayout(new GridLayout(5, 1, 10, 10));

    // Botones
    JButton btnCrear = new JButton("➕ Crear Profesor");
    JButton btnListar = new JButton("📋 Listar Profesores");
    JButton btnActualizar = new JButton("📝 Actualizar Profesor");
    JButton btnEliminar = new JButton("❌ Eliminar Profesor");
    JButton btnVolver = new JButton("🔙 Volver");

    // Listeners
    btnCrear.addActionListener(e -> crearProfesor());
    btnListar.addActionListener(e -> listarProfesores());
    btnActualizar.addActionListener(e -> actualizarProfesor());
    btnEliminar.addActionListener(e -> eliminarProfesor());
    btnVolver.addActionListener(e -> dispose());

    // Agregar al panel
    panel.add(btnCrear);
    panel.add(btnListar);
    panel.add(btnActualizar);
    panel.add(btnEliminar);
    panel.add(btnVolver);

    add(panel);


    }


    private void crearProfesor() {
        String nombre = JOptionPane.showInputDialog(this, "📝 Nombre del profesor:");
        if (nombre == null || nombre.isBlank()) return;

        String apellidos =  JOptionPane.showInputDialog(this, "📝 Apellido del profesor:");
        if (apellidos == null || apellidos.isBlank()) return;

        String email =  JOptionPane.showInputDialog(this, "📝 Email del profesor:");
        if (email == null || email.isBlank()) return;

        String tipoContrato = JOptionPane.showInputDialog(this, "📝 Tipo de contrato del profesor:");
        if (tipoContrato == null || tipoContrato.isBlank()) return;


        ProfesorSolicitudDTO solicitud = new ProfesorSolicitudDTO(nombre, apellidos, email, tipoContrato);
        controlador.crearProfesor(solicitud);

        JOptionPane.showMessageDialog(this, "✅ Profesor creado exitosamente.");
    }

    private void listarProfesores() {
        StringBuilder sb = new StringBuilder("=== Lista de Profesores ===\n");
        controlador.listarProfesores().forEach(profesor -> sb.append("📚 ").append(profesor.toString()).append("\n"));

        JOptionPane.showMessageDialog(this, sb.toString(), "📋 Profesores", JOptionPane.INFORMATION_MESSAGE);
    }

    private void actualizarProfesor() {
        String idStr = JOptionPane.showInputDialog(this, "🔍 ID del Profesor a actualizar:");
        if (idStr == null) return;
        long id = Long.parseLong(idStr);

        String nombre = JOptionPane.showInputDialog(this, "📝 Nuevo Nombre del profesor:");
        if (nombre == null || nombre.isBlank()) return;

        String apellidos =  JOptionPane.showInputDialog(this, "📝 Nuevo Apellido del profesor:");
        if (apellidos == null || apellidos.isBlank()) return;

        String email =  JOptionPane.showInputDialog(this, "📝 Nuevo Email del profesor:");
        if (email == null || email.isBlank()) return;

        String tipoContrato = JOptionPane.showInputDialog(this, "📝 Nuevo Tipo de contrato del profesor:");
        if (tipoContrato == null || tipoContrato.isBlank()) return;


        ProfesorSolicitudDTO solicitud = new ProfesorSolicitudDTO(nombre, apellidos, email, tipoContrato);
        controlador.actualizarProfesor(id, solicitud);

        JOptionPane.showMessageDialog(this, "✅ Profesor actualizado exitosamente.");
    }

    private void eliminarProfesor() {
        String idStr = JOptionPane.showInputDialog(this, "🔍 ID del Profesor a eliminar:");
        if (idStr == null) return;
        long id = Long.parseLong(idStr);

        controlador.eliminarProfesor(id);
        JOptionPane.showMessageDialog(this, "✅ Profesor eliminado exitosamente.");
    }



}