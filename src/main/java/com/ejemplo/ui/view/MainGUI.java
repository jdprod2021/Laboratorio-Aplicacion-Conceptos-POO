package com.ejemplo.ui.view;
import javax.swing.*;
import java.awt.*;


public class MainGUI extends JFrame {

    public static void createAndShowGUI(){
        JFrame frame = new JFrame("Sistema de Información Académica");
        frame.setSize(1080, 720);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // TOP PANEL
        JPanel top = new JPanel();
        top.setPreferredSize(new Dimension(720, 30));
        top.setBackground(Color.lightGray);
        JLabel title = new JLabel("Bienvenido al sistema de gestión e inscripción de cursos");
        title.setFont(new Font("Arial", Font.BOLD, 16));
        top.add(title);
        frame.getContentPane().add(top, BorderLayout.PAGE_START);



        // LEFT PANEL (Placeholder)
        JPanel left = new JPanel(new BorderLayout());
        left.setPreferredSize(new Dimension(200, 100));
        left.setBackground(Color.lightGray);


        JPanel titleLeftPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel Inscripcion = new JLabel("Inscripción De Personas", SwingConstants.CENTER);
        Inscripcion.setFont(new Font("Arial", Font.BOLD, 16));
        titleLeftPanel.add(Inscripcion);
        left.add(titleLeftPanel, BorderLayout.PAGE_START);


        

        frame.getContentPane().add(left, BorderLayout.LINE_START);


        // ---
        // CORRECTED CENTER PANEL
        // ---
        JPanel mainCenterPanel = new JPanel(new BorderLayout());
        mainCenterPanel.setBackground(Color.LIGHT_GRAY);

        JPanel subtitlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel subtitle = new JLabel("Gestionar Cursos", SwingConstants.CENTER);
        subtitle.setFont(new Font("Arial", Font.BOLD, 16));
        subtitlePanel.add(subtitle);
        mainCenterPanel.add(subtitlePanel, BorderLayout.NORTH);

        JPanel buttonPanelCenter = new JPanel();
        buttonPanelCenter.setLayout(new BoxLayout(buttonPanelCenter, BoxLayout.X_AXIS));
        buttonPanelCenter.add(Box.createHorizontalGlue());

        JButton inscribir = new JButton("Inscribir Cursos");
        JButton verCursos = new JButton("Ver Cursos Inscritos");
        JButton cursosProfesores = new JButton("Ver Cursos De Profesores");

        buttonPanelCenter.add(inscribir);
        buttonPanelCenter.add(Box.createRigidArea(new Dimension(15, 0)));
        buttonPanelCenter.add(verCursos);
        buttonPanelCenter.add(Box.createRigidArea(new Dimension(15, 0)));
        buttonPanelCenter.add(cursosProfesores);

        buttonPanelCenter.add(Box.createHorizontalGlue());
        mainCenterPanel.add(buttonPanelCenter, BorderLayout.CENTER);

        // ADDING THE CENTER PANEL TO THE FRAME
        frame.getContentPane().add(mainCenterPanel, BorderLayout.CENTER);


        // ---
        // CORRECTED RIGHT PANEL
        // ---
        JPanel mainRightPanel = new JPanel(new BorderLayout());
        mainRightPanel.setPreferredSize(new Dimension(250, 200));
        mainRightPanel.setBackground(Color.lightGray);

        JPanel rightSubtitlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel gestionFacultad = new JLabel("Gestionar Facultad", SwingConstants.CENTER);
        gestionFacultad.setFont(new Font("Arial", Font.BOLD, 16));
        rightSubtitlePanel.add(gestionFacultad);
        mainRightPanel.add(rightSubtitlePanel, BorderLayout.NORTH);

        JPanel rightButtonPanel = new JPanel();
        rightButtonPanel.setLayout(new BoxLayout(rightButtonPanel, BoxLayout.Y_AXIS));
        rightButtonPanel.add(Box.createVerticalGlue());

        JButton gestionarCursos = new JButton("Gestionar Cursos");
        JButton gestionarPrograma = new JButton("Gestionar Programa");
        JButton gestionarFacultadBtn = new JButton("Gestionar Facultad");

        gestionarCursos.setAlignmentX(Component.CENTER_ALIGNMENT);
        gestionarPrograma.setAlignmentX(Component.CENTER_ALIGNMENT);
        gestionarFacultadBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        rightButtonPanel.add(gestionarCursos);
        rightButtonPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        rightButtonPanel.add(gestionarPrograma);
        rightButtonPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        rightButtonPanel.add(gestionarFacultadBtn);

        rightButtonPanel.add(Box.createVerticalGlue());
        mainRightPanel.add(rightButtonPanel, BorderLayout.CENTER);

        // ADDING THE RIGHT PANEL TO THE FRAME
        frame.getContentPane().add(mainRightPanel, BorderLayout.LINE_END);


        // BOTTOM PANEL (Placeholder)
        JPanel bottom = new JPanel();
        bottom.setPreferredSize(new Dimension(300, 50));
        bottom.setBackground(Color.red);
        frame.getContentPane().add(bottom, BorderLayout.PAGE_END);

        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainGUI::createAndShowGUI);
    }
}