package druyaned.alg.yandex.train1.l3sets;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.Test;

public class IntListTest {
    
    private static final Logger logger
            = Logger.getLogger(IntListTest.class.getName());
    
    @Test public void testThroughInput() {
        logger.entering(IntListTest.class.getName(), "testThroughInput");
        logger.log(Level.INFO, "logger.hash={0}", logger.hashCode());
        IntList list = new IntList();
        StringBuilder sb = new StringBuilder();
        sb.append('\n').append("IntList(): ").append(list);
        int[] initCapacities = {-1, 0, 1, 2, 16, 17, 32, 33, 0xfffff, 0x1000000, 0x1000001};
        for (int i = 0; i < initCapacities.length; ++i) {
            sb.append('\n').append(String.format("IntList(%d):", initCapacities[i]));
            try {
                list = new IntList(initCapacities[i]);
                sb.append(' ').append(list);
            } catch (IllegalArgumentException exc) {
                sb.append(' ').append(exc.getMessage());
            }
        }
        list = new IntList(new int[0]);
        sb.append('\n').append("IntList(new int[0]): ").append(list);
        list = new IntList(new int[] {2});
        sb.append('\n').append("IntList(new int[] {2}): ").append(list);
        list = new IntList(2, 3);
        sb.append('\n').append("IntList(2, 3): ").append(list);
        list = new IntList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17);
        sb.append('\n').append("IntList(1, 2, ..., 17): ").append(list);
        int[] indexes = {-1, 0, 1, 2, 16, 17, 100};
        for (int i = 0; i < indexes.length; ++i) {
            sb.append('\n').append(String.format("get(%d):", indexes[i]));
            try {
                sb.append(' ').append(list.get(indexes[i]));
            } catch (IndexOutOfBoundsException exc) {
                sb.append(' ').append(exc.getMessage());
            }
        }
        list = new IntList();
        for (int i = 0; i < 16; ++i)
            list.add(i);
        sb.append('\n').append("list.add(i): ").append(list);
        if (list.remove())
            sb.append('\n').append("list.remove(): ").append(list);
        else
            sb.append('\n').append("fail to remove");
        list.add(100);
        list.add(200);
        sb.append('\n').append("list.add(...): ").append(list);
        while (list.remove())
            sb.append('\n').append("list.remove(): ").append(list);
        logger.info(sb.toString());
    }
    
}
