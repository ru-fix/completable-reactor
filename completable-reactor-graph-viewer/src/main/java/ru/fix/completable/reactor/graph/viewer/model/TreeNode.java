package ru.fix.completable.reactor.graph.viewer.model;

import com.sun.istack.internal.Nullable;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class TreeNode<T> implements Iterable<TreeNode<T>> {

    T data;
    TreeNode<T> parent;
    List<TreeNode<T>> children;

    public TreeNode(T data) {
        this.data = data;
        this.children = new LinkedList<TreeNode<T>>();
    }

    public TreeNode<T> addChild(T child) {
        TreeNode<T> childNode = new TreeNode<T>(child);
        childNode.parent = this;
        this.children.add(childNode);
        return childNode;
    }

    // other features ...

    public List<TreeNode<T>> childs() {
        return children;
    }

    public TreeNode<T> getParent() {
        return parent;
    }

    public T getData() {
        return data;
    }

    @Override
    public Iterator<TreeNode<T>> iterator() {
        return children.iterator();
    }

    @Override
    public String toString() {
        return Optional.ofNullable(data).map(Object::toString).orElseGet(this::toString);
    }
}
