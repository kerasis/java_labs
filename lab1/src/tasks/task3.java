package tasks;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.Scanner;

public class Task3 {
    public static void run() {
        try {
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
            Locale.setDefault(new Locale("ru", "RU"));
        } catch (UnsupportedEncodingException e) {
            System.err.println("Ошибка кодировки вывода.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите координату X: ");
        int targetX = scanner.nextInt();
        System.out.print("Введите координату Y: ");
        int targetY = scanner.nextInt();
        scanner.nextLine();  
        int currentX = 0, currentY = 0, steps = 0;

        while (true) {
            String direction = scanner.nextLine().trim().toLowerCase();
            if (direction.equals("стоп")) break;
            int distance = scanner.nextInt();
            scanner.nextLine();

            switch (direction) {
                case "север" -> currentY += distance;
                case "юг"    -> currentY -= distance;
                case "восток"-> currentX += distance;
                case "запад" -> currentX -= distance;
                default      -> {
                    System.out.println("Ошибка: некорректный ввод направления.");
                    return;
                }
            }
            steps++;
            if (currentX == targetX && currentY == targetY) break;
        }

        if (currentX == targetX && currentY == targetY) {
            System.out.println("Количество шагов: " + steps);
        } else {
            System.out.println("Ошибка: до клада не дойти.");
        }
    }
}
