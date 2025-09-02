package com.ejemplo.ui.presenter;

import com.ejemplo.Servicios.Personas.EstudianteServicio;
import com.ejemplo.Servicios.Personas.ProfesorServicio;
import com.ejemplo.ui.view.MainMenuView;

public class MainMenuPresenter {

    private final MainMenuView view;
    private final EstudianteServicio estudianteServicio;
    private final ProfesorServicio profesorServicio;

    public MainMenuPresenter(MainMenuView view,
                             EstudianteServicio estudianteServicio,
                             ProfesorServicio profesorServicio) {
        this.view = view;
        this.estudianteServicio = estudianteServicio;
        this.profesorServicio = profesorServicio;
        initListeners();
    }

    private void initListeners() {
        view.onInscribirPersona(this::abrirDialogEstudiante); // usa onInscribirPersona
        view.setOnSalir(this::salirAplicacion);
    }

    private void abrirDialogEstudiante() {
        // Delegamos en la vista
        view.showInscribirPersonaDialog(estudianteServicio, profesorServicio);
    }

    private void salirAplicacion() {
        System.exit(0);
    }
}
