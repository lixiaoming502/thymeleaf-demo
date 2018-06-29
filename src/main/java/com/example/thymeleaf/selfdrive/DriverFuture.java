package com.example.thymeleaf.selfdrive;

/**
 * Created by lixiaoming on 2018/6/27.
 */
public class DriverFuture {

    private int crawlerId;

    private String pageUrl;

    private Object respone;

    public DriverFuture(int crawlerId, String url) {
        this.crawlerId = crawlerId;
        this.pageUrl = url;
    }

    public int getCrawlerId() {
        return crawlerId;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public Object getRespone() {
        return respone;
    }

    public void setRespone(Object respone){
        this.respone = respone;
    }

    public boolean isDone() {
        return respone!=null;
    }

}
