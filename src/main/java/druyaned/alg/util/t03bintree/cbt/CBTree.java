package druyaned.alg.util.t03bintree.cbt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

/**
 * Count-Balance Tree is a self-balancing binary search tree
 * that uses counts of descendant nodes in left and right subtrees
 * ({@code leftCount} and {@code rightCount}) for balancing
 * (see {@link #unbalanced}) and indexing functionality.
 * Null values are not accepted. Balance is achieved by node rotations:
 * {@link Node#rotateLeft() left-rotation} and {@link Node#rotateRight()
 * right-rotation}.
 * 
 * <P>
 * There are two implementations: {@link CBSTree} and {@link CBMTree}.
 * The first one doesn't support repetition of the values,
 * but the second one does.
 * 
 * <P>
 * <i>Time complexity of methods</i><br>
 * All modification methods has <code><b>O(log(n))</b></code> time complexity:
 * {@link #add(Object)}, {@link #remove(Object)}, {@link #removeAt(int)}.<br>
 * All getter methods has <code><b>O(log(n))</b></code> time complexity:
 * {@link #getNode(Object)},
 * {@link #getNodeAt(int)},
 * {@link #getNodeFirst()},
 * {@link #getNodeLast()},
 * {@link #getNodeLess(Object)},
 * {@link #getNodeGreater(Object)},
 * {@link #getNodeLessEq(Object)},
 * {@link #getNodeGreaterEq(Object)},
 * {@link #contains(Object)},
 * {@link #getAt(int)},
 * {@link #getFirst()},
 * {@link #getLast()},
 * {@link #getLess(Object)},
 * {@link #getGreater(Object)},
 * {@link #getLessEq(Object)},
 * {@link #getGreaterEq(Object)},
 * {@link #getIndexLess(Object)},
 * {@link #getIndexGreater(Object)},
 * {@link #getIndexLessEq(Object)},
 * {@link #getIndexGreaterEq(Object)},
 * {@link CBSTree#getIndexOf(Object)},
 * {@link CBMTree#getFirstIndexOf(Object)},
 * {@link CBMTree#getLastIndexOf(Object)}.<br>
 * And there are some methods with linear complexity <code><b>O(n)</b></code>:
 * {@link #forEachNode(Consumer)}, {@link #forEach(Consumer)}, {@link #clear()}.
 * 
 * @author druyaned
 * @param <T> type of value maintained by the tree
 * @see Node
 */
public abstract class CBTree<T> implements Iterable<T>, Collection<T> {
    
    /**
     * The root of the tree is an entry point for most operations.
     * Must be modified only in {@link #add(Object)}, {@link #remove(Object)},
     * {@link #removeAt(int)} and {@link #clear()} methods.
     */
    protected Node<T> root = null;
    
    /**
     * Number of nodes in the tree. Must be modified only in {@link #add(Object)},
     * {@link #remove(Object)}, {@link #removeAt(int)} and {@link #clear()} methods.
     */
    protected int size = 0;
    
    /**
     * Comparator to compare values of nodes that leads to
     * the state when the tree always sorted.
     */
    protected final Comparator<T> comp;
    
    /**
     * Creates Count-Balance Tree with the given comparator.
     * @param comp to compare values
     */
    public CBTree(Comparator<T> comp) {
        if (comp == null) {
            throw new NullPointerException("comp can't be null");
        }
        this.comp = comp;
    }
    
    /**
     * Returns root of the tree or null if the tree is empty.
     * @return root of the tree or null if the tree is empty
     */
    public Node<T> root() {
        return root;
    }
    
    /**
     * Returns number of values in the tree.
     * @return number of values in the tree
     */
    @Override public int size() {
        return size;
    }
    
    /**
     * Returns {@code true} if the tree is empty, otherwise - {@code false}.
     * @return {@code true} if the tree is empty, otherwise - {@code false}
     */
    @Override public boolean isEmpty() {
        return size == 0;
    }
    
    @Override public boolean contains(Object valueObj) {
        return getNode((T)valueObj) != null;
    }
    
    /**
     * Returns the node with the given value in this tree
     * or {@code null} if there is no such node.
     * 
     * @param value to get the node
     * @return node with the given value in this tree
     *      or {@code null} if there is no such node
     */
    public abstract Node<T> getNode(T value);
    
    /**
     * Returns node at the specified index.
     * @param index to get a node; belongs to <code>[0, size-1]</code>
     * @return node at the specified index
     */
    public Node<T> getNodeAt(int index) {
        throwIfBadIndex(index);
        Node<T> node = root;
        for (int curr = 0; curr + node.leftCount != index; ) {
            if (curr + node.leftCount < index) {
                curr += 1 + node.leftCount;
                node = node.right;
            } else {
                node = node.left;
            }
        }
        return node;
    }
    
