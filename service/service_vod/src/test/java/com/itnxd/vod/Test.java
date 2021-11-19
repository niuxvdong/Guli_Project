package com.itnxd.vod;

import java.util.Arrays;
import java.util.List;

/**
 * @author ITNXD
 * @create 2021-11-09 14:03
 */
public class Test {

    @org.junit.Test
    public void test(){
        List<String> list = Arrays.asList("n","m","v");
        String join = String.join(",", list);
        System.out.println(join);
    }
}
