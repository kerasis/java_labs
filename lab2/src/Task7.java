import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class Task7 {
    
    public static int[] oneDimArrOfTwoDim(int[][] twoDim){
        int[] array = new int[twoDim[0].length];
        for (int j = 0; j<twoDim[0].length; j++) {
            int max = 0;
            for (int i = 0;i<twoDim.length; i++) {
                if (max < twoDim[i][j]) {
                    max = twoDim[i][j];
                }
            }
            array[j] = max;
        }
        return array;
    }
    public static void main(String[] args) throws UnsupportedEncodingException {
        Scanner console = new Scanner(System.in);
        System.setOut(new PrintStream(System.out, true, "UTF-8"));
        System.out.println("Введите размерность двумерного массива (x на y):");
        int x = console.nextInt();
        int y = console.nextInt();
        int[][] array = new int[x][y];
        System.out.println("Введите элементы массива (построчно):");
        for (int j = 0; j<y; j++){ 
            for (int i = 0; i<x; i++){
                array[i][j]= console.nextInt();
            }
        }
        int[] oneDim = oneDimArrOfTwoDim(array);
        for (int i = 0;i<oneDim.length;i++) {
            System.out.print(oneDim[i] + " ");
        }
    }
}
