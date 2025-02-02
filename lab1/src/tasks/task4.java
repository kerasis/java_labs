package tasks;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.Scanner;

public class Task4 {
    public static void run() {
        try {
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
            Locale.setDefault(new Locale("ru", "RU"));
        } catch (UnsupportedEncodingException e) {
            System.err.println("Ошибка кодировки вывода.");
            return;
        }
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите количество дорог: ");
        int roads = scanner.nextInt();
        int maxTunnelHeight = 0, bestRoadIndex = -1;
        for (int i = 1; i <= roads; i++) {
            System.out.print("Введите количество туннелей на дороге " + i + ": ");
            int tunnels = scanner.nextInt();
            int minTunnelHeight = Integer.MAX_VALUE;

            for (int j = 0; j < tunnels; j++) {
                System.out.print("Введите высоту туннеля: ");
                int height = scanner.nextInt();
                minTunnelHeight = Math.min(minTunnelHeight, height);
            }
            if (minTunnelHeight > maxTunnelHeight) {
                maxTunnelHeight = minTunnelHeight;
                bestRoadIndex = i;
            }
        }
        System.out.println("Лучшая дорога: " + bestRoadIndex + " с высотой туннеля: " + maxTunnelHeight);
    }
}
