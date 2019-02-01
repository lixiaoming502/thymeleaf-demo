package com.example.thymeleaf.model;

import java.util.Date;

public class NearText {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_near_text.near_id
     *
     * @mbggenerated Fri Dec 14 13:50:13 CST 2018
     */
    private Integer nearId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_near_text.article_id
     *
     * @mbggenerated Fri Dec 14 13:50:13 CST 2018
     */
    private Integer articleId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_near_text.text_id
     *
     * @mbggenerated Fri Dec 14 13:50:13 CST 2018
     */
    private Integer textId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_near_text.origin_text
     *
     * @mbggenerated Fri Dec 14 13:50:13 CST 2018
     */
    private String originText;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_near_text.near_text
     *
     * @mbggenerated Fri Dec 14 13:50:13 CST 2018
     */
    private String nearText;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_near_text.is_online
     *
     * @mbggenerated Fri Dec 14 13:50:13 CST 2018
     */
    private Boolean isOnline;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_near_text.create_time
     *
     * @mbggenerated Fri Dec 14 13:50:13 CST 2018
     */
    private Date createTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_near_text.near_id
     *
     * @return the value of t_near_text.near_id
     *
     * @mbggenerated Fri Dec 14 13:50:13 CST 2018
     */
    public Integer getNearId() {
        return nearId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_near_text.near_id
     *
     * @param nearId the value for t_near_text.near_id
     *
     * @mbggenerated Fri Dec 14 13:50:13 CST 2018
     */
    public void setNearId(Integer nearId) {
        this.nearId = nearId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_near_text.article_id
     *
     * @return the value of t_near_text.article_id
     *
     * @mbggenerated Fri Dec 14 13:50:13 CST 2018
     */
    public Integer getArticleId() {
        return articleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_near_text.article_id
     *
     * @param articleId the value for t_near_text.article_id
     *
     * @mbggenerated Fri Dec 14 13:50:13 CST 2018
     */
    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_near_text.text_id
     *
     * @return the value of t_near_text.text_id
     *
     * @mbggenerated Fri Dec 14 13:50:13 CST 2018
     */
    public Integer getTextId() {
        return textId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_near_text.text_id
     *
     * @param textId the value for t_near_text.text_id
     *
     * @mbggenerated Fri Dec 14 13:50:13 CST 2018
     */
    public void setTextId(Integer textId) {
        this.textId = textId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_near_text.origin_text
     *
     * @return the value of t_near_text.origin_text
     *
     * @mbggenerated Fri Dec 14 13:50:13 CST 2018
     */
    public String getOriginText() {
        return originText;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_near_text.origin_text
     *
     * @param originText the value for t_near_text.origin_text
     *
     * @mbggenerated Fri Dec 14 13:50:13 CST 2018
     */
    public void setOriginText(String originText) {
        this.originText = originText == null ? null : originText.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_near_text.near_text
     *
     * @return the value of t_near_text.near_text
     *
     * @mbggenerated Fri Dec 14 13:50:13 CST 2018
     */
    public String getNearText() {
        return nearText;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_near_text.near_text
     *
     * @param nearText the value for t_near_text.near_text
     *
     * @mbggenerated Fri Dec 14 13:50:13 CST 2018
     */
    public void setNearText(String nearText) {
        this.nearText = nearText == null ? null : nearText.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_near_text.is_online
     *
     * @return the value of t_near_text.is_online
     *
     * @mbggenerated Fri Dec 14 13:50:13 CST 2018
     */
    public Boolean getIsOnline() {
        return isOnline;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_near_text.is_online
     *
     * @param isOnline the value for t_near_text.is_online
     *
     * @mbggenerated Fri Dec 14 13:50:13 CST 2018
     */
    public void setIsOnline(Boolean isOnline) {
        this.isOnline = isOnline;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_near_text.create_time
     *
     * @return the value of t_near_text.create_time
     *
     * @mbggenerated Fri Dec 14 13:50:13 CST 2018
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_near_text.create_time
     *
     * @param createTime the value for t_near_text.create_time
     *
     * @mbggenerated Fri Dec 14 13:50:13 CST 2018
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}