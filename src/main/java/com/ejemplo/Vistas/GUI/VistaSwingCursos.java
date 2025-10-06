package com.ejemplo.Vistas.GUI;

import com.ejemplo.Controladores.CursoControlador;
import com.ejemplo.DTOs.Respuesta.CursoRespuestaDTO;
import com.ejemplo.DTOs.Solicitud.CursoSolicitudDTO;

import javax.swing.*;
import java.awt.*;
import java.util.List;

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

        List<CursoRespuestaDTO> cursos = controlador.listarCursos();

        if (cursos.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No hay cursos registrados",
                    "📋 Cursos",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Definir columnas
        String[] columnas = {"ID", "Nombre", "Estado"};

        // Crear matriz de datos
        Object[][] datos = new Object[cursos.size()][3];
        for (int i = 0; i < cursos.size(); i++) {
            CursoRespuestaDTO curso = cursos.get(i);
            datos[i][0] = curso.getId();
            datos[i][1] = curso.getNombre();
            datos[i][2] = curso.isActivo() ? "✅ Activo" : "❌ Inactivo";
        }

        // Crear tabla
        JTable tabla = new JTable(datos, columnas);
        tabla.setEnabled(false);
        tabla.setFillsViewportHeight(true);

        // Personalizar apariencia
        tabla.getTableHeader().setReorderingAllowed(false);
        tabla.setRowHeight(25);

        // Ajustar ancho de columnas
        tabla.getColumnModel().getColumn(0).setPreferredWidth(50);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(200);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(100);

        // Mostrar en un JScrollPane
        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.setPreferredSize(new Dimension(450, 300));

        JOptionPane.showMessageDialog(this,
                scrollPane,
                "📋 Lista de Cursos",
                JOptionPane.PLAIN_MESSAGE);


    }


    private void actualizarCurso() {

        // Obtener todos los cursos desde el controlador
        List<CursoRespuestaDTO> cursos = controlador.listarCursos();

        if (cursos == null || cursos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "⚠️ No hay cursos registrados para actualizar.");
            return;
        }

        // Crear JComboBox con los cursos
        JComboBox<CursoRespuestaDTO> comboCursos = new JComboBox<>(cursos.toArray(new CursoRespuestaDTO[0]));

        // Personalizar cómo se muestran los elementos (mostrar nombre en vez de toString)
        comboCursos.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                    JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof CursoRespuestaDTO curso) {
                    setText("ID: " + curso.getId() + " - " + curso.getNombre());
                }
                return this;
            }
        });

        // Crear panel con el combo y mostrarlo en un JOptionPane
        JPanel panel = new JPanel();
        panel.add(new JLabel("📚 Selecciona el curso:"));
        panel.add(comboCursos);

        int option = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Seleccionar curso para actualizar",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (option != JOptionPane.OK_OPTION) return;

        // Obtener el curso seleccionado
        CursoRespuestaDTO cursoSeleccionado = (CursoRespuestaDTO) comboCursos.getSelectedItem();
        if (cursoSeleccionado == null) return;

        long id = cursoSeleccionado.getId();

        // Pedir los nuevos datos
        String nombre = JOptionPane.showInputDialog(this, "📝 Nuevo nombre:", cursoSeleccionado.getNombre());
        if (nombre == null || nombre.isBlank()) return;

        String programaIdStr = JOptionPane.showInputDialog(this, "🏛️ Nuevo ID del programa:");
        if (programaIdStr == null) return;
        long programaId = Long.parseLong(programaIdStr);

        int confirm = JOptionPane.showConfirmDialog(this, "⚡ ¿Activo?", "Estado", JOptionPane.YES_NO_OPTION);
        boolean activo = (confirm == JOptionPane.YES_OPTION);

        // Crear DTO y actualizar
        CursoSolicitudDTO solicitud = new CursoSolicitudDTO(nombre, programaId, activo);
        controlador.actualizarCurso(id, solicitud);

        JOptionPane.showMessageDialog(this, "✅ Curso actualizado exitosamente.");
    }

    private void eliminarCurso() {

        // Obtener todos los cursos desde el controlador
        List<CursoRespuestaDTO> cursos = controlador.listarCursos();

        if (cursos == null || cursos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "⚠️ No hay cursos registrados para actualizar.");
            return;
        }

        // Crear JComboBox con los cursos
        JComboBox<CursoRespuestaDTO> comboCursos = new JComboBox<>(cursos.toArray(new CursoRespuestaDTO[0]));

        // Personalizar cómo se muestran los elementos (mostrar nombre en vez de toString)
        comboCursos.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                    JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof CursoRespuestaDTO curso) {
                    setText("ID: " + curso.getId() + " - " + curso.getNombre());
                }
                return this;
            }
        });

        // Crear panel con el combo y mostrarlo en un JOptionPane
        JPanel panel = new JPanel();
        panel.add(new JLabel("📚 Selecciona el curso:"));
        panel.add(comboCursos);

        int option = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Seleccionar curso para actualizar",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (option != JOptionPane.OK_OPTION) return;

        // Obtener el curso seleccionado
        CursoRespuestaDTO cursoSeleccionado = (CursoRespuestaDTO) comboCursos.getSelectedItem();
        if (cursoSeleccionado == null) return;

        long id = cursoSeleccionado.getId();


        controlador.eliminarCurso(id);
        JOptionPane.showMessageDialog(this, "✅ Curso eliminado exitosamente.");
    }
}
