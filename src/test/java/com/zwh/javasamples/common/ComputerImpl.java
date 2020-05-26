package com.zwh.javasamples.common;

/**
 * @author ZhangWeihui
 * @date 2019/8/22 16:59
 */
public class ComputerImpl implements IComputer {

    @Override
    public void powerSwitch(int i) {
        switch (i) {
            case 0 :
                System.out.printf("ComputerImpl[powerSwitch] : %s.\n", "关机");
                break;
            case 1 :
                System.out.printf("ComputerImpl[powerSwitch] : %s.\n", "开机");
                break;
            default:
                System.out.printf("ComputerImpl[powerSwitch] : %s.\n", "无效的指令");
        }
    }

    @Override
    public void print(String s) {
        System.out.printf("ComputerImpl[print] : %s.\n", s);
    }

    public static void main(String[] args) {
        Storage storage = new StorageImpl();
        storage.store(IComputer.name);
    }
}
