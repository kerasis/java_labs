import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class Task6 {
    
    public static int sumOfTwoDimArray(int[][] array) {
        int sum = 0;
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                sum += array[i][j];
            }
        }
        return sum;
    }
    
    public static void main(String[] args) throws UnsupportedEncodingException {
        Scanner scanner = new Scanner(System.in);
        System.setOut(new PrintStream(System.out, true, "UTF-8"));
        
        System.out.print("Введите размерность двумерного массива (x y): ");
        int x = scanner.nextInt();
        int y = scanner.nextInt();
        int[][] array = new int[x][y];
        
        System.out.println("Введите элементы массива (построчно):");
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                array[i][j] = scanner.nextInt();
            }
        }
        
        System.out.println("Сумма элементов массива: " + sumOfTwoDimArray(array));
    }
}
