package druyaned.alg.yandex.train5.l4binsearch.util;

import static druyaned.alg.yandex.train5.l4binsearch.util.BinarySearch.left;
import static druyaned.alg.yandex.train5.l4binsearch.util.BinarySearch.right;
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
        StringBuilder sb = new StringBuilder();
        sb
                .append('\n').append("Conditions {")
                .append('\n').append("  forLft={ key <= elem };")
                .append('\n').append("  forRht={ elem <= key };")
                .append('\n').append('}');
        int[] a1 = { 2, 4, 6, 7 };
        int[] a2 = { 2, 4, 6, 8, 9 };
        int minKey1 = -1, maxKey1 = 10;
        int minKey2 = -1, maxKey2 = 11;
        int[] el1 = { +0, +0, +0, +0, +1, +1, +2, +2, +3, +4, +4, +4 };
        int[] er1 = { -1, -1, -1, +0, +0, +1, +1, +2, +3, +3, +3, +3 };
        int[] el2 = { +0, +0, +0, +0, +1, +1, +2, +2, +3, +3, +4, +5, +5 };
        int[] er2 = { -1, -1, -1, +0, +0, +1, +1, +2, +2, +3, +4, +4, +4 };
        int[] l1 = new int[maxKey1 - minKey1 + 1];
        int[] r1 = new int[maxKey1 - minKey1 + 1];
        int[] l2 = new int[maxKey2 - minKey2 + 1];
        int[] r2 = new int[maxKey2 - minKey2 + 1];
        for (int key = minKey1, i = 0; key <= maxKey1; key++, i++) {
            final int keyCopy = key;
            l1[i] = left(a1, elem -> keyCopy <= elem);
            r1[i] = right(a1, elem -> elem <= keyCopy);
            assertEquals(el1[i], l1[i]);
            assertEquals(er1[i], r1[i]);
        }
        for (int key = minKey2, i = 0; key <= maxKey2; key++, i++) {
            final int keyCopy = key;
            l2[i] = left(a2, elem -> keyCopy <= elem);
            r2[i] = right(a2, elem -> elem <= keyCopy);
            assertEquals(el2[i], l2[i]);
            assertEquals(er2[i], r2[i]);
        }
        // array#1
        sb.append('\n').append("array#1");
        sb.append('\n').append("ind:");
        for (int i = 0; i < a1.length; i++)
            sb.append(String.format(" %2d", i));
        sb.append('\n').append(" a1:");
        for (int i = 0; i < a1.length; i++)
            sb.append(String.format(" %2d", a1[i]));
        sb.append('\n').append("kys:");
        for (int key = minKey1; key <= maxKey1; key++)
            sb.append(String.format(" %2d", key));
        sb.append('\n').append("lft:");
        for (int i = 0; i < l1.length; i++)
            sb.append(String.format(" %2d", l1[i]));
        sb.append('\n').append("rht:");
        for (int i = 0; i < r1.length; i++)
            sb.append(String.format(" %2d", r1[i]));
        // array#2
        sb.append('\n').append("array#2");
        sb.append('\n').append("ind:");
        for (int i = 0; i < a2.length; i++)
            sb.append(String.format(" %2d", i));
        sb.append('\n').append(" a2:");
        for (int i = 0; i < a2.length; i++)
            sb.append(String.format(" %2d", a2[i]));
        sb.append('\n').append("kys:");
        for (int key = minKey2; key <= maxKey2; key++)
            sb.append(String.format(" %2d", key));
        sb.append('\n').append("lft:");
        for (int i = 0; i < l2.length; i++)
            sb.append(String.format(" %2d", l2[i]));
        sb.append('\n').append("rht:");
        for (int i = 0; i < r2.length; i++)
            sb.append(String.format(" %2d", r2[i]));
        logger.info(sb.toString());
    }
    
}
