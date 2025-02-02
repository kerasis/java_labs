package tasks;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.Scanner;

public class Task2 {
    public static void run() {
        try {
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
            Locale.setDefault(new Locale("ru", "RU"));
        } catch (UnsupportedEncodingException e) {
            System.err.println("Ошибка кодировки вывода.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите количество чисел: ");
        int n = scanner.nextInt();
        int sum = 0;

        System.out.println("Введите " + n + " чисел:");
        for (int i = 0; i < n; i++) {
            int number = scanner.nextInt();
            sum += (i % 2 == 0) ? number : -number;
        }

        System.out.println("Сумма знакочередующегося ряда: " + sum);
    }
}

