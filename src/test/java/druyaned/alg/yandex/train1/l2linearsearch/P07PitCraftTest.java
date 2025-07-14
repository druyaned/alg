package druyaned.alg.yandex.train1.l2linearsearch;

import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 * To run the test enter in the Terminal {@code mvn test}.
 * @author druyaned
 */
public class P07PitCraftTest {
    
    private static final Logger logger
            = Logger.getLogger(P07PitCraftTest.class.getName());
    
    @Test public void testThroughInput() {
        logger.entering(P07PitCraftTest.class.getName(), "testThroughInput");
        logger.log(Level.INFO, "logger.hash={0}", logger.hashCode());
        int[][] heightsArray = {
            {3, 1, 5, 2, 3, 4, 3, 7, 5, 4, 7, 3, 2, 4, 3, 2}, // 18
            {1, 2, 3}, // 0
            {}, // 0
            {2, 1, 2}, // 1
            {1}, // 0
            {1, 2, 1}, // 0
            {1, 5, 1, 1, 5, 1, 1, 2, 5, 5} // 19
        };
        int[] waterCounts = {18, 0, 0, 1, 0, 0, 19};
        int[] ns = new int[heightsArray.length];
        for (int i = 0; i < ns.length; ++i) {
            ns[i] = heightsArray[i].length;
        }
        assertEquals(ns.length, waterCounts.length);
        P07PitCraft problem07 = new P07PitCraft();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ns.length; ++i) {
            int ind = problem07.indexOfRightMaxHeight(heightsArray[i]);
            int leftWaterCount = problem07.leftWaterCount(heightsArray[i], ind);
            int rightWaterCount = problem07.rightWaterCount(heightsArray[i], ind);
            int waterCount = leftWaterCount + rightWaterCount;
            sb
                    .append('\n').append(String.format("================#%02d", i + 1))
                    .append('\n').append("n:       ").append(ns[i])
                    .append('\n').append("heights:");
            for (int j = 0; j < heightsArray[i].length; ++j)
                sb.append(" ").append(heightsArray[i][j]);
            sb.append('\n').append("waterCount: ").append(waterCounts[i]);
            assertEquals(waterCount, waterCounts[i]);
        }
        logger.info(sb.toString());
    }
    
}
