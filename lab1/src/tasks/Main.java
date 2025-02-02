package tasks; 
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Выберите задачу (1-5):");
        int choice = scanner.nextInt();
        switch (choice) {
            case 1 -> Task1.run();
            case 2 -> Task2.run();
            case 3 -> Task3.run();
            case 4 -> Task4.run();
            case 5 -> Task5.run();
            default -> System.out.println("Некорректный выбор.");
        }
    }
}
