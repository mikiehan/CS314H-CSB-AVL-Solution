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
        overallRoot = insert(target, overallRoot, true);
    }

    @Override
    protected BinaryNode<E> insert(E target, BinaryNode<E> root, boolean useAvlNode) {
        root = super.insert(target, root, useAvlNode);
        return balance((AvlNode<E>) root);
    }

    @Override
    protected BinaryNode<E> remove(E target, BinaryNode<E> root) {
        root = super.remove(target, root);
        return balance((AvlNode<E>) root);
    }

    private AvlNode<E> balance(AvlNode<E> root){
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

        root.depth = Math.max( height(root.left), height(root.right)) + 1;
        return root;
    }

    private AvlNode<E> singleRotationWithLeftChild(AvlNode<E> k2){
        AvlNode<E> k1 = (AvlNode<E>) k2.left;
        k2.left = k1.right;
        k1.right = k2;
        k2.depth = Math.max( height(k2.left), height(k2.right)) + 1;
        k1.depth = Math.max( height(k1.left), k2.depth) + 1;
        return k1; //k1 is now promoted as root
    }

    private AvlNode<E> singleRotationWithRightChild(AvlNode<E> k1){
        AvlNode<E> k2 = (AvlNode<E>) k1.right;
        k1.right = k2.left;
        k2.left = k1;

        k1.depth = Math.max( height(k1.left), height(k1.right)) + 1;
//        k2.depth = Math.max( height(k2.left), height(k2.right)) + 1;
        k2.depth = Math.max( k1.depth, height(k2.right) ) + 1;
        return k2; //k2 is now promoted as root
    }

    private AvlNode<E> doubleRotationWithLeftChild(AvlNode<E> k3) {
        k3.left = singleRotationWithRightChild((AvlNode<E>) k3.left);
        return singleRotationWithLeftChild(k3); //Implement your method here
    }

    private AvlNode<E> doubleRotationWithRightChild(AvlNode<E> k1) {
        k1.right = singleRotationWithLeftChild((AvlNode<E>) k1.right);
        return singleRotationWithRightChild(k1);
    }

    public void checkBalance( ) {
        checkBalance((AvlNode<E>)overallRoot);
    }

    private int checkBalance( AvlNode<E> t ) {
        if( t == null )
            return -1;

        if( t != null ) {
            int hl = checkBalance((AvlNode<E>) t.left);
            int hr = checkBalance((AvlNode<E>) t.right);
            if( Math.abs( height( t.left ) - height( t.right ) ) > 1 ||
                    height( t.left ) != hl || height( t.right ) != hr )
                System.out.println( "OOPS!!" );
        }
        return height( t );
    }

    @Override
    protected int height(BinaryNode<E> root) {
        return root == null ? -1 : ((AvlNode<E>)root).depth;
    }
}