    private void throwIfBadIndex(int index) {
        if (index < 0 || size <= index) {
            throw new IndexOutOfBoundsException("size=" + size
                    + " index=" + index);
        }
    }
    
    /**
     * Returns the first (leftmost) node in the tree.
     * @return the first (leftmost) node in the tree
     */
    public Node<T> getNodeFirst() {
        if (root == null) {
            return null;
        }
        Node<T> node = root;
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }
    
    /**
     * Returns the last (rightmost) node in the tree.
     * @return the last (rightmost) node in the tree
     */
    public Node<T> getNodeLast() {
        if (root == null) {
            return null;
        }
        Node<T> node = root;
        while (node.right != null) {
            node = node.right;
        }
        return node;
    }
    
    /**
     * Returns the last node, whose value is strictly less than
     * the given value, or {@code null} if there is no such node.
     * 
     * @param value to get the node
     * @return last node, whose value is strictly less than
     *      the given value, or {@code null} if there is no such node
     */
    public Node<T> getNodeLess(T value) {
        Node<T> less = null;
        for (Node<T> node = root; node != null; ) {
            int comparison = comp.compare(node.value, value);
            if (comparison < 0) {
                less = node;
                node = node.right;
            } else {
                node = node.left;
            }
        }
        return less;
    }
    
    /**
     * Returns the first node, whose value is strictly greater than
     * the given value, or {@code null} if there is no such node.
     * 
     * @param value to get the node
     * @return first node, whose value is strictly greater than
     *      the given value, or {@code null} if there is no such node
     */
    public Node<T> getNodeGreater(T value) {
        Node<T> greater = null;
        for (Node<T> node = root; node != null; ) {
            int comparison = comp.compare(node.value, value);
            if (comparison > 0) {
                greater = node;
                node = node.left;
            } else {
                node = node.right;
            }
        }
        return greater;
    }
    
    /**
     * Returns the last node, whose value is less than or equal to
     * the given value, or {@code null} if there is no such node.
     * 
     * @param value to get the node
     * @return last node, whose value is less than or equal to
     *      the given value, or {@code null} if there is no such node
     */
    public abstract Node<T> getNodeLessEq(T value);
    
    /**
     * Returns the first node, whose value is greater than or equal to
     * the given value, or {@code null} if there is no such node.
     * 
     * @param value to get the node
     * @return first node, whose value is greater than or equal to
     *      the given value, or {@code null} if there is no such node
     */
    public abstract Node<T> getNodeGreaterEq(T value);
    
    /**
     * Returns index of the last node, whose value is strictly less than
     * the given value, or {@code -1} if there is no such node.
     * 
     * @param value to get the index
     * @return index of the last node, whose value is strictly less than
     *      the given value, or {@code -1} if there is no such node
     */
    public int getIndexLess(T value) {
        int lessIndex = -1;
        Node<T> node = root;
        for (int curr = 0; node != null; ) {
            int comparison = comp.compare(node.value, value);
            if (comparison < 0) {
                lessIndex = curr + node.leftCount;
                curr += 1 + node.leftCount;
                node = node.right;
            } else {
                node = node.left;
            }
        }
        return lessIndex;
    }
    
    /**
     * Returns index of the first node, whose value is strictly greater than
     * the given value, or {@code size} of the tree if there is no such node.
     * 
     * @param value to get the index
     * @return index of the first node, whose value is strictly greater than
     *      the given value, or {@code size} of the tree if there is no such node
     */
    public int getIndexGreater(T value) {
        int greaterIndex = size;
        Node<T> node = root;
        for (int curr = 0; node != null; ) {
            int comparison = comp.compare(node.value, value);
            if (comparison > 0) {
                greaterIndex = curr + node.leftCount;
                node = node.left;
            } else {
                curr += 1 + node.leftCount;
                node = node.right;
            }
        }
        return greaterIndex;
    }
    
    /**
     * Returns index of the last node, whose value is less than or equal to
     * the given value, or {@code -1} if there is no such node.
     * 
     * @param value to get the index
     * @return index of the last node, whose value is less than or equal to
     *      the given value, or {@code -1} if there is no such node
     */
    public abstract int getIndexLessEq(T value);
    
    /**
     * Returns index of the first node, whose value is greater than or equal to
     * the given value, or {@code size} of the tree if there is no such node.
     * 
     * @param value to get the index
     * @return index of the first node, whose value is greater than or equal to
     *      the given value, or {@code size} of the tree if there is no such node
     */
    public abstract int getIndexGreaterEq(T value);
    
    /**
     * Returns value at the specified index.
     * @param index to get a value; belongs to <code>[0, size-1]</code>
     * @return value at the specified index
     * @see getNodeAt
     */
    public T getAt(int index) {
        return getNodeAt(index).value;
    }
    
