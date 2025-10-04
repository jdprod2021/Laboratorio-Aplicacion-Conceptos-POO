package com.ejemplo.Vistas.GUI;

import com.ejemplo.Controladores.ProgramaControlador;
import com.ejemplo.DTOs.Solicitud.ProgramaSolicitudDTO;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;

public class VistaSwingProgramas extends JFrame {

    private ProgramaControlador controlador;

    public VistaSwingProgramas(ProgramaControlador controlador) {
        this.controlador = controlador;
        initUI();
    }

    private void initUI(){

        setTitle("📘 Menú Programas");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Layout principal
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 10, 10));

        // Botones
        JButton btnCrear = new JButton("➕ Crear Programa");
        JButton btnListar = new JButton("📋 Listar Programas");
        JButton btnActualizar = new JButton("📝 Actualizar Programa");
        JButton btnEliminar = new JButton("❌ Eliminar Programa");
        JButton btnVolver = new JButton("🔙 Volver");

        // Listeners
        btnCrear.addActionListener(e -> crearPrograma());
        btnListar.addActionListener(e -> listarProgramas());
        btnActualizar.addActionListener(e -> actualizarPrograma());
        btnEliminar.addActionListener(e -> eliminarPrograma());
        btnVolver.addActionListener(e -> dispose());

        // Agregar al panel
        panel.add(btnCrear);
        panel.add(btnListar);
        panel.add(btnActualizar);
        panel.add(btnEliminar);
        panel.add(btnVolver);

        add(panel);
    }


    private void crearPrograma() {
        String nombre = JOptionPane.showInputDialog(this, "📝 Nombre del programa:");
        if (nombre == null || nombre.isBlank()) return;

        String duracionStr = JOptionPane.showInputDialog(this, "📝 Duración del programa (en años):");
        if (duracionStr == null || duracionStr.isBlank()) return;
        double duracion = Double.parseDouble(duracionStr);

        Date registro = new Date(System.currentTimeMillis());

        String facultadIdStr = JOptionPane.showInputDialog(this, "📝 ID de la facultad:");
        if (facultadIdStr == null || facultadIdStr.isBlank()) return;
        long facultadId = Long.parseLong(facultadIdStr);

        ProgramaSolicitudDTO solicitud = new ProgramaSolicitudDTO(nombre, duracion, registro, facultadId);
        controlador.crearPrograma(solicitud);

        JOptionPane.showMessageDialog(this, "✅ Programa creado exitosamente.");
    }

    private void listarProgramas() {
        StringBuilder sb = new StringBuilder("=== Lista de Programas ===\n");
        controlador.listarProgramas().forEach(programa -> sb.append("📘 ").append(programa.toString()).append("\n"));

        JOptionPane.showMessageDialog(this, sb.toString(), "📋 Programas", JOptionPane.INFORMATION_MESSAGE);
    }

    private void actualizarPrograma() {
        String idStr = JOptionPane.showInputDialog(this, "🔍 ID del programa a actualizar:");
        if (idStr == null) return;
        long id = Long.parseLong(idStr);

        String nombre = JOptionPane.showInputDialog(this, "📝 Nuevo Nombre del programa:");
        if (nombre == null || nombre.isBlank()) return;

        String duracionStr = JOptionPane.showInputDialog(this, "📝 Nueva Duración del programa (en años):");
        if (duracionStr == null || duracionStr.isBlank()) return;
        double duracion = Double.parseDouble(duracionStr);

        Date registro = new Date(System.currentTimeMillis());

        String facultadIdStr = JOptionPane.showInputDialog(this, "📝 Nuevo ID de la facultad:");
        if (facultadIdStr == null || facultadIdStr.isBlank()) return;
        long facultadId = Long.parseLong(facultadIdStr);

        ProgramaSolicitudDTO solicitud = new ProgramaSolicitudDTO(nombre, duracion, registro, facultadId);
        controlador.actualizarPrograma(id, solicitud);

        JOptionPane.showMessageDialog(this, "✅ Programa actualizado exitosamente.");
    }

    private void eliminarPrograma() {
        String idStr = JOptionPane.showInputDialog(this, "🔍 ID del programa a eliminar:");
        if (idStr == null) return;
        long id = Long.parseLong(idStr);

        controlador.eliminarPrograma(id);
        JOptionPane.showMessageDialog(this, "✅ Programa eliminado exitosamente.");
    }
}