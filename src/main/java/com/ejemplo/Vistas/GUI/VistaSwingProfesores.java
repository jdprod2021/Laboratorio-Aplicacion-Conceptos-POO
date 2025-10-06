package com.ejemplo.Vistas.GUI;

import com.ejemplo.Controladores.ProfesorControlador;
import com.ejemplo.DTOs.Respuesta.ProfesorRespuestaDTO;
import com.ejemplo.DTOs.Solicitud.ProfesorSolicitudDTO;

import javax.swing.*;
import java.awt.*;
import java.util.List;
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
        List<ProfesorRespuestaDTO> profesores = controlador.listarProfesores();

        if (profesores.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No hay profesores registrados",
                    "📋 Profesores",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Definir columnas
        String[] columnas = {"ID", "Nombres", "Apellidos", "Email", "Tipo de Contrato"};

        // Crear matriz de datos
        Object[][] datos = new Object[profesores.size()][5];
        for (int i = 0; i < profesores.size(); i++) {
            ProfesorRespuestaDTO profesor = profesores.get(i);
            datos[i][0] = profesor.getID();
            datos[i][1] = profesor.getNombres();
            datos[i][2] = profesor.getApellidos();
            datos[i][3] = profesor.getEmail();
            datos[i][4] = profesor.getTipoContrato();
        }


        JTable tabla = new JTable(datos, columnas);
        tabla.setEnabled(false);
        tabla.setFillsViewportHeight(true);


        tabla.getTableHeader().setReorderingAllowed(false);
        tabla.setRowHeight(25);


        tabla.getColumnModel().getColumn(0).setPreferredWidth(50);   // ID
        tabla.getColumnModel().getColumn(1).setPreferredWidth(120);  // Nombres
        tabla.getColumnModel().getColumn(2).setPreferredWidth(120);  // Apellidos
        tabla.getColumnModel().getColumn(3).setPreferredWidth(180);  // Email
        tabla.getColumnModel().getColumn(4).setPreferredWidth(130);  // Tipo de Contrato


        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.setPreferredSize(new Dimension(650, 350));

        JOptionPane.showMessageDialog(this,
                scrollPane,
                "👨‍🏫 Lista de Profesores",
                JOptionPane.PLAIN_MESSAGE);
    }

    private void actualizarProfesor() {

        List<ProfesorRespuestaDTO> profesores = controlador.listarProfesores();


        if (profesores == null || profesores.isEmpty()) {
            JOptionPane.showMessageDialog(this, "⚠️ No hay estudiantes registrados para actualizar.");
            return;
        }


        JComboBox<ProfesorRespuestaDTO> comboProfesores = new JComboBox<>(profesores.toArray(new ProfesorRespuestaDTO[0]));


        comboProfesores.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                    JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof ProfesorRespuestaDTO profesor) {
                    setText("ID: " + profesor.getID() + " - " + profesor.getNombres() +  " - " + profesor.getApellidos());
                }
                return this;
            }
        });

        JPanel panel = new JPanel();
        panel.add(new JLabel("👨‍🎓 Selecciona el profesor:"));
        panel.add(comboProfesores);

        int option = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Seleccionar profesor para actualizar",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (option != JOptionPane.OK_OPTION) return;


        ProfesorRespuestaDTO profesorSeleccionado = (ProfesorRespuestaDTO) comboProfesores.getSelectedItem();
        if (profesorSeleccionado == null) return;

        long id = profesorSeleccionado.getID();

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

        List<ProfesorRespuestaDTO> profesores = controlador.listarProfesores();


        if (profesores == null || profesores.isEmpty()) {
            JOptionPane.showMessageDialog(this, "⚠️ No hay estudiantes registrados para actualizar.");
            return;
        }


        JComboBox<ProfesorRespuestaDTO> comboProfesores = new JComboBox<>(profesores.toArray(new ProfesorRespuestaDTO[0]));


        comboProfesores.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                    JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof ProfesorRespuestaDTO profesor) {
                    setText("ID: " + profesor.getID() + " - " + profesor.getNombres() +  " - " + profesor.getApellidos());
                }
                return this;
            }
        });

        JPanel panel = new JPanel();
        panel.add(new JLabel("👨‍🎓 Selecciona el profesor:"));
        panel.add(comboProfesores);

        int option = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Seleccionar profesor para actualizar",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (option != JOptionPane.OK_OPTION) return;


        ProfesorRespuestaDTO profesorSeleccionado = (ProfesorRespuestaDTO) comboProfesores.getSelectedItem();
        if (profesorSeleccionado == null) return;

        long id = profesorSeleccionado.getID();

        controlador.eliminarProfesor(id);
        JOptionPane.showMessageDialog(this, "✅ Profesor eliminado exitosamente.");
    }



}