    /**
     * Returns the first (leftmost) value in the tree.
     * @return the first (leftmost) value in the tree
     * @see getNodeFirst
     */
    public T getFirst() {
        Node<T> first = getNodeFirst();
        return first != null ? first.value : null;
    }
    
    /**
     * Returns the last (rightmost) value in the tree.
     * @return the last (rightmost) value in the tree
     * @see getNodeLast
     */
    public T getLast() {
        Node<T> last = getNodeLast();
        return last != null ? last.value : null;
    }
    
    /**
     * Returns the last value in the tree, that is strictly less than
     * the given value, or {@code null} if there is no such value.
     * 
     * @param value to get the value
     * @return last value in the tree, that is strictly less than
     *      the given value, or {@code null} if there is no such value
     * @see getNodeLess
     */
    public T getLess(T value) {
        Node<T> less = getNodeLess(value);
        return less != null ? less.value : null;
    }
    
    /**
     * Returns the first value in the tree, that is strictly greater than
     * the given value, or {@code null} if there is no such value.
     * 
     * @param value to get the value
     * @return first value in the tree, that is strictly greater than
     *      the given value, or {@code null} if there is no such value
     * @see getNodeGreater
     */
    public T getGreater(T value) {
        Node<T> greater = getNodeGreater(value);
        return greater != null ? greater.value : null;
    }
    
    /**
     * Returns the last value in the tree, that is less than or equal to
     * the given value, or {@code null} if there is no such value.
     * 
     * @param value to get the value
     * @return last value in the tree, that is less than or equal to
     *      the given value, or {@code null} if there is no such value
     * @see getNodeLessEq
     */
    public T getLessEq(T value) {
        Node<T> lessEq = getNodeLessEq(value);
        return lessEq != null ? lessEq.value : null;
    }
    
    /**
     * Returns the first value in the tree, that is greater than or equal to
     * the given value, or {@code null} if there is no such value.
     * 
     * @param value to get the value
     * @return first value in the tree, that is greater than or equal to
     *      the given value, or {@code null} if there is no such value
     */
    public T getGreaterEq(T value) {
        Node<T> greaterEq = getNodeGreaterEq(value);
        return greaterEq != null ? greaterEq.value : null;
    }
    
    /**
     * Node has {@code leftCount} and {@code rightCount}, so
     * the method defines whether these counts are unbalanced.
     * The counts are unbalanced if
     * <code>[log<sub>2</sub>(increasedCount)]
     * - [log<sub>2</sub>(siblingCount)] > 1</code>, where
     * <code>[x]</code> - is selecting integer part of <code>x</code>.
     * 
     * @param increasedCount left or right count of descendant nodes
     *      that possibly becomes too big regarding to its sibling count
     * @param siblingCount sibling to {@code increasedCount}
     * @return {@code true} if the node's counts are unbalanced,
     *      otherwise - {@code false}
     */
    public static boolean unbalanced(int increasedCount, int siblingCount) {
        return Integer.numberOfLeadingZeros(siblingCount)
                - Integer.numberOfLeadingZeros(increasedCount) > 1;
    }
    
    /**
     * Insertion of the given value into the tree. Null values are not supported.
     * 
     * <P>
     * Sequence of actions:<ol>
     *   <li>find the deepest place for a new node with the given value;</li>
     *   <li>create this node and attach it to the leaf which holds the found place;</li>
     *   <li>{@link #insertionBalance balance the tree}.</li>
     * </ol>
     * 
     * @param value to be added
     * @return {@code true} if the value was added, otherwise - {@code false}
     */
    @Override public abstract boolean add(T value);
    
    /**
     * After the {@link #add insertion} of a node the balance
     * can be violated (see {@link #unbalanced}).
     * The loop of the balancing starts from defining <b>P</b>
     * as a parent of the node (designated here as <b>N</b>)
     * and iterates while <code><b>P</b> != null</code>.
     * 
     * <P>
     * In each iteration:<ol>
     *   <li>corresponding count (left or right) of <b>P</b> is increased;</li>
     *   <li>if <b>P</b> is unbalanced and rotation of <b>P</b>
     *     leads to unbalancing <b>N</b>, then rotate <b>N</b>
     *     and after that rotate <b>P</b>; next <b>N</b> is <b>P</b>.parent;</li>
     *   <li>else if <b>P</b> is unbalanced and rotation of it is enough for
     *     balancing just rotate <b>P</b>; next <b>N</b> is <b>P</b>.parent;</li>
     *   <li>otherwise <b>P</b> is balanced and next <b>N</b> is <b>P</b>.</li>
     * </ol>
     * 
     * @param node new leaf with the inserted value
     */
    protected final void insertionBalance(Node<T> node) {
        for (Node<T> p; (p = node.parent) != null; ) {
            if (p.left == node) {
                p.leftCount++;
                if (unbalanced(p.leftCount, p.rightCount)) {
                    if (unbalanced(node.rightCount + 1 + p.rightCount, node.leftCount)) {
                        node.rotateLeft();
                    }
                    p.rotateRight();
                    node = p.parent;
                } else {
                    node = p;
                }
            } else {
                p.rightCount++;
                if (unbalanced(p.rightCount, p.leftCount)) {
                    if (unbalanced(node.leftCount + 1 + p.leftCount, node.rightCount)) {
                        node.rotateRight();
                    }
                    p.rotateLeft();
                    node = p.parent;
                } else {
                    node = p;
                }
            }
        }
        root = node;
    }
    
