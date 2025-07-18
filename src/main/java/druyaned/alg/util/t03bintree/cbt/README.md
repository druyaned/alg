# Count-Balance Tree

Count-Balance Tree is a self-balancing binary search tree
that uses counts of descendant nodes in left and right subtrees
(<code>leftCount</code> and <code>rightCount</code>) for balancing
(see <code>unbalanced</code> method) and indexing functionality.
Null values are not accepted. Balance is achieved by node rotations.

*Left-rotation of the node*
```
  N             R  
 / \           / \ 
L   R         N  RR
   / \  -->  / \   
  RL RR     L  RL  

N.rightCount = R.leftCount;
R.leftCount  = R.leftCount + 1 + N.leftCount;
```

*Right-rotation of the node*
```
    N         L    
   / \       / \   
  L   R     LL  N  
 / \    -->    / \ 
LL LR         LR  R

N.leftCount  = L.rightCount;
L.rightCount = L.rightCount + 1 + N.rightCount;
```

There are two implementations: CBSTree and CBMTree.
The first one doesn't support repetition of the values,
but the second one does.

## Time complexity of methods

All modification methods has <code><b>O(log(n))</b></code> time complexity:
<code>add(Object)</code>, <code>remove(Object)</code>, <code>remove(int)</code>.

All getter methods has <code><b>O(log(n))</b></code> time complexity:
<code>getNode(Object)</code>,
<code>getNodeAt(int)</code>,
<code>getNodeFirst()</code>,
<code>getNodeLast()</code>,
<code>getNodeLess(Object)</code>,
<code>getNodeGreater(Object)</code>,
<code>getNodeLessEq(Object)</code>,
<code>getNodeGreaterEq(Object)</code>,
<code>contains(Object)</code>,
<code>getAt(int)</code>,
<code>getFirst()</code>,
<code>getLast()</code>,
<code>getLess(Object)</code>,
<code>getGreater(Object)</code>,
<code>getLessEq(Object)</code>,
<code>getGreaterEq(Object)</code>,
<code>getIndexLess(Object)}</code>,
<code>getIndexGreater(Object)}</code>,
<code>getIndexLessEq(Object)}</code>,
<code>getIndexGreaterEq(Object)}</code>,
<code>CBSTree.getIndexOf(Object)</code>,
<code>CBMTree.getFirstIndexOf(Object)</code>,
<code>CBMTree.getLastIndexOf(Object)</code>.

And there are some methods with linear complexity <code><b>O(n)</b></code>:
<code>forEachNode(Consumer)</code>, <code>forEach(Consumer)</code>, <code>clear()</code>.

## Example of CBSTree

<img src="https://github.com/druyaned/alg/blob/main/src/main/resources/util/t03bintree/CBSTree-transparent-example.png?raw=true"
    height="400" />

## Example of CBMTree

<img src="https://github.com/druyaned/alg/blob/main/src/main/resources/util/t03bintree/CBMTree-transparent-example.png?raw=true"
    height="400" />
