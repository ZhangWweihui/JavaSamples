package com.zwh.javasamples.common;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import java.math.BigDecimal;
import java.util.Arrays;

/**
 * @Description
 * @Author 张炜辉
 * @Date 2019/6/22
 */
public class ForLoopTest {

    //@Test
    public void test1() {
        for (int i = 0; i < 10; i++){
            System.out.println("i : "+i);
        }

        for (int m = 0; m < 10; ++m){
            System.out.println("m : "+m);
        }
    }

    //@Test
    public void countWheat() {
        BigDecimal countOfWheat = BigDecimal.ZERO;
        int i = 0;
        while (i<64){
            countOfWheat = countOfWheat.add(new BigDecimal(Math.pow(2, i++)));
        }
        System.out.println("i : " + i);
        System.out.println("count of wheat : " + countOfWheat.toPlainString());
        BigDecimal bi = countOfWheat;
        BigDecimal bi2 = bi.multiply(new BigDecimal("0.00004")).divide(new BigDecimal("1000"));
        System.out.println("ton of wheat : " + bi2.toPlainString());

        System.out.println(new BigDecimal(Math.pow(2, 63)).toPlainString());
    }

    /**
     * 用二分法求平方根
     * @param num
     * @param deltaThreshold 增量阀值
     * @return
     */
    public double findSquareRoot(double num, double deltaThreshold) {
        double max = num, min = 1;
        while(min < max){
            double middle = (max+min)/2;
            double square = middle * middle;
            double delta = Math.abs((square/num)-1);
            if(square == num || delta <= deltaThreshold){
                return middle;
            } else if(square > num) {
                max = middle;
            } else if(square < num) {
                min = middle;
            }
        }
        return -1;
    }

    //@Test
    public void testFindSquareRoot() {
        System.out.println(findSquareRoot(10000, 0.0000001D));
    }

    public int findStr(String[] array, String target) {
        if(array==null || array.length==0) {
            return -1;
        }
        Arrays.sort(array);
        int min = 0;
        int max = array.length;
        while (min < max) {
            int middle = (min + max) / 2;
            if(array[middle].equals(target)){
                return middle;
            } else if (array[middle].compareTo(target) > 0) {
                max = middle - 1;
            } else if (array[middle].compareTo(target) < 0) {
                min = middle + 1;
            }
        }
        return -1;
    }

    //@Test
    public void testFindStr() {
        String str = "As a tenant you should be able to enjoy a mutually beneficial relationship with your landlord";
        String[] array = str.split("\\s");
        int index = findStr(array, "enjoy");
        System.out.println(index);
    }

    /**
     * 舍罕王赏麦问题，用数学归纳法证明
     * @param k 棋盘格子索引，从 1 开始
     * @param result
     * @return 如果假设成立返回 true， 否则返回 false
     */
    public boolean mathInduction(int k, Result result){
        if(k==1){
            result.setWheatNum(Math.pow(2, k-1));
            result.setTotalWheatNum(Math.pow(2, k) - 1);
            return result.getWheatNum().compareTo(BigDecimal.ONE)==0
                    && result.getTotalWheatNum().compareTo(BigDecimal.ONE)==0;
        } else {
            boolean previous = mathInduction(k-1, result);
            System.out.println("k : " + k + ", previous : " + previous);
            if(previous) {
                result.setWheatNum(result.getWheatNum().multiply(BigDecimal.valueOf(2)));
                result.setTotalWheatNum(result.getTotalWheatNum().add(result.getWheatNum()));
                BigDecimal wheatNum = BigDecimal.valueOf(Math.pow(2,k-1));
                BigDecimal totalWheatNum = BigDecimal.valueOf(Math.pow(2,k)-1);
                if(k==54) {
                    System.out.println(JSON.toJSONString(result));
                    System.out.println("wheatNum : " + wheatNum + ", totalWheatNum : = " + totalWheatNum);
                }
                return result.getWheatNum().compareTo(wheatNum)==0
                        && result.getTotalWheatNum().compareTo(totalWheatNum)==0;
            }
            return false;
        }
    }

    public static class Result {
        private BigDecimal wheatNum = BigDecimal.ZERO;
        private BigDecimal totalWheatNum = BigDecimal.ZERO;

        public void setWheatNum(double wheatNum) {
            this.wheatNum = BigDecimal.valueOf(wheatNum);
        }

        public void setTotalWheatNum(double totalWheatNum) {
            this.totalWheatNum = BigDecimal.valueOf(totalWheatNum);
        }

        public void setWheatNum(BigDecimal wheatNum) {
            this.wheatNum = wheatNum;
        }

        public void setTotalWheatNum(BigDecimal totalWheatNum) {
            this.totalWheatNum = totalWheatNum;
        }

        public BigDecimal getWheatNum() {
            return wheatNum;
        }

        public BigDecimal getTotalWheatNum() {
            return totalWheatNum;
        }
    }

    @Test
    public void testMathInduction(){
        Result result = new Result();
        boolean flag = mathInduction(64, result);
        System.out.println("flag : " + flag);
        System.out.println("result : " + JSON.toJSONString(result));
    }
}
