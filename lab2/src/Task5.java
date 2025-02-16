import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class Task5 {
    
    public static String findTargetSumPair(int[] array, int target) {
        for (int i = 0; i < array.length; i++) {
            for (int j = i + 1; j < array.length; j++) {
                if (array[i] + array[j] == target) {
                    return array[i] + " " + array[j];
                }
            }
        }
        return "Пара не найдена";
    }
    
    public static void main(String[] args) throws UnsupportedEncodingException {
        Scanner scanner = new Scanner(System.in);
        System.setOut(new PrintStream(System.out, true, "UTF-8"));
        
        System.out.print("Введите число target: ");
        int target = scanner.nextInt();
        
        System.out.print("Введите размерность массива: ");
        int n = scanner.nextInt();
        int[] array = new int[n];
        
        System.out.println("Введите элементы массива:");
        for (int i = 0; i < n; i++) {
            array[i] = scanner.nextInt();
        }
        
        String result = findTargetSumPair(array, target);
        System.out.println(result);
    }
}
