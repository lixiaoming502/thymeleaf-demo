package com.example.thymeleaf.crawler;

import com.example.thymeleaf.util.AppUtils;

import java.io.IOException;

/**
 * Created by lixiaoming on 2019/2/2.
 */
public class Kdata163Crawler {

    public static void main(String[] args) throws IOException {
        String baseUrl = "http://quotes.money.163.com/service/chddata.html?code=%s%s&start=%s&end=%s";
        String url = String.format(baseUrl, "0", "600050", "20190101", "20190201");
        String domainName = AppUtils.extraDomain(url);
        System.out.println(domainName);
        /*HttpRequest httpRequest = HttpRequest.get(url);
        HttpResponse response = httpRequest.send();
        String contentDisposition = response.header("Content-Disposition");
        Preconditions.checkNotNull(contentDisposition,"Content-Disposition不能为空");
        byte[] bytes = response.bodyBytes();
        String targetFile = getFileName("163","sh","600050");
        File target = new File(targetFile);
        FileUtils.writeByteArrayToFile(target,bytes);
        System.out.println("done");*/
    }

    private static String getFileName(String source, String exchange, String stockCode) {
        // stockdata/stock/sh/600000/kdata/bfq/163_dayk.csv
        String baseDir = "stockdata/stock/%s/%s/kdata/bfq/%s_dayk.csv";
        return String.format(baseDir,exchange,stockCode,source);
    }
}
