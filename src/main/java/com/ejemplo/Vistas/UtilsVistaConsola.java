package com.ejemplo.Vistas;

import java.util.Scanner;

public abstract class UtilsVistaConsola {
    protected Scanner scanner;

    public UtilsVistaConsola(Scanner scanner) {
        this.scanner = scanner;
    }

    protected String leerTexto(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    protected long leerLong(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                long valor = Long.parseLong(scanner.nextLine().trim());
                return valor;
            } catch (NumberFormatException e) {
                System.out.println("❌ Por favor ingrese un número válido.");
            }
        }
    }

    protected void mostrarError(String mensaje) {
        System.out.println("❌ ERROR: " + mensaje);
    }

    protected void mostrarMensaje(String mensaje) {
        System.out.println("ℹ️ " + mensaje);
    }

    protected void pausar() {
        System.out.println("\n📋 Presione ENTER para continuar...");
        scanner.nextLine();
    }

    protected void limpiarPantalla() {
        for (int i = 0; i < 40; i++) {
            System.out.println();
        }
    }
    protected void mostrarBanner(String titulo) {
        System.out.println("╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║  🎓 " + titulo + " 🎓     ║");
        System.out.println("║                                                               ║");
        System.out.println("║  Desarrollado con patrón Factory para máxima flexibilidad     ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝");
        System.out.println();
    }

    protected int leerOpcionMenu() {
        try {
            int opcion = Integer.parseInt(scanner.nextLine().trim());
            return opcion;
        } catch (NumberFormatException e) {
            mostrarError("Por favor ingrese un número válido.");
            pausar();
            return -1;
        }
    }
    
}
