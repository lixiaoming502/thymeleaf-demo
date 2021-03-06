package com.example.thymeleaf.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FutureCrawlerExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_future_crawler
     *
     * @mbggenerated Wed Feb 13 16:38:32 CST 2019
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_future_crawler
     *
     * @mbggenerated Wed Feb 13 16:38:32 CST 2019
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_future_crawler
     *
     * @mbggenerated Wed Feb 13 16:38:32 CST 2019
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_future_crawler
     *
     * @mbggenerated Wed Feb 13 16:38:32 CST 2019
     */
    public FutureCrawlerExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_future_crawler
     *
     * @mbggenerated Wed Feb 13 16:38:32 CST 2019
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_future_crawler
     *
     * @mbggenerated Wed Feb 13 16:38:32 CST 2019
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_future_crawler
     *
     * @mbggenerated Wed Feb 13 16:38:32 CST 2019
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_future_crawler
     *
     * @mbggenerated Wed Feb 13 16:38:32 CST 2019
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_future_crawler
     *
     * @mbggenerated Wed Feb 13 16:38:32 CST 2019
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_future_crawler
     *
     * @mbggenerated Wed Feb 13 16:38:32 CST 2019
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_future_crawler
     *
     * @mbggenerated Wed Feb 13 16:38:32 CST 2019
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_future_crawler
     *
     * @mbggenerated Wed Feb 13 16:38:32 CST 2019
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_future_crawler
     *
     * @mbggenerated Wed Feb 13 16:38:32 CST 2019
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_future_crawler
     *
     * @mbggenerated Wed Feb 13 16:38:32 CST 2019
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table t_future_crawler
     *
     * @mbggenerated Wed Feb 13 16:38:32 CST 2019
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andSeedIdIsNull() {
            addCriterion("seed_id is null");
            return (Criteria) this;
        }

        public Criteria andSeedIdIsNotNull() {
            addCriterion("seed_id is not null");
            return (Criteria) this;
        }

        public Criteria andSeedIdEqualTo(Integer value) {
            addCriterion("seed_id =", value, "seedId");
            return (Criteria) this;
        }

        public Criteria andSeedIdNotEqualTo(Integer value) {
            addCriterion("seed_id <>", value, "seedId");
            return (Criteria) this;
        }

        public Criteria andSeedIdGreaterThan(Integer value) {
            addCriterion("seed_id >", value, "seedId");
            return (Criteria) this;
        }

        public Criteria andSeedIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("seed_id >=", value, "seedId");
            return (Criteria) this;
        }

        public Criteria andSeedIdLessThan(Integer value) {
            addCriterion("seed_id <", value, "seedId");
            return (Criteria) this;
        }

        public Criteria andSeedIdLessThanOrEqualTo(Integer value) {
            addCriterion("seed_id <=", value, "seedId");
            return (Criteria) this;
        }

        public Criteria andSeedIdIn(List<Integer> values) {
            addCriterion("seed_id in", values, "seedId");
            return (Criteria) this;
        }

        public Criteria andSeedIdNotIn(List<Integer> values) {
            addCriterion("seed_id not in", values, "seedId");
            return (Criteria) this;
        }

        public Criteria andSeedIdBetween(Integer value1, Integer value2) {
            addCriterion("seed_id between", value1, value2, "seedId");
            return (Criteria) this;
        }

        public Criteria andSeedIdNotBetween(Integer value1, Integer value2) {
            addCriterion("seed_id not between", value1, value2, "seedId");
            return (Criteria) this;
        }

        public Criteria andDomainIdIsNull() {
            addCriterion("domain_id is null");
            return (Criteria) this;
        }

        public Criteria andDomainIdIsNotNull() {
            addCriterion("domain_id is not null");
            return (Criteria) this;
        }

        public Criteria andDomainIdEqualTo(Integer value) {
            addCriterion("domain_id =", value, "domainId");
            return (Criteria) this;
        }

        public Criteria andDomainIdNotEqualTo(Integer value) {
            addCriterion("domain_id <>", value, "domainId");
            return (Criteria) this;
        }

        public Criteria andDomainIdGreaterThan(Integer value) {
            addCriterion("domain_id >", value, "domainId");
            return (Criteria) this;
        }

        public Criteria andDomainIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("domain_id >=", value, "domainId");
            return (Criteria) this;
        }

        public Criteria andDomainIdLessThan(Integer value) {
            addCriterion("domain_id <", value, "domainId");
            return (Criteria) this;
        }

        public Criteria andDomainIdLessThanOrEqualTo(Integer value) {
            addCriterion("domain_id <=", value, "domainId");
            return (Criteria) this;
        }

        public Criteria andDomainIdIn(List<Integer> values) {
            addCriterion("domain_id in", values, "domainId");
            return (Criteria) this;
        }

        public Criteria andDomainIdNotIn(List<Integer> values) {
            addCriterion("domain_id not in", values, "domainId");
            return (Criteria) this;
        }

        public Criteria andDomainIdBetween(Integer value1, Integer value2) {
            addCriterion("domain_id between", value1, value2, "domainId");
            return (Criteria) this;
        }

        public Criteria andDomainIdNotBetween(Integer value1, Integer value2) {
            addCriterion("domain_id not between", value1, value2, "domainId");
            return (Criteria) this;
        }

        public Criteria andLevelIsNull() {
            addCriterion("level is null");
            return (Criteria) this;
        }

        public Criteria andLevelIsNotNull() {
            addCriterion("level is not null");
            return (Criteria) this;
        }

        public Criteria andLevelEqualTo(Integer value) {
            addCriterion("level =", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelNotEqualTo(Integer value) {
            addCriterion("level <>", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelGreaterThan(Integer value) {
            addCriterion("level >", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelGreaterThanOrEqualTo(Integer value) {
            addCriterion("level >=", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelLessThan(Integer value) {
            addCriterion("level <", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelLessThanOrEqualTo(Integer value) {
            addCriterion("level <=", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelIn(List<Integer> values) {
            addCriterion("level in", values, "level");
            return (Criteria) this;
        }

        public Criteria andLevelNotIn(List<Integer> values) {
            addCriterion("level not in", values, "level");
            return (Criteria) this;
        }

        public Criteria andLevelBetween(Integer value1, Integer value2) {
            addCriterion("level between", value1, value2, "level");
            return (Criteria) this;
        }

        public Criteria andLevelNotBetween(Integer value1, Integer value2) {
            addCriterion("level not between", value1, value2, "level");
            return (Criteria) this;
        }

        public Criteria andPageUrlIsNull() {
            addCriterion("page_url is null");
            return (Criteria) this;
        }

        public Criteria andPageUrlIsNotNull() {
            addCriterion("page_url is not null");
            return (Criteria) this;
        }

        public Criteria andPageUrlEqualTo(String value) {
            addCriterion("page_url =", value, "pageUrl");
            return (Criteria) this;
        }

        public Criteria andPageUrlNotEqualTo(String value) {
            addCriterion("page_url <>", value, "pageUrl");
            return (Criteria) this;
        }

        public Criteria andPageUrlGreaterThan(String value) {
            addCriterion("page_url >", value, "pageUrl");
            return (Criteria) this;
        }

        public Criteria andPageUrlGreaterThanOrEqualTo(String value) {
            addCriterion("page_url >=", value, "pageUrl");
            return (Criteria) this;
        }

        public Criteria andPageUrlLessThan(String value) {
            addCriterion("page_url <", value, "pageUrl");
            return (Criteria) this;
        }

        public Criteria andPageUrlLessThanOrEqualTo(String value) {
            addCriterion("page_url <=", value, "pageUrl");
            return (Criteria) this;
        }

        public Criteria andPageUrlLike(String value) {
            addCriterion("page_url like", value, "pageUrl");
            return (Criteria) this;
        }

        public Criteria andPageUrlNotLike(String value) {
            addCriterion("page_url not like", value, "pageUrl");
            return (Criteria) this;
        }

        public Criteria andPageUrlIn(List<String> values) {
            addCriterion("page_url in", values, "pageUrl");
            return (Criteria) this;
        }

        public Criteria andPageUrlNotIn(List<String> values) {
            addCriterion("page_url not in", values, "pageUrl");
            return (Criteria) this;
        }

        public Criteria andPageUrlBetween(String value1, String value2) {
            addCriterion("page_url between", value1, value2, "pageUrl");
            return (Criteria) this;
        }

        public Criteria andPageUrlNotBetween(String value1, String value2) {
            addCriterion("page_url not between", value1, value2, "pageUrl");
            return (Criteria) this;
        }

        public Criteria andResponseIsNull() {
            addCriterion("response is null");
            return (Criteria) this;
        }

        public Criteria andResponseIsNotNull() {
            addCriterion("response is not null");
            return (Criteria) this;
        }

        public Criteria andResponseEqualTo(String value) {
            addCriterion("response =", value, "response");
            return (Criteria) this;
        }

        public Criteria andResponseNotEqualTo(String value) {
            addCriterion("response <>", value, "response");
            return (Criteria) this;
        }

        public Criteria andResponseGreaterThan(String value) {
            addCriterion("response >", value, "response");
            return (Criteria) this;
        }

        public Criteria andResponseGreaterThanOrEqualTo(String value) {
            addCriterion("response >=", value, "response");
            return (Criteria) this;
        }

        public Criteria andResponseLessThan(String value) {
            addCriterion("response <", value, "response");
            return (Criteria) this;
        }

        public Criteria andResponseLessThanOrEqualTo(String value) {
            addCriterion("response <=", value, "response");
            return (Criteria) this;
        }

        public Criteria andResponseLike(String value) {
            addCriterion("response like", value, "response");
            return (Criteria) this;
        }

        public Criteria andResponseNotLike(String value) {
            addCriterion("response not like", value, "response");
            return (Criteria) this;
        }

        public Criteria andResponseIn(List<String> values) {
            addCriterion("response in", values, "response");
            return (Criteria) this;
        }

        public Criteria andResponseNotIn(List<String> values) {
            addCriterion("response not in", values, "response");
            return (Criteria) this;
        }

        public Criteria andResponseBetween(String value1, String value2) {
            addCriterion("response between", value1, value2, "response");
            return (Criteria) this;
        }

        public Criteria andResponseNotBetween(String value1, String value2) {
            addCriterion("response not between", value1, value2, "response");
            return (Criteria) this;
        }

        public Criteria andCrawlerStateIsNull() {
            addCriterion("crawler_state is null");
            return (Criteria) this;
        }

        public Criteria andCrawlerStateIsNotNull() {
            addCriterion("crawler_state is not null");
            return (Criteria) this;
        }

        public Criteria andCrawlerStateEqualTo(String value) {
            addCriterion("crawler_state =", value, "crawlerState");
            return (Criteria) this;
        }

        public Criteria andCrawlerStateNotEqualTo(String value) {
            addCriterion("crawler_state <>", value, "crawlerState");
            return (Criteria) this;
        }

        public Criteria andCrawlerStateGreaterThan(String value) {
            addCriterion("crawler_state >", value, "crawlerState");
            return (Criteria) this;
        }

        public Criteria andCrawlerStateGreaterThanOrEqualTo(String value) {
            addCriterion("crawler_state >=", value, "crawlerState");
            return (Criteria) this;
        }

        public Criteria andCrawlerStateLessThan(String value) {
            addCriterion("crawler_state <", value, "crawlerState");
            return (Criteria) this;
        }

        public Criteria andCrawlerStateLessThanOrEqualTo(String value) {
            addCriterion("crawler_state <=", value, "crawlerState");
            return (Criteria) this;
        }

        public Criteria andCrawlerStateLike(String value) {
            addCriterion("crawler_state like", value, "crawlerState");
            return (Criteria) this;
        }

        public Criteria andCrawlerStateNotLike(String value) {
            addCriterion("crawler_state not like", value, "crawlerState");
            return (Criteria) this;
        }

        public Criteria andCrawlerStateIn(List<String> values) {
            addCriterion("crawler_state in", values, "crawlerState");
            return (Criteria) this;
        }

        public Criteria andCrawlerStateNotIn(List<String> values) {
            addCriterion("crawler_state not in", values, "crawlerState");
            return (Criteria) this;
        }

        public Criteria andCrawlerStateBetween(String value1, String value2) {
            addCriterion("crawler_state between", value1, value2, "crawlerState");
            return (Criteria) this;
        }

        public Criteria andCrawlerStateNotBetween(String value1, String value2) {
            addCriterion("crawler_state not between", value1, value2, "crawlerState");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andCallbackBeanIsNull() {
            addCriterion("callback_bean is null");
            return (Criteria) this;
        }

        public Criteria andCallbackBeanIsNotNull() {
            addCriterion("callback_bean is not null");
            return (Criteria) this;
        }

        public Criteria andCallbackBeanEqualTo(String value) {
            addCriterion("callback_bean =", value, "callbackBean");
            return (Criteria) this;
        }

        public Criteria andCallbackBeanNotEqualTo(String value) {
            addCriterion("callback_bean <>", value, "callbackBean");
            return (Criteria) this;
        }

        public Criteria andCallbackBeanGreaterThan(String value) {
            addCriterion("callback_bean >", value, "callbackBean");
            return (Criteria) this;
        }

        public Criteria andCallbackBeanGreaterThanOrEqualTo(String value) {
            addCriterion("callback_bean >=", value, "callbackBean");
            return (Criteria) this;
        }

        public Criteria andCallbackBeanLessThan(String value) {
            addCriterion("callback_bean <", value, "callbackBean");
            return (Criteria) this;
        }

        public Criteria andCallbackBeanLessThanOrEqualTo(String value) {
            addCriterion("callback_bean <=", value, "callbackBean");
            return (Criteria) this;
        }

        public Criteria andCallbackBeanLike(String value) {
            addCriterion("callback_bean like", value, "callbackBean");
            return (Criteria) this;
        }

        public Criteria andCallbackBeanNotLike(String value) {
            addCriterion("callback_bean not like", value, "callbackBean");
            return (Criteria) this;
        }

        public Criteria andCallbackBeanIn(List<String> values) {
            addCriterion("callback_bean in", values, "callbackBean");
            return (Criteria) this;
        }

        public Criteria andCallbackBeanNotIn(List<String> values) {
            addCriterion("callback_bean not in", values, "callbackBean");
            return (Criteria) this;
        }

        public Criteria andCallbackBeanBetween(String value1, String value2) {
            addCriterion("callback_bean between", value1, value2, "callbackBean");
            return (Criteria) this;
        }

        public Criteria andCallbackBeanNotBetween(String value1, String value2) {
            addCriterion("callback_bean not between", value1, value2, "callbackBean");
            return (Criteria) this;
        }

        public Criteria andCallbackLevelIsNull() {
            addCriterion("callback_level is null");
            return (Criteria) this;
        }

        public Criteria andCallbackLevelIsNotNull() {
            addCriterion("callback_level is not null");
            return (Criteria) this;
        }

        public Criteria andCallbackLevelEqualTo(Integer value) {
            addCriterion("callback_level =", value, "callbackLevel");
            return (Criteria) this;
        }

        public Criteria andCallbackLevelNotEqualTo(Integer value) {
            addCriterion("callback_level <>", value, "callbackLevel");
            return (Criteria) this;
        }

        public Criteria andCallbackLevelGreaterThan(Integer value) {
            addCriterion("callback_level >", value, "callbackLevel");
            return (Criteria) this;
        }

        public Criteria andCallbackLevelGreaterThanOrEqualTo(Integer value) {
            addCriterion("callback_level >=", value, "callbackLevel");
            return (Criteria) this;
        }

        public Criteria andCallbackLevelLessThan(Integer value) {
            addCriterion("callback_level <", value, "callbackLevel");
            return (Criteria) this;
        }

        public Criteria andCallbackLevelLessThanOrEqualTo(Integer value) {
            addCriterion("callback_level <=", value, "callbackLevel");
            return (Criteria) this;
        }

        public Criteria andCallbackLevelIn(List<Integer> values) {
            addCriterion("callback_level in", values, "callbackLevel");
            return (Criteria) this;
        }

        public Criteria andCallbackLevelNotIn(List<Integer> values) {
            addCriterion("callback_level not in", values, "callbackLevel");
            return (Criteria) this;
        }

        public Criteria andCallbackLevelBetween(Integer value1, Integer value2) {
            addCriterion("callback_level between", value1, value2, "callbackLevel");
            return (Criteria) this;
        }

        public Criteria andCallbackLevelNotBetween(Integer value1, Integer value2) {
            addCriterion("callback_level not between", value1, value2, "callbackLevel");
            return (Criteria) this;
        }

        public Criteria andCallbackStatusIsNull() {
            addCriterion("callback_status is null");
            return (Criteria) this;
        }

        public Criteria andCallbackStatusIsNotNull() {
            addCriterion("callback_status is not null");
            return (Criteria) this;
        }

        public Criteria andCallbackStatusEqualTo(Integer value) {
            addCriterion("callback_status =", value, "callbackStatus");
            return (Criteria) this;
        }

        public Criteria andCallbackStatusNotEqualTo(Integer value) {
            addCriterion("callback_status <>", value, "callbackStatus");
            return (Criteria) this;
        }

        public Criteria andCallbackStatusGreaterThan(Integer value) {
            addCriterion("callback_status >", value, "callbackStatus");
            return (Criteria) this;
        }

        public Criteria andCallbackStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("callback_status >=", value, "callbackStatus");
            return (Criteria) this;
        }

        public Criteria andCallbackStatusLessThan(Integer value) {
            addCriterion("callback_status <", value, "callbackStatus");
            return (Criteria) this;
        }

        public Criteria andCallbackStatusLessThanOrEqualTo(Integer value) {
            addCriterion("callback_status <=", value, "callbackStatus");
            return (Criteria) this;
        }

        public Criteria andCallbackStatusIn(List<Integer> values) {
            addCriterion("callback_status in", values, "callbackStatus");
            return (Criteria) this;
        }

        public Criteria andCallbackStatusNotIn(List<Integer> values) {
            addCriterion("callback_status not in", values, "callbackStatus");
            return (Criteria) this;
        }

        public Criteria andCallbackStatusBetween(Integer value1, Integer value2) {
            addCriterion("callback_status between", value1, value2, "callbackStatus");
            return (Criteria) this;
        }

        public Criteria andCallbackStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("callback_status not between", value1, value2, "callbackStatus");
            return (Criteria) this;
        }

        public Criteria andRefIdIsNull() {
            addCriterion("ref_id is null");
            return (Criteria) this;
        }

        public Criteria andRefIdIsNotNull() {
            addCriterion("ref_id is not null");
            return (Criteria) this;
        }

        public Criteria andRefIdEqualTo(Integer value) {
            addCriterion("ref_id =", value, "refId");
            return (Criteria) this;
        }

        public Criteria andRefIdNotEqualTo(Integer value) {
            addCriterion("ref_id <>", value, "refId");
            return (Criteria) this;
        }

        public Criteria andRefIdGreaterThan(Integer value) {
            addCriterion("ref_id >", value, "refId");
            return (Criteria) this;
        }

        public Criteria andRefIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("ref_id >=", value, "refId");
            return (Criteria) this;
        }

        public Criteria andRefIdLessThan(Integer value) {
            addCriterion("ref_id <", value, "refId");
            return (Criteria) this;
        }

        public Criteria andRefIdLessThanOrEqualTo(Integer value) {
            addCriterion("ref_id <=", value, "refId");
            return (Criteria) this;
        }

        public Criteria andRefIdIn(List<Integer> values) {
            addCriterion("ref_id in", values, "refId");
            return (Criteria) this;
        }

        public Criteria andRefIdNotIn(List<Integer> values) {
            addCriterion("ref_id not in", values, "refId");
            return (Criteria) this;
        }

        public Criteria andRefIdBetween(Integer value1, Integer value2) {
            addCriterion("ref_id between", value1, value2, "refId");
            return (Criteria) this;
        }

        public Criteria andRefIdNotBetween(Integer value1, Integer value2) {
            addCriterion("ref_id not between", value1, value2, "refId");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table t_future_crawler
     *
     * @mbggenerated do_not_delete_during_merge Wed Feb 13 16:38:32 CST 2019
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table t_future_crawler
     *
     * @mbggenerated Wed Feb 13 16:38:32 CST 2019
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}