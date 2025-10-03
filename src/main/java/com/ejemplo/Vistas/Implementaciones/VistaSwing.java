package com.ejemplo.Vistas.Implementaciones;

import javax.swing.*;

import com.ejemplo.Fabricas.FabricaInterna.FabricaControladores;
import com.ejemplo.Vistas.GUI.VistaSwingCursos;
import com.ejemplo.Vistas.Interface.Vista;

import java.awt.*;

public class VistaSwing implements Vista{

    private FabricaControladores fabricaControladores;

    public VistaSwing(FabricaControladores fabricaControladores){
        this.fabricaControladores = fabricaControladores;
    }

    @Override
    public void inicializar() {
        crearMenu();
    }

    public void crearMenu() {
        JFrame frame = new JFrame("Menú Principal - Swing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setLayout(new FlowLayout());

        JButton profesoresBtn = new JButton("Gestión de Profesores");
        JButton facultadesBtn = new JButton("Gestión de Facultades");
        JButton programasBtn = new JButton("Gestión de Programas");
        JButton cursosBtn = new JButton("Gestión de Cursos");
        JButton estudiantesBtn = new JButton("Gestión de Estudiantes");

        // Acción botón Estudiante
        cursosBtn.addActionListener(e -> mostrarGestionCursos());

        frame.add(cursosBtn);
        frame.add(facultadesBtn);
        frame.add(programasBtn);
        frame.add(profesoresBtn);
        frame.add(estudiantesBtn);

        frame.setLocationRelativeTo(null); // Centrar ventana
        frame.setVisible(true);
    }

    private void mostrarGestionCursos() {
        new VistaSwingCursos(fabricaControladores.crearControladorCurso()).setVisible(true);
    }
}
