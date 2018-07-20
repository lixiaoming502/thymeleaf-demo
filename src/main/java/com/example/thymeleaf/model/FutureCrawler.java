package com.example.thymeleaf.model;

import java.util.Date;

public class FutureCrawler {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_future_crawler.id
     *
     * @mbggenerated Fri Jul 20 09:34:33 CST 2018
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_future_crawler.seed_id
     *
     * @mbggenerated Fri Jul 20 09:34:33 CST 2018
     */
    private Integer seedId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_future_crawler.domain_id
     *
     * @mbggenerated Fri Jul 20 09:34:33 CST 2018
     */
    private Integer domainId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_future_crawler.level
     *
     * @mbggenerated Fri Jul 20 09:34:33 CST 2018
     */
    private Integer level;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_future_crawler.page_url
     *
     * @mbggenerated Fri Jul 20 09:34:33 CST 2018
     */
    private String pageUrl;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_future_crawler.response
     *
     * @mbggenerated Fri Jul 20 09:34:33 CST 2018
     */
    private String response;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_future_crawler.crawler_state
     *
     * @mbggenerated Fri Jul 20 09:34:33 CST 2018
     */
    private String crawlerState;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_future_crawler.create_time
     *
     * @mbggenerated Fri Jul 20 09:34:33 CST 2018
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_future_crawler.update_time
     *
     * @mbggenerated Fri Jul 20 09:34:33 CST 2018
     */
    private Date updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_future_crawler.callback_bean
     *
     * @mbggenerated Fri Jul 20 09:34:33 CST 2018
     */
    private String callbackBean;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_future_crawler.callback_level
     *
     * @mbggenerated Fri Jul 20 09:34:33 CST 2018
     */
    private Integer callbackLevel;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_future_crawler.callback_status
     *
     * @mbggenerated Fri Jul 20 09:34:33 CST 2018
     */
    private Integer callbackStatus;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_future_crawler.id
     *
     * @return the value of t_future_crawler.id
     *
     * @mbggenerated Fri Jul 20 09:34:33 CST 2018
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_future_crawler.id
     *
     * @param id the value for t_future_crawler.id
     *
     * @mbggenerated Fri Jul 20 09:34:33 CST 2018
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_future_crawler.seed_id
     *
     * @return the value of t_future_crawler.seed_id
     *
     * @mbggenerated Fri Jul 20 09:34:33 CST 2018
     */
    public Integer getSeedId() {
        return seedId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_future_crawler.seed_id
     *
     * @param seedId the value for t_future_crawler.seed_id
     *
     * @mbggenerated Fri Jul 20 09:34:33 CST 2018
     */
    public void setSeedId(Integer seedId) {
        this.seedId = seedId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_future_crawler.domain_id
     *
     * @return the value of t_future_crawler.domain_id
     *
     * @mbggenerated Fri Jul 20 09:34:33 CST 2018
     */
    public Integer getDomainId() {
        return domainId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_future_crawler.domain_id
     *
     * @param domainId the value for t_future_crawler.domain_id
     *
     * @mbggenerated Fri Jul 20 09:34:33 CST 2018
     */
    public void setDomainId(Integer domainId) {
        this.domainId = domainId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_future_crawler.level
     *
     * @return the value of t_future_crawler.level
     *
     * @mbggenerated Fri Jul 20 09:34:33 CST 2018
     */
    public Integer getLevel() {
        return level;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_future_crawler.level
     *
     * @param level the value for t_future_crawler.level
     *
     * @mbggenerated Fri Jul 20 09:34:33 CST 2018
     */
    public void setLevel(Integer level) {
        this.level = level;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_future_crawler.page_url
     *
     * @return the value of t_future_crawler.page_url
     *
     * @mbggenerated Fri Jul 20 09:34:33 CST 2018
     */
    public String getPageUrl() {
        return pageUrl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_future_crawler.page_url
     *
     * @param pageUrl the value for t_future_crawler.page_url
     *
     * @mbggenerated Fri Jul 20 09:34:33 CST 2018
     */
    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl == null ? null : pageUrl.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_future_crawler.response
     *
     * @return the value of t_future_crawler.response
     *
     * @mbggenerated Fri Jul 20 09:34:33 CST 2018
     */
    public String getResponse() {
        return response;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_future_crawler.response
     *
     * @param response the value for t_future_crawler.response
     *
     * @mbggenerated Fri Jul 20 09:34:33 CST 2018
     */
    public void setResponse(String response) {
        this.response = response == null ? null : response.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_future_crawler.crawler_state
     *
     * @return the value of t_future_crawler.crawler_state
     *
     * @mbggenerated Fri Jul 20 09:34:33 CST 2018
     */
    public String getCrawlerState() {
        return crawlerState;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_future_crawler.crawler_state
     *
     * @param crawlerState the value for t_future_crawler.crawler_state
     *
     * @mbggenerated Fri Jul 20 09:34:33 CST 2018
     */
    public void setCrawlerState(String crawlerState) {
        this.crawlerState = crawlerState == null ? null : crawlerState.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_future_crawler.create_time
     *
     * @return the value of t_future_crawler.create_time
     *
     * @mbggenerated Fri Jul 20 09:34:33 CST 2018
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_future_crawler.create_time
     *
     * @param createTime the value for t_future_crawler.create_time
     *
     * @mbggenerated Fri Jul 20 09:34:33 CST 2018
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_future_crawler.update_time
     *
     * @return the value of t_future_crawler.update_time
     *
     * @mbggenerated Fri Jul 20 09:34:33 CST 2018
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_future_crawler.update_time
     *
     * @param updateTime the value for t_future_crawler.update_time
     *
     * @mbggenerated Fri Jul 20 09:34:33 CST 2018
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_future_crawler.callback_bean
     *
     * @return the value of t_future_crawler.callback_bean
     *
     * @mbggenerated Fri Jul 20 09:34:33 CST 2018
     */
    public String getCallbackBean() {
        return callbackBean;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_future_crawler.callback_bean
     *
     * @param callbackBean the value for t_future_crawler.callback_bean
     *
     * @mbggenerated Fri Jul 20 09:34:33 CST 2018
     */
    public void setCallbackBean(String callbackBean) {
        this.callbackBean = callbackBean == null ? null : callbackBean.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_future_crawler.callback_level
     *
     * @return the value of t_future_crawler.callback_level
     *
     * @mbggenerated Fri Jul 20 09:34:33 CST 2018
     */
    public Integer getCallbackLevel() {
        return callbackLevel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_future_crawler.callback_level
     *
     * @param callbackLevel the value for t_future_crawler.callback_level
     *
     * @mbggenerated Fri Jul 20 09:34:33 CST 2018
     */
    public void setCallbackLevel(Integer callbackLevel) {
        this.callbackLevel = callbackLevel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_future_crawler.callback_status
     *
     * @return the value of t_future_crawler.callback_status
     *
     * @mbggenerated Fri Jul 20 09:34:33 CST 2018
     */
    public Integer getCallbackStatus() {
        return callbackStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_future_crawler.callback_status
     *
     * @param callbackStatus the value for t_future_crawler.callback_status
     *
     * @mbggenerated Fri Jul 20 09:34:33 CST 2018
     */
    public void setCallbackStatus(Integer callbackStatus) {
        this.callbackStatus = callbackStatus;
    }
}