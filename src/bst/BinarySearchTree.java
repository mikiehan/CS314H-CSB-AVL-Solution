package bst;// bst.BinarySearchTree class
//
// CONSTRUCTION: with no initializer
//
// ******************PUBLIC OPERATIONS*********************
// void insert( x )       --> Insert x
// void remove( x )       --> Remove x
// boolean contains( x )  --> Return true if x is present
// Comparable findMin( )  --> Return smallest item
// Comparable findMax( )  --> Return largest item
// boolean isEmpty( )     --> Return true if empty; else false
// void makeEmpty( )      --> Remove all items
// void printTree( )      --> Print tree in sorted order
// ******************ERRORS********************************
// Throws util.UnderflowException as appropriate

import util.UnderflowException;

/**
 * Implements an unbalanced binary search tree.
 * Note that all "matching" is based on the compareTo method.
 * @author Mark Allen Weiss, Mi Kyung Han
 */
public class BinarySearchTree<E extends Comparable<? super E>>
{
    /** The tree overallRoot. */
    protected BinaryNode<E> overallRoot;

    /**
     * Construct the tree.
     */
    public BinarySearchTree() {
        overallRoot = null;
    }

    /**
     * Remove from the tree. Nothing is done if x is not found.
     * @param target the item to remove.
     */
    public void remove(E target) {
        overallRoot = remove(target, overallRoot);
    }

    protected BinaryNode<E> remove(E target, BinaryNode<E> root){
        if(root == null) return root;

        if(target.compareTo(root.data) < 0) {
            root.left = remove(target, root.left);
        } else if (target.compareTo(root.data) > 0) {
            root.right = remove(target, root.right);
        } else { //target is equal to root's data (removing root!!!)
            if(root.left == null && root.right == null) { //case 1: both are null
                root = null;
            } else if(root.left == null || root.right == null) { //case 2: only one of them is null
                root = root.left == null ? root.right : root.left;
            } else { //case 3: both are not null
                root.data = findMin(root.right); //find the min from the right tree
                root.right = remove(root.data, root.right); //remove the min from the right tree recursively
            }
        }

        return root;
    }

    /**
     * Insert into the tree; duplicates are ignored.
     * @param target the item to insert.
     */
    public void insert(E target) {
        overallRoot = insert(new BinaryNode<E>(target), overallRoot);
    }

    /**
     * @param targetNode the node to insert that has target data
     * @param root the node that roots the subtree
     * @return the new overallRoot of the subtree
     */
    protected BinaryNode<E> insert(BinaryNode<E> targetNode, BinaryNode<E> root){
        if(root == null) {
            root = targetNode;
        }
        else if(targetNode.data.compareTo(root.data) > 0)
            root.right = insert(targetNode, root.right);
        else if(targetNode.data.compareTo(root.data) < 0)
            root.left = insert(targetNode, root.left);
        else
            ; //we are ignoring the dupe
        return root;
    }

    /**
     * Find the height of the tree
     */
    public int height() {
        return height(overallRoot);
    }

    protected int height(BinaryNode<E> root){
        if(root == null) return -1;
        return 1 + Math.max( height(root.left), height(root.right));
    }

    /**
     * Find the smallest item in the tree.
     * @return smallest item or null if empty.
     */
    public E findMin() {
        if(isEmpty( ))
            throw new UnderflowException();
        return findMin(overallRoot); //Overwrite this code
    }

    private E findMin(BinaryNode<E> root){
        while(root.left != null){
            root = root.left;
        }
        return root.data;
    }

    /**
     * Find the largest item in the tree.
     * @return the largest item of null if empty.
     */
    public E findMax() {
        if(isEmpty( ))
            throw new UnderflowException( );
        return findMax(overallRoot); //Overwrite this code
    }

    private E findMax(BinaryNode<E> root){
        while(root.right != null){
            root = root.right;
        }
        return root.data;
    }

    /**
     * Find an item in the tree.
     * @param target the item to search for.
     * @return true if not found.
     */
    public boolean contains(E target) {
        return contains(target, overallRoot);
    }

    private boolean contains(E target, BinaryNode<E> root){
        if(root == null) return false;
        if( target.compareTo(root.data) == 0)  return true;
        if( target.compareTo(root.data) < 0)
            return contains(target, root.left);
        return contains(target, root.right); // target is greater than root
    }

    /**
     * Make the tree logically empty.
     */
    public void makeEmpty() {
        overallRoot = null;
    }

    /**
     * Test if the tree is logically empty.
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty() {
        return overallRoot == null;
    }

    /**
     * Print the tree contents in sorted order.
     */
    public void printTree() {
        if( isEmpty( ) )
            System.out.println( "Empty tree" );
        else
            printTree(overallRoot); //call helper method
    }

    /**
     * Implement this helper method
     * Internal method to print a subtree in sorted order. (traverse in-order)
     * @param t the node that roots the subtree.
     */
    private void printTree(BinaryNode<E> t) {
        if(t != null){
            printTree(t.left);
            System.out.println(t.data);
            printTree(t.right);
        }
    }
}