    /**
     * Implementation of Count-Balance Tree provides auto-sorting,
     * uniqueness of values and indexing functionality.
     * Null values are not accepted. Abbreviation stands for
     * <b>Count-Balance Set Tree</b>.
     * 
     * @param <T> type of value maintained by the tree
     * @see CBMTree
     */
    public static class CBSTree<T> extends CBTree<T> {
        /**
         * Creates Count-Balance Set Tree with the given comparator.
         * @param comp to compare values
         */
        public CBSTree(Comparator<T> comp) {
            super(comp);
        }
        /**
         * {@inheritDoc}
         * 
         * If the value had been presented in the tree, then returns false.
         */
        @Override public boolean add(T value) {
            // null values are not accepted
            if (value == null) {
                throw new NullPointerException("value must not be null");
            }
            // new root case
            if (root == null) {
                root = Node.newRoot(value);
                size++;
                return true;
            }
            // find insertion place
            Node<T> node = root, p;
            int comparison;
            do {
                p = node;
                comparison = comp.compare(node.value, value);
                if (comparison < 0) {
                    node = node.right;
                } else if (comparison > 0) {
                    node = node.left;
                } else {
                    return false;
                }
            } while (node != null);
            // complete insertion and balance the tree
            if (comparison < 0) {
                p.right = Node.newNode(value, p);
                insertionBalance(p.right);
            } else {
                p.left = Node.newNode(value, p);
                insertionBalance(p.left);
            }
            size++;
            return true;
        }
        @Override public Node<T> getNode(T value) {
            for (Node<T> node = root; node != null; ) {
                int comparison = comp.compare(node.value, value);
                if (comparison == 0) {
                    return node;
                } else if (comparison < 0) {
                    node = node.right;
                } else {
                    node = node.left;
                }
            }
            return null;
        }
        @Override public Node<T> getNodeLessEq(T value) {
            Node<T> lessEq = null;
            for (Node<T> node = root; node != null; ) {
                int comparison = comp.compare(node.value, value);
                if (comparison == 0) {
                    return node;
                } else if (comparison < 0) {
                    lessEq = node;
                    node = node.right;
                } else {
                    node = node.left;
                }
            }
            return lessEq;
        }
        @Override public Node<T> getNodeGreaterEq(T value) {
            Node<T> greaterEq = null;
            for (Node<T> node = root; node != null; ) {
                int comparison = comp.compare(node.value, value);
                if (comparison == 0) {
                    return node;
                } else if (comparison > 0) {
                    greaterEq = node;
                    node = node.left;
                } else {
                    node = node.right;
                }
            }
            return greaterEq;
        }
        @Override public int getIndexLessEq(T value) {
            int indexLessEq = -1;
            Node<T> node = root;
            for (int curr = 0; node != null; ) {
                int comparison = comp.compare(node.value, value);
                if (comparison == 0) {
                    return curr + node.leftCount;
                } else if (comparison < 0) {
                    indexLessEq = curr + node.leftCount;
                    curr += 1 + node.leftCount;
                    node = node.right;
                } else {
                    node = node.left;
                }
            }
            return indexLessEq;
        }
        @Override public int getIndexGreaterEq(T value) {
            int indexGreaterEq = size;
            Node<T> node = root;
            for (int curr = 0; node != null; ) {
                int comparison = comp.compare(node.value, value);
                if (comparison == 0) {
                    return curr + node.leftCount;
                } else if (comparison > 0) {
                    indexGreaterEq = curr + node.leftCount;
                    node = node.left;
                } else {
                    curr += 1 + node.leftCount;
                    node = node.right;
                }
            }
            return indexGreaterEq;
        }
        /**
         * Returns index of the value in the tree
         * or {@code -1} if there is no such value.
         * 
         * @param value to be found
         * @return index of the value in the tree
         *      or {@code -1} if there is no such value
         */
        public int getIndexOf(T value) {
            Node<T> node = root;
            for (int curr = 0; node != null; ) {
                int comparison = comp.compare(node.value, value);
                if (comparison == 0) {
                    return curr + node.leftCount;
                } else if (comparison < 0) {
                    curr += 1 + node.leftCount;
                    node = node.right;
                } else {
                    node = node.left;
                }
            }
            return -1;
        }
    }
    
