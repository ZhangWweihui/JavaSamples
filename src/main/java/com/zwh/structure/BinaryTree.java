package com.zwh.structure;

/**
 * @author ZhangWeihui
 * @date 2019/8/5 14:22
 */
public interface BinaryTree<T> {

    Object search(T t);

    Object add(T t);

    Object delete(T t);

    void print();
}
