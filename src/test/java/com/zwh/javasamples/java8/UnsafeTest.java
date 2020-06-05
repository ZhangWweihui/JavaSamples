package com.zwh.javasamples.java8;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @Description
 * @Author 张炜辉
 * @Date 2020/6/5
 */
public class UnsafeTest {

//    private Unsafe unsafe;
//
//    @Before
//    public void setUnsafe() {
//        try {
//            Field field = Unsafe.class.getDeclaredField("theUnsafe");
//            field.setAccessible(true);
//            unsafe = (Unsafe) field.get(null);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    @Test
    public void testModifyField() throws Exception {
        Unsafe unsafe = null;
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Book book = new Book(123L, "信息技术部", 33.5D);

        long nameOffset = unsafe.objectFieldOffset(Book.class.getDeclaredField("name"));
        Assert.assertEquals("信息技术部", unsafe.getObject(book, nameOffset));

        unsafe.putObject(book, nameOffset, "Java编程思想");
        Assert.assertEquals("Java编程思想", book.getName());

        long idOffset = unsafe.objectFieldOffset(Book.class.getDeclaredField("id"));
        Assert.assertEquals(book.getId().longValue(), unsafe.getLong(idOffset));

        unsafe.putLong(book, idOffset, 456L);
        Assert.assertEquals(456L, book.getId().longValue());
    }

    public static class Book {
        private Long id;
        private String name;
        private Double price;

        public Book(Long id, String name, Double price) {
            this.id = id;
            this.name = name;
            this.price = price;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }
    }
}
