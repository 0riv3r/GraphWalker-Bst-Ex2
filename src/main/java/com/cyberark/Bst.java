package com.cyberark;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * Binary Serach Tree class
 * 
 * @param <T>
 */
public class Bst<T extends Comparable<T>> {
    private Node root = null;
    private int nodeCount = 0;

    private class Node {
        T element;
        Node left, right;

        public Node(Node left, Node right, T element) {
            this.left = left;
            this.right = right;
            this.element = element;
        }
    }

    // is the tree empty
    public boolean isEmpty() {
        return nodeCount == 0;
    }

    private Node _insert(Node node, T value) {
        /** a leaf node, insert the value in this position. */
        if (node == null) {
            node = new Node(null, null, value);
        }
        /**
         * If the value is less than the current node's value, choose left.
         */
        else if (value.compareTo(node.element) < 0) {
            node.left = _insert(node.left, value);
        }
        /**
         * If the value is greater than the current node's value, choose right.
         */
        else if (value.compareTo(node.element) > 0) {
            node.right = _insert(node.right, value);
        }
        /** 
         * A new node is created, or
         * a node with this value already exists in the tree.
         * return this node
         * */
        return node;

    }

    /** add a node with the given value */
    public void add(T value) {
        root = _insert(root, value);
        nodeCount++;
    }

    private ArrayList<Integer> _listNodes(Node node, ArrayList<Integer> lstNodes) {
        if (node != null) {
            /** recursively call 'display' on the left sub-tree */
            _listNodes(node.left, lstNodes);
            /** adds the value at the current node */
            lstNodes.add((Integer) node.element);
            /** recursively call 'display' on the right subtree. */
            _listNodes(node.right, lstNodes);
        }
        return(lstNodes);
    }

    /** return a list of the tree nodes */
    public ArrayList<Integer> nodes() {
        ArrayList<Integer> lstNodes = new ArrayList<Integer>();
        return _listNodes(root, lstNodes);
    }

    private boolean _contains(Node node, T value) {
        /** if the value is not found */
        if (node == null)
            return false;
        /** if the value is found */
        if (value.compareTo(node.element) == 0) {
            return true;
        }
        /** Otherwise, continue the search recursively */
        return value.compareTo(node.element) < 0 ? _contains(node.left, value) : _contains(node.right, value);

    }

    /** check if the tree contains a given value */
    public boolean find(T value) {
        return _contains(root, value);
    }

    /** return the smallest value */
    public T smallest() {
        Node node = this .root;
        while (node.left != null) {
            node = node.left;
        }
        return node.element;
    }

    /** return the largest value */
    public T largest() {
        Node node = this .root;
        while (node.right != null) {
            node = node.right;
        }
        return node.element;
    }

    private boolean _deleteLeaf(Node node, T value) {
        /** if the value is not found */
        if (node == null)
            // not found
            return false;
        /** if the value is found */
        if (value.compareTo(node.element) == 0) {
            // is it a leaf? if yes, delete it
            if(node.left == null && node.right == null){
                node = null;
                return true;
            }
            else{
                // not a leaf
                return false;
            }
        }
        /** Otherwise, continue the search recursively */
        return value.compareTo(node.element) < 0 ? _deleteLeaf(node.left, value) : _deleteLeaf(node.right, value);
    }

    // delete if leaf
    public boolean delete(T value) {
        return _deleteLeaf(root, value);
    }

    public static void main(String[] args) {
        Bst<Integer> bst = new Bst<Integer>();
        // values are taken from https://en.wikipedia.org/wiki/Binary_search_tree
        bst.add(14);
        bst.add(1);
        bst.add(13);
        bst.add(6);
        bst.add(8);
        bst.add(7);
        bst.add(10);
        bst.add(4);
        bst.add(3);
        assert(bst.find(14) == true);
        assert(bst.find(42) == false);
        assert(bst.smallest() == 1);
        assert(bst.largest() == 14);

        List<Integer> expected = (List<Integer>) asList(1, 3, 4, 6, 7, 8, 10, 13, 14);
        assert(bst.nodes().equals(expected));
    }
}