package com.ejemplo.Vistas.GUI;

import com.ejemplo.Controladores.HoraControlador;

import javax.swing.*;



public class VistaSwingHoraServidor extends JFrame {

    private HoraControlador controlador;

    public VistaSwingHoraServidor(HoraControlador controlador) {
        this.controlador = controlador;
        initUI();
    }

    private void initUI(){
        JOptionPane.showMessageDialog(this, "Hora del servidor: " + controlador.obtenerHoraServidor());

    }

}
