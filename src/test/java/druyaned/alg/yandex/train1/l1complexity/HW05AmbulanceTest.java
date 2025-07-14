package druyaned.alg.yandex.train1.l1complexity;

import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 * To run the test enter in the Terminal {@code mvn test}.
 * @author druyaned
 */
public class HW05AmbulanceTest {
    
    private static final Logger logger
            = Logger.getLogger(HW05AmbulanceTest.class.getName());
    
    @Test public void testThroughInput() {
        logger.entering(HW05AmbulanceTest.class.getName(), "testThroughInput");
        logger.log(Level.INFO, "logger.hash={0}", logger.hashCode());
        int[][] inputs = {
            {89, 20, 41, 1, 11}, // 1
            {11, 1, 1, 1, 1}, // 2
            {3, 2, 2, 2, 1}, // 3
            {5, 3, 23, 3, 2}, // 4
            {6, 3, 27, 3, 3}, // 5
            {16, 3, 27, 3, 3}, // 6
            {8, 6, 7, 1, 5}, // 7
            {4, 2, 5, 1, 2}, // 8
            {1, 1, 1, 1, 1}, // 9
            {2, 1, 1, 1, 1}, // 10
            {2, 1, 3, 1, 1}, // 11
            {3, 1, 3, 1, 1}, // 12
            {100, 1, 3, 1, 1}, // 13
            {2, 3, 1, 1, 1}, // 14
            {3, 3, 1, 1, 1}, // 15
            {4, 3, 1, 1, 1}, // 16
            {3, 3, 4, 1, 1}, // 17
            {4, 3, 4, 1, 1}, // 18
            {5, 3, 4, 1, 1}, // 19
            {12, 3, 4, 1, 1}, // 20
            {13, 3, 4, 1, 1}, // 21
            {100, 3, 4, 1, 1}, // 22
            {6, 2, 13, 2, 1}, // 23
            {11, 2, 13, 2, 1} // 24
        };
        int[][] results = {
            {2, 3}, // 1
            {0, 1}, // 2
            {-1, -1}, // 3
            {1, 2}, // 4
            {1, 2}, // 5
            {2, 3}, // 6
            {-1, -1}, // 7
            {1, 0}, // 8
            {1, 1}, // 9
            {0, 1}, // 10
            {1, 1}, // 11
            {1, 1}, // 12
            {0, 1}, // 13
            {1, 0}, // 14
            {1, 0}, // 15
            {0, 0}, // 16
            {1, 1}, // 17
            {1, 1}, // 18
            {1, 0}, // 19
            {1, 0}, // 20
            {0, 0}, // 21
            {0, 0}, // 22
            {1, 0}, // 23
            {0, 0} // 24
        };
        assertEquals(inputs.length, results.length);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < inputs.length; ++i) {
            HW05Ambulance.P1N1 p1n1 = HW05Ambulance.findP1N1(inputs[i][0], inputs[i][1],
                    inputs[i][2], inputs[i][3], inputs[i][4]);
            sb
                    .append('\n').append(String.format("================#%02d", i + 1))
                    .append('\n').append(String.format("%3s|%3s|%3s|%3s|%3s",
                            "k1", "m", "k2", "p2", "n2"))
                    .append('\n').append("---+---+---+---+---")
                    .append('\n').append(String.format("%3d|%3d|%3d|%3d|%3d",
                            inputs[i][0], inputs[i][1], inputs[i][2],
                            inputs[i][3], inputs[i][4]))
                    .append('\n').append(String.format("%3s|%3s", "p1", "n1"))
                    .append('\n').append("---+---")
                    .append('\n').append(String.format("%3d|%3d", p1n1.p1, p1n1.n1));
            assertEquals(p1n1.p1, results[i][0]);
            assertEquals(p1n1.n1, results[i][1]);
        }
        logger.info(sb.toString());
    }
    
}
