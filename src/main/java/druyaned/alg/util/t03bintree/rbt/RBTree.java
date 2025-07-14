package druyaned.alg.util.t03bintree.rbt;

import java.util.Comparator;
import java.util.TreeMap;
import java.util.function.Consumer;

/**
 * Red-Black Tree implementation. That's a self-balancing binary search tree
 * used in the {@link TreeMap TreeMap} as an example.
 * <a href=https://ru.wikipedia.org/wiki/Красно-чёрное_дерево>
 * Very good wiki-page</a>. Values are unique, so the tree can be considered
 * as a set. The most interesting activities are {@link #add insertion}
 * and {@link #remove removal}. The properties (balance) can be violated
 * in case of the insertion or removal. The balance is achieved through
 * left- or right-rotation of the {@link Node}. Decision of the rotation
 * is made according to the color of the nearest nodes.
 * 
 * <P><i>Properties</i><ol>
 *  <li>Every node is either red or black.</li>
 *  <li>The root is basically black.</li>
 *  <li>All leafs are black.</li>
 *  <li>A red node has only black children (and parent).</li>
 *  <li>Every path from a given node to any of its descendant leafs
 *      goes through the same number of black nodes
 *      (the main property, should be remembered).</li>
 * </ol>
 * 
 * <P><i>Left-rotation of a node</i>
 * <pre>
 *    N             R  
 *   / \           / \ 
 *  L   R         N  RR
 *     / \  -->  / \   
 *    RL RR     L  RL  
 * </pre>
 * <i>Right-rotation of a node</i>
 * <pre>
 *      N         L    
 *     / \       / \   
 *    L   R     LL  N  
 *   / \    -->    / \ 
 *  LL LR         LR  R
 * </pre>
 * 
 * @author druyaned
 * @param <T> type of value maintained by the tree
 * @see Node
 */
public class RBTree<T> {
    
    private Node<T> root = null;
    private int size = 0;
    private final Comparator<T> comp;
    
    /**
     * Creates a new Red-Black Tree with the given comparator to compare values.
     * @param comp to compare values
     */
    public RBTree(Comparator<T> comp) {
        this.comp = comp;
    }
    
    /**
     * Returns root of the tree.
     * @return root of the tree
     */
    public Node<T> root() {
        return root;
    }
    
    /**
     * Returns number of values in the tree.
     * @return number of values in the tree
     */
    public int size() {
        return size;
    }
    
    /**
     * Returns {@code true} if the tree is empty, otherwise {@code false}.
     * @return {@code true} if the tree is empty, otherwise {@code false}
     */
    public boolean isEmpty() {
        return size == 0;
    }
    
    /**
     * Returns {@code true} if the value is presented in the tree,
     * otherwise - {@code false}.
     * 
     * @param value to be found
     * @return {@code true} if the value is presented in the tree,
     *      otherwise - {@code false}
     */
    public boolean contains(T value) {
        return getNode(value) != null;
    }
    
    /**
     * Returns found {@link Node node} of the value in this tree
     * or {@code null} if there is no such value; complexity is O(log(n)).
     * 
     * @param value to found a node
     * @return found {@link Node node} of the value in this tree
     *      or {@code null} if there is no such value
     */
    public Node<T> getNode(T value) {
        for (Node<T> node = root; node != null; ) {
            int comparison = comp.compare(node.value, value);
            if (comparison < 0) {
                node = node.right;
            } else if (comparison > 0) {
                node = node.left;
            } else {
                return node;
            }
        }
        return null;
    }
    
