package druyaned.alg.util.t01graph;

public class FenwickTree {
    
    private final int[] subsums;
    
    public FenwickTree(int size) {
        subsums = new int[size + 1];
    }
    
    public void update(int index, int delta) {
        index++;
        while (index < subsums.length) {
            subsums[index] += delta;
            index += index & -index;
        }
    }
    
    public int sumFor(int index) {
        index++;
        int sum = 0;
        while (index > 0) {
            sum += subsums[index];
            index -= index & -index;
        }
        return sum;
    }
    
}
/*
lbs(i) = i & -i;
lbs(i) - least significant set bit,
    this is the greatest power of 2 which divides an index i.
Examples: lbs(10)=2, lbs(8)=8, lbs(4)=4, lbs(5)=1.

       0
       ∑(0,0]
 ______|_____________
|      |      |      |
1      2      4      8
∑(0,1] ∑(0,2] ∑(0,4] ∑(0,8]
 ______|  ____|_     |________________
|        |      |      |      |       |
3        5      6      9      10      12
∑(2,3]   ∑(4,5] ∑(4,6] ∑(8,9] ∑(8,10] ∑(8,12]
             ___|   __________|  _____|__
            |      |            |        |
            7      11           13       14
            ∑(6,7] ∑(10,11]     ∑(12,13] ∑(12,14]
                                         |
                                         15
                                         ∑(14,15]
*/
