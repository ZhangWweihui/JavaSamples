package com.zwh.structure;

import cn.hutool.core.collection.CollectionUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 用数组实现的二叉树
 * @author ZhangWeihui
 * @date 2019/8/5 15:23
 */
public class ArrayBinaryTree {

    private Integer[] datas;

    public ArrayBinaryTree(int root, int length){
        datas = new Integer[length];
        datas[1] = root;
    }

    public List<Integer> addChildren(int parentIndex, int left, int right){
        if(datas.length-1 < parentIndex*2+1){
            expand();
        }
        datas[parentIndex*2] = left;
        datas[parentIndex*2+1] = right;
        return CollectionUtil.newArrayList(parentIndex*2, parentIndex*2+1);
    }

    private void expand() {
        Integer[] _datas = new Integer[datas.length*2];
        System.arraycopy(datas, 0, _datas, 0, datas.length);
        datas = _datas;
    }

    /**
     * 查找特定值在树中的位置
     * @param data
     * @return
     */
    public List<Integer> search(int data){
        List<Integer> indexs = new ArrayList<>();
        for(int i=1;i<datas.length;i++){
            if(datas[i]!=null && datas[i]==data){
                indexs.add(i);
            }
        }
        return indexs;
    }

    /**
     * 通过前序，中序，或后序打印树中所有的节点
     * @param traversalMode
     */
    public void print(TraversalModeEnum traversalMode){
        List<Integer> nodes = new ArrayList<>();
        print(1, traversalMode, nodes);
        System.out.println(traversalMode.toString() + " : " + nodes.toString());
    }

    private void print(int parentIndex, TraversalModeEnum traversalMode, List<Integer> nodes){
        if(traversalMode == TraversalModeEnum.PRE_ORDER) {
            nodes.add(datas[parentIndex]);
            if(parentIndex*2 < datas.length && datas[parentIndex*2]!=null) {
                print(parentIndex * 2, TraversalModeEnum.PRE_ORDER, nodes);
            }
            if(parentIndex*2+1 < datas.length && datas[parentIndex*2+1]!=null) {
                print(parentIndex * 2 + 1, TraversalModeEnum.PRE_ORDER, nodes);
            }
        } else if (traversalMode == TraversalModeEnum.IN_ORDER) {
            if(parentIndex*2 < datas.length && datas[parentIndex*2]!=null) {
                print(parentIndex * 2, TraversalModeEnum.IN_ORDER, nodes);
            }
            nodes.add(datas[parentIndex]);
            if(parentIndex*2+1 < datas.length && datas[parentIndex*2+1]!=null) {
                print(parentIndex * 2 + 1, TraversalModeEnum.IN_ORDER, nodes);
            }
        } else if (traversalMode == TraversalModeEnum.POST_ORDER) {
            if(parentIndex*2 < datas.length && datas[parentIndex*2]!=null) {
                print(parentIndex * 2, TraversalModeEnum.POST_ORDER, nodes);
            }
            if(parentIndex*2+1 < datas.length && datas[parentIndex*2+1]!=null) {
                print(parentIndex * 2 + 1, TraversalModeEnum.POST_ORDER, nodes);
            }
            nodes.add(datas[parentIndex]);
        }
    }

    public static void main(String[] args) {
        ArrayBinaryTree tree = new ArrayBinaryTree(1, 5);
        List<Integer> index_3_4 = tree.addChildren(1, 3,4);
        List<Integer> index_5_6 = tree.addChildren(index_3_4.get(0), 5,6);
        List<Integer> index_7_8 = tree.addChildren(index_3_4.get(1), 3,8);
        tree.addChildren(index_5_6.get(0), 9,10);
        tree.addChildren(index_5_6.get(1), 11,12);
        tree.addChildren(index_7_8.get(0), 3,14);
        tree.addChildren(index_7_8.get(1), 15,16);

        tree.print(TraversalModeEnum.PRE_ORDER);
        tree.print(TraversalModeEnum.IN_ORDER);
        tree.print(TraversalModeEnum.POST_ORDER);

        List<Integer> selected = tree.search(3);
        selected.forEach(s -> System.out.println(s.toString()));
    }
}
