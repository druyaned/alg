package druyaned.alg.util.t02arithm;

public class GcdAndLcm {
    
    public static long greatestCommonDivisor(long v1, long v2) {
        return (v2 == 0L) ? v1 : greatestCommonDivisor(v2, v1%v2);
    }
    
    public static long gcd(long v1, long v2) {
        while (v2 != 0L) {
            long stock = v1;
            v1 = v2;
            v2 = stock % v2;
        }
        return v1;
    }
    
    public static long leastCommonMultiple(long v1, long v2) {
        return v1 / greatestCommonDivisor(v1, v2) * v2;
    }
    
}
/*
v1 = 2 * 2 * 3 * 7 * 7 = 588
v2 = 2 * 7 * 7 * 11 = 1078
gdc(588, 1078) = 2 * 7 * 7 = 98

gcd(588, 1078)
gcd(1078, 588)
gcd(588, 490)
gcd(490, 98)
gcd(98, 0)
gcd(588, 1078)=98

r
    = v1 % v2
    = v1 - (v1 / v2) * v2
gdc(v1, v2)
    = gdc(v2, v1 - (v1 / v2) * v2);
2 * 7 * 7 * 11 - x1 * 2 * 2 * 3 * 7 * 7 = 2 * 7 * 7 * (11 - x1 * 2 * 3) = 2 * 7 * 7 * 5
2 * 2 * 3 * 7 * 7 - x2 * 2 * 7 * 7 * 5 = 2 * 7 * 7 * (2 * 3 - x2 * 5) = 2 * 7 * 7
2 * 7 * 7 * 5 - x3 * 2 * 7 * 7 = 2 * 7 * 7 * (5 - x3) = 0
*/
