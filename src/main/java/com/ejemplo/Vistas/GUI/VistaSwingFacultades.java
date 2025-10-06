package com.ejemplo.Vistas.GUI;

import com.ejemplo.Controladores.FacultadControlador;
import com.ejemplo.DTOs.Respuesta.EstudianteRespuestaDTO;
import com.ejemplo.DTOs.Respuesta.FacultadRespuestaDTO;
import com.ejemplo.DTOs.Solicitud.FacultadSolicitudDTO;

import javax.swing.*;
import java.awt.*;
import java.util.List;

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
        List<FacultadRespuestaDTO> facultades = controlador.listarFacultades();

        if (facultades.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No hay facultades registradas",
                    "📋 Facultades",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Definir columnas
        String[] columnas = {"ID", "Nombre"};

        // Crear matriz de datos
        Object[][] datos = new Object[facultades.size()][2];
        for (int i = 0; i < facultades.size(); i++) {
            FacultadRespuestaDTO facultad = facultades.get(i);
            datos[i][0] = facultad.getID();
            datos[i][1] = facultad.getNombre();
        }


        JTable tabla = new JTable(datos, columnas);
        tabla.setEnabled(false);
        tabla.setFillsViewportHeight(true);


        tabla.getTableHeader().setReorderingAllowed(false);
        tabla.setRowHeight(25);


        tabla.getColumnModel().getColumn(0).setPreferredWidth(50);   // ID
        tabla.getColumnModel().getColumn(1).setPreferredWidth(300);  // Nombre

        // Mostrar en un JScrollPane
        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.setPreferredSize(new Dimension(400, 300));

        JOptionPane.showMessageDialog(this,
                scrollPane,
                "🏛️ Lista de Facultades",
                JOptionPane.PLAIN_MESSAGE);
    }

    private void actualizarFacultad() {

        List<FacultadRespuestaDTO> facultades = controlador.listarFacultades();

        if (facultades == null || facultades.isEmpty()) {
            JOptionPane.showMessageDialog(this, "⚠️ No hay estudiantes registrados para actualizar.");
            return;
        }


        JComboBox<FacultadRespuestaDTO> comboFacultades = new JComboBox<>(facultades.toArray(new FacultadRespuestaDTO[0]));


        comboFacultades.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                    JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof FacultadRespuestaDTO facultad) {
                    setText("ID: " + facultad.getID() + " - " + facultad.getNombre());
                }
                return this;
            }
        });

        JPanel panel = new JPanel();
        panel.add(new JLabel("👨‍🎓 Selecciona la facultad:"));
        panel.add(comboFacultades);

        int option = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Seleccionar facultad para actualizar",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (option != JOptionPane.OK_OPTION) return;


        FacultadRespuestaDTO facultadSeleccionada = (FacultadRespuestaDTO) comboFacultades.getSelectedItem();
        if (facultadSeleccionada == null) return;

        long id = facultadSeleccionada.getID();



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

        List<FacultadRespuestaDTO> facultades = controlador.listarFacultades();

        if (facultades == null || facultades.isEmpty()) {
            JOptionPane.showMessageDialog(this, "⚠️ No hay estudiantes registrados para actualizar.");
            return;
        }


        JComboBox<FacultadRespuestaDTO> comboFacultades = new JComboBox<>(facultades.toArray(new FacultadRespuestaDTO[0]));


        comboFacultades.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                    JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof FacultadRespuestaDTO facultad) {
                    setText("ID: " + facultad.getID() + " - " + facultad.getNombre());
                }
                return this;
            }
        });

        JPanel panel = new JPanel();
        panel.add(new JLabel("👨‍🎓 Selecciona la facultad:"));
        panel.add(comboFacultades);

        int option = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Seleccionar facultad para actualizar",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (option != JOptionPane.OK_OPTION) return;


        FacultadRespuestaDTO facultadSeleccionada = (FacultadRespuestaDTO) comboFacultades.getSelectedItem();
        if (facultadSeleccionada == null) return;

        long id = facultadSeleccionada.getID();

        controlador.eliminarFacultad(id);
        JOptionPane.showMessageDialog(this, "✅ Facultad eliminada exitosamente.");
    }
}