    /**
     * Returns the first (leftmost) node of the tree.
     * @return first (leftmost) node of the tree
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
     * Returns the last (rightmost) node of the tree.
     * @return last (rightmost) node of the tree
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
     * Returns node with the last value that is less than the given value.
     * @param value to found the node
     * @return node with the last value that is less than the given value
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
     * Returns node with the first value that is greater than the given value.
     * @param value to found the node
     * @return node with the first value that is greater than the given value
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
     * Returns node with the last value that is less than or equal to the given value.
     * @param value to found the node
     * @return node with the last value that is less than or equal to the given value
     */
    public Node<T> getNodeLessEq(T value) {
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
    
    /**
     * Returns node with the first value that is greater than or equal to the given value.
     * @param value to found the node
     * @return node with the first value that is greater than or equal to the given value
     */
    public Node<T> getNodeGreaterEq(T value) {
        Node<T> greater = null;
        for (Node<T> node = root; node != null; ) {
            int comparison = comp.compare(node.value, value);
            if (comparison == 0) {
                return node;
            } else if (comparison > 0) {
                greater = node;
                node = node.left;
            } else {
                node = node.right;
            }
        }
        return greater;
    }
    
    /**
     * Returns the first (leftmost) value of the tree.
     * @return first (leftmost) value of the tree
     */
    public T getFirst() {
        Node<T> first = getNodeFirst();
        return first != null ? first.value : null;
    }
    
    /**
     * Returns the last (rightmost) value of the tree.
     * @return last (rightmost) value of the tree
     */
    public T getLast() {
        Node<T> last = getNodeLast();
        return last != null ? last.value : null;
    }
    
    /**
     * Returns the first value that is greater than the given value.
     * @param value to found the value in the tree
     * @return first value that is greater than the given value
     */
    public T getGreater(T value) {
        Node<T> greater = getNodeGreater(value);
        return greater != null ? greater.value : null;
    }
    
    /**
     * Returns the last value that is less than the given value.
     * @param value to found the value in the tree
     * @return last value that is less than the given value
     */
    public T getLess(T value) {
        Node<T> less = getNodeLess(value);
        return less != null ? less.value : null;
    }
    
    /**
     * Returns the last value that is less than or equal to the given value.
     * @param value to found the value in the tree
     * @return last value that is less than or equal to the given value
     */
    public T getLessEq(T value) {
        Node<T> lessEq = getNodeLessEq(value);
        return lessEq != null ? lessEq.value : null;
    }
    
    /**
     * Returns the first value that is greater than or equal to the given value.
     * @param value to found the value in the tree
     * @return first value that is greater than or equal to the given value
     */
    public T getGreaterEq(T value) {
        Node<T> greaterEq = getNodeGreaterEq(value);
        return greaterEq != null ? greaterEq.value : null;
    }
    
    /**
     * Accepts the given action to each node in the tree.
     * @param action to be accepted to each node in the tree
     */
    public void forEachNode(Consumer<Node<T>> action) {
        Object[] stack = new Object[size];
        int stackN = 0;
        for (Node<T> node = root; node != null; ) {
            stack[stackN++] = node;
            node = node.left;
        }
        while (stackN > 0) {
            Node<T> node = (Node<T>)stack[--stackN];
            action.accept(node);
            node = node.right;
            while (node != null) {
                stack[stackN++] = node;
                node = node.left;
            }
        }
    }
    
    /**
     * Accepts the given action to each value in the tree.
     * @param action to be accepted to each value in the tree
     */
    public void forEach(Consumer<T> action) {
        forEachNode(node -> action.accept(node.value));
    }
    
    /**
     * Inserts a node of the value into the tree if there hasn't been such value.
     * 
     * <P>A new node in the red-black tree is inserted into place of one of the leafs,
     * is colored red and 2 leafs are attached as his children.
     * What happens next depends on the color of the nearest nodes.
     * <i>Note</i>: a balance case can have a mirror analogue that the code
     * takes care of.
     * 
     * <P><i>Illustration Definitions</i><br>
     * b - black color;<br>
     * r - red color;<br>
     * N - current node;<br>
     * L - left child of the node;<br>
     * R - right child of the node;<br>
     * P - parent of the node;<br>
     * G - grandpa of the node;<br>
     * U - uncle of the node;<br>
     * S - sibling of the node;<br>
     * SL - left child of the node's sibling;<br>
     * SR - right child of the node's sibling;<br>
     * * - subtree with at least one black node;<br>
     * So 19(P b) means a parent of the current node with a black color and value=19;
     * (b) is just a leaf.
     * 
     * <P><i>Insertion Balance Execution Sequence</i><pre>
     *  if (case1) { return; }
     *  if (case2) { return; }
     *  if (case3) { case1(G); }
     *  else {
     *      case4;
     *      case5; // possible root change
     *      return;
     *  }
     * </pre>
     * 
     * <P><i>Case 1</i><br>
     * <b>N</b> is a root. It is colored black to meet the 2nd property.
     * And that's it. All other properties are not violated
     * (black node can have either red or black children).
     * 
     * <P><i>Case 2</i><br>
     * <u>Illustration</u><pre>
     *       |                           |       
     *     5(P b)                      8(P b)    
     *     /    \   [or mirror case]   /    \    
     *  2(N r)   *                    *    9(N r)
     *   / \                                / \  
     *  *   *                              *   * 
     * </pre>
     * <b>P</b> is black. Nothing should be changed. The property 5
     * is not violated cause there wasn't a red node before the insertion,
     * and a red node doesn't change the number of black nodes in the subtree.
     * <i>Note</i>: in the following cases <b>P</b> is red.
     * 
     * <P><i>Case 3</i><br>
     * <u>Illustration</u><pre>
     *          |                       |        
     *        7(G b)                  7(G r)     
     *        /    \                  /    \     
     *    5(P r)   9(U r)         5(P b)   9(U b)
     *     /  \     / \    -->     /  \     / \  
     *  3(N r) *   *   *        3(N r) *   *   * 
     *   / \                     / \             
     *  *   *                   *   *            
     * </pre>
     * <b>G</b> is black (property 4: red node definition), <b>P</b> is red and
     * <b>U</b> is red. The property 5 is not violated but the 4th is.
     * What is left to do? Recolor <b>G</b>, <b>P</b> and <b>U</b>.
     * The number of black nodes in subtrees is not changed. But the structure differs
     * so the property 4 can be violated. It's necessary to back to the case 1
     * relative to <b>G</b>.
     * 
     * <P><i>Case 4</i><br>
     * <u>Illustration</u><pre>
     *          |                          |         
     *        7(G b)                     7(G b)      
     *        /    \                     /    \      
     *  5(P r)      9(U b)         6(N r)      9(U b)
     *   /  \        / \    -->     /  \        / \  
     *  *    6(N r)              5(P r) *            
     *        / \                 / \                
     *       *   *               *   *               
     * </pre>
     * <b>G</b> is black (property 4: red node definition), <b>P</b> is red and
     * <b>U</b> is black. The property 5 is not violated but the 4th is.
     * Left-rotate <b>P</b> to move forward to the case 5.
     * <i>Note</i>: after the rotation the current Node becomes the old Parent.
     * 
     * <P><i>Case 5</i><br>
     * <u>Illustration</u><pre>
     *            |                        |            
     *          7(G b)                   6(P b)         
     *          /    \                   /    \         
     *    6(P r)      9(U b)       5(N r)      7(G r)   
     *     /  \        / \    -->   /  \        / \     
     *  5(N r) *                   *    *      *  9(U b)
     *   / \                                       / \  
     *  *   *                                           
     * </pre>
     * The continuation of the case 4.
     * Right-rotate <b>G</b>, recolor <b>G</b> and <b>P</b>.
     * All the properties are not violated.<br>
     * <i>Note</i>: after the rotations the root can be changed.
     * 
     * <P><i>Formulary Explanation of the Trick</i><pre>
     *  rr(b)b --> r(r)bb --> r(b)rb
     * </pre>
     * 
     * @param value to be inserted
     * @return {@code true} if the value has not been presented in the tree
     *      and it was successfully added, otherwise - {@code false}
     */
    public boolean add(T value) {
        if (value == null) {
            throw new NullPointerException("value must not be null");
        }
        if (size == 0) {
            root = Node.newRoot(value);
            size++;
            return true;
        }
        Node<T> node = root, parent;
        int comparison;
        do {
            parent = node;
            comparison = comp.compare(node.value, value);
            if (comparison < 0) {
                node = node.right;
            } else if (comparison > 0) {
                node = node.left;
            } else {
                return false;
            }
        } while (node != null);
        if (comparison < 0) {
            parent.right = Node.newNode(value, parent);
            insertionBalance(parent.right);
        } else {
            parent.left = Node.newNode(value, parent);
            insertionBalance(parent.left);
        }
        size++;
        return true;
    }
    
    private void insertionBalance(Node<T> node) {
        // cases 1, 2
        while (node.parent != null && node.parent.color == Color.RED) {
            // case 3: recolor G, P and U
            Node<T> u = node.uncle();
            if (u != null && u.color == Color.RED) {
            // && node.parent.color == Color.RED
                node.parent.color = Color.BLACK;
                u.color = Color.BLACK;
                Node<T> g = node.grandpa();
                g.color = Color.RED;
                node = g;
                continue; // go to the case 1 relative to G
            }
            // case 4: rotate P to prepare for the case 5
            Node<T> g = node.grandpa();
            if (node == node.parent.right && g.left == node.parent) {
                node.parent.rotateLeft();
                node = node.left;
            } else if (node == node.parent.left && g.right == node.parent) {
                node.parent.rotateRight();
                node = node.right;
            }
            // case 5: rotate G, recolor G and P
            g = node.grandpa();
            node.parent.color = Color.BLACK;
            g.color = Color.RED;
            if (node == node.parent.left && node.parent == g.left) {
                g.rotateRight();
            } else {
                g.rotateLeft();
            } if (node.parent.parent == null) {
                root = node.parent;
            }
            return;
        }
        root.color = Color.BLACK;
    }
    
    /**
     * Removes the value from this tree, if there has been any.
     * See the illustration definitions in the {@link #add insertion}.
     * 
     * <P><i>Main Removal Stages</i>:<ol>
     *  <li>find the node</li>
     *  <li>find the limit</li>
     *  <li>copy limit value into the found node</li>
     *  <li>remove the limit by replacing by its child</li>
     *  <li>balance cases</li>
     * </ol>
     * 
     * <P>After finding the node with the value the limit is being determined -
     * the lowest value of the right subtree or the highest in the left subtree.
     * The limit has at most one not-leaf-child (otherwise it's not the limit).
     * Then the values between the node with the given value and the limit are replaced.
     * 
     * <P>Let the limit (min or max) node be <b>M</b> and his not-leaf-child
     * or any leaf-child be <b>C</b>. At this moment there could be some simple cases.<br>
     * <u>Simple Case 1</u><pre>
     *     |              |   
     *   4(M r)          (C b)
     *    /  \      -->       
     *  (b)  (C b)            
     * </pre>
     * If <b>M</b> is red it is replaced by <b>C</b> which can be only a leaf
     * (otherwise the 5th or 4th properties are violated for the origin tree).
     * All properties are not violated after the replacement, so that's it.<br>
     * <u>Simple Case 2</u><pre>
     *       |              |    
     *     5(M b)         3(C b) 
     *      /  \           /  \  
     *  3(C r) (b)  -->  (b)  (b)
     *    / \                    
     *  (b) (b)                  
     * </pre>
     * If <b>M</b> is black and <b>C</b> is red <b>M</b> is replaced
     * by <b>C</b> and is being colored black. All properties are not violated.<br>
     * <u>Complicated Case 3</u><pre>
     *       |           |   
     *     4(M b)       (C b)
     *      / \    -->       
     *  (C b) (b)            
     * </pre>
     * <b>M</b> and <b>C</b> are black. After replacement by child
     * the property 5 is violated. That's why this case produces
     * the other 6 balance cases.<br>
     * <i>Important Notes</i><ol>
     *  <li>The children of <b>M</b> are both leafs (otherwise the property 5
     *      is violated for the origin tree).</li>
     *  <li>In the following balance cases <b>C</b> (which replaced <b>M</b>)
     *      is called as <b>N</b>.</li>
     *  <li>All balance cases are designed to make the tree valid for
     *      the 5th and all other properties.</li>
     *  <li>A balance case can have a mirror analogue that the code
     *      takes is care of.</li>
     * </ol>
     * 
     * <P><i>Removal Balance Execution Sequence</i><pre>
     *  if (case1) { return; };
     *  if (case2) { case1(P); }
     *  else {
     *      case3; // possible root change
     *      if (case4) { return; }
     *      case5;
     *      case6; // possible root change
     *      return;
     *  }
     * </pre>
     * 
     * <P><i>Removal Balance Formulary Notation</i><pre>
     *  case2: b(b)bb -> b(b)rb
     *  case3: b(b)rb* --> [b(r)b*](b)b* --> b(r)b*
     *  case4: b(r)bb --> b(b)rb
     *  case5: *r(b)b --> *(b)[*(r)b]
     *  case6: b(a)[*(b)r*] --> bb(a)b*
     * </pre>
     * 
     * <P><i>Case 1</i><br>
     * If <b>N</b> is a root all's done.<br>
     * 
     * <P><i>Case 2</i><br>
     * <u>Illustration</u><pre>
     *          |                            |             
     *        5(P b)                       5(P b)          
     *        /    \                       /    \          
     *  2(N b)      8(S b)      -->  2(N b)      8(S r)    
     *   /  \        / \              /  \         / \     
     *          (SL b) (SR b)                 (SL b) (SR b)
     *            / \   / \                     / \   / \  
     * </pre>
     * <b>N</b>, <b>P</b>, <b>S</b>, <b>SL</b> and <b>SR</b> are black.
     * Recolor <b>S</b> and go to the case 1 for <b>P</b>.
     * Now the property 5 is not violated for subtree of <b>S</b>.
     * But the number of black nodes in the right subtree of <b>P</b>
     * was changed, that's why the going to the case 1 is necessary.<br>
     * <i>Note</i>: <b>SL</b> and <b>SR</b> don't have values cause they
     * can be leafs.
     * 
     * <P><i>Case 3</i><br>
     * <u>Illustration</u><pre>
     *          |                                 |          
     *        5(P b)                            8(S b)       
     *        /    \                            /    \       
     *  3(N b)      8(S r)                5(P r)      9(SR b)
     *   /  \        / \        -->        /  \         / \  
     *         6(SL b) 9(SR b)        3(N b) 6(SL b)   *   * 
     *           / \     / \            / \    / \           
     *          *   *   *   *                 *   *          
     * </pre>
     * <b>N</b>, <b>P</b>, <b>SL</b> and <b>SR</b> are black and <b>S</b> is red.
     * Left-rotate <b>P</b>, recolor <b>P</b> and <b>S</b>. Before the rotation
     * the property 5 was violated so each of <b>SL</b> and <b>SR</b> had
     * at least 1 black child. This case is a preparation for the next changes.<br>
     * <i>Important Notes</i><ol>
     *  <li>The root can be changed.</li>
     *  <li>The left branch of the <b>P</b> ("256") is at the focus
     *      in the following cases (imagine its left-rotation).</li>
     *  <li>Before and after the rotation the right branch of <b>P</b> ("689")
     *      had and has at least 2 black nodes. The task is to make the left
     *      branch having the same amount of black nodes (property 5).</li>
     * </ol>
     * 
     * <P><i>Case 4</i><br>
     * <u>Illustration</u><pre>
     *          |                            |             
     *        5(P r)                       5(P b)          
     *        /    \                       /    \          
     *  2(N b)      8(S b)      -->  2(N b)      8(S r)    
     *   /  \        / \              /  \         / \     
     *          (SL b) (SR b)                 (SL b) (SR b)
     *           / \     / \                   / \     / \ 
     * </pre>
     * An another way or a continuance of the case 3 with the updated roles.
     * <b>P</b> is red and <b>N</b>, <b>S</b>, <b>SL</b> and <b>SR</b> are black.
     * Recolor <b>P</b> and <b>S</b>. The 5th and all other properties are
     * not violated. Otherwise go to the case 5.<br>
     * 
     * <P><i>Case 5</i><br>
     * <u>Illustration</u><pre>
     *         |                  |         
     *       4(S b)            3(SL b)      
     *       /    \             /    \      
     *  3(SL r)   (SR b)  -->  *   4(S r)   
     *     / \     / \              /  \    
     *    *   *                    *  (SR b)
     *                                 / \  
     * </pre>
     * An another continuance of the case 3 and a preparation for the case 6.
     * <b>S</b> and <b>SR</b> are black and <b>SL</b> is red.
     * Right-rotate <b>S</b>, recolor <b>SL</b> and <b>S</b>.
     * 
     * <P><i>Case 6</i><br>
     * <u>Illustration</u><pre>
     *       |                         |         
     *      4(P)                      6(S)       
     *     /    \                    /    \      
     *  3(N b) 6(S b)             4(P b)  8(SR b)
     *   / \    / \       -->      /  \     / \  
     *         * 8(SR r)       3(N b)  *   *   * 
     *            / \            / \             
     *           *   *                           
     * </pre>
     * A continuance of the case 5. <b>P</b> can be red or black,
     * <b>N</b> and <b>S</b> are black, <b>SR</b> is red.
     * Left-rotate <b>P</b>, recolor <b>S</b> as <b>P</b>,
     * recolor <b>P</b> and <b>SR</b> to black.
     * So the 5th and all others properties are not violated.<br>
     * <i>Note</i>: the root can be changed.
     * 
     * @param value to be removed
     * @return {@code true} if the value has been presented in the tree
     *      and it was successfully removed, otherwise - {@code false}
     */
    public boolean remove(T value) {
        Node<T> found = getNode(value);
        if (found == null) {
            return false;
        }
        Node<T> limit = found; // l==null && r==null
        if (found.left != null && found.right == null) {
            limit = found.left;
            while (limit.right != null) {
                limit = limit.right;
            }
        } else if (found.right != null) {
            // (l==null && r!=null) || (l!=null && r!=null)
            limit = found.right;
            while (limit.left != null) {
                limit = limit.left;
            }
        }
        found.value = limit.value;
        deleteLimit(limit);
        limit.clear();
        size--;
        return true;
    }
    
    private void deleteLimit(Node<T> limit) {
        if (limit.color == Color.RED) { // simple case 1
            replaceLimitParent(limit, null);
            return;
        } // limit.color == Color.BLACK
        Node<T> child = limit.left == null ? limit.right : limit.left;
        if (child != null) { // simple case 2
            child.color = Color.BLACK;
            child.parent = limit.parent;
            replaceLimitParent(limit, child);
            return;
        } // child == null; complicated case 3
        removalBalance(limit);
        replaceLimitParent(limit, null);
    }
    
    private void replaceLimitParent(Node<T> limit, Node<T> child) {
        if (limit.parent != null) {
            if (limit.parent.left == limit) {
                limit.parent.left = child;
            } else {
                limit.parent.right = child;
            }
        } else {
            root = child;
        }
    }
    
    private void removalBalance(Node<T> node) {
        while (node.parent != null) {
            // case 2: recolor S and go to the case 1 for P
            Node<T> sibling = node.sibling();
            if (
                    node.parent.color == Color.BLACK &&
                    sibling.color == Color.BLACK &&
                    Node.isBlack(sibling.left) &&
                    Node.isBlack(sibling.right)
            ) {
                sibling.color = Color.RED;
                node = node.parent;
                continue; // case1(node.parent)
            }
            // case 3: left-rotate P, recolor P and S
            if (sibling.color == Color.RED) {
                node.parent.color = Color.RED;
                sibling.color = Color.BLACK;
                if (node.parent.left == node) {
                    node.parent.rotateLeft();
                } else {
                    node.parent.rotateRight();
                }
                if (sibling.parent == null) {
                    root = sibling;
                }
            }
            // case 4: recolor P and S
            sibling = node.sibling();
            if (
                    node.parent.color == Color.RED &&
                    sibling.color == Color.BLACK &&
                    Node.isBlack(sibling.left) &&
                    Node.isBlack(sibling.right)
            ) {
                node.parent.color = Color.BLACK;
                sibling.color = Color.RED;
                return;
            }
            // case 5: right-rotate S, recolor SL and S
            if (sibling.color == Color.BLACK) {
                if (
                        node.parent.left == node &&
                        Node.isBlack(sibling.right) &&
                        Node.isRed(sibling.left)
                ) {
                    sibling.color = Color.RED;
                    sibling.left.color = Color.BLACK;
                    sibling.rotateRight();
                } else if (
                        node.parent.right == node &&
                        Node.isBlack(sibling.left) &&
                        Node.isRed(sibling.right)
                ) {
                    sibling.color = Color.RED;
                    sibling.right.color = Color.BLACK;
                    sibling.rotateLeft();
                }
            }
            // case 6: left-rotate P, recolor S as P,
            // recolor P and SR to black
            sibling = node.sibling();
            sibling.color = node.parent.color;
            node.parent.color = Color.BLACK;
            if (node.parent.left == node) {
                sibling.right.color = Color.BLACK;
                node.parent.rotateLeft();
            } else {
                sibling.left.color = Color.BLACK;
                node.parent.rotateRight();
            }
            if (sibling.parent == null) {
                root = sibling;
            }
            return;
        }
    }
    
    /**
     * Removes and clears each node in the tree; so {@code size = 0}
     * and {@code root = null}.
     */
    public void clear() {
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
    
    @Override public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        Object[] stack = new Object[size];
        int stackN = 0;
        for (Node<T> node = root; node != null; ) {
            stack[stackN++] = node;
            node = node.left;
        }
        if (stackN > 0) {
            Node<T> node = (Node<T>)stack[--stackN];
            sb.append(node.value);
            node = node.right;
            while (node != null) {
                stack[stackN++] = node;
                node = node.left;
            }
        }
        while (stackN > 0) {
            Node<T> node = (Node<T>)stack[--stackN];
            sb.append(", ").append(node.value);
            node = node.right;
            while (node != null) {
                stack[stackN++] = node;
                node = node.left;
            }
        }
        sb.append('}');
        return sb.toString();
    }
    
    /**
     * Color of the {@link Node node} of the {@link RBTree Red-Black Tree}.
     * This field is designed for the tree balance
     * (look for <i>self-balancing binary search tree (BST)</i>).
     * 
     * @author druyaned
     */
    public static enum Color {RED, BLACK}
    
    /**
     * The node is an actual component of the {@link RBT Red-Black Tree}.
     * Also it is used to get the {@link Color color} and {@code value} in a public visibility.
     * 
     * <P><i>Types of Node</i><ul>
     *  <li>root</li>
     *  <li>leaf</li>
     *  <li>node</li>
     * </ul>
     * 
     * <P><i>Root Properties</i><ul>
     *  <li>The {@code value} is {@code NOT NULL}.</li>
     *  <li>The {@code color} is {@code BLACK}.</li>
     *  <li>The {@code parent} is {@code NULL} and each of the {@code children}
     *      can be {@code LEAF} or {@code NOT NULL}.</li>
     * </ul>
     * 
     * <P><i>Leaf Property</i><br>
     * It's {@code NULL} but the color is {@code BLACK}.
     * 
     * <P><i>Node Properties</i><ul>
     *  <li>The {@code value} is {@code NOT NULL}.</li>
     *  <li>The {@code color} is {@code BLACK} or {@code RED}.</li>
     *  <li>The {@code parent} is {@code NOT NULL} and each of the {@code children}
     *      can be {@code LEAF} or {@code NOT NULL}.</li>
     * </ul>
     * 
     * @author druyaned
     * @param <T> the type of value maintained by the node
     */
    public static class Node<T> {
        private T value;
        private Color color;
        private Node<T> parent, left, right;
        private Node(T value, Color color, Node<T> parent) {
            this.value = value;
            this.color = color;
            this.parent = parent;
            this.left = null;
            this.right = null;
        }
        private static <T> Node<T> newRoot(T value) {
            return new Node<>(value, Color.BLACK, null);
        }
        private static <T> Node<T> newNode(T value, Node<T> parent) {
            return new Node<>(value, Color.RED, parent);
        }
        private static <T> boolean isBlack(Node<T> node) {
            return node == null || node.color == Color.BLACK;
        }
        private static <T> boolean isRed(Node<T> node) {
            return node != null && node.color == Color.RED;
        }
        private Node<T> sibling() {
            return parent.left == this ? parent.right : parent.left;
        }
        private Node<T> grandpa() {
            return parent.parent;
        }
        private Node<T> uncle() {
            return parent.parent.left == parent
                    ? parent.parent.right
                    : parent.parent.left;
        }
        private void clear() {
            value = null;
            color = null;
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
         * </pre>
         */
        private void rotateLeft() {
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
         * </pre>
         */
        private void rotateRight() {
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
        public T value() {
            return value;
        }
        public Color color() {
            return color;
        }
        public Node<T> parent() {
            return parent;
        }
        public Node<T> left() {
            return left;
        }
        public Node<T> right() {
            return right;
        }
        @Override public String toString() {
            return "Node{value=" + value
                    + ", color=" + color
                    + '}';
        }
    }
    
}
