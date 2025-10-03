package com.ejemplo.Vistas.GUI;

import com.ejemplo.Controladores.CursoControlador;
import com.ejemplo.DTOs.Solicitud.CursoSolicitudDTO;

import javax.swing.*;
import java.awt.*;

public class VistaSwingCursos extends JFrame {

    private CursoControlador controlador;

    public VistaSwingCursos(CursoControlador controlador) {
        this.controlador = controlador;
        initUI();
    }

    private void initUI() {
        setTitle("📚 Menú Cursos");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Layout principal
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 10, 10));

        // Botones
        JButton btnCrear = new JButton("➕ Crear Curso");
        JButton btnListar = new JButton("📋 Listar Cursos");
        JButton btnActualizar = new JButton("📝 Actualizar Curso");
        JButton btnEliminar = new JButton("❌ Eliminar Curso");
        JButton btnVolver = new JButton("🔙 Volver");

        // Listeners
        btnCrear.addActionListener(e -> crearCurso());
        btnListar.addActionListener(e -> listarCursos());
        btnActualizar.addActionListener(e -> actualizarCurso());
        btnEliminar.addActionListener(e -> eliminarCurso());
        btnVolver.addActionListener(e -> dispose());

        // Agregar al panel
        panel.add(btnCrear);
        panel.add(btnListar);
        panel.add(btnActualizar);
        panel.add(btnEliminar);
        panel.add(btnVolver);

        add(panel);
    }

    private void crearCurso() {
        String nombre = JOptionPane.showInputDialog(this, "📝 Nombre del curso:");
        if (nombre == null || nombre.isBlank()) return;

        String programaIdStr = JOptionPane.showInputDialog(this, "🏛️ ID del programa:");
        if (programaIdStr == null) return;
        long programaId = Long.parseLong(programaIdStr);

        int confirm = JOptionPane.showConfirmDialog(this, "⚡ ¿Activo?", "Estado", JOptionPane.YES_NO_OPTION);
        boolean activo = (confirm == JOptionPane.YES_OPTION);

        CursoSolicitudDTO solicitud = new CursoSolicitudDTO(nombre, programaId, activo);
        controlador.crearCurso(solicitud);

        JOptionPane.showMessageDialog(this, "✅ Curso creado exitosamente.");
    }

    private void listarCursos() {
        StringBuilder sb = new StringBuilder("=== Lista de Cursos ===\n");
        controlador.listarCursos().forEach(curso -> sb.append("📚 ").append(curso.toString()).append("\n"));

        JOptionPane.showMessageDialog(this, sb.toString(), "📋 Cursos", JOptionPane.INFORMATION_MESSAGE);
    }

    private void actualizarCurso() {
        String idStr = JOptionPane.showInputDialog(this, "🔍 ID del curso a actualizar:");
        if (idStr == null) return;
        long id = Long.parseLong(idStr);

        String nombre = JOptionPane.showInputDialog(this, "📝 Nuevo nombre:");
        if (nombre == null || nombre.isBlank()) return;

        String programaIdStr = JOptionPane.showInputDialog(this, "🏛️ Nuevo ID del programa:");
        if (programaIdStr == null) return;
        long programaId = Long.parseLong(programaIdStr);

        int confirm = JOptionPane.showConfirmDialog(this, "⚡ ¿Activo?", "Estado", JOptionPane.YES_NO_OPTION);
        boolean activo = (confirm == JOptionPane.YES_OPTION);

        CursoSolicitudDTO solicitud = new CursoSolicitudDTO(nombre, programaId, activo);
        controlador.actualizarCurso(id, solicitud);

        JOptionPane.showMessageDialog(this, "✅ Curso actualizado exitosamente.");
    }

    private void eliminarCurso() {
        String idStr = JOptionPane.showInputDialog(this, "🔍 ID del curso a eliminar:");
        if (idStr == null) return;
        long id = Long.parseLong(idStr);

        controlador.eliminarCurso(id);
        JOptionPane.showMessageDialog(this, "✅ Curso eliminado exitosamente.");
    }
}
