package com.ejemplo.Utils;

import java.util.Scanner;

public class InputUtils {
    private static final Scanner SC = new Scanner(System.in);

    public static int readInt() {
        while (true) {
            String s = SC.nextLine();
            try { return Integer.parseInt(s.trim()); }
            catch (NumberFormatException e) { System.out.println("Entrada inválida. Intente de nuevo."); }
        }
    }

    public static long readLong() {
        while (true) {
            String s = SC.nextLine();
            try { return Long.parseLong(s.trim()); }
            catch (NumberFormatException e) { System.out.println("Entrada inválida. Intente de nuevo."); }
        }
    }

    public static double readDouble() {
        while (true) {
            String s = SC.nextLine();
            try { return Double.parseDouble(s.trim()); }
            catch (NumberFormatException e) { System.out.println("Entrada inválida. Intente de nuevo."); }
        }
    }

    public static boolean readBoolean() {
        while (true) {
            String s = SC.nextLine().trim().toLowerCase();
            if (s.equals("s") || s.equals("si") || s.equals("sí") || s.equals("y") || s.equals("true")) return true;
            if (s.equals("n") || s.equals("no") || s.equals("false")) return false;
            System.out.println("Responda 's' o 'n'.");
        }
    }

    public static String readLine() {
        return SC.nextLine();
    }

    public static void mostrarError(String mensaje) {
        System.out.println("❌ ERROR: " + mensaje);
    }

    public static void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    public static void pausar() {
        System.out.println("\n📋 Presione ENTER para continuar...");
        SC.nextLine();
    }

    public static void limpiarPantalla() {
        for (int i = 0; i < 40; i++) {
            System.out.println();
        }
    }
}
