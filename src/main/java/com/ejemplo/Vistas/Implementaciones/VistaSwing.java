package com.ejemplo.Vistas.Implementaciones;

import java.awt.*;

import javax.swing.*;

import com.ejemplo.Controladores.HoraControlador;
import com.ejemplo.Fabricas.FabricaExterna.FabricaControladores;
import com.ejemplo.Vistas.GUI.*;
import com.ejemplo.Vistas.Interface.Vista;

public class VistaSwing extends Component implements Vista {

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
        frame.setSize(300, 260);
        frame.setLayout(new FlowLayout());

        JButton profesoresBtn = new JButton("Gestión de Profesores");
        JButton facultadesBtn = new JButton("Gestión de Facultades");
        JButton programasBtn = new JButton("Gestión de Programas");
        JButton cursosBtn = new JButton("Gestión de Cursos");
        JButton estudiantesBtn = new JButton("Gestión de Estudiantes");
        JButton consultaHoraBtn = new JButton("Consultar hora del servidor");

        // Acción botón Estudiante
        cursosBtn.addActionListener(e -> mostrarGestionCursos());
        profesoresBtn.addActionListener(e -> mostrarGestionProfesores());
        estudiantesBtn.addActionListener(e -> mostrarGestionEstudiantes());
        facultadesBtn.addActionListener(e -> mostrarGestionFacultades());
        programasBtn.addActionListener(e -> mostrarGestionProgramas());
        consultaHoraBtn.addActionListener(e -> mostrarHora());

        frame.add(cursosBtn);
        frame.add(facultadesBtn);
        frame.add(programasBtn);
        frame.add(profesoresBtn);
        frame.add(estudiantesBtn);
        frame.add(consultaHoraBtn);

        frame.setLocationRelativeTo(null); // Centrar ventana
        frame.setVisible(true);
    }

    private void mostrarGestionCursos() {
        new VistaSwingCursos(fabricaControladores.crearControladorCurso()).setVisible(true);
    }
    private void mostrarGestionProfesores() {
        new VistaSwingProfesores(fabricaControladores.crearControladorProfesor()).setVisible(true);
    }
    private void mostrarGestionEstudiantes() {
        new VistaSwingEstudiantes(fabricaControladores.crearControladorEstudiante()).setVisible(true);
    }
    private void mostrarGestionFacultades() {
        new VistaSwingFacultades(fabricaControladores.crearControladorFacultad()).setVisible(true);
    }
    private void mostrarGestionProgramas(){
        new VistaSwingProgramas(fabricaControladores.crearControladorPrograma()).setVisible(true);
    }
    private void mostrarHora(){
        new VistaSwingHoraServidor(fabricaControladores.crearControladorHora()).setVisible(true);

    }

}
