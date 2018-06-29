package com.example.thymeleaf.model;

import java.util.Date;

public class FutureCrawlerCfg {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_future_crawler_cfg.id
     *
     * @mbggenerated Fri Jun 29 09:08:42 CST 2018
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_future_crawler_cfg.domain_name
     *
     * @mbggenerated Fri Jun 29 09:08:42 CST 2018
     */
    private String domainName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_future_crawler_cfg.gap
     *
     * @mbggenerated Fri Jun 29 09:08:42 CST 2018
     */
    private Integer gap;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_future_crawler_cfg.last_crawl_time
     *
     * @mbggenerated Fri Jun 29 09:08:42 CST 2018
     */
    private Date lastCrawlTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_future_crawler_cfg.id
     *
     * @return the value of t_future_crawler_cfg.id
     *
     * @mbggenerated Fri Jun 29 09:08:42 CST 2018
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_future_crawler_cfg.id
     *
     * @param id the value for t_future_crawler_cfg.id
     *
     * @mbggenerated Fri Jun 29 09:08:42 CST 2018
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_future_crawler_cfg.domain_name
     *
     * @return the value of t_future_crawler_cfg.domain_name
     *
     * @mbggenerated Fri Jun 29 09:08:42 CST 2018
     */
    public String getDomainName() {
        return domainName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_future_crawler_cfg.domain_name
     *
     * @param domainName the value for t_future_crawler_cfg.domain_name
     *
     * @mbggenerated Fri Jun 29 09:08:42 CST 2018
     */
    public void setDomainName(String domainName) {
        this.domainName = domainName == null ? null : domainName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_future_crawler_cfg.gap
     *
     * @return the value of t_future_crawler_cfg.gap
     *
     * @mbggenerated Fri Jun 29 09:08:42 CST 2018
     */
    public Integer getGap() {
        return gap;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_future_crawler_cfg.gap
     *
     * @param gap the value for t_future_crawler_cfg.gap
     *
     * @mbggenerated Fri Jun 29 09:08:42 CST 2018
     */
    public void setGap(Integer gap) {
        this.gap = gap;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_future_crawler_cfg.last_crawl_time
     *
     * @return the value of t_future_crawler_cfg.last_crawl_time
     *
     * @mbggenerated Fri Jun 29 09:08:42 CST 2018
     */
    public Date getLastCrawlTime() {
        return lastCrawlTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_future_crawler_cfg.last_crawl_time
     *
     * @param lastCrawlTime the value for t_future_crawler_cfg.last_crawl_time
     *
     * @mbggenerated Fri Jun 29 09:08:42 CST 2018
     */
    public void setLastCrawlTime(Date lastCrawlTime) {
        this.lastCrawlTime = lastCrawlTime;
    }
}