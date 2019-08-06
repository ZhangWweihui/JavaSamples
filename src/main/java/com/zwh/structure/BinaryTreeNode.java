package com.zwh.structure;

import cn.hutool.core.lang.Assert;

/**
 * @author ZhangWeihui
 * @date 2019/8/5 14:28
 */
public class BinaryTreeNode<T extends Comparable> {

    private T data;
    private BinaryTreeNode left;
    private BinaryTreeNode right;

    public BinaryTreeNode(T data) {
        Assert.notNull(data, "节点数据不能为空");
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public BinaryTreeNode getLeft() {
        return left;
    }

    public void setLeft(BinaryTreeNode left) {
        this.left = left;
    }

    public BinaryTreeNode getRight() {
        return right;
    }

    public void setRight(BinaryTreeNode right) {
        this.right = right;
    }

    @Override
    public String toString(){
        return new StringBuilder().append(data).append("-")
                .append(left!=null ? left.data : "null").append("-")
                .append(right!=null ? right.data : "null").toString();
    }
}
