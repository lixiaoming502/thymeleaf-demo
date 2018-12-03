package com.example.thymeleaf;

import com.example.thymeleaf.util.AppUtils;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class TestGuavaCache {

    @Test
    public void testUserCacheLoader() throws ExecutionException {
        // 模拟数据
        final List<Person> list = new ArrayList<Person>(5);
        list.add(new Person("1", "zhangsan"));
        list.add(new Person("2", "lisi"));
        list.add(new Person("3", "wangwu"));

        // 创建cache
        LoadingCache<String, Person> cache = CacheBuilder.newBuilder()//
                .refreshAfterWrite(1, TimeUnit.MINUTES)// 给定时间内没有被读/写访问，则回收。
                // .expireAfterWrite(5, TimeUnit.SECONDS)//给定时间内没有写访问，则回收。
                // .expireAfterAccess(3, TimeUnit.SECONDS)// 缓存过期时间为3秒
                .maximumSize(100).// 设置缓存个数
                build(new CacheLoader<String, Person>() {
                    @Override
                    /**  当本地缓存命没有中时，调用load方法获取结果并将结果缓存
                     */
                    public Person load(String key) throws ExecutionException {
                        System.out.println(key + " load in cache");
                        return getPerson(key);
                    }

                    // 此时一般我们会进行相关处理，如到数据库去查询
                    private Person getPerson(String key) throws ExecutionException {
                        System.out.println(key + " query");
                        for (Person p : list) {
                            if (p.getId().equals(key))
                                return p;
                        }
                        return null;
                    }
                });

        cache.get("1");
        cache.get("2");
        cache.get("3");
        System.out.println("======= sencond time  ==========");
        cache.get("1");
        cache.get("2");
        AppUtils.sleep(61);
        cache.get("3");
    }

    private class Person {
        private String id;
        public Person(String id, String zhangsan) {
            this.id = id;
        }

        public String getId() {
            return id;
        }
    }
}