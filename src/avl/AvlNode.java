package avl;

import bst.BinaryNode;

public class AvlNode<E> extends BinaryNode<E> {
    public int height;

    //Implement this class
    public AvlNode(E data){
        this(data, null, null);
    }

    public AvlNode(E data, AvlNode<E> left, AvlNode<E> right) {
        this(data, left, right, 0);
    }

    public AvlNode(E data, AvlNode<E> left, AvlNode<E> right, int height){
        super(data, left, right);
        this.height = height;
    }
}