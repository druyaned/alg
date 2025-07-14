package druyaned.alg.yandex.train1.l2linearsearch;

import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class HW06SymmetricSequenceTest {
    
    private static final Logger logger
            = Logger.getLogger(HW06SymmetricSequenceTest.class.getName());
    
    @Test public void testThroughInput() {
        logger.entering(HW06SymmetricSequenceTest.class.getName(), "testThroughInput");
        logger.log(Level.INFO, "logger.hash={0}", logger.hashCode());
        int[] ns = {8, 9, 10, 9, 8, 4, 3, 2, 1};
        int[][] digitsArray = {
            {1, 2, 3, 4, 5, 4, 3, 2},
            {1, 2, 3, 4, 5, 4, 3, 2, 1},
            {1, 2, 3, 4, 5, 5, 4, 3, 2, 1},
            {1, 2, 3, 4, 5, 5, 4, 3, 2},
            {1, 2, 3, 4, 5, 5, 4, 3},
            {3, 3, 3, 3},
            {3, 3, 3},
            {3, 3},
            {3}
        };
        int[] outputs = {1, 0, 0, 1, 2, 0, 0, 0, 0};
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ns.length; ++i) {
            int m = HW06SymmetricSequence.numberOfElements(digitsArray[i], ns[i]);
            sb
                    .append('\n').append(String.format("================#%02d", i + 1))
                    .append('\n').append("N:      ").append(ns[i])
                    .append('\n').append("digits:");
            for (int j = 0; j < ns[i]; ++j)
                sb.append(" ").append(digitsArray[i][j]);
            sb.append('\n').append("m:      ").append(m);
            assertEquals(m, outputs[i]);
        }
        logger.info(sb.toString());
    }
    
}
