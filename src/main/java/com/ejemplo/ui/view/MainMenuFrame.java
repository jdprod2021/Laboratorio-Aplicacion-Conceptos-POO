package com.ejemplo.ui.view;

import com.ejemplo.Servicios.Personas.EstudianteServicio;
import com.ejemplo.Servicios.Personas.ProfesorServicio;

import javax.swing.*;
import java.awt.*;

public class MainMenuFrame extends JFrame implements MainMenuView {

    // --- Constantes de estilo ---
    private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 16);
    private static final Color BG = Color.LIGHT_GRAY;

    // --- Controles ---
    private final JButton btnInscribir = new JButton("Inscribir Persona");
    private final JButton btnInscribirCurso = new JButton("Inscribir Curso");
    private final JButton btnCursosInscritos = new JButton("Ver Cursos");
    private final JButton btnCursosProfesores = new JButton("Ver Cursos de Profesores");
    private final JButton btnGestionarCursos = new JButton("Gestionar Cursos");
    private final JButton btnGestionarProgramas = new JButton("Gestionar Programas");
    private final JButton btnGestionarFacultad = new JButton("Gestionar Facultad");
    private final JButton btnSalir = new JButton("Salir");

    // --- Callbacks ---
    private Runnable onInscribir;
    private Runnable onInscribirCurso;
    private Runnable onCursosInscritos;
    private Runnable onCursosProfesores;
    private Runnable onGestionarCursos;
    private Runnable onGestionarProgramas;
    private Runnable onGestionarFacultad;

    public MainMenuFrame() {
        super("Sistema Académico - Menú Principal");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Contenedor principal
        var root = getContentPane();
        root.setLayout(new BorderLayout());
        root.setBackground(BG);

        // Armar UI
        root.add(buildTopPanel(), BorderLayout.NORTH);
        root.add(buildLeftPanel(), BorderLayout.WEST);
        root.add(buildCenterPanel(), BorderLayout.CENTER);
        root.add(buildRightPanel(), BorderLayout.EAST);
        root.add(buildBottomPanel(), BorderLayout.SOUTH);

        // Tamaño según contenido
        pack();
        setLocationRelativeTo(null);

        // Listeners básicos
        wireActions();
    }

    // ---------- Builders de paneles ----------

    private JComponent buildTopPanel() {
        var p = new JPanel(new BorderLayout());
        p.setBackground(BG);
        var title = new JLabel("Bienvenido al sistema de gestión e inscripción de cursos", SwingConstants.CENTER);
        title.setFont(TITLE_FONT);
        title.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        p.add(title, BorderLayout.CENTER);
        return p;
    }

    private JComponent buildLeftPanel() {
        var p = new JPanel();
        p.setBackground(BG);
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        var title = new JLabel("Inscribir persona");
        title.setFont(TITLE_FONT);

        p.add(title);
        p.add(Box.createVerticalStrut(10));
        p.add(btnInscribir);

        return p;
    }

    private JComponent buildCenterPanel() {
        var center = new JPanel(new BorderLayout());
        center.setBackground(BG);
        center.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        var title = new JLabel("Gestionar Cursos", SwingConstants.CENTER);
        title.setFont(TITLE_FONT);
        center.add(title, BorderLayout.NORTH);

        var buttons = new JPanel();
        buttons.setBackground(BG);
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));

        buttons.add(btnInscribirCurso);
        buttons.add(Box.createRigidArea(new Dimension(15, 0)));
        buttons.add(btnCursosInscritos);
        buttons.add(Box.createRigidArea(new Dimension(15, 0)));
        buttons.add(btnCursosProfesores);
        buttons.add(Box.createHorizontalGlue());

        center.add(buttons, BorderLayout.CENTER);
        return center;
    }

    private JComponent buildRightPanel() {
        var right = new JPanel(new BorderLayout());
        right.setBackground(BG);
        right.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        var title = new JLabel("Gestionar Facultad", SwingConstants.CENTER);
        title.setFont(TITLE_FONT);
        right.add(title, BorderLayout.NORTH);

        var buttons = new JPanel();
        buttons.setBackground(BG);
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));

        buttons.add(btnGestionarCursos);
        buttons.add(Box.createRigidArea(new Dimension(15, 0)));
        buttons.add(btnGestionarProgramas);
        buttons.add(Box.createRigidArea(new Dimension(15, 0)));
        buttons.add(btnGestionarFacultad);
        buttons.add(Box.createHorizontalGlue());

        right.add(buttons, BorderLayout.CENTER);
        return right;
    }

    private JComponent buildBottomPanel() {
        var p = new JPanel(new BorderLayout());
        p.setBackground(BG);
        p.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));

        var label = new JLabel("© Creado por Dhaniel y Daniel, un poquis de GPT y otro poquis de Gemini :D",
                SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.PLAIN, 12));

        // Botón Salir a la izquierda, texto centrado
        p.add(btnSalir, BorderLayout.WEST);
        p.add(label, BorderLayout.CENTER);
        return p;
    }

    // ---------- Listeners y API de la vista ----------

    private void wireActions() {
        btnInscribir.addActionListener(e -> { if (onInscribir != null) onInscribir.run(); });
        btnInscribirCurso.addActionListener(e -> { if (onInscribirCurso != null) onInscribirCurso.run(); });
        btnCursosInscritos.addActionListener(e -> { if (onCursosInscritos != null) onCursosInscritos.run(); });
        btnCursosProfesores.addActionListener(e -> { if (onCursosProfesores != null) onCursosProfesores.run(); });
        btnGestionarCursos.addActionListener(e -> { if (onGestionarCursos != null) onGestionarCursos.run(); });
        btnGestionarProgramas.addActionListener(e -> { if (onGestionarProgramas != null) onGestionarProgramas.run(); });
        btnGestionarFacultad.addActionListener(e -> { if (onGestionarFacultad != null) onGestionarFacultad.run(); });
    }

    @Override public void showUI() { setVisible(true); }

    @Override public void onInscribirPersona(Runnable r) { this.onInscribir = r; }
    @Override public void onInscribirCurso(Runnable r) { this.onInscribirCurso = r; }
    @Override public void onCursosInscritos(Runnable r) { this.onCursosInscritos = r; }
    @Override public void onCursosProfesores(Runnable r) { this.onCursosProfesores = r; }
    @Override public void onGestionarCursos(Runnable r) { this.onGestionarCursos = r; }
    @Override public void onGestionarProgramas(Runnable r) { this.onGestionarProgramas = r; }
    @Override public void onGestionarFacultad(Runnable r) { this.onGestionarFacultad = r; }

    @Override public void showMessage(String msg) { JOptionPane.showMessageDialog(this, msg); }

    @Override public void showError(String msg, Throwable t) {
        JOptionPane.showMessageDialog(
                this,
                msg + (t != null ? "\n" + t.getMessage() : ""),
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }

    /* Compatibilidad con tu código existente
    @Override public void setOnNuevoEstudiante(Runnable action) {
        btnInscribir.addActionListener(e -> action.run());
    }
*/
    @Override public void setOnSalir(Runnable action) {
        btnSalir.addActionListener(e -> action.run());
    }

    // ⚠️ NUEVO: implementación para abrir el diálogo
    @Override
    public void showInscribirPersonaDialog(EstudianteServicio estSrv, ProfesorServicio profSrv) {
        InscribirPersonaDialog dlg = new InscribirPersonaDialog(this, estSrv, profSrv);
        dlg.setLocationRelativeTo(this);
        dlg.setVisible(true);
    }

    // --- main de prueba (opcional) ---
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainMenuFrame().showUI());
    }
}
