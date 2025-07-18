package druyaned.alg.util.t03bintree;

import druyaned.alg.util.t03bintree.cbt.CBTree;
import druyaned.alg.util.t02arithm.BinarySearch;
import druyaned.alg.util.t03bintree.cbt.CBTree.CBMTree;
import druyaned.alg.util.t03bintree.cbt.CBTree.CBSTree;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.TreeSet;
import java.util.function.IntSupplier;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CBTreeTest {
    
    private CBSTree<Integer> tree;
    private CBMTree<Integer> treeRep;
    
    @BeforeEach public void setUp() {
        tree = new CBSTree<>(Integer::compare);
        treeRep = new CBMTree<>(Integer::compare);
    }
    
    @Test public void assertionsOfEmptyTrees() {
        assertNull(tree.root());
        assertEquals(0, tree.size());
        assertTrue(tree.isEmpty());
        assertFalse(tree.contains(0));
        assertNull(tree.getFirst());
        assertNull(tree.getLast());
        assertNull(tree.getLess(0));
        assertNull(tree.getGreater(0));
        assertNull(tree.getLessEq(0));
        assertNull(tree.getGreaterEq(0));
        assertThrows(IndexOutOfBoundsException.class, () -> tree.getAt(0));
        assertNull(treeRep.root());
        assertEquals(0, treeRep.size());
        assertTrue(treeRep.isEmpty());
        assertFalse(treeRep.contains(0));
        assertNull(treeRep.getFirst());
        assertNull(treeRep.getLast());
        assertNull(treeRep.getLess(0));
        assertNull(treeRep.getGreater(0));
        assertNull(treeRep.getLessEq(0));
        assertNull(treeRep.getGreaterEq(0));
        assertThrows(IndexOutOfBoundsException.class, () -> treeRep.getAt(0));
    }
    
    @Test public void assertionsOfModifications() {
        Random random = new Random();
        int minVal = -2_048;
        int maxVal = +2_048;
        int n = 2 * (maxVal - minVal + 1);
        int targetOpCount = n / 2;
        IntSupplier nextValue = () -> minVal + random.nextInt(maxVal - minVal + 1);
        TreeSet<Integer> treeSet = new TreeSet<>(Integer::compare);
        List<Integer> list = new ArrayList<>(n + targetOpCount);
        // addition
        for (int i = 0; i < n; i++) {
            int value = nextValue.getAsInt();
            addAndAssert(value, treeSet);
            addAndAssert(value, list);
        }
        // addition with removal
        for (int opCount = 0; opCount != targetOpCount; ) {
            if (random.nextBoolean()) {
                if (random.nextBoolean()) {
                    if (addAndAssert(nextValue.getAsInt(), treeSet)) {
                        opCount++;
                    }
                } else {
                    if (addAndAssert(nextValue.getAsInt(), list)) {
                        opCount++;
                    }
                }
            } else {
                if (random.nextBoolean()) {
                    if (removeAndAssert(nextValue.getAsInt(), treeSet)) {
                        opCount++;
                    }
                } else {
                    if (removeAndAssert(nextValue.getAsInt(), list)) {
                        opCount++;
                    }
                }
            }
        }
        // removal
        List<Integer> allValues = new ArrayList<>(treeSet.size() + list.size());
        allValues.addAll(treeSet);
        allValues.addAll(list);
        for (int size = allValues.size(); size > 0; size--) {
            int index = random.nextInt(size);
            Integer value = allValues.get(index);
            removeAndAssert(value, treeSet);
            removeAndAssert(value, list);
            // Swap means deleting here
            allValues.set(index, allValues.get(size - 1));
            allValues.set(size - 1, value);
        }
        assertionsOfEmptyTrees();
        // clearing
        for (int i = 0; i < n; i++) {
            int value = nextValue.getAsInt();
            addAndAssert(value, treeSet);
            addAndAssert(value, list);
        }
        tree.clear();
        treeRep.clear();
        assertionsOfEmptyTrees();
    }
    
    private boolean addAndAssert(Integer value, TreeSet<Integer> treeSet) {
        boolean expected = treeSet.add(value);
        boolean actual = tree.add(value);
        assertEquals(expected, actual);
        if (!actual) {
            return actual;
        }
        assertEquals(treeSet.size(), tree.size());
        Iterator<Integer> treeSetIter = treeSet.iterator();
        Iterator<Integer> treeIter = tree.iterator();
        while (treeSetIter.hasNext() && treeIter.hasNext()) {
            assertEquals(treeSetIter.hasNext(), treeIter.hasNext());
            assertEquals(treeSetIter.next(), treeIter.next());
        }
        tree.forEachNode(node -> {
            int lc = node.leftCount();
            int rc = node.rightCount();
            if (lc > rc) {
                assertFalse(CBTree.unbalanced(lc, rc));
            }
            if (lc < rc) {
                assertFalse(CBTree.unbalanced(rc, lc));
            }
        });
        return actual;
    }
    
    private boolean addAndAssert(Integer value, List<Integer> list) {
        boolean expected = list.add(value);
        boolean actual = treeRep.add(value);
        assertEquals(expected, actual);
        if (!actual) {
            return actual;
        }
        list.sort(Integer::compare);
        Iterator<Integer> listIter = list.iterator();
        Iterator<Integer> treeRepIter = treeRep.iterator();
        while (listIter.hasNext() && treeRepIter.hasNext()) {
            assertEquals(listIter.hasNext(), treeRepIter.hasNext());
            assertEquals(listIter.next(), treeRepIter.next());
        }
        treeRep.forEachNode(node -> {
            int lc = node.leftCount();
            int rc = node.rightCount();
            if (lc > rc) {
                assertFalse(CBTree.unbalanced(lc, rc));
            }
            if (lc < rc) {
                assertFalse(CBTree.unbalanced(rc, lc));
            }
        });
        return actual;
    }
    
    private boolean removeAndAssert(Integer value, TreeSet<Integer> treeSet) {
        boolean expected = treeSet.remove(value);
        boolean actual = tree.remove(value);
        assertEquals(expected, actual);
        if (!actual) {
            return actual;
        }
        assertEquals(treeSet.size(), tree.size());
        Iterator<Integer> treeSetIter = treeSet.iterator();
        Iterator<Integer> treeIter = tree.iterator();
        while (treeSetIter.hasNext() && treeIter.hasNext()) {
            assertEquals(treeSetIter.hasNext(), treeIter.hasNext());
            assertEquals(treeSetIter.next(), treeIter.next());
        }
        tree.forEachNode(node -> {
            int lc = node.leftCount();
            int rc = node.rightCount();
            if (lc > rc) {
                assertFalse(CBTree.unbalanced(lc, rc));
            }
            if (lc < rc) {
                assertFalse(CBTree.unbalanced(rc, lc));
            }
        });
        return actual;
    }
    
    private boolean removeAndAssert(Integer value, List<Integer> list) {
        boolean expected = list.remove(value);
        boolean actual = treeRep.remove(value);
        assertEquals(expected, actual);
        if (!actual) {
            return actual;
        }
        assertEquals(list.size(), treeRep.size());
        Iterator<Integer> treeSetIter = list.iterator();
        Iterator<Integer> treeIter = treeRep.iterator();
        while (treeSetIter.hasNext() && treeIter.hasNext()) {
            assertEquals(treeSetIter.hasNext(), treeIter.hasNext());
            assertEquals(treeSetIter.next(), treeIter.next());
        }
        treeRep.forEachNode(node -> {
            int lc = node.leftCount();
            int rc = node.rightCount();
            if (lc > rc) {
                assertFalse(CBTree.unbalanced(lc, rc));
            }
            if (lc < rc) {
                assertFalse(CBTree.unbalanced(rc, lc));
            }
        });
        return actual;
    }
    
    @Test public void assertionsOfAddNull() {
        assertThrows(NullPointerException.class, () -> tree.add(null));
        assertThrows(NullPointerException.class, () -> treeRep.add(null));
    }
    
    @Test public void assertionsOfGetters() {
        Random random = new Random();
        int minVal = -32_768;
        int maxVal = +32_768;
        int n = 2 * (maxVal - minVal + 1);
        List<Integer> list = new ArrayList<>(n);
        List<Integer> listRep = new ArrayList<>(n);
        IntSupplier nextValue = () -> minVal + random.nextInt(maxVal - minVal + 1);
        for (int i = 0; i < n; i++) {
            int value = nextValue.getAsInt();
            if (tree.add(value)) {
                list.add(value);
            }
            if (treeRep.add(value)) {
                listRep.add(value);
            }
        }
        list.sort(Integer::compare);
        listRep.sort(Integer::compare);
        for (int value = minVal - 2; value <= maxVal + 2; value++) {
            getAndAssert(list, tree, value);
            getAndAssert(listRep, treeRep, value);
        }
    }
    
    private void getAndAssert(List<Integer> list, CBSTree<Integer> tree, Integer value) {
        int index;
        if ((index = BinarySearch.left(list, e -> e.compareTo(value) >= 0)) == list.size()) {
            assertFalse(tree.contains(value));
            assertEquals(-1, tree.getIndexOf(value));
        } else {
            assertEquals(list.get(index).equals(value), tree.contains(value));
            if (list.get(index).equals(value)) {
                assertEquals(value, tree.getAt(index));
                assertEquals(index, tree.getIndexOf(value));
            } else {
                assertNotEquals(value, tree.getAt(index));
                assertEquals(-1, tree.getIndexOf(value));
            }
        }
        getAndAssert(list, (CBTree<Integer>)tree, value);
    }
    
    private void getAndAssert(List<Integer> list, CBMTree<Integer> tree, Integer value) {
        int fstInd, lstInd;
        if ((fstInd = BinarySearch.left(list, e -> e.compareTo(value) >= 0)) == list.size()) {
            assertFalse(tree.contains(value));
            assertEquals(-1, tree.getFirstIndexOf(value));
        } else {
            assertEquals(list.get(fstInd).equals(value), tree.contains(value));
            if (list.get(fstInd).equals(value)) {
                assertEquals(value, tree.getAt(fstInd));
                assertEquals(fstInd, tree.getFirstIndexOf(value));
            } else {
                assertNotEquals(value, tree.getAt(fstInd));
                assertEquals(-1, tree.getFirstIndexOf(value));
            }
        }
        if ((lstInd = BinarySearch.right(list, e -> e.compareTo(value) <= 0)) == -1) {
            assertFalse(tree.contains(value));
            assertEquals(-1, tree.getLastIndexOf(value));
        } else {
            assertEquals(list.get(lstInd).equals(value), tree.contains(value));
            if (list.get(lstInd).equals(value)) {
                assertEquals(value, tree.getAt(lstInd));
                assertEquals(lstInd, tree.getLastIndexOf(value));
            } else {
                assertNotEquals(value, tree.getAt(lstInd));
                assertEquals(-1, tree.getLastIndexOf(value));
            }
        }
        getAndAssert(list, (CBTree<Integer>)tree, value);
    }
    
    private void getAndAssert(List<Integer> list, CBTree<Integer> tree, Integer value) {
        int index;
        if ((index = BinarySearch.right(list, e -> e.compareTo(value) < 0)) == -1) {
            assertNull(tree.getLess(value));
            assertEquals(-1, tree.getIndexLess(value));
        } else {
            assertEquals(list.get(index), tree.getLess(value));
            assertEquals(index, tree.getIndexLess(value));
        }
        if ((index = BinarySearch.left(list, e -> e.compareTo(value) > 0)) == list.size()) {
            assertNull(tree.getGreater(value));
            assertEquals(list.size(), tree.getIndexGreater(value));
        } else {
            assertEquals(list.get(index), tree.getGreater(value));
            assertEquals(index, tree.getIndexGreater(value));
        }
        if ((index = BinarySearch.right(list, e -> e.compareTo(value) <= 0)) == -1) {
            assertNull(tree.getLessEq(value));
            assertEquals(-1, tree.getIndexLessEq(value));
        } else {
            assertEquals(list.get(index), tree.getLessEq(value));
            assertEquals(index, tree.getIndexLessEq(value));
        }
        if ((index = BinarySearch.left(list, e -> e.compareTo(value) >= 0)) == list.size()) {
            assertNull(tree.getGreaterEq(value));
            assertEquals(list.size(), tree.getIndexGreaterEq(value));
        } else {
            assertEquals(list.get(index), tree.getGreaterEq(value));
            assertEquals(index, tree.getIndexGreaterEq(value));
        }
    }
    
    @Test public void removalByIndex() {
        Random random = new Random();
        int minVal = -32_768;
        int maxVal = +32_768;
        int n = 2 * (maxVal - minVal + 1);
        IntSupplier nextValue = () -> minVal + random.nextInt(maxVal - minVal + 1);
        assertThrows(IndexOutOfBoundsException.class, () -> tree.removeAt(0));
        assertThrows(IndexOutOfBoundsException.class, () -> treeRep.removeAt(0));
        for (int i = 0; i < n; i++) {
            int value = nextValue.getAsInt();
            tree.add(value);
            treeRep.add(value);
        }
        assertThrows(IndexOutOfBoundsException.class, () -> tree.removeAt(-2));
        assertThrows(IndexOutOfBoundsException.class, () -> tree.removeAt(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> tree.removeAt(tree.size()));
        assertThrows(IndexOutOfBoundsException.class, () -> tree.removeAt(tree.size() + 1));
        assertThrows(IndexOutOfBoundsException.class, () -> treeRep.removeAt(-2));
        assertThrows(IndexOutOfBoundsException.class, () -> treeRep.removeAt(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> treeRep.removeAt(treeRep.size()));
        assertThrows(IndexOutOfBoundsException.class, () -> treeRep.removeAt(treeRep.size() + 1));
        for (int size = tree.size(); size > 0; size--) {
            int index = random.nextInt(size);
            assertEquals(tree.getAt(index), tree.removeAt(index));
        }
        for (int size = treeRep.size(); size > 0; size--) {
            int index = random.nextInt(size);
            assertEquals(treeRep.getAt(index), treeRep.removeAt(index));
        }
        assertionsOfEmptyTrees();
    }
    
}
