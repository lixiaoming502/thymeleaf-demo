package com.example.thymeleaf;

import java.io.IOException;

/**
 * Created by lixiaoming on 2018/6/28.
 */
public class T1 {

    private String correct(String title) {
        if(title.length()>3){
            title = title.substring(0,3);
        }
        return title;
    }

    public static void main(String[] args) throws IOException {
        T1 t1 = new T1();
        String title = "徐漫陆亦深小说免费阅读";
        System.out.println(t1.correct(title));
    }
}
