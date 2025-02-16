import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class Task4 {
    public static int[][] turnRightArray(int[][] array1){
        int[][] array = new int[array1[0].length][array1.length]; // 5 2
        for (int j = 0; j < array1.length; j++) { // 2
            for (int i = 0; i < array1[0].length; i++) { // 5
                array[i][j]= array1[j][array1[0].length-1-i];
            }
        }
        return array;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        Scanner console = new Scanner(System.in);
        System.setOut(new PrintStream(System.out, true, "UTF-8"));
        System.out.println("Введите размерность двумерного массива (x на y):");
        int x = console.nextInt();
        int y = console.nextInt();
        int[][] array1 = new int[x][y];
        System.out.println("Введите элементы массива (построчно):");
        for (int j = 0; j<y; j++){ 
            for (int i = 0; i<x; i++){
                array1[i][j]= console.nextInt();
            }
        }
        System.out.println("До поворота");
        for (int j = 0; j<y; j++) {
            for (int i = 0; i < x; i++) {
                System.out.print (array1[i][j]+" ");
            }
            System.out.println();
        }
        int[][] array = turnRightArray(array1);
        System.out.println("После поворота");
        for (int j = 0; j<x; j++) {
            for (int i = 0; i < y; i++) {
                System.out.print (array[i][j]+" ");
            }
            System.out.println();
        }
    }
}
