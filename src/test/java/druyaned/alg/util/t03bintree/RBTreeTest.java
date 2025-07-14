package druyaned.alg.util.t03bintree;

import druyaned.alg.util.t03bintree.rbt.RBTree;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.TreeSet;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RBTreeTest {
    
    private RBTree<Integer> tree;
    
    @BeforeEach public void setUp() {
        tree = new RBTree<>(Integer::compare);
    }
    
    private List<Integer> additionAndReturnsExpected() {
        int[] values = {3, 8, 7, 1, 9, 8, 2, 1, 7, 4};
        for (int value : values)
            tree.add(value);
        List<Integer> expected = new ArrayList<>(Arrays
                .asList(1, 2, 3, 4, 7, 8, 9));
        return expected;
    }
    
    @Test public void assertionsOfEmptyRBTree() {
        assertNull(tree.root());
        assertNull(tree.getNode(0));
        assertNull(tree.getNodeFirst());
        assertNull(tree.getNodeLast());
        assertNull(tree.getNodeLess(0));
        assertNull(tree.getNodeLessEq(0));
        assertNull(tree.getNodeGreater(0));
        assertNull(tree.getNodeGreaterEq(0));
        assertEquals(0, tree.size());
        assertTrue(tree.isEmpty());
    }
    
    @Test public void compareAdditionWithTreeSet() {
        // TODO: fix with rbTree.iterator();
        // TODO: add several tests
        // TODO: add compareRemovalWithTreeSet
        TreeSet<Integer> langTree = new TreeSet<>(Integer::compare);
        Random random = new Random();
        int minVal = -1000;
        int maxVal = +1000;
        int n = 10_000;
        for (int i = 0; i < n; i++) {
            int value = minVal + random.nextInt(maxVal - minVal + 1);
            tree.add(value);
            langTree.add(value);
            Iterator<Integer> iter = langTree.iterator();
            tree.forEachNode(node -> {
                if (iter.hasNext() && !node.value().equals(iter.next())) {
                    throw new RuntimeException();
                }
            });
        }
    }
    
    @Test public void additionOfCorrectValues() {
        List<Integer> expected = additionAndReturnsExpected();
        List<Integer> actual = new ArrayList<>(expected.size());
        tree.forEachNode(node -> actual.add(node.value()));
        assertIterableEquals(expected, actual);
        assertEquals(7, tree.size());
        assertFalse(tree.isEmpty());
    }
    
    @Test public void additionOfNull() {
        assertThrows(NullPointerException.class, () -> tree.add(null));
    }
    
    @Test public void removalOfAllValues() {
        List<Integer> expected = additionAndReturnsExpected();
        int n = expected.size();
        for (int i = 0; i < n; i++) {
            Integer removed = expected.remove(0);
            assertTrue(tree.remove(removed));
            List<Integer> actual = new ArrayList<>(expected.size());
            tree.forEachNode(node -> actual.add(node.value()));
            assertIterableEquals(expected, actual);
        }
        assertionsOfEmptyRBTree();
    }
    
    @Test public void removeFromEmptyRbt() {
        assertFalse(tree.remove(1));
    }
    
    @Test public void removeNotPresentedValue() {
        tree.add(1);
        assertFalse(tree.remove(2));
    }
    
    @Test public void clearRbt() {
        additionAndReturnsExpected();
        tree.clear();
        assertionsOfEmptyRBTree();
    }
    
    @Test public void getExistingValues() {
        tree.add(2);
        tree.add(4);
        tree.add(1);
        assertEquals(tree.root().value(), 2);
        assertEquals(tree.root().color(), RBTree.Color.BLACK);
        assertEquals(tree.getNode(4).value(), 4);
        assertEquals(tree.getNode(4).color(), RBTree.Color.RED);
        assertEquals(tree.getNodeFirst().value(), 1);
        assertEquals(tree.getNodeLast().value(), 4);
    }
    
    @Test public void getNonExistingValues() {
        tree.add(2);
        tree.add(4);
        tree.add(1);
        assertNull(tree.getNode(3));
        assertNull(tree.getNode(100));
    }
    
    @Test public void getLessValues() {
        additionAndReturnsExpected();
        int[] values = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        Integer[] expectations = {null, null, 1, 2, 3, 4, 4, 4, 7, 8, 9, 9};
        int n = values.length;
        for (int i = 0; i < n; i++) {
            if (expectations[i] == null)
                assertNull(tree.getNodeLess(values[i]));
            else
                assertEquals(expectations[i], tree.getNodeLess(values[i]).value());
        }
    }
    
    @Test public void getLessEqualValues() {
        additionAndReturnsExpected();
        int[] values = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        Integer[] expectations = {null, 1, 2, 3, 4, 4, 4, 7, 8, 9, 9, 9};
        int n = values.length;
        for (int i = 0; i < n; i++) {
            if (expectations[i] == null)
                assertNull(tree.getNodeLessEq(values[i]));
            else
                assertEquals(expectations[i], tree.getNodeLessEq(values[i]).value());
        }
    }
    
    @Test public void getGreaterValues() {
        additionAndReturnsExpected();
        int[] args = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        Integer[] expectations = {1, 2, 3, 4, 7, 7, 7, 8, 9, null, null, null};
        int n = args.length;
        for (int i = 0; i < n; i++) {
            if (expectations[i] == null)
                assertNull(tree.getNodeGreater(args[i]));
            else
                assertEquals(expectations[i], tree.getNodeGreater(args[i]).value());
        }
    }
    
    @Test public void getGreaterEqualValues() {
        additionAndReturnsExpected();
        int[] args = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        Integer[] expectations = {1, 1, 2, 3, 4, 7, 7, 7, 8, 9, null, null};
        int n = args.length;
        for (int i = 0; i < n; i++) {
            if (expectations[i] == null)
                assertNull(tree.getNodeGreaterEq(args[i]));
            else
                assertEquals(expectations[i], tree.getNodeGreaterEq(args[i]).value());
        }
    }
    
}