    /**
     * Implementation of Count-Balance Tree provides auto-sorting,
     * repetitions of values and indexing functionality.
     * Null values are not accepted. Abbreviation stands for
     * <b>Count-Balance Multiset Tree</b>.
     * 
     * @param <T> type of value maintained by the tree
     * @see CBSTree
     */
    public static class CBMTree<T> extends CBTree<T> {
        /**
         * Creates Count-Balance Multiset Tree with the given comparator.
         * @param comp to compare values
         */
        public CBMTree(Comparator<T> comp) {
            super(comp);
        }
        /**
         * {@inheritDoc}
         * 
         * If the value has been presented in the tree,
         * goes to the right subtree (greater part).
         * 
         * @return {@code true} tells the value was added
         */
        @Override public boolean add(T value) {
            // null values are not accepted
            if (value == null) {
                throw new NullPointerException("value must not be null");
            }
            // new root case
            if (root == null) {
                root = Node.newRoot(value);
                size++;
                return true;
            }
            // find insertion place
            Node<T> node = root, p;
            int comparison;
            do {
                p = node;
                comparison = comp.compare(node.value, value);
                if (comparison <= 0) {
                    node = node.right;
                } else {
                    node = node.left;
                }
            } while (node != null);
            // complete insertion and balance the tree
            if (comparison <= 0) {
                p.right = Node.newNode(value, p);
                insertionBalance(p.right);
            } else {
                p.left = Node.newNode(value, p);
                insertionBalance(p.left);
            }
            size++;
            return true;
        }
        /**
         * Returns the first node with the given value in this tree
         * or {@code null} if there is no such node.
         * 
         * @param value to get the node
         * @return first node with the given value in this tree
         *      or {@code null} if there is no such node
         */
        @Override public Node<T> getNode(T value) {
            Node<T> equal = null;
            for (Node<T> node = root; node != null; ) {
                int comparison = comp.compare(node.value, value);
                if (comparison == 0) {
                    equal = node;
                    node = node.left;
                } else if (comparison < 0) {
                    node = node.right;
                } else {
                    node = node.left;
                }
            }
            return equal;
        }
        @Override public Node<T> getNodeLessEq(T value) {
            Node<T> lessEq = null;
            for (Node<T> node = root; node != null; ) {
                int comparison = comp.compare(node.value, value);
                if (comparison <= 0) {
                    lessEq = node;
                    node = node.right;
                } else {
                    node = node.left;
                }
            }
            return lessEq;
        }
        @Override public Node<T> getNodeGreaterEq(T value) {
            Node<T> greaterEq = null;
            for (Node<T> node = root; node != null; ) {
                int comparison = comp.compare(node.value, value);
                if (comparison >= 0) {
                    greaterEq = node;
                    node = node.left;
                } else {
                    node = node.right;
                }
            }
            return greaterEq;
        }
        @Override public int getIndexLessEq(T value) {
            int indexLessEq = -1;
            Node<T> node = root;
            for (int curr = 0; node != null; ) {
                int comparison = comp.compare(node.value, value);
                if (comparison <= 0) {
                    indexLessEq = curr + node.leftCount;
                    curr += 1 + node.leftCount;
                    node = node.right;
                } else {
                    node = node.left;
                }
            }
            return indexLessEq;
        }
        @Override public int getIndexGreaterEq(T value) {
            int indexGreaterEq = size;
            Node<T> node = root;
            for (int curr = 0; node != null; ) {
                int comparison = comp.compare(node.value, value);
                if (comparison >= 0) {
                    indexGreaterEq = curr + node.leftCount;
                    node = node.left;
                } else {
                    curr += 1 + node.leftCount;
                    node = node.right;
                }
            }
            return indexGreaterEq;
        }
        /**
         * Returns the first index of node with the given value,
         * or {@code -1} if there is no such node.
         * 
         * @param value to found the index
         * @return first index of node with the given value,
         *      or {@code -1} if there is no such node
         */
        public int getFirstIndexOf(T value) {
            Node<T> node = root;
            int index = -1;
            for (int curr = 0; node != null; ) {
                int comparison = comp.compare(node.value, value);
                if (comparison == 0) {
                    index = curr + node.leftCount;
                    node = node.left;
                } else if (comparison < 0) {
                    curr += 1 + node.leftCount;
                    node = node.right;
                } else {
                    node = node.left;
                }
            }
            return index;
        }
        /**
         * Returns the last index of node with the given value,
         * or {@code -1} if there is no such node.
         * 
         * @param value to found the index
         * @return last index of node with the given value,
         *      or {@code -1} if there is no such node
         */
        public int getLastIndexOf(T value) {
            Node<T> node = root;
            int index = -1;
            for (int curr = 0; node != null; ) {
                int comparison = comp.compare(node.value, value);
                if (comparison == 0) {
                    index = curr + node.leftCount;
                    curr += 1 + node.leftCount;
                    node = node.right;
                } else if (comparison < 0) {
                    curr += 1 + node.leftCount;
                    node = node.right;
                } else {
                    node = node.left;
                }
            }
            return index;
        }
    }
    
