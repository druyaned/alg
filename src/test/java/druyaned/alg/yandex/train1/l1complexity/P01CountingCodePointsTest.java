package druyaned.alg.yandex.train1.l1complexity;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 * To run the test enter in the Terminal {@code mvn test}.
 * @author druyaned
 */
public class P01CountingCodePointsTest {
    
    private static final Logger logger
            = Logger.getLogger(P01CountingCodePointsTest.class.getName());
    
    @Test public void testThroughInput() {
        logger.entering(P01CountingCodePointsTest.class.getName(), "testThroughInput");
        logger.log(Level.INFO, "logger.hash={0}", logger.hashCode());
        String[] inputs = {
            "Привет 😀! Как дела?", // 3
            "ababa", // 3
            "aab", // 2
            "a", // 1
            "", // 0
            "aa", // 2
            "bb", // 2
            "\ta  a a b b", // 5
            "good\ntest" // 2
        };
        int[] maxCounts = {3, 3, 2, 1, 0, 2, 2, 5, 2};
        assertEquals(inputs.length, maxCounts.length);
        P01CountingCodePoints task1 = new P01CountingCodePoints();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < inputs.length; ++i) {
            sb
                    .append('\n').append(String.format("================#%02d", i + 1))
                    .append('\n').append("input: '").append(inputs[i]).append('\'');
            Map<Integer, Integer> codePointToCount = task1.codePointToCount(inputs[i]);
            for (Map.Entry<Integer, Integer> entry : codePointToCount.entrySet()) {
                Integer currentCodePoint = entry.getKey();
                Integer currentCount = entry.getValue();
                String codePoints = new String(new int[] {currentCodePoint}, 0, 1);
                sb
                        .append('\n').append("  '").append(codePoints)
                        .append("': ").append(currentCount);
            }
            int maxCount = task1.maxCount(codePointToCount);
            sb.append('\n').append("maxCount: ").append(maxCount);
            assertEquals(maxCount, maxCounts[i]);
        }
        logger.info(sb.toString());
    }
    
}
