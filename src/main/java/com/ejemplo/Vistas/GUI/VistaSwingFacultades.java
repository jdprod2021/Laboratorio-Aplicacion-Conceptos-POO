package com.ejemplo.Vistas.GUI;

import com.ejemplo.Controladores.FacultadControlador;
import com.ejemplo.DTOs.Solicitud.FacultadSolicitudDTO;

import javax.swing.*;
import java.awt.*;

public class VistaSwingFacultades extends JFrame {

    private FacultadControlador controlador;

    public VistaSwingFacultades(FacultadControlador controlador) {
        this.controlador = controlador;
        initUI();
    }

    private void initUI(){

        setTitle("🏛️ Menú Facultades");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Layout principal
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 10, 10));

        // Botones
        JButton btnCrear = new JButton("➕ Crear Facultad");
        JButton btnListar = new JButton("📋 Listar Facultades");
        JButton btnActualizar = new JButton("📝 Actualizar Facultad");
        JButton btnEliminar = new JButton("❌ Eliminar Facultad");
        JButton btnVolver = new JButton("🔙 Volver");

        // Listeners
        btnCrear.addActionListener(e -> crearFacultad());
        btnListar.addActionListener(e -> listarFacultades());
        btnActualizar.addActionListener(e -> actualizarFacultad());
        btnEliminar.addActionListener(e -> eliminarFacultad());
        btnVolver.addActionListener(e -> dispose());

        // Agregar al panel
        panel.add(btnCrear);
        panel.add(btnListar);
        panel.add(btnActualizar);
        panel.add(btnEliminar);
        panel.add(btnVolver);

        add(panel);
    }


    private void crearFacultad() {
        String nombre = JOptionPane.showInputDialog(this, "📝 Nombre de la facultad:");
        if (nombre == null || nombre.isBlank()) return;

        String decanoIdStr = JOptionPane.showInputDialog(this, "📝 ID del decano:");
        if (decanoIdStr == null || decanoIdStr.isBlank()) return;
        long decanoId = Long.parseLong(decanoIdStr);

        FacultadSolicitudDTO solicitud = new FacultadSolicitudDTO(nombre, decanoId);
        controlador.crearFacultad(solicitud);

        JOptionPane.showMessageDialog(this, "✅ Facultad creada exitosamente.");
    }

    private void listarFacultades() {
        StringBuilder sb = new StringBuilder("=== Lista de Facultades ===\n");
        controlador.listarFacultades().forEach(facultad -> sb.append("🏛️ ").append(facultad.toString()).append("\n"));

        JOptionPane.showMessageDialog(this, sb.toString(), "📋 Facultades", JOptionPane.INFORMATION_MESSAGE);
    }

    private void actualizarFacultad() {
        String idStr = JOptionPane.showInputDialog(this, "🔍 ID de la facultad a actualizar:");
        if (idStr == null) return;
        long id = Long.parseLong(idStr);

        String nombre = JOptionPane.showInputDialog(this, "📝 Nuevo Nombre de la facultad:");
        if (nombre == null || nombre.isBlank()) return;

        String decanoIdStr = JOptionPane.showInputDialog(this, "📝 Nuevo ID del decano:");
        if (decanoIdStr == null || decanoIdStr.isBlank()) return;
        long decanoId = Long.parseLong(decanoIdStr);

        FacultadSolicitudDTO solicitud = new FacultadSolicitudDTO(nombre, decanoId);
        controlador.actualizarFacultad(id, solicitud);

        JOptionPane.showMessageDialog(this, "✅ Facultad actualizada exitosamente.");
    }

    private void eliminarFacultad() {
        String idStr = JOptionPane.showInputDialog(this, "🔍 ID de la facultad a eliminar:");
        if (idStr == null) return;
        long id = Long.parseLong(idStr);

        controlador.eliminarFacultad(id);
        JOptionPane.showMessageDialog(this, "✅ Facultad eliminada exitosamente.");
    }
}