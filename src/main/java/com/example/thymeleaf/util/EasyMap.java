package com.example.thymeleaf.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lixiaoming on 17/3/14.
 */
public class EasyMap<K,V> {

    public static Map toMap(Object ... args) {
        Map container = new HashMap();
        for(int i=0;i<args.length;){
            container.put(args[i++],args[i++]);
        }
        return container;
    }

    public static void main(String[] args) {
        Map<String,String> map = EasyMap.toMap("key1","value1","key2","value2","key3","value3");
        System.out.println("done");
    }
}
