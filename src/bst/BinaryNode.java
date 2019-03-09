package bst;

// Basic node stored in unbalanced binary search trees
public class BinaryNode<E> {
    public E data;
    public BinaryNode<E> left;
    public BinaryNode<E> right;

    public BinaryNode(E data, BinaryNode<E> left, BinaryNode<E> right){
        this.data = data;
        this.left = left;
        this.right = right;
    }
    public BinaryNode(E data) {
        this(data, null, null);
    }
}