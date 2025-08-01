package druyaned.alg.yandex.train1.l3sets;

import java.util.Scanner;

public class HW05OpenCalculator {

    public static void main(String[] args) {
        Scanner sin = new Scanner(System.in);
        final int DIGITS_AMOUNT = 10;
        boolean[] digits = new boolean[DIGITS_AMOUNT];
        digits[sin.nextInt()] = true;
        digits[sin.nextInt()] = true;
        digits[sin.nextInt()] = true;
        int num = sin.nextInt(), digit;
        int count = 0;
        while (num != 0) {
            digit = num % 10;
            if (!digits[digit]) {
                digits[digit] = true;
                ++count;
            }
            num /= 10;
        }
        System.out.println(count);
    }
    
}
