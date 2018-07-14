package com.example.thymeleaf.util;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Created by lixiaoming on 2018/7/13.
 */
public class HanziConvert {
    private static char[] SCDigits = {'零','一','二','三','四','五','六','七','八','九','十','百','千','万','亿'};

    private static Map<String ,Integer> cvMap = new HashMap<>();
    static {
        cvMap.put("零",0);
        cvMap.put("一",1);
        cvMap.put("两",2);
        cvMap.put("二",2);
        cvMap.put("三",3);
        cvMap.put("四",4);
        cvMap.put("五",5);
        cvMap.put("六",6);
        cvMap.put("七",7);
        cvMap.put("八",8);
        cvMap.put("九",9);
        cvMap.put("十",10);
        cvMap.put("百",100);
        cvMap.put("千",1000);
        cvMap.put("万",10000);
    }
    public static int convertFromHanzi(String hanzi) {
        String s1 = AppUtils.getPureNumbers(hanzi);
        if(StringUtils.isEmpty(s1)){
            Stack<Integer> stack =  new Stack();
            char[] chars = hanzi.toCharArray();
            for(int i=0;i<chars.length;i++){
                char c = chars[i];
                Integer number = cvMap.get(""+c);
                if(number!=null){
                    if(number>10){
                        int pre = 1;
                        if(!stack.empty()){
                            pre = stack.pop();
                        }
                        number = pre * number;
                    }
                    stack.push(number);
                }else{
                    return -1;
                }
            }
            int sum = 0;
            while (!stack.empty()){
                sum += stack.pop();
            }
            return sum;
        }else{
            return Integer.parseInt(s1);
        }
    }


    public static void main(String[] args) {
        String hanzi = "二百零八";

        int sum = convertFromHanzi(hanzi);
        System.out.println(sum);
    }


}
