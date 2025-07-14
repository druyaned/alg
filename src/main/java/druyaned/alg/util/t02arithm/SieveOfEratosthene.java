package druyaned.alg.util.t02arithm;

public class SieveOfEratosthene {
    
    public static int[] fullSieveOfEratosthenes(int maxVal) {
        int sqrtMaxVal = (int)Math.sqrt(maxVal);
        boolean[] prime = new boolean[maxVal + 1];
        for (int p = 2; p <= maxVal; p++)
            prime[p] = true;
        for (int p = 2; p <= sqrtMaxVal; p++) {
            if (prime[p]) {
                for (int f = p * p; f <= maxVal; f += p)
                    prime[f] = false;
            }
        }
        int count = 0;
        for (int p = 2; p <= maxVal; p++) {
            if (prime[p])
                count++;
        }
        int[] sieve = new int[count];
        int size = 0;
        for (int p = 2; p <= maxVal; p++) {
            if (prime[p])
                sieve[size++] = p;
        }
        return sieve;
    }
    
    public static int[] shortSieveOfEratosthenes(int maxVal) {
        int sqrtMaxVal = (int)Math.sqrt(maxVal);
        boolean[] prime = new boolean[sqrtMaxVal + 1];
        for (int p = 2; p <= sqrtMaxVal; p++)
            prime[p] = true;
        int count = 0;
        for (int p = 2; p <= sqrtMaxVal; p++) {
            if (prime[p]) {
                count++;
                for (int f = p * p; f <= sqrtMaxVal; f += p)
                    prime[f] = false;
            }
        }
        int[] sieve = new int[count];
        int size = 0;
        for (int p = 2; p <= sqrtMaxVal; p++) {
            if (prime[p])
                sieve[size++] = p;
        }
        return sieve;
    }
    
    public static int[] getMultipliers(int[] shortSieve, int num) {
        int[] multipliers = new int[shortSieve.length + 1];
        int size = 0;
        int numCopy = num;
        for (int i = 0; i < shortSieve.length && numCopy >= shortSieve[i]; i++) {
            if (numCopy % shortSieve[i] == 0)
                multipliers[size++] = shortSieve[i];
            while (numCopy % shortSieve[i] == 0)
                numCopy /= shortSieve[i];
        }
        if (numCopy > 1)
            multipliers[size++] = numCopy;
        int[] multipliersFitted = new int[size];
        System.arraycopy(multipliers, 0, multipliersFitted, 0, size);
        return multipliersFitted;
    }
    
}
