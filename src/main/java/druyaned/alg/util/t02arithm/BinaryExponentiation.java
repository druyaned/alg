package druyaned.alg.util.t02arithm;

public class BinaryExponentiation {
    
    public static final long MOD = (long)1e9 + 7L;
    
    public static long power(long base, long exp) {
        long result = 1L;
        while (exp > 0L) {
            if ( (exp & 1L) == 1L )
                result = (result * base) % MOD;
            base = (base * base) % MOD;
            exp >>>= 1;
        }
        return result;
    }
    
}
/*
base=3 exp=11
(11)_10 = (1011)_2
   res |  base |      exp
-------+-------+---------
   3^1 |   3^1 | (1011)_2
   3^3 |   3^2 |  (101)_2
   3^3 |   3^4 |   (10)_2
  3^11 |   3^8 |    (1)_2
*/
