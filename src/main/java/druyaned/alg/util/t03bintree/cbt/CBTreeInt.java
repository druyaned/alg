package druyaned.alg.util.t03bintree.cbt;

import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;

public abstract class CBTreeInt {
    
    protected Node root = null;
    protected int size = 0;
    protected final IntBinaryOperator comp;
    
    public CBTreeInt(IntBinaryOperator comp) {
        if (comp == null) {
            throw new NullPointerException("comp can't be null");
        }
        this.comp = comp;
    }
    
    public Node root() {
        return root;
    }
    
    public int size() {
        return size;
    }
    
    public boolean isEmpty() {
        return size == 0;
    }
    
    public boolean contains(int value) {
        return getNode(value) != null;
    }
    
    public abstract Node getNode(int value);
    
    public Node getNodeAt(int index) {
        throwIfBadIndex(index);
        Node node = root;
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
    
    public Node getNodeFirst() {
        if (root == null) {
            return null;
        }
        Node node = root;
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }
    
    public Node getNodeLast() {
        if (root == null) {
            return null;
        }
        Node node = root;
        while (node.right != null) {
            node = node.right;
        }
        return node;
    }
    
    public Node getNodeLess(int value) {
        Node less = null;
        for (Node node = root; node != null; ) {
            int comparison = comp.applyAsInt(node.value, value);
            if (comparison < 0) {
                less = node;
                node = node.right;
            } else {
                node = node.left;
            }
        }
        return less;
    }
    
    public Node getNodeGreater(int value) {
        Node greater = null;
        for (Node node = root; node != null; ) {
            int comparison = comp.applyAsInt(node.value, value);
            if (comparison > 0) {
                greater = node;
                node = node.left;
            } else {
                node = node.right;
            }
        }
        return greater;
    }
    
    public abstract Node getNodeLessEq(int value);
    
    public abstract Node getNodeGreaterEq(int value);
    
    public int getIndexLess(int value) {
        int lessIndex = -1;
        Node node = root;
        for (int curr = 0; node != null; ) {
            int comparison = comp.applyAsInt(node.value, value);
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
    
    public int getIndexGreater(int value) {
        int greaterIndex = size;
        Node node = root;
        for (int curr = 0; node != null; ) {
            int comparison = comp.applyAsInt(node.value, value);
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
    
    public abstract int getIndexLessEq(int value);
    
    public abstract int getIndexGreaterEq(int value);
    
    public static boolean unbalanced(int increasedCount, int siblingCount) {
        return Integer.numberOfLeadingZeros(siblingCount)
                - Integer.numberOfLeadingZeros(increasedCount) > 1;
    }
    
    public abstract boolean add(int value);
    
    protected final void insertionBalance(Node node) {
        for (Node p; (p = node.parent) != null; ) {
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
    
    public static class CBSTreeInt extends CBTreeInt {
        public CBSTreeInt(IntBinaryOperator comp) {
            super(comp);
        }
        @Override public boolean add(int value) {
            // new root case
            if (root == null) {
                root = Node.newRoot(value);
                size++;
                return true;
            }
            // find insertion place
            Node node = root, p;
            int comparison;
            do {
                p = node;
                comparison = comp.applyAsInt(node.value, value);
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
        @Override public Node getNode(int value) {
            for (Node node = root; node != null; ) {
                int comparison = comp.applyAsInt(node.value, value);
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
        @Override public Node getNodeLessEq(int value) {
            Node lessEq = null;
            for (Node node = root; node != null; ) {
                int comparison = comp.applyAsInt(node.value, value);
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
        @Override public Node getNodeGreaterEq(int value) {
            Node greaterEq = null;
            for (Node node = root; node != null; ) {
                int comparison = comp.applyAsInt(node.value, value);
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
        @Override public int getIndexLessEq(int value) {
            int indexLessEq = -1;
            Node node = root;
            for (int curr = 0; node != null; ) {
                int comparison = comp.applyAsInt(node.value, value);
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
        @Override public int getIndexGreaterEq(int value) {
            int indexGreaterEq = size;
            Node node = root;
            for (int curr = 0; node != null; ) {
                int comparison = comp.applyAsInt(node.value, value);
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
        public int getIndexOf(int value) {
            Node node = root;
            for (int curr = 0; node != null; ) {
                int comparison = comp.applyAsInt(node.value, value);
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
    
    public static class CBMTreeInt extends CBTreeInt {
        public CBMTreeInt(IntBinaryOperator comp) {
            super(comp);
        }
        @Override public boolean add(int value) {
            // new root case
            if (root == null) {
                root = Node.newRoot(value);
                size++;
                return true;
            }
            // find insertion place
            Node node = root, p;
            int comparison;
            do {
                p = node;
                comparison = comp.applyAsInt(node.value, value);
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
        @Override public Node getNode(int value) {
            Node equal = null;
            for (Node node = root; node != null; ) {
                int comparison = comp.applyAsInt(node.value, value);
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
        @Override public Node getNodeLessEq(int value) {
            Node lessEq = null;
            for (Node node = root; node != null; ) {
                int comparison = comp.applyAsInt(node.value, value);
                if (comparison == 0) {
                    lessEq = node;
                    node = node.right;
                } else if (comparison < 0) {
                    lessEq = node;
                    node = node.right;
                } else {
                    node = node.left;
                }
            }
            return lessEq;
        }
        @Override public Node getNodeGreaterEq(int value) {
            Node greaterEq = null;
            for (Node node = root; node != null; ) {
                int comparison = comp.applyAsInt(node.value, value);
                if (comparison == 0) {
                    greaterEq = node;
                    node = node.left;
                } else if (comparison > 0) {
                    greaterEq = node;
                    node = node.left;
                } else {
                    node = node.right;
                }
            }
            return greaterEq;
        }
        @Override public int getIndexLessEq(int value) {
            int indexLessEq = -1;
            Node node = root;
            for (int curr = 0; node != null; ) {
                int comparison = comp.applyAsInt(node.value, value);
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
        @Override public int getIndexGreaterEq(int value) {
            int indexGreaterEq = size;
            Node node = root;
            for (int curr = 0; node != null; ) {
                int comparison = comp.applyAsInt(node.value, value);
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
        public int getFirstIndexOf(int value) {
            Node node = root;
            int index = -1;
            for (int curr = 0; node != null; ) {
                int comparison = comp.applyAsInt(node.value, value);
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
        public int getLastIndexOf(int value) {
            Node node = root;
            int index = -1;
            for (int curr = 0; node != null; ) {
                int comparison = comp.applyAsInt(node.value, value);
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
    
    public boolean remove(int value) {
        // find node to be removed or return false
        Node found = getNode(value);
        if (found == null) {
            return false;
        }
        remove(found);
        return true;
    }
    
    public Node removeAt(int index) {
        throwIfBadIndex(index);
        // find node to be removed or return null
        Node found = getNodeAt(index);
        if (found == null) {
            return null;
        }
        remove(found);
        return found;
    }
    
    private void remove(Node found) {
        // find limit and child
        Node limit = found, child = null; // l==null && r==null
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
        Node p = limit.parent;
        if (child != null) {
            child.parent = p;
        }
        limit.clear();
        if (p != null) {
            if (p.left == limit) {
                p.leftCount--;
                p.left = child;
                if (unbalanced(p.rightCount, p.leftCount)) {
                    Node r = p.right;
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
                    Node l = p.left;
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
    
    private void removalBalance(Node node) {
        for (Node p; (p = node.parent) != null; ) {
            if (p.left == node) {
                p.leftCount--;
                if (unbalanced(p.rightCount, p.leftCount)) {
                    Node r = p.right;
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
                    Node l = p.left;
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
    
    public void clear() {
        if (root == null) {
            return;
        }
        for (Node node = root; node != null; ) {
            if (node.left != null) {
                node = node.left;
            } else if (node.right != null) {
                node = node.right;
            } else {
                Node p = node.parent;
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
    
    public void forEachNode(Consumer<Node> action) {
        Node[] stack = new Node[size];
        int stackN = 0;
        for (Node node = root; node != null; node = node.left) {
            stack[stackN++] = node;
        }
        while (stackN > 0) {
            Node node = stack[--stackN];
            action.accept(node);
            for (node = node.right; node != null; node = node.left) {
                stack[stackN++] = node;
            }
        }
    }
    
    public void forEach(IntConsumer action) {
        forEachNode(node -> action.accept(node.value));
    }
    
    public IteratorInt iterator() {
        return new CBTreeIntIterator(this);
    }
    
    public static interface IteratorInt {
        boolean hasNext();
        int next();
    }
    
    private static class CBTreeIntIterator implements IteratorInt {
        private final Node[] stack;
        private int stackN;
        private CBTreeIntIterator(CBTreeInt tree) {
            stack = new Node[tree.size];
            stackN = 0;
            for (Node node = tree.root; node != null; node = node.left) {
                stack[stackN++] = node;
            }
        }
        @Override public boolean hasNext() {
            return stackN > 0;
        }
        @Override public int next() {
            if (stackN == 0) {
                throw new NoSuchElementException("there is no more elements");
            }
            Node found = stack[--stackN];
            for (Node node = found.right; node != null; node = node.left) {
                stack[stackN++] = node;
            }
            return found.value;
        }
    }
    
    public int[] toArray() {
        int[] array = new int[size];
        int arrayN = 0;
        for (IteratorInt iter = iterator(); iter.hasNext(); ) {
            array[arrayN++] = iter.next();
        }
        return array;
    }
    
    @Override public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        IteratorInt iter = iterator();
        if (iter.hasNext()) {
            sb.append(iter.next());
        }
        while (iter.hasNext()) {
            sb.append(", ").append(iter.next());
        }
        sb.append('}');
        return sb.toString();
    }
    
    public static class Node {
        private int value;
        private int leftCount, rightCount;
        private Node parent, left, right;
        private Node(int value, Node parent) {
            this.value = value;
            this.leftCount = 0;
            this.rightCount = 0;
            this.parent = parent;
            this.left = null;
            this.right = null;
        }
        private static Node newRoot(int value) {
            return new Node(value, null);
        }
        private static Node newNode(int value, Node parent) {
            return new Node(value, parent);
        }
        private void clear() {
            parent = left = right = null;
        }
        private void rotateLeft() {
            // counts
            rightCount = right.leftCount;
            right.leftCount += 1 + leftCount;
            // nodes
            Node r = right;
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
        private void rotateRight() {
            // counts
            leftCount = left.rightCount;
            left.rightCount += 1 + rightCount;
            // nodes
            Node l = left;
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
        public int value() {
            return value;
        }
        public int leftCount() {
            return leftCount;
        }
        public int rightCount() {
            return rightCount;
        }
        public Node parent() {
            return parent;
        }
        public Node left() {
            return left;
        }
        public Node right() {
            return right;
        }
        @Override public String toString() {
            return "Node{value=" + value
                    + ", leftCount=" + leftCount
                    + ", rightCount=" + rightCount
                    + '}';
        }
        public static Node dummy() {
            return new Node(0, null);
        }
    }
    
}
