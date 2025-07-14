package druyaned.alg.yandex.train1.l2linearsearch;

import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class P08RLETest {
    
    private static final Logger logger
            = Logger.getLogger(P08RLETest.class.getName());
    
    @Test public void testThroughInput() {
        logger.entering(P08RLETest.class.getName(), "testThroughInput");
        logger.log(Level.INFO, "logger.hash={0}", logger.hashCode());
        String[] lines = {
            "AAAABBBCCXYZDDDD",
            "ulala",
            "",
            "B",
            "EXCEPTION",
            "GOOD"
        };
        String[] encodedLines = {
            "A4B3C2XYZD4",
            "exception",
            "",
            "B",
            "EXCEPTION",
            "GO2D"
        };
        assertEquals(lines.length, encodedLines.length);
        P08RLE task8 = new P08RLE();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lines.length; ++i) {
            String encoded;
            try {
                encoded = task8.RLE(lines[i]);
            } catch (IllegalArgumentException exc) {
                encoded = "exception";
            }
            sb
                    .append('\n').append(String.format("================#%02d", i + 1))
                    .append('\n').append("line:    ").append(lines[i])
                    .append('\n').append("encoded: ").append(encodedLines[i]);
            assertEquals(encoded, encodedLines[i]);
        }
        logger.info(sb.toString());
    }
    
}
