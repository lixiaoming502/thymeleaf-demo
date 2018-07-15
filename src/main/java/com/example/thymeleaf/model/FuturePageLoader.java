package com.example.thymeleaf.model;

import java.util.Date;

public class FuturePageLoader {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_future_page_loader.id
     *
     * @mbggenerated Sun Jul 15 17:39:01 CST 2018
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_future_page_loader.crawler_id
     *
     * @mbggenerated Sun Jul 15 17:39:01 CST 2018
     */
    private Integer crawlerId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_future_page_loader.domain_id
     *
     * @mbggenerated Sun Jul 15 17:39:01 CST 2018
     */
    private Integer domainId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_future_page_loader.page_url
     *
     * @mbggenerated Sun Jul 15 17:39:01 CST 2018
     */
    private String pageUrl;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_future_page_loader.response
     *
     * @mbggenerated Sun Jul 15 17:39:01 CST 2018
     */
    private String response;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_future_page_loader.state_code
     *
     * @mbggenerated Sun Jul 15 17:39:01 CST 2018
     */
    private Integer stateCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_future_page_loader.loader_state
     *
     * @mbggenerated Sun Jul 15 17:39:01 CST 2018
     */
    private String loaderState;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_future_page_loader.paned
     *
     * @mbggenerated Sun Jul 15 17:39:01 CST 2018
     */
    private String paned;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_future_page_loader.create_time
     *
     * @mbggenerated Sun Jul 15 17:39:01 CST 2018
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_future_page_loader.update_time
     *
     * @mbggenerated Sun Jul 15 17:39:01 CST 2018
     */
    private Date updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_future_page_loader.plan_time
     *
     * @mbggenerated Sun Jul 15 17:39:01 CST 2018
     */
    private Date planTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_future_page_loader.id
     *
     * @return the value of t_future_page_loader.id
     *
     * @mbggenerated Sun Jul 15 17:39:01 CST 2018
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_future_page_loader.id
     *
     * @param id the value for t_future_page_loader.id
     *
     * @mbggenerated Sun Jul 15 17:39:01 CST 2018
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_future_page_loader.crawler_id
     *
     * @return the value of t_future_page_loader.crawler_id
     *
     * @mbggenerated Sun Jul 15 17:39:01 CST 2018
     */
    public Integer getCrawlerId() {
        return crawlerId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_future_page_loader.crawler_id
     *
     * @param crawlerId the value for t_future_page_loader.crawler_id
     *
     * @mbggenerated Sun Jul 15 17:39:01 CST 2018
     */
    public void setCrawlerId(Integer crawlerId) {
        this.crawlerId = crawlerId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_future_page_loader.domain_id
     *
     * @return the value of t_future_page_loader.domain_id
     *
     * @mbggenerated Sun Jul 15 17:39:01 CST 2018
     */
    public Integer getDomainId() {
        return domainId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_future_page_loader.domain_id
     *
     * @param domainId the value for t_future_page_loader.domain_id
     *
     * @mbggenerated Sun Jul 15 17:39:01 CST 2018
     */
    public void setDomainId(Integer domainId) {
        this.domainId = domainId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_future_page_loader.page_url
     *
     * @return the value of t_future_page_loader.page_url
     *
     * @mbggenerated Sun Jul 15 17:39:01 CST 2018
     */
    public String getPageUrl() {
        return pageUrl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_future_page_loader.page_url
     *
     * @param pageUrl the value for t_future_page_loader.page_url
     *
     * @mbggenerated Sun Jul 15 17:39:01 CST 2018
     */
    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl == null ? null : pageUrl.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_future_page_loader.response
     *
     * @return the value of t_future_page_loader.response
     *
     * @mbggenerated Sun Jul 15 17:39:01 CST 2018
     */
    public String getResponse() {
        return response;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_future_page_loader.response
     *
     * @param response the value for t_future_page_loader.response
     *
     * @mbggenerated Sun Jul 15 17:39:01 CST 2018
     */
    public void setResponse(String response) {
        this.response = response == null ? null : response.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_future_page_loader.state_code
     *
     * @return the value of t_future_page_loader.state_code
     *
     * @mbggenerated Sun Jul 15 17:39:01 CST 2018
     */
    public Integer getStateCode() {
        return stateCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_future_page_loader.state_code
     *
     * @param stateCode the value for t_future_page_loader.state_code
     *
     * @mbggenerated Sun Jul 15 17:39:01 CST 2018
     */
    public void setStateCode(Integer stateCode) {
        this.stateCode = stateCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_future_page_loader.loader_state
     *
     * @return the value of t_future_page_loader.loader_state
     *
     * @mbggenerated Sun Jul 15 17:39:01 CST 2018
     */
    public String getLoaderState() {
        return loaderState;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_future_page_loader.loader_state
     *
     * @param loaderState the value for t_future_page_loader.loader_state
     *
     * @mbggenerated Sun Jul 15 17:39:01 CST 2018
     */
    public void setLoaderState(String loaderState) {
        this.loaderState = loaderState == null ? null : loaderState.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_future_page_loader.paned
     *
     * @return the value of t_future_page_loader.paned
     *
     * @mbggenerated Sun Jul 15 17:39:01 CST 2018
     */
    public String getPaned() {
        return paned;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_future_page_loader.paned
     *
     * @param paned the value for t_future_page_loader.paned
     *
     * @mbggenerated Sun Jul 15 17:39:01 CST 2018
     */
    public void setPaned(String paned) {
        this.paned = paned == null ? null : paned.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_future_page_loader.create_time
     *
     * @return the value of t_future_page_loader.create_time
     *
     * @mbggenerated Sun Jul 15 17:39:01 CST 2018
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_future_page_loader.create_time
     *
     * @param createTime the value for t_future_page_loader.create_time
     *
     * @mbggenerated Sun Jul 15 17:39:01 CST 2018
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_future_page_loader.update_time
     *
     * @return the value of t_future_page_loader.update_time
     *
     * @mbggenerated Sun Jul 15 17:39:01 CST 2018
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_future_page_loader.update_time
     *
     * @param updateTime the value for t_future_page_loader.update_time
     *
     * @mbggenerated Sun Jul 15 17:39:01 CST 2018
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_future_page_loader.plan_time
     *
     * @return the value of t_future_page_loader.plan_time
     *
     * @mbggenerated Sun Jul 15 17:39:01 CST 2018
     */
    public Date getPlanTime() {
        return planTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_future_page_loader.plan_time
     *
     * @param planTime the value for t_future_page_loader.plan_time
     *
     * @mbggenerated Sun Jul 15 17:39:01 CST 2018
     */
    public void setPlanTime(Date planTime) {
        this.planTime = planTime;
    }
}