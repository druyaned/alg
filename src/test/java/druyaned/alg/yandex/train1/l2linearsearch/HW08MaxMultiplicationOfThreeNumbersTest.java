package druyaned.alg.yandex.train1.l2linearsearch;

import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;


public class HW08MaxMultiplicationOfThreeNumbersTest {
    
    private static final Logger logger
            = Logger.getLogger(HW08MaxMultiplicationOfThreeNumbersTest.class.getName());
    
    @Test public void testThroughInput() {
        logger.entering(
                HW08MaxMultiplicationOfThreeNumbersTest.class.getName(),
                "testThroughInput"
        );
        logger.log(Level.INFO, "logger.hash={0}", logger.hashCode());
        int[][] inputs = {
            {4, 5, 6},
            {4, 6, 5},
            {5, 6, 4},
            {5, 4, 6},
            {6, 4, 5},
            {6, 5, 4},
            {3, 5, 1, 7, 9, 0, 9, -3, 10},
            {-5, -30000, -12},
            {1, 2, 3},
            {-1, 2, 3},
            {1, -2, 3},
            {1, 2, -3},
            {-1, 2, -3}
        };
        String[] answers = {
            "4 5 6",
            "4 5 6",
            "4 5 6",
            "4 5 6",
            "4 5 6",
            "4 5 6",
            "9 9 10",
            "-30000 -12 -5",
            "1 2 3",
            "-1 2 3",
            "-2 1 3",
            "-3 1 2",
            "-3 -1 2"
        };
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < inputs.length; ++i) {
            String answer = HW08MaxMultiplicationOfThreeNumbers.solve(inputs[i]);
            sb
                    .append('\n').append(String.format("================#%02d", i + 1))
                    .append('\n').append("input:");
            for (int j = 0; j < inputs[i].length; ++j)
                sb.append(" ").append(inputs[i][j]);
            sb.append('\n').append("answer: ").append(answer);
            assertEquals(answer, answers[i]);
        }
        logger.info(sb.toString());
    }
    
}
