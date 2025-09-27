package com.ejemplo.Utils;

import java.util.Scanner;

public class InputUtils {
    private static final Scanner SC = new Scanner(System.in);

    public static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = SC.nextLine();
            try { return Integer.parseInt(s.trim()); }
            catch (NumberFormatException e) { System.out.println("Entrada inválida. Intente de nuevo."); }
        }
    }

    public static long readLong(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = SC.nextLine();
            try { return Long.parseLong(s.trim()); }
            catch (NumberFormatException e) { System.out.println("Entrada inválida. Intente de nuevo."); }
        }
    }

    public static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = SC.nextLine();
            try { return Double.parseDouble(s.trim()); }
            catch (NumberFormatException e) { System.out.println("Entrada inválida. Intente de nuevo."); }
        }
    }

    public static boolean readBoolean(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = SC.nextLine().trim().toLowerCase();
            if (s.equals("s") || s.equals("si") || s.equals("sí") || s.equals("y") || s.equals("true")) return true;
            if (s.equals("n") || s.equals("no") || s.equals("false")) return false;
            System.out.println("Responda 's' o 'n'.");
        }
    }

    public static String readLine(String prompt) {
        System.out.print(prompt);
        return SC.nextLine();
    }
}
