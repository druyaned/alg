package druyaned.alg.yandex.train1.l6binsearch;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class BinarySearchTest {
    
    private static final Logger logger
            = Logger.getLogger(BinarySearchTest.class.getName());
    
    @Test public void testThroughInput() {
        logger.entering(BinarySearchTest.class.getName(), "testThroughInput");
        logger.log(Level.INFO, "logger.hash={0}", logger.hashCode());
        int[] array =  {-4, -3, -3, -3, -1, 0, 1, 3, 3,  3, 4};
        int[] keys =   {-5, -4, -3, -2, -1, 0, 1, 2, 3,  4, 5};
        int[] lefts =  { 0,  0,  1,  4,  4, 5, 6, 7, 7, 10, -1};
        int[] rights = {-1,  0,  3,  3,  4, 5, 6, 6, 9, 10, 10};
        assertEquals(keys.length, lefts.length);
        assertEquals(keys.length, rights.length);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < keys.length; ++i) {
            int left = BinarySearch.left(array, keys[i]);
            int right = BinarySearch.right(array, keys[i]);
            sb
                    .append('\n').append(String.format("================#%02d", i + 1))
                    .append('\n').append("key:   ").append(keys[i])
                    .append('\n').append("array: ").append(Arrays.toString(array));
            if (left == -1)
                sb.append('\n').append(String.format("left:  %d", left));
            else
                sb.append('\n').append(String.format("left:  %d (%d)", left, array[left]));
            if (right == -1)
                sb.append('\n').append(String.format("right: %d", right));
            else
                sb.append('\n').append(String.format("right: %d (%d)", right, array[right]));
            assertEquals(left, lefts[i]);
            assertEquals(right, rights[i]);
        }
        logger.info(sb.toString());
    }
    
}
