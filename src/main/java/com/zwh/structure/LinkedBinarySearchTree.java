package com.zwh.structure;

/**
 * 基于链表的二叉查找树
 * @author ZhangWeihui
 * @date 2019/8/6 10:10
 */
public class LinkedBinarySearchTree<T extends Comparable> extends LinkedBinaryTree<T> {

    public LinkedBinarySearchTree(BinaryTreeNode root){
        super(root);
    }

    /**
     * 查找特定数据所在的节点
     * @param data
     * @return
     */
    public BinaryTreeNode<T> find(T data) {
        BinaryTreeNode node = root;
        while (node!=null) {
            if (node.getData().equals(data)) {
                return node;
            } else if (node.getData().compareTo(data) > 0) {
                node = node.getLeft();
            } else if (node.getData().compareTo(data) < 0) {
                node = node.getRight();
            }
        }
        return null;
    }

    /**
     * 插入新的节点
     * @param data
     */
    public void add(T data) {
        BinaryTreeNode dataNode = new BinaryTreeNode(data);
        BinaryTreeNode node = root;
        while (node!=null) {
            if (node.getData().equals(data) || node.getData().compareTo(data) < 0) {
                if(node.getRight()==null){
                    node.setRight(dataNode);
                    break;
                }
                node = node.getRight();
            } else if (node.getData().compareTo(data) > 0) {
                if(node.getLeft()==null){
                    node.setLeft(dataNode);
                    break;
                }
                node = node.getLeft();
            }
        }
    }

    /**
     * 删除对应数据的节点
     * @param data
     */
    public void delete(T data) {
        BinaryTreeNode node, parent;
        //如果要删除的节点有两个子节点，就用右子树中最小的节点提换这个节点

        //如果要删除的节点有一个子节点，就把父节点的应用指向子节点

        //如果要删除的节点没有子节点，就把父节点的引用设为空
    }
}