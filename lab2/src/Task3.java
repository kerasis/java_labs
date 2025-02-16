import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class Task3 {
    public static int maxSumOfSub(int[] array){
        int supSum,maxSum=0;
        for (int i = 0;i<array.length;i++){
            supSum = 0;
            supSum += array[i];
            for (int j = i+1;j<array.length;j++){
                supSum +=array[j];
            }
            if (supSum>maxSum){
                maxSum = supSum;
            }
        }
        return maxSum;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        Scanner console = new Scanner(System.in);
        System.setOut(new PrintStream(System.out, true, "UTF-8"));
        System.out.println("Введите размерность массива:");
        int n = console.nextInt();
        int[] array = new int[n];
        System.out.println("Введите элементы массива:");
        for (int i = 0;i<n;i++){
            array[i] = console.nextInt();
        }
        System.out.println("Максимальная сумма подмассива: " + maxSumOfSub(array));
    }
}