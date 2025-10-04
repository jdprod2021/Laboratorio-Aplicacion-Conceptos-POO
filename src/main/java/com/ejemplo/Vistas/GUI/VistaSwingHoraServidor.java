package com.ejemplo.Vistas.GUI;

import com.ejemplo.Controladores.HoraControlador;
import com.ejemplo.Fabricas.FabricaExterna.FabricaControladores;

import javax.swing.*;
import java.awt.*;


public class VistaSwingHoraServidor extends JFrame {

    private HoraControlador controlador;
    private FabricaControladores fabricaControladores;

    public VistaSwingHoraServidor(HoraControlador controlador) {
        this.controlador = controlador;
        initUI();
    }

    private void initUI(){
        JOptionPane.showMessageDialog(this, "Hora del servidor: " + controlador.obtenerHoraServidor());

    }

}
