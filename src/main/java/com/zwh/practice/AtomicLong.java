package com.zwh.practice;

import sun.misc.Unsafe;

import java.io.Serializable;
import java.util.function.LongBinaryOperator;
import java.util.function.LongUnaryOperator;

/**
 * 仿写 JDK中的 AtomicLong
 * @Description
 * @Author 张炜辉
 * @Date 2020/5/28
 */
public class AtomicLong extends Number implements Serializable {

    private static final long serialVersionUID = 780500624035502967L;

    private static final Unsafe unsafe = Unsafe.getUnsafe();

    private volatile long value;

    private static final long valueOffSet;

    /**
     * unsafe.objectFieldOffset
     * 返回指定静态field的内存地址偏移量,在这个类(Unsafe)的其他方法中这个值只是被用作一个访问
     * 特定field的一个方式。这个值对于给定的field是唯一的，并且后续对该方法的调用都应该
     * 返回相同的值。
     */
    static {
        try{
            valueOffSet = unsafe.objectFieldOffset(AtomicLong.class.getDeclaredField("value"));
        } catch (Exception e) {
            throw new Error(e);
        }
    }

    static final boolean VM_SUPPORTS_LONG_CAS = VMSupportsCS8();

    private static native boolean VMSupportsCS8();

    public AtomicLong() {}

    public AtomicLong(long value) {
        this.value = value;
    }

    public final long get() {
        return this.value;
    }

    public final void set(long value) {
        this.value = value;
    }

    /**
     * unsafe.putOrderedLong
     * 设置obj对象中offset偏移地址对应的long型field的值为指定值。这是一个有序或者
     * 有延迟的 putLongVolatile 方法，并且不保证值的改变被其他线程立
     * 即看到。只有在field被 volatile 修饰并且期望被意外修改的时候
     * 使用才有用。
     * @param newValue
     */
    public final void lazySet(long newValue) {
        unsafe.putOrderedLong(this, valueOffSet, newValue);
    }

    /**
     * 原子性的设置新值和获取旧值
     * @param newValue
     * @return
     */
    public final long getAndSet(long newValue) {
        return unsafe.getAndSetLong(this, valueOffSet, newValue);
    }

    /**
     * 原子的设置新值如果当前值和期望值相等
     * unsafe.compareAndSwapLong
     * 在obj的offset位置比较long field和期望的值，如果相同则更新。这个方法
     * 的操作应该是原子的，因此提供了一种不可中断的方式更新long field。
     * @param expect
     * @param newValue
     * @return
     */
    public final boolean compareAndSet(long expect, long newValue) {
        return unsafe.compareAndSwapLong(this, valueOffSet, expect, newValue);
    }

    public final boolean weakCompareAndSet(long expect, long newValue) {
        return unsafe.compareAndSwapLong(this, valueOffSet, expect, newValue);
    }

    /**
     * Atomically increments by one the current value.
     *
     * @return the previous value
     */
    public final long getAndIncrement() {
        return unsafe.getAndAddLong(this, valueOffSet, 1L);
    }

    /**
     * Atomically decrement by one the current value.
     *
     * @return the previous value
     */
    public final long getAndDecrement() {
        return unsafe.getAndAddLong(this, valueOffSet, -1L);
    }

    public final long getAndAdd(long delta) {
        return unsafe.getAndAddLong(this, valueOffSet, delta);
    }

    public final long addAndGet(long delta) {
        return unsafe.getAndAddLong(this, valueOffSet, delta) + delta;
    }

    public final long incrementAndGet() {
        return unsafe.getAndAddLong(this, valueOffSet, 1L) + 1L;
    }

    public final long decrementAndGet() {
        return unsafe.getAndAddLong(this, valueOffSet, -1L) - 1L;
    }

    /**
     * Atomically update the current value with the result of applying the given function,
     * returning the previous value. The function should be side-effect-free, since it may
     * be re-applied when attempted updates fail due to contention among threads.
     *
     * @param operator a side-effect-free function
     * @return the previous value
     */
    public final long getAndUpdate(LongUnaryOperator operator) {
        long prev, next;
        do{
            prev = get();
            next = operator.applyAsLong(prev);
        } while (!compareAndSet(prev, next));
        return prev;
    }

    public final long updateAndGet(LongUnaryOperator operator) {
        long prev, next;
        do{
            prev = get();
            next = operator.applyAsLong(prev);
        } while (!compareAndSet(prev, next));
        return next;
    }

    /**
     * 操作函数有两个参数
     * @param x
     * @param operator
     * @return
     */
    public final long getAndAccumulate(long x, LongBinaryOperator operator) {
        long prev, next;
        do {
            prev = get();
            next = operator.applyAsLong(x, prev);
        } while (!compareAndSet(prev, next));
        return prev;
    }

    private final long accumulateAndGet(long x, LongBinaryOperator operator) {
        long prev, next;
        do {
            prev = get();
            next = operator.applyAsLong(x, prev);
        } while (!compareAndSet(prev, next));
        return next;
    }

    @Override
    public int intValue() {
        return (int)get();
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
        return (double)get();
    }

    @Override
    public String toString() {
        return Long.toString(get());
    }
}