    /**
     * Removal of the given value from the tree.
     * 
     * <P>
     * Here limit (designated as <b>M</b>) is a leaf node with at most one
     * child (designated as <b>C</b>). <b>M</b> exchanges its value
     * with the found node of the given value and then <b>M</b> is being deleted.
     * Parent nodes can be unbalanced so the balancing is needed.
     * 
     * <P>
     * Sequence of actions:<ol>
     *   <li>find node to be removed or return false;</li>
     *   <li>find <b>M</b> and its <b>C</b>;</li>
     *   <li>exchange values between found node and <b>M</b>;</li>
     *   <li>delete <b>M</b>;</li>
     *   <li>{@link #removalBalance balance the tree}.</li>
     * </ol>
     * 
     * @param valueObj to be removed from the tree
     * @return {@code true} if the value was removed, otherwise - {@code false}
     */
    @Override public boolean remove(Object valueObj) {
        // find node to be removed or return false
        Node<T> found = getNode((T)valueObj);
        if (found == null) {
            return false;
        }
        remove(found);
        return true;
    }
    
    /**
     * Removal of node at the given index from the tree.
     * 
     * <P>
     * Here limit (designated as <b>M</b>) is a leaf node with at most one
     * child (designated as <b>C</b>). <b>M</b> exchanges its value
     * with the found node of the given value and then <b>M</b> is being deleted.
     * Parent nodes can be unbalanced so the balancing is needed.
     * 
     * <P>
     * Sequence of actions:<ol>
     *   <li>find node to be removed or return null;</li>
     *   <li>find <b>M</b> and its <b>C</b>;</li>
     *   <li>change values of found node and <b>M</b>;</li>
     *   <li>delete <b>M</b>;</li>
     *   <li>{@link #removalBalance balance the tree}.</li>
     * </ol>
     * 
     * @param index node at this index is being deleted
     * @return removed value if it was removed, otherwise - {@code null}
     */
    public T removeAt(int index) {
        throwIfBadIndex(index);
        // find node to be removed or return null
        Node<T> found = getNodeAt(index);
        if (found == null) {
            return null;
        }
        T removed = found.value;
        remove(found);
        return removed;
    }
    
    private void remove(Node<T> found) {
        // find limit and child
        Node<T> limit = found, child = null; // l==null && r==null
        if (found.left != null && found.right == null) {
            limit = found.left;
            while (limit.right != null) {
                limit = limit.right;
            }
            child = limit.left;
        } else if (found.right != null) {
            limit = found.right;
            while (limit.left != null) {
                limit = limit.left;
            }
            child = limit.right;
        }
        // change value
        found.value = limit.value;
        // delete limit and balance the tree
        Node<T> p = limit.parent;
        if (child != null) {
            child.parent = p;
        }
        limit.clear();
        if (p != null) {
            if (p.left == limit) {
                p.leftCount--;
                p.left = child;
                if (unbalanced(p.rightCount, p.leftCount)) {
                    Node<T> r = p.right;
                    if (
                            unbalanced(r.leftCount, p.leftCount)
                            || unbalanced(r.leftCount + 1 + p.leftCount, r.rightCount)
                    ) {
                        r.rotateRight();
                    }
                    p.rotateLeft();
                    p = p.parent;
                }
            } else {
                p.rightCount--;
                p.right = child;
                if (unbalanced(p.leftCount, p.rightCount)) {
                    Node<T> l = p.left;
                    if (
                            unbalanced(l.rightCount, p.rightCount)
                            || unbalanced(l.rightCount + 1 + l.rightCount, l.leftCount)
                    ) {
                        l.rotateLeft();
                    }
                    p.rotateRight();
                    p = p.parent;
                }
            }
            removalBalance(p);
        } else {
            root = child;
        }
        size--;
    }
    
