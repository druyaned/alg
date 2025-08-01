package druyaned.alg.yandex.train1.l1complexity;

import java.util.Scanner;

public class HW04EquationRoot {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int a = scanner.nextInt();
        int b = scanner.nextInt();
        int c = scanner.nextInt();
        if (a == 0) {
            if (c < 0 || b < 0 || b != c * c) {
                System.out.println("NO SOLUTION");
            } else {
                System.out.println("MANY SOLUTIONS");
            }
            return;
        }
        int ax = c * c - b;
        final int minAX = -b;
        if (c < 0 || ax < minAX || ax % a != 0) {
            System.out.println("NO SOLUTION");
        } else {
            System.out.println(ax / a);
        }
    }

}
