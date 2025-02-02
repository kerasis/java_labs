package tasks;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.Scanner;

public class Task1 {
    public static void run() {
        try {
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
            Locale.setDefault(new Locale("ru", "RU"));
        } catch (UnsupportedEncodingException e) {
            System.err.println("Ошибка кодировки вывода.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите целое число: ");

        try {
            int n = scanner.nextInt();
            int iterations = 0;

            while (n != 1) {
                n = (n % 2 == 0) ? n / 2 : 3 * n + 1;
                iterations++;
            }

            System.out.println("Число итераций: " + iterations);
        } catch (Exception e) {
            System.out.println("Ошибка: введено не целое число.");
        }
    }
}
