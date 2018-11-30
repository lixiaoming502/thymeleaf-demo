package com.example.thymeleaf.service;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

/**
 * Created by lixiaoming on 2018/11/28.
 */
@Component
public class MarkCleaner {
    public String cleanLines(String content,int begin,int end){
        String[] lines = content.split("\n");
        StringBuffer lineBuffer = new StringBuffer();
        int totalCount = lines.length;
        for(int i=0;i<totalCount;i++){
            if(i>=begin&&i<=end){
                continue;
            }else{
                lineBuffer.append(lines[i]+"\n");
            }
        }
        return lineBuffer.toString();
    }

    public String replaceWithRegex(String content,String regex){
        return content.replaceAll(regex,"");
    }

    public String replaceWithStrDelimer(String content,String begin,String end){
        String regex =begin+".+"+end;
        return replaceWithRegex(content,regex);
    }

    public static void main(String[] args) throws IOException {
        MarkCleaner markCleaner = new MarkCleaner();
        String path ="/Users/lixiaoming/Downloads/crawler_816074.txt";
        String respone = FileUtils.readFileToString(new File(path), "utf-8");
        String rr = "AAAAAAAAAAA<br>　　http://www.yqxs.cc/html/64/64352/5126700.htmlBBBBBBBB";
        //String rr = markCleaner.cleanLines(respone,0,2);
        //String regex ="<br>　　老铁!还在找.+</p>";
        rr = markCleaner.replaceWithStrDelimer(rr,"<br>　　http://www.yqxs.cc",".html");
        System.out.println("AA:"+rr);

    }
}
