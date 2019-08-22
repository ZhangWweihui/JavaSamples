package com.zwh.javasamples.common;

/**
 * @author ZhangWeihui
 * @date 2019/8/22 16:50
 */
public interface IComputer {

    String name = "lenovo";

    interface InputAndOutput{
        void input(String s);
        String output();
    }

    interface Storage {
        void store(String s);
    }

    public interface CPU {
        int add(int a,int b);
    }

    void powerSwitch(int i);

    void print(String s);

    class InputAndOutputImpl implements InputAndOutput {

        @Override
        public void input(String s) {
            System.out.printf("InputAndOutputImpl[input] : %s.\n", s);
        }

        @Override
        public String output() {
            System.out.printf("InputAndOutputImpl[output] : %s.\n", this.toString());
            return this.toString();
        }
    }

    class StorageImpl implements Storage {

        @Override
        public void store(String s) {
            System.out.printf("StorageImpl : the info [%s] stored.\n", s);
        }
    }

    class CPUImpl implements CPU {

        @Override
        public int add(int a, int b) {
            return Math.addExact(a,b);
        }
    }
}
