package com.ejemplo.ui.view;

import javax.swing.*;

import java.awt.*;

public class MainMenuFrame extends JFrame implements MainMenuView {

    private final JButton btnInscribir = new JButton("Inscribir Persona");
    private final JButton btnInscribirCurso = new JButton("Inscribir Curso");
    private final JButton btnCursosInscritos = new JButton("Ver Cursos");
    private final JButton btnCursosProfesores = new JButton("Ver Cursos de  Profesores");
    private final JButton btnGestionarCursos = new JButton("Gestionar Cursos");
    private final JButton btnGestionarProgramas = new JButton("Gestionar Programas");
    private final JButton btnGestionarFacultad = new  JButton("Gestionar Facultad");



    private Runnable onInscribir;
    private Runnable onInscribirCurso;
    private Runnable onCursosInscritos;
    private Runnable onCursosProfesores;
    private Runnable onGestionarCursos;
    private Runnable onGestionarProgramas;
    private Runnable onGestionarFacultad;




    private void topPanel(){
        var topPanel = new JPanel(new GridBagLayout());
        setPreferredSize(new Dimension(720, 30));
        topPanel.setBackground(Color.lightGray);
        JLabel title = new JLabel("Bienvenido al sistema de gestión e inscripción de cursos");
        title.setFont(new Font("Arial", Font.BOLD, 16));
        topPanel.add(title);
        getContentPane().add(topPanel, BorderLayout.PAGE_START);

    }

    private void leftPanel(){
        var leftPanel = new JPanel(new GridBagLayout());
        setPreferredSize(new Dimension(720, 30));
        leftPanel.setBackground(Color.lightGray);
        JLabel title = new JLabel("Inscribir persona");
        title.setFont(new Font("Arial", Font.BOLD, 16));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        ImageIcon icon = new ImageIcon(getClass().getResource("/resources/user_logo.png"));
        JLabel imageLabel = new JLabel(icon);

        gbc.gridx = 0; // Columna 0
        gbc.gridy = 0; // Fila 0

        leftPanel.add(imageLabel, gbc);


        leftPanel.add(btnInscribir);

        btnInscribir.addActionListener(e -> {
            if (onInscribir != null) onInscribir.run();
        });



    }

    private void centerPanel(){

        var centerPanel = new JPanel(new GridBagLayout());
        setPreferredSize(new Dimension(720, 30));
        centerPanel.setBackground(Color.lightGray);
        JLabel title = new JLabel("Gestionar Cursos");
        title.setFont(new Font("Arial", Font.BOLD, 16));
        centerPanel.add(title, BorderLayout.CENTER);
        getContentPane().add(centerPanel, BorderLayout.CENTER);


        var buttonPanelCenter = new JPanel();
        buttonPanelCenter.setLayout(new BoxLayout(buttonPanelCenter, BoxLayout.X_AXIS));
        buttonPanelCenter.setBackground(Color.lightGray);

        buttonPanelCenter.add(btnInscribirCurso);
        buttonPanelCenter.add(Box.createRigidArea(new Dimension(15, 0)));
        buttonPanelCenter.add(btnCursosInscritos);
        buttonPanelCenter.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanelCenter.add(btnCursosProfesores);
        buttonPanelCenter.add(Box.createRigidArea(new Dimension(0, 10)));

        buttonPanelCenter.add(Box.createHorizontalGlue());
        centerPanel.add(buttonPanelCenter, BorderLayout.CENTER);


        btnInscribirCurso.addActionListener(e -> {
            if (onInscribirCurso != null) onInscribirCurso.run();
        });
        btnCursosInscritos.addActionListener(e -> {
            if (onCursosInscritos != null) onCursosInscritos.run();
        });
        btnCursosProfesores.addActionListener(e -> {
            if (onCursosProfesores != null) onCursosProfesores.run();
        });


    }


    private void  rightPanel(){
        var rightPanel = new JPanel(new GridBagLayout());
        setPreferredSize(new Dimension(720, 30));
        rightPanel.setBackground(Color.lightGray);
        JLabel title = new JLabel("Gestionar Facultad");
        title.setFont(new Font("Arial", Font.BOLD, 16));
        rightPanel.add(title, BorderLayout.CENTER);
        getContentPane().add(rightPanel, BorderLayout.CENTER);

        var buttonPanelRight = new JPanel();
        buttonPanelRight.setLayout(new BoxLayout(buttonPanelRight, BoxLayout.X_AXIS));
        buttonPanelRight.setBackground(Color.lightGray);

        buttonPanelRight.add(btnGestionarCursos);
        buttonPanelRight.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanelRight.add(btnGestionarProgramas);
        buttonPanelRight.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanelRight.add(btnGestionarFacultad);
        buttonPanelRight.add(Box.createRigidArea(new Dimension(0, 10)));

        buttonPanelRight.add(Box.createHorizontalGlue());
        rightPanel.add(buttonPanelRight, BorderLayout.CENTER);

        btnGestionarCursos.addActionListener(e -> {
            if(onGestionarCursos!= null) onGestionarCursos.run();
        });
        btnGestionarProgramas.addActionListener(e -> {
            if(onGestionarProgramas!= null) onGestionarProgramas.run();
        });
        btnGestionarFacultad.addActionListener(e -> {
            if(onGestionarFacultad!= null) onGestionarFacultad.run();
        });



    }
    private void bottomPanel() {

        var bottomPanel = new JPanel(new GridBagLayout());
        setPreferredSize(new Dimension(720, 30));
        bottomPanel.setBackground(Color.lightGray);
        JLabel title = new JLabel("© Creado por Dhaniel y Daniel, un poquis de GPT y otro poquis de Gemini :D");
        title.setFont(new Font("Arial", Font.BOLD, 16));
        bottomPanel.add(title);




    }

    public MainMenuFrame() {
        setTitle("Sistema Académico - Menú Principal");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);

        topPanel();
        leftPanel();
        centerPanel();
        rightPanel();
        bottomPanel();





    }

    @Override public void showUI() { setVisible(true); }
    @Override public void onInscribirPersona(Runnable r) { this.onInscribir = r; }
    @Override public void onInscribirCurso(Runnable r) { this.onCursosInscritos = r; }
    @Override public void onCursosInscritos(Runnable r) { this.onCursosInscritos = r; }
    @Override public void onCursosProfesores(Runnable r) { this.onCursosProfesores = r; }
    @Override public void onGestionarCursos(Runnable r) { this.onGestionarCursos = r; }
    @Override public void onGestionarProgramas(Runnable r) { this.onGestionarProgramas = r; }
    @Override public void onGestionarFacultad(Runnable r) { this.onGestionarFacultad = r; }
    @Override public void showMessage(String msg) { JOptionPane.showMessageDialog(this, msg); }
    @Override public void showError(String msg, Throwable t) {
        JOptionPane.showMessageDialog(this, msg + (t != null ? "\n" + t.getMessage() : ""), "Error", JOptionPane.ERROR_MESSAGE);
    }
}