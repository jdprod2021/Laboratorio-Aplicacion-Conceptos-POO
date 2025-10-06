package com.ejemplo.Vistas.GUI;

import com.ejemplo.Controladores.ProgramaControlador;
import com.ejemplo.DTOs.Respuesta.ProgramaRespuestaDTO;
import com.ejemplo.DTOs.Solicitud.ProgramaSolicitudDTO;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.util.List;

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
        List<ProgramaRespuestaDTO> programas = controlador.listarProgramas();

        if (programas.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No hay programas registrados",
                    "📋 Programas",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Definir columnas
        String[] columnas = {"ID", "Nombre", "Duración (años)"};

        // Crear matriz de datos
        Object[][] datos = new Object[programas.size()][3];
        for (int i = 0; i < programas.size(); i++) {
            ProgramaRespuestaDTO programa = programas.get(i);
            datos[i][0] = programa.getID();
            datos[i][1] = programa.getNombre();
            datos[i][2] = programa.getDuracion();
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
        tabla.getColumnModel().getColumn(1).setPreferredWidth(250);  // Nombre
        tabla.getColumnModel().getColumn(2).setPreferredWidth(120);  // Duración

        // Mostrar en un JScrollPane
        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.setPreferredSize(new Dimension(470, 300));

        JOptionPane.showMessageDialog(this,
                scrollPane,
                "🎓 Lista de Programas",
                JOptionPane.PLAIN_MESSAGE);
    }

    private void actualizarPrograma() {

        List<ProgramaRespuestaDTO> programas = controlador.listarProgramas();


        if (programas == null || programas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "⚠️ No hay estudiantes registrados para actualizar.");
            return;
        }


        JComboBox<ProgramaRespuestaDTO> comboProgramas = new JComboBox<ProgramaRespuestaDTO>(programas.toArray(new ProgramaRespuestaDTO[0]));


        comboProgramas.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                    JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof ProgramaRespuestaDTO programa) {
                    setText("ID: " + programa.getID() + " - " + programa.getNombre() +  " - " + programa.getDuracion());
                }
                return this;
            }
        });

        JPanel panel = new JPanel();
        panel.add(new JLabel("👨‍🎓 Selecciona el programa:"));
        panel.add(comboProgramas);

        int option = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Seleccionar programa para actualizar",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (option != JOptionPane.OK_OPTION) return;


        ProgramaRespuestaDTO programaSeleccionado = (ProgramaRespuestaDTO) comboProgramas.getSelectedItem();
        if (programaSeleccionado == null) return;

        long id = programaSeleccionado.getID();

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

        List<ProgramaRespuestaDTO> programas = controlador.listarProgramas();


        if (programas == null || programas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "⚠️ No hay estudiantes registrados para actualizar.");
            return;
        }


        JComboBox<ProgramaRespuestaDTO> comboProgramas = new JComboBox<ProgramaRespuestaDTO>(programas.toArray(new ProgramaRespuestaDTO[0]));


        comboProgramas.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                    JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof ProgramaRespuestaDTO programa) {
                    setText("ID: " + programa.getID() + " - " + programa.getNombre() +  " - " + programa.getDuracion());
                }
                return this;
            }
        });

        JPanel panel = new JPanel();
        panel.add(new JLabel("👨‍🎓 Selecciona el programa:"));
        panel.add(comboProgramas);

        int option = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Seleccionar programa para actualizar",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (option != JOptionPane.OK_OPTION) return;


        ProgramaRespuestaDTO programaSeleccionado = (ProgramaRespuestaDTO) comboProgramas.getSelectedItem();
        if (programaSeleccionado == null) return;

        long id = programaSeleccionado.getID();

        controlador.eliminarPrograma(id);
        JOptionPane.showMessageDialog(this, "✅ Programa eliminado exitosamente.");
    }
}