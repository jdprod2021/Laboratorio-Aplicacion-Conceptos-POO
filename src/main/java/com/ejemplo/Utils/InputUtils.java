package com.ejemplo.Utils;

import java.util.Scanner;

public class InputUtils {
    private static final Scanner SC = new Scanner(System.in);

    public static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = SC.nextLine();
            try {
                return Integer.parseInt(line.trim());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Intente de nuevo.");
            }
        }
    }

    public static String readLine(String prompt) {
        System.out.print(prompt);
        return SC.nextLine();
    }
}
