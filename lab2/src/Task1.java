import java.util.Scanner;
import java.util.HashSet;
import java.util.Set;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
public class Task1 {
    public static String findLongUniqueSub(String input){
        String longSub = "";
        int maxLen = 0;

        for (int i = 0; i < input.length(); i++) {
            for (int j = i; j < input.length(); j++) {
                String curSub = input.substring(i, j + 1);

                if (areUnique(curSub)) {
                    if (curSub.length() > maxLen) {
                        maxLen = curSub.length();
                        longSub = curSub;
                    }
                }
            }
        }

        return longSub;
    }
    private static boolean areUnique(String str) { 
        Set<Character> charSet = new HashSet<>(); 
        for (char c : str.toCharArray()) {
            if (!charSet.add(c)) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        System.setOut(new PrintStream(System.out, true, "UTF-8"));
        Scanner console = new Scanner(System.in);
        System.out.println("Введите строку");
        String stroka = console.nextLine();
        System.out.println("Наибольшая подстрока " + findLongUniqueSub(stroka));
    }
}
