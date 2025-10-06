package com.ejemplo.Vistas.GUI;

import com.ejemplo.Controladores.EstudianteControlador;
import com.ejemplo.DTOs.Respuesta.EstudianteRespuestaDTO;
import com.ejemplo.DTOs.Solicitud.EstudianteSolicitudDTO;

import javax.swing.*;
import java.awt.*;
import java.util.List;

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
        List<EstudianteRespuestaDTO> estudiantes = controlador.listarEstudiantes();

        if (estudiantes.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No hay estudiantes registrados",
                    "📋 Estudiantes",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Definir columnas
        String[] columnas = {"ID", "Nombres", "Apellidos", "Email", "Programa"};

        // Crear matriz de datos
        Object[][] datos = new Object[estudiantes.size()][5];
        for (int i = 0; i < estudiantes.size(); i++) {
            EstudianteRespuestaDTO estudiante = estudiantes.get(i);
            datos[i][0] = estudiante.getID();
            datos[i][1] = estudiante.getNombres();
            datos[i][2] = estudiante.getApellidos();
            datos[i][3] = estudiante.getEmail();
            datos[i][4] = estudiante.getNombrePrograma();
        }

        // Crear tabla
        JTable tabla = new JTable(datos, columnas);
        tabla.setEnabled(false);
        tabla.setFillsViewportHeight(true);

        // Personalizar apariencia
        tabla.getTableHeader().setReorderingAllowed(false);
        tabla.setRowHeight(25);

        // Ajustar ancho de columnas
        tabla.getColumnModel().getColumn(0).setPreferredWidth(50);   // ID
        tabla.getColumnModel().getColumn(1).setPreferredWidth(120);  // Nombres
        tabla.getColumnModel().getColumn(2).setPreferredWidth(120);  // Apellidos
        tabla.getColumnModel().getColumn(3).setPreferredWidth(180);  // Email
        tabla.getColumnModel().getColumn(4).setPreferredWidth(130);  // Programa

        // Mostrar en un JScrollPane
        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.setPreferredSize(new Dimension(650, 350));

        JOptionPane.showMessageDialog(this,
                scrollPane,
                "👥 Lista de Estudiantes",
                JOptionPane.PLAIN_MESSAGE);
    }

    private void actualizarEstudiante() {

        List<EstudianteRespuestaDTO> estudiantes = controlador.listarEstudiantes();

        if (estudiantes == null || estudiantes.isEmpty()) {
            JOptionPane.showMessageDialog(this, "⚠️ No hay estudiantes registrados para actualizar.");
            return;
        }

        JComboBox<EstudianteRespuestaDTO> comboEstudiantes = new JComboBox<>(estudiantes.toArray(new EstudianteRespuestaDTO[0]));


        comboEstudiantes.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                    JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof EstudianteRespuestaDTO estudiante) {
                    setText("ID: " + estudiante.getID() + " - " + estudiante.getNombres() + " " + estudiante.getApellidos());
                }
                return this;
            }
        });

        JPanel panel = new JPanel();
        panel.add(new JLabel("👨‍🎓 Selecciona el estudiante:"));
        panel.add(comboEstudiantes);

        int option = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Seleccionar estudiante para actualizar",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (option != JOptionPane.OK_OPTION) return;


        EstudianteRespuestaDTO estudianteSeleccionado = (EstudianteRespuestaDTO) comboEstudiantes.getSelectedItem();
        if (estudianteSeleccionado == null) return;

        long id = estudianteSeleccionado.getID();


        JOptionPane.showMessageDialog(this, "✅ Estudiante actualizado exitosamente.");
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
        List<EstudianteRespuestaDTO> estudiantes = controlador.listarEstudiantes();

        if (estudiantes == null || estudiantes.isEmpty()) {
            JOptionPane.showMessageDialog(this, "⚠️ No hay estudiantes registrados para actualizar.");
            return;
        }

        JComboBox<EstudianteRespuestaDTO> comboEstudiantes = new JComboBox<>(estudiantes.toArray(new EstudianteRespuestaDTO[0]));


        comboEstudiantes.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                    JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof EstudianteRespuestaDTO estudiante) {
                    setText("ID: " + estudiante.getID() + " - " + estudiante.getNombres() + " " + estudiante.getApellidos());
                }
                return this;
            }
        });

        JPanel panel = new JPanel();
        panel.add(new JLabel("👨‍🎓 Selecciona el estudiante:"));
        panel.add(comboEstudiantes);

        int option = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Seleccionar estudiante para actualizar",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (option != JOptionPane.OK_OPTION) return;


        EstudianteRespuestaDTO estudianteSeleccionado = (EstudianteRespuestaDTO) comboEstudiantes.getSelectedItem();
        if (estudianteSeleccionado == null) return;

        long id = estudianteSeleccionado.getID();

        controlador.eliminarEstudiante(id);
        JOptionPane.showMessageDialog(this, "✅ Estudiante eliminado exitosamente.");
    }
}