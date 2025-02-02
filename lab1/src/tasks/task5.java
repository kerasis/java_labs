package tasks;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.Scanner;

public class Task5 {
    public static void run() {
        try {
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
            Locale.setDefault(new Locale("ru", "RU"));
        } catch (UnsupportedEncodingException e) {
            System.err.println("Ошибка кодировки вывода.");
            return;
        }
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите трёхзначное число: ");
        int number = scanner.nextInt();
        int hundreds = number / 100;
        int tens = (number / 10) % 10;
        int units = number % 10;
        boolean isDoubleEven = (hundreds + tens + units) % 2 == 0 && (hundreds * tens * units) % 2 == 0;

        if (isDoubleEven) {
            System.out.println("Число является дважды чётным.");
        } else {
            System.out.println("Число не является дважды чётным.");
        }
    }
}
