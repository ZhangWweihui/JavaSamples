package com.zwh.structure;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * 链表实现的二叉树
 * @author ZhangWeihui
 * @date 2019/8/5 14:30
 */
public class LinkedBinaryTree<T extends Comparable> {

    protected BinaryTreeNode root;
    private List<T> datas;

    public LinkedBinaryTree(BinaryTreeNode root){
        Assert.notNull(root, "根节点不能为空");
        this.root = root;
    }

    public BinaryTreeNode getRoot() {
        return root;
    }

    public void addChildren(BinaryTreeNode parent, BinaryTreeNode left, BinaryTreeNode right) {
        if(parent==null){
            parent = root;
        }
        parent.setLeft(left);
        parent.setRight(right);
    }

    public List<BinaryTreeNode> addChildren(BinaryTreeNode parent, T left, T right) {
        if(parent==null){
            parent = root;
        }
        BinaryTreeNode<T> leftNode = null;
        BinaryTreeNode<T> rightNode = null;
        if(left!=null) {
            leftNode = new BinaryTreeNode<>(left);
            parent.setLeft(leftNode);
        }
        if(right!=null) {
            rightNode = new BinaryTreeNode<>(right);
            parent.setRight(rightNode);
        }
        return CollectionUtil.newArrayList(leftNode, rightNode);
    }

    public List<BinaryTreeNode> search(T t) {
        List<BinaryTreeNode> nodes = new ArrayList<>();
        search(t, root, nodes);
        return nodes;
    }

    private void search(T t, BinaryTreeNode node, List<BinaryTreeNode> nodes) {
        if(node.getData().equals(t)){
            nodes.add(node);
        }
        if(node.getLeft()!=null){
            search(t, node.getLeft(), nodes);
        }
        if(node.getRight()!=null){
            search(t, node.getRight(), nodes);
        }
    }

    public void print(TraversalModeEnum traversalMode){
        datas = new ArrayList<>();
        print(root, traversalMode);
        System.out.println(traversalMode.toString() +" : "+ datas.toString());
    }

    /**
     * 遍历打印二叉树中的所有节点
     * @param node
     * @param traversalMode
     */
    private void print(BinaryTreeNode<T> node, TraversalModeEnum traversalMode) {
        if(traversalMode == TraversalModeEnum.PRE_ORDER){
            datas.add(node.getData());
            if(node.getLeft()!=null){
                print(node.getLeft(), TraversalModeEnum.PRE_ORDER);
            }
            if(node.getRight()!=null){
                print(node.getRight(), TraversalModeEnum.PRE_ORDER);
            }
        } else if (traversalMode == TraversalModeEnum.IN_ORDER){
            if(node.getLeft()!=null){
                print(node.getLeft(), TraversalModeEnum.IN_ORDER);
            }
            datas.add(node.getData());
            if(node.getRight()!=null){
                print(node.getRight(), TraversalModeEnum.IN_ORDER);
            }
        } else if (traversalMode == TraversalModeEnum.POST_ORDER){
            if(node.getLeft()!=null){
                print(node.getLeft(), TraversalModeEnum.POST_ORDER);
            }
            if(node.getRight()!=null){
                print(node.getRight(), TraversalModeEnum.POST_ORDER);
            }
            datas.add(node.getData());
        }
    }

    public static void main(String[] args) {
        BinaryTreeNode<Integer> root = new BinaryTreeNode<>(1);
        LinkedBinaryTree<Integer> tree = new LinkedBinaryTree<>(root);
        List<BinaryTreeNode> nodes_3_4 = tree.addChildren(root, 3,4);
        List<BinaryTreeNode> nodes_5_6 = tree.addChildren(nodes_3_4.get(0), 5,6);
        List<BinaryTreeNode> nodes_7_8 = tree.addChildren(nodes_3_4.get(1), 3,8);
        tree.addChildren(nodes_5_6.get(0), 9,10);
        tree.addChildren(nodes_5_6.get(1), 11,12);
        tree.addChildren(nodes_7_8.get(0), 13,14);
        tree.addChildren(nodes_7_8.get(1), 15,3);

        tree.print(TraversalModeEnum.PRE_ORDER);
        tree.print(TraversalModeEnum.IN_ORDER);
        tree.print(TraversalModeEnum.POST_ORDER);

        List<BinaryTreeNode> selected = tree.search(3);
        selected.forEach(s -> System.out.println(s.toString()));
    }
}