    /**
     * After the {@link #remove removal} of a node the balance
     * can be violated (see {@link #unbalanced}).
     * The loop of the balancing starts from defining <b>P</b>
     * as a parent of the node (designated here as <b>M</b>)
     * and iterates while <code><b>P</b> != null</code>.
     * Also sibling of the node is designated as <b>S</b>.
     * 
     * <P>
     * In each iteration:<ol>
     *   <li>corresponding count (left or right) of <b>P</b> is decreased;</li>
     *   <li>if <b>P</b> is unbalanced and rotation of <b>P</b>
     *     leads to unbalancing <b>P</b> or <b>S</b>, then rotate <b>S</b>
     *     and after that rotate <b>P</b>; next <b>M</b> is <b>P</b>.parent;</li>
     *   <li> else if <b>P</b> is unbalanced and rotation of it is enough
     *     for balancing just rotate <b>P</b>; next <b>M</b> is <b>P</b>.parent;</li>
     *   <li>otherwise <b>P</b> is balanced and next <b>M</b> is <b>P</b>.</li>
     * </ol>
     * 
     * @param node limit which had held the removed value
     */
    private void removalBalance(Node<T> node) {
        for (Node<T> p; (p = node.parent) != null; ) {
            if (p.left == node) {
                p.leftCount--;
                if (unbalanced(p.rightCount, p.leftCount)) {
                    Node<T> r = p.right;
                    if (
                            unbalanced(r.leftCount, p.leftCount)
                            || unbalanced(r.leftCount + 1 + p.leftCount, r.rightCount)
                    ) {
                        r.rotateRight();
                    }
                    p.rotateLeft();
                    node = p.parent;
                } else {
                    node = p;
                }
            } else {
                p.rightCount--;
                if (unbalanced(p.leftCount, p.rightCount)) {
                    Node<T> l = p.left;
                    if (
                            unbalanced(l.rightCount, p.rightCount)
                            || unbalanced(l.rightCount + 1 + l.rightCount, l.leftCount)
                    ) {
                        l.rotateLeft();
                    }
                    p.rotateRight();
                    node = p.parent;
                } else {
                    node = p;
                }
            }
        }
        root = node;
    }
    
    @Override public void clear() {
        if (root == null) {
            return;
        }
        for (Node<T> node = root; node != null; ) {
            if (node.left != null) {
                node = node.left;
            } else if (node.right != null) {
                node = node.right;
            } else {
                Node<T> p = node.parent;
                if (p != null) {
                    if (p.left == node) {
                        p.left = null;
                    } else {
                        p.right = null;
                    }
                }
                node.clear();
                node = p;
            }
        }
        root = null;
        size = 0;
    }
    
    /**
     * Accepts the action to every node of the tree in an ascending order.
     * @param action to be accepted
     */
    public void forEachNode(Consumer<Node<T>> action) {
        Object[] stack = new Object[size];
        int stackN = 0;
        for (Node<T> node = root; node != null; node = node.left) {
            stack[stackN++] = node;
        }
        while (stackN > 0) {
            Node<T> node = (Node<T>)stack[--stackN];
            action.accept(node);
            for (node = node.right; node != null; node = node.left) {
                stack[stackN++] = node;
            }
        }
    }
    
    /**
     * Accepts the action to every value of the tree in an ascending order.
     * Uses {@link forEachNode}.
     * 
     * @param action to be accepted
     */
    @Override public void forEach(Consumer<? super T> action) {
        forEachNode(node -> action.accept(node.value));
    }
    
    @Override public Iterator<T> iterator() {
        return new CBTreeIterator<>(this);
    }
    
    private static class CBTreeIterator<T> implements Iterator<T> {
        private final Object[] stack;
        private int stackN;
        private CBTreeIterator(CBTree<T> tree) {
            stack = new Object[tree.size];
            stackN = 0;
            for (Node<T> node = tree.root; node != null; node = node.left) {
                stack[stackN++] = node;
            }
        }
        @Override public boolean hasNext() {
            return stackN > 0;
        }
        @Override public T next() {
            if (stackN == 0) {
                throw new NoSuchElementException("there is no more elements");
            }
            Node<T> found = (Node<T>)stack[--stackN];
            for (Node<T> node = found.right; node != null; node = node.left) {
                stack[stackN++] = node;
            }
            return found.value;
        }
    }
    
    @Override public boolean addAll(Collection<? extends T> collection) {
        boolean changed = false;
        for (T value : collection) {
            if (add(value)) {
                changed = true;
            }
        }
        return changed;
    }
    
    @Override public boolean containsAll(Collection<?> collection) {
        for (Object valueObj : collection) {
            if (!contains((T)valueObj)) {
                return false;
            }
        }
        return true;
    }
    
    @Override public boolean removeAll(Collection<?> collection) {
        boolean changed = false;
        for (Object valueObj : collection) {
            if (remove((T)valueObj)) {
                changed = true;
            }
        }
        return changed;
    }
    
    @Override public boolean retainAll(Collection<?> collection) {
        List<T> removalList = new ArrayList<>(size);
        for (T value : this) {
            if (!collection.contains(value)) {
                removalList.add(value);
            }
        }
        for (T value : removalList) {
            remove(value);
        }
        return removalList.isEmpty();
    }
    
