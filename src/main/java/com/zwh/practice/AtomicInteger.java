package com.zwh.practice;

import sun.misc.Unsafe;

import java.io.Serializable;
import java.util.function.IntBinaryOperator;
import java.util.function.IntUnaryOperator;

/**
 * 仿写 JDK中的 AtomicInteger
 * @Description
 * @Author 张炜辉
 * @Date 2020/5/28
 */
public class AtomicInteger extends Number implements Serializable {

    private static final long serialVersionUID = -2381988972424820047L;

    private static final Unsafe unsafe = Unsafe.getUnsafe();

    private static final long valueOffset;

    private volatile int value;

    static{
        try {
            valueOffset = unsafe.objectFieldOffset(AtomicInteger.class.getDeclaredField("value"));
        } catch (Exception e) {
            throw new Error(e);
        }
    }

    public AtomicInteger() {}

    public AtomicInteger(int value) {
        this.value = value;
    }

    public int get() { return this.value; }

    public void set(int value) {
        this.value = value;
    }

    public void lazySet(int newValue) {
        unsafe.putOrderedInt(this, valueOffset, newValue);
    }

    public int getAndSet(int newValue) {
        return unsafe.getAndSetInt(this, valueOffset, newValue);
    }

    public boolean compareAndSet(int expect, int newValue) {
        return unsafe.compareAndSwapInt(this, valueOffset, expect, newValue);
    }

    public boolean weakCompareAndSet(int expect, int newValue) {
        return unsafe.compareAndSwapInt(this, valueOffset, expect, newValue);
    }

    public int getAndIncrement() {
        return unsafe.getAndAddInt(this, valueOffset, 1);
    }

    public int getAndDecrement() {
        return unsafe.getAndAddInt(this, valueOffset, -1);
    }

    public int incrementAndGet() {
        return unsafe.getAndAddInt(this, valueOffset, 1) + 1;
    }

    public int decrementAndGet() {
        return unsafe.getAndAddInt(this, valueOffset, -1) - 1;
    }

    public int getAndAdd(int x) {
        return unsafe.getAndAddInt(this, valueOffset, x);
    }

    public int addAndGet(int x) {
        return unsafe.getAndAddInt(this, valueOffset, x) + x;
    }

    public int getAndUpdate(IntUnaryOperator operator) {
        int prev, next;
        do{
            prev = get();
            next = operator.applyAsInt(prev);
        } while (!compareAndSet(prev,next));
        return prev;
    }

    public int updateAndGet(IntUnaryOperator operator) {
        int prev, next;
        do{
            prev = get();
            next = operator.applyAsInt(prev);
        } while (!compareAndSet(prev,next));
        return next;
    }

    public int getAndAccumulate(int x, IntBinaryOperator operator) {
        int prev, next;
        do{
            prev = get();
            next = operator.applyAsInt(x, prev);
        } while (!compareAndSet(prev,next));
        return prev;
    }

    public int accumulateAndGet(int x, IntBinaryOperator operator) {
        int prev, next;
        do{
            prev = get();
            next = operator.applyAsInt(x, prev);
        } while (!compareAndSet(prev,next));
        return next;
    }

    @Override
    public String toString() {
        return Integer.toString(get());
    }

    @Override
    public int intValue() {
        return get();
    }

    @Override
    public long longValue() {
        return get();
    }

    @Override
    public float floatValue() {
        return (float)get();
    }

    @Override
    public double doubleValue() {
        return get();
    }
}
