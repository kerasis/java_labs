import java.util.Arrays;
import java.util.Scanner;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

public class Task2 {
    
    public static int[] mergeAndSort(int[] array1, int[] array2) {
        // Объединение и сортировка массивов
        int[] mergedArray = new int[array1.length + array2.length];
        System.arraycopy(array1, 0, mergedArray, 0, array1.length);
        System.arraycopy(array2, 0, mergedArray, array1.length, array2.length);
        Arrays.sort(mergedArray);
        return mergedArray;
    }
    
    public static int[] mergeSortedArrays(int[] array1, int[] array2) {
        // Объединение двух отсортированных массивов
        int[] mergedArray = new int[array1.length + array2.length];
        int i = 0, j = 0, k = 0;
        
        while (i < array1.length && j < array2.length) {
            if (array1[i] <= array2[j]) {
                mergedArray[k++] = array1[i++];
            } else {
                mergedArray[k++] = array2[j++];
            }
        }
        
        while (i < array1.length) {
            mergedArray[k++] = array1[i++];
        }
        
        while (j < array2.length) {
            mergedArray[k++] = array2[j++];
        }
        
        return mergedArray;
    }
    
    public static void main(String[] args) throws UnsupportedEncodingException {
        Scanner scanner = new Scanner(System.in);
        System.setOut(new PrintStream(System.out, true, "UTF-8"));
        
        System.out.print("Введите размерность массива 1: ");
        int n1 = scanner.nextInt();
        int[] array1 = new int[n1];
        System.out.println("Введите элементы массива 1: ");
        for (int i = 0; i < n1; i++) {
            array1[i] = scanner.nextInt();
        }
        
        System.out.print("Введите размерность массива 2: ");
        int n2 = scanner.nextInt();
        int[] array2 = new int[n2];
        System.out.println("Введите элементы массива 2: ");
        for (int i = 0; i < n2; i++) {
            array2[i] = scanner.nextInt();
        }
        
        int[] mergedSorted = mergeSortedArrays(array1, array2);
        int[] mergedAny = mergeAndSort(array1, array2);
        
        System.out.println("Объединенный и отсортированный массив (независимо от исходного порядка):");
        System.out.println(Arrays.toString(mergedAny));
        
        System.out.println("Объединенный массив из двух уже отсортированных массивов:");
        System.out.println(Arrays.toString(mergedSorted));
    }
}