    @Override public Object[] toArray() {
        Object[] array = new Object[size];
        int arrayN = 0;
        for (T value : this) {
            array[arrayN++] = value;
        }
        return array;
    }
    
    @Override public <E> E[] toArray(E[] array) {
        Object[] stack = new Object[size];
        int stackN = 0;
        for (T value : this) {
            stack[stackN++] = value;
        }
        if (array.length < size) {
            return (E[])Arrays.copyOf(stack, size, array.getClass());
        }
        System.arraycopy(stack, 0, array, 0, size);
        if (array.length > size) {
            array[size] = null;
        }
        return array;
    }
    
    @Override public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        Iterator<T> iter = iterator();
        if (iter.hasNext()) {
            sb.append(iter.next());
        }
        while (iter.hasNext()) {
            sb.append(", ").append(iter.next());
        }
        sb.append('}');
        return sb.toString();
    }
    
    /**
     * Node is an actual component of the {@link CBTree}.
     * It holds {@code value}, counts of descendant nodes in the left
     * and right subtrees ({@code leftCount} and {@code rightCount}),
     * {@code parent}, {@code left} child and {@code right} child.
     * 
     * @param <T> type of value maintained by the node
     */
    public static class Node<T> {
        private T value;
        private int leftCount, rightCount;
        private Node<T> parent, left, right;
        private Node(T value, Node<T> parent) {
            this.value = value;
            this.leftCount = 0;
            this.rightCount = 0;
            this.parent = parent;
            this.left = null;
            this.right = null;
        }
        private static <T> Node<T> newRoot(T value) {
            return new Node<>(value, null);
        }
        private static <T> Node<T> newNode(T value, Node<T> parent) {
            return new Node<>(value, parent);
        }
        private void clear() {
            value = null;
            parent = left = right = null;
        }
        /**
         * Left-rotation of the node.
         * <pre>
         *    N             R  
         *   / \           / \ 
         *  L   R         N  RR
         *     / \  -->  / \   
         *    RL RR     L  RL  
         *  
         *  N.rightCount = R.leftCount;
         *  R.leftCount  = R.leftCount + 1 + N.leftCount;
         * </pre>
         */
        private void rotateLeft() {
            // counts
            rightCount = right.leftCount;
            right.leftCount += 1 + leftCount;
            // nodes
            Node<T> r = right;
            r.parent = parent;
            if (parent != null) {
                if (parent.left == this) {
                    parent.left = r;
                } else {
                    parent.right = r;
                }
            }
            right = r.left;
            if (r.left != null) {
                r.left.parent = this;
            }
            parent = r;
            r.left = this;
        }
        /**
         * Right-rotation of the node.
         * <pre>
         *      N         L    
         *     / \       / \   
         *    L   R     LL  N  
         *   / \    -->    / \ 
         *  LL LR         LR  R
         *  
         *  N.leftCount  = L.rightCount;
         *  L.rightCount = L.rightCount + 1 + N.rightCount;
         * </pre>
         */
        private void rotateRight() {
            // counts
            leftCount = left.rightCount;
            left.rightCount += 1 + rightCount;
            // nodes
            Node<T> l = left;
            l.parent = parent;
            if (parent != null) {
                if (parent.left == this) {
                    parent.left = l;
                } else {
                    parent.right = l;
                }
            }
            left = l.right;
            if (l.right != null) {
                l.right.parent = this;
            }
            parent = l;
            l.right = this;
        }
        /**
         * Returns value of the node.
         * @return value of the node
         */
        public T value() {
            return value;
        }
        /**
         * Returns count of descendant nodes in the left subtree.
         * @return count of descendant nodes in the left subtree
         */
        public int leftCount() {
            return leftCount;
        }
        /**
         * Returns count of descendant nodes in the right subtree.
         * @return count of descendant nodes in the right subtree
         */
        public int rightCount() {
            return rightCount;
        }
        /**
         * Returns parent node to this node.
         * @return parent node to this node
         */
        public Node<T> parent() {
            return parent;
        }
        /**
         * Returns left child of this node.
         * @return left child of this node
         */
        public Node<T> left() {
            return left;
        }
        /**
         * Returns right child of this node.
         * @return right child of this node
         */
        public Node<T> right() {
            return right;
        }
        @Override public String toString() {
            return "Node{value=" + value
                    + ", leftCount=" + leftCount
                    + ", rightCount=" + rightCount
                    + '}';
        }
        /**
         * Returns dummy node for some purpose, examply gratia for using in a queue.
         * @param <T> type of value maintained by the node
         * @return dummy node for some purpose, examply gratia for using in a queue
         */
        public static <T> Node<T> dummy() {
            return new Node<>(null, null);
        }
    }
    
}
