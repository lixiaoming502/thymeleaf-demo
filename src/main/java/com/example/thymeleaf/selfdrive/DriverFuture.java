package com.example.thymeleaf.selfdrive;

/**
 * Created by lixiaoming on 2018/6/27.
 */
public class DriverFuture {

    private final Integer domainId;
    private int crawlerId;

    private String pageUrl;

    private Object respone;

    private int statusCode;

    public DriverFuture(int crawlerId, Integer domainId, String url) {
        this.crawlerId = crawlerId;
        this.pageUrl = url;
        this.domainId = domainId;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getCrawlerId() {
        return crawlerId;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public Integer getDomainId() {
        return domainId;
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
