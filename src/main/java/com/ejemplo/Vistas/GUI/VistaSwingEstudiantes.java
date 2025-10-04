package com.ejemplo.Vistas.GUI;

import com.ejemplo.Controladores.EstudianteControlador;
import com.ejemplo.DTOs.Solicitud.EstudianteSolicitudDTO;

import javax.swing.*;
import java.awt.*;

public class VistaSwingEstudiantes extends JFrame {

    private EstudianteControlador controlador;

    public VistaSwingEstudiantes(EstudianteControlador controlador) {
        this.controlador = controlador;
        initUI();
    }

    private void initUI(){

        setTitle("🎓 Menú Estudiantes");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Layout principal
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 10, 10));

        // Botones
        JButton btnCrear = new JButton("➕ Crear Estudiante");
        JButton btnListar = new JButton("📋 Listar Estudiantes");
        JButton btnActualizar = new JButton("📝 Actualizar Estudiante");
        JButton btnEliminar = new JButton("❌ Eliminar Estudiante");
        JButton btnVolver = new JButton("🔙 Volver");

        // Listeners
        btnCrear.addActionListener(e -> crearEstudiante());
        btnListar.addActionListener(e -> listarEstudiantes());
        btnActualizar.addActionListener(e -> actualizarEstudiante());
        btnEliminar.addActionListener(e -> eliminarEstudiante());
        btnVolver.addActionListener(e -> dispose());

        // Agregar al panel
        panel.add(btnCrear);
        panel.add(btnListar);
        panel.add(btnActualizar);
        panel.add(btnEliminar);
        panel.add(btnVolver);

        add(panel);
    }


    private void crearEstudiante() {
        String nombres = JOptionPane.showInputDialog(this, "📝 Nombres del estudiante:");
        if (nombres == null || nombres.isBlank()) return;

        String apellidos = JOptionPane.showInputDialog(this, "📝 Apellidos del estudiante:");
        if (apellidos == null || apellidos.isBlank()) return;

        String email = JOptionPane.showInputDialog(this, "📝 Email del estudiante:");
        if (email == null || email.isBlank()) return;

        String codigoStr = JOptionPane.showInputDialog(this, "📝 Código del estudiante:");
        if (codigoStr == null || codigoStr.isBlank()) return;
        long codigo = Long.parseLong(codigoStr);

        String activoStr = JOptionPane.showInputDialog(this, "📝 ¿Está activo? (true/false):");
        if (activoStr == null || activoStr.isBlank()) return;
        boolean activo = Boolean.parseBoolean(activoStr);

        String promedioStr = JOptionPane.showInputDialog(this, "📝 Promedio del estudiante:");
        if (promedioStr == null || promedioStr.isBlank()) return;
        double promedio = Double.parseDouble(promedioStr);

        String programaIdStr = JOptionPane.showInputDialog(this, "📝 ID del programa:");
        if (programaIdStr == null || programaIdStr.isBlank()) return;
        long programaId = Long.parseLong(programaIdStr);

        EstudianteSolicitudDTO solicitud = new EstudianteSolicitudDTO(nombres, apellidos, email, codigo, activo, promedio, programaId);
        controlador.crearEstudiante(solicitud);

        JOptionPane.showMessageDialog(this, "✅ Estudiante creado exitosamente.");
    }

    private void listarEstudiantes() {
        StringBuilder sb = new StringBuilder("=== Lista de Estudiantes ===\n");
        controlador.listarEstudiantes().forEach(estudiante -> sb.append("🎓 ").append(estudiante.toString()).append("\n"));

        JOptionPane.showMessageDialog(this, sb.toString(), "📋 Estudiantes", JOptionPane.INFORMATION_MESSAGE);
    }

    private void actualizarEstudiante() {
        String idStr = JOptionPane.showInputDialog(this, "🔍 ID del estudiante a actualizar:");
        if (idStr == null) return;
        long id = Long.parseLong(idStr);

        String nombres = JOptionPane.showInputDialog(this, "📝 Nuevos Nombres del estudiante:");
        if (nombres == null || nombres.isBlank()) return;

        String apellidos = JOptionPane.showInputDialog(this, "📝 Nuevos Apellidos del estudiante:");
        if (apellidos == null || apellidos.isBlank()) return;

        String email = JOptionPane.showInputDialog(this, "📝 Nuevo Email del estudiante:");
        if (email == null || email.isBlank()) return;

        String codigoStr = JOptionPane.showInputDialog(this, "📝 Nuevo Código del estudiante:");
        if (codigoStr == null || codigoStr.isBlank()) return;
        long codigo = Long.parseLong(codigoStr);

        String activoStr = JOptionPane.showInputDialog(this, "📝 ¿Está activo? (true/false):");
        if (activoStr == null || activoStr.isBlank()) return;
        boolean activo = Boolean.parseBoolean(activoStr);

        String promedioStr = JOptionPane.showInputDialog(this, "📝 Nuevo Promedio del estudiante:");
        if (promedioStr == null || promedioStr.isBlank()) return;
        double promedio = Double.parseDouble(promedioStr);

        String programaIdStr = JOptionPane.showInputDialog(this, "📝 Nuevo ID del programa:");
        if (programaIdStr == null || programaIdStr.isBlank()) return;
        long programaId = Long.parseLong(programaIdStr);

        EstudianteSolicitudDTO solicitud = new EstudianteSolicitudDTO(nombres, apellidos, email, codigo, activo, promedio, programaId);
        controlador.actualizarEstudiante(id, solicitud);

        JOptionPane.showMessageDialog(this, "✅ Estudiante actualizado exitosamente.");
    }

    private void eliminarEstudiante() {
        String idStr = JOptionPane.showInputDialog(this, "🔍 ID del estudiante a eliminar:");
        if (idStr == null) return;
        long id = Long.parseLong(idStr);

        controlador.eliminarEstudiante(id);
        JOptionPane.showMessageDialog(this, "✅ Estudiante eliminado exitosamente.");
    }
}