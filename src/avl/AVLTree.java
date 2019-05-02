package avl;// AvlTree class
//
// CONSTRUCTION: with no initializer
//
// ******************PUBLIC OPERATIONS*********************
// void insert( x )       --> Insert x
// void remove( x )       --> Remove x (unimplemented)
// boolean contains( x )  --> Return true if x is present
// boolean remove( x )    --> Return true if x was present
// Comparable findMin( )  --> Return smallest item
// Comparable findMax( )  --> Return largest item
// boolean isEmpty( )     --> Return true if empty; else false
// void makeEmpty( )      --> Remove all items
// void printTree( )      --> Print tree in sorted order
// ******************ERRORS********************************
// Throws util.UnderflowException as appropriate

import bst.BinaryNode;
import bst.BinarySearchTree;

/**
 * Implements an AVL tree that extends BST
 * Note that all "matching" is based on the compareTo method.
 * @author Mikyung Han
 */
public class AVLTree<E extends Comparable<? super E>> extends BinarySearchTree<E> {
    private static final int ALLOWED_IMBALANCE = 1;

    @Override
    public void insert(E target) {
        overallRoot = insert(new AvlNode<E>(target), overallRoot);
    }

    @Override
    protected BinaryNode<E> insert(BinaryNode<E> targetNode, BinaryNode<E> root) {
        root = super.insert(targetNode, root);
        return balance(root);
    }

    @Override
    protected BinaryNode<E> remove(E target, BinaryNode<E> root) {
        root = super.remove(target, root);
        return balance(root);
    }

    private BinaryNode<E> balance(BinaryNode<E> root){
        if(root == null) return root;

        //case 1 and 2 means left child is longer than the right child
        if(height(root.left) - height(root.right) > ALLOWED_IMBALANCE) {
            if(height (root.left.left) >= height (root.left.right)){
                //case 1: single rotation with left child
                root = singleRotationWithLeftChild(root);
            } else {
                //case 2: double rotation with left child
                root = doubleRotationWithLeftChild(root);
            }
        }else if(height(root.right) - height(root.left) > ALLOWED_IMBALANCE) {
            //case 3 and 4 means right child is longer than the left child
            if(height (root.right.right) >= height (root.right.left)){
                //case 4: single rotation with right child
                root = singleRotationWithRightChild(root);
            } else {
                //case 3: double rotation with right child
                root = doubleRotationWithRightChild(root);
            }
        }

        ((AvlNode<E>)root).depth = Math.max( height(root.left), height(root.right)) + 1;
        return root;
    }

    //case 1
    private BinaryNode<E> singleRotationWithLeftChild(BinaryNode<E> k2){
        AvlNode<E> k1 = (AvlNode<E>) k2.left;
        k2.left = k1.right;
        k1.right = k2;
        ((AvlNode<E>)k2).depth = Math.max( height(k2.left), height(k2.right)) + 1;
        k1.depth = Math.max( height(k1.left), ((AvlNode<E>)k2).depth) + 1;
        return k1; //k1 is now promoted as root
    }

    //case 2
    private BinaryNode<E> doubleRotationWithLeftChild(BinaryNode<E> k3) {
        k3.left = singleRotationWithRightChild(k3.left);
        return singleRotationWithLeftChild(k3); //Implement your method here
    }

    //case 3
    private BinaryNode<E> doubleRotationWithRightChild(BinaryNode<E> k1) {
        k1.right = singleRotationWithLeftChild(k1.right);
        return singleRotationWithRightChild(k1);
    }

    //case 4
    private BinaryNode<E> singleRotationWithRightChild(BinaryNode<E> k1){
        AvlNode<E> k2 = (AvlNode<E>) k1.right;
        k1.right = k2.left;
        k2.left = k1;
        ((AvlNode<E>)k1).depth = Math.max( height(k1.left), height(k1.right)) + 1;
//        k2.depth = Math.max( height(k2.left), height(k2.right)) + 1;
        k2.depth = Math.max( ((AvlNode<E>)k1).depth, height(k2.right) ) + 1;
        return k2; //k2 is now promoted as root
    }

    public void checkBalance( ) {
        checkBalance(overallRoot);
    }

    //returns the calculated height given the current tree rooted at t
    //when something is wrong (imbalanced, or the height calculation and
    //the depth information stored at the node has mismatch), throws IllegalStateException
    private int checkBalance(BinaryNode<E> t) {
        //Implement me
        if(t == null) {
            return -1;
        }
        int left_height = checkBalance(t.left);
        int right_height = checkBalance(t.right);

        if(Math.abs(left_height - right_height) > ALLOWED_IMBALANCE) {
            throw new IllegalStateException("tree is imbalanced with left-height"
                    + left_height + ", right-height " + right_height);
        }
        if(left_height != height(t.left)){
            throw new IllegalStateException("left child does not have the correct depth "+
                    "should be "+ left_height + "but it says " + height(t.left));
        }
        if(right_height != height(t.right)){
            throw new IllegalStateException("right child does not have the correct depth "+
                    "should be "+ right_height + "but it says " + height(t.right));
        }
        return Math.max(left_height, right_height) + 1 ; //Overwrite this code
    }

    @Override
    protected int height(BinaryNode<E> root) {
        return root == null ? -1 : ((AvlNode<E>)root).depth;
    }
}
