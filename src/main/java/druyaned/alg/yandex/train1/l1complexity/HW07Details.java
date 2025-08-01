package druyaned.alg.yandex.train1.l1complexity;

import java.util.Scanner;

public class HW07Details {

    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt(); // кг металлического сплава
        int k = scanner.nextInt(); // вес заготовки
        int m = scanner.nextInt(); // вес детали
        System.out.println(detailsCount(n, k, m));
    }
    
    public static int detailsCount(int n, int k, int m) {
        if (n < k || n < m || k < m) {
            return 0;
        }
        int billetsCount = n / k;
        int detailsPerBillet = k / m;
        int detailsCount = billetsCount * detailsPerBillet;
        int detailsWeight = detailsCount * m;
        for (int left = n - detailsWeight; k <= left; left = n - detailsWeight) {
            billetsCount = left / k;
            int currentDetailsCount = billetsCount * detailsPerBillet;
            int currentDetailsWeight = currentDetailsCount * m;
            detailsWeight += currentDetailsWeight;
            detailsCount += currentDetailsCount;
        }
        return detailsCount;
    }

}
