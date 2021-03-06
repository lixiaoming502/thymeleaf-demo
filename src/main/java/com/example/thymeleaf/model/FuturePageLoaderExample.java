package com.example.thymeleaf.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FuturePageLoaderExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_future_page_loader
     *
     * @mbggenerated Mon Jul 23 11:10:52 CST 2018
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_future_page_loader
     *
     * @mbggenerated Mon Jul 23 11:10:52 CST 2018
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_future_page_loader
     *
     * @mbggenerated Mon Jul 23 11:10:52 CST 2018
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_future_page_loader
     *
     * @mbggenerated Mon Jul 23 11:10:52 CST 2018
     */
    public FuturePageLoaderExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_future_page_loader
     *
     * @mbggenerated Mon Jul 23 11:10:52 CST 2018
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_future_page_loader
     *
     * @mbggenerated Mon Jul 23 11:10:52 CST 2018
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_future_page_loader
     *
     * @mbggenerated Mon Jul 23 11:10:52 CST 2018
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_future_page_loader
     *
     * @mbggenerated Mon Jul 23 11:10:52 CST 2018
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_future_page_loader
     *
     * @mbggenerated Mon Jul 23 11:10:52 CST 2018
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_future_page_loader
     *
     * @mbggenerated Mon Jul 23 11:10:52 CST 2018
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_future_page_loader
     *
     * @mbggenerated Mon Jul 23 11:10:52 CST 2018
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_future_page_loader
     *
     * @mbggenerated Mon Jul 23 11:10:52 CST 2018
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
     * This method corresponds to the database table t_future_page_loader
     *
     * @mbggenerated Mon Jul 23 11:10:52 CST 2018
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_future_page_loader
     *
     * @mbggenerated Mon Jul 23 11:10:52 CST 2018
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table t_future_page_loader
     *
     * @mbggenerated Mon Jul 23 11:10:52 CST 2018
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

        public Criteria andCrawlerIdIsNull() {
            addCriterion("crawler_id is null");
            return (Criteria) this;
        }

        public Criteria andCrawlerIdIsNotNull() {
            addCriterion("crawler_id is not null");
            return (Criteria) this;
        }

        public Criteria andCrawlerIdEqualTo(Integer value) {
            addCriterion("crawler_id =", value, "crawlerId");
            return (Criteria) this;
        }

        public Criteria andCrawlerIdNotEqualTo(Integer value) {
            addCriterion("crawler_id <>", value, "crawlerId");
            return (Criteria) this;
        }

        public Criteria andCrawlerIdGreaterThan(Integer value) {
            addCriterion("crawler_id >", value, "crawlerId");
            return (Criteria) this;
        }

        public Criteria andCrawlerIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("crawler_id >=", value, "crawlerId");
            return (Criteria) this;
        }

        public Criteria andCrawlerIdLessThan(Integer value) {
            addCriterion("crawler_id <", value, "crawlerId");
            return (Criteria) this;
        }

        public Criteria andCrawlerIdLessThanOrEqualTo(Integer value) {
            addCriterion("crawler_id <=", value, "crawlerId");
            return (Criteria) this;
        }

        public Criteria andCrawlerIdIn(List<Integer> values) {
            addCriterion("crawler_id in", values, "crawlerId");
            return (Criteria) this;
        }

        public Criteria andCrawlerIdNotIn(List<Integer> values) {
            addCriterion("crawler_id not in", values, "crawlerId");
            return (Criteria) this;
        }

        public Criteria andCrawlerIdBetween(Integer value1, Integer value2) {
            addCriterion("crawler_id between", value1, value2, "crawlerId");
            return (Criteria) this;
        }

        public Criteria andCrawlerIdNotBetween(Integer value1, Integer value2) {
            addCriterion("crawler_id not between", value1, value2, "crawlerId");
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

        public Criteria andStateCodeIsNull() {
            addCriterion("state_code is null");
            return (Criteria) this;
        }

        public Criteria andStateCodeIsNotNull() {
            addCriterion("state_code is not null");
            return (Criteria) this;
        }

        public Criteria andStateCodeEqualTo(Integer value) {
            addCriterion("state_code =", value, "stateCode");
            return (Criteria) this;
        }

        public Criteria andStateCodeNotEqualTo(Integer value) {
            addCriterion("state_code <>", value, "stateCode");
            return (Criteria) this;
        }

        public Criteria andStateCodeGreaterThan(Integer value) {
            addCriterion("state_code >", value, "stateCode");
            return (Criteria) this;
        }

        public Criteria andStateCodeGreaterThanOrEqualTo(Integer value) {
            addCriterion("state_code >=", value, "stateCode");
            return (Criteria) this;
        }

        public Criteria andStateCodeLessThan(Integer value) {
            addCriterion("state_code <", value, "stateCode");
            return (Criteria) this;
        }

        public Criteria andStateCodeLessThanOrEqualTo(Integer value) {
            addCriterion("state_code <=", value, "stateCode");
            return (Criteria) this;
        }

        public Criteria andStateCodeIn(List<Integer> values) {
            addCriterion("state_code in", values, "stateCode");
            return (Criteria) this;
        }

        public Criteria andStateCodeNotIn(List<Integer> values) {
            addCriterion("state_code not in", values, "stateCode");
            return (Criteria) this;
        }

        public Criteria andStateCodeBetween(Integer value1, Integer value2) {
            addCriterion("state_code between", value1, value2, "stateCode");
            return (Criteria) this;
        }

        public Criteria andStateCodeNotBetween(Integer value1, Integer value2) {
            addCriterion("state_code not between", value1, value2, "stateCode");
            return (Criteria) this;
        }

        public Criteria andLoaderStateIsNull() {
            addCriterion("loader_state is null");
            return (Criteria) this;
        }

        public Criteria andLoaderStateIsNotNull() {
            addCriterion("loader_state is not null");
            return (Criteria) this;
        }

        public Criteria andLoaderStateEqualTo(String value) {
            addCriterion("loader_state =", value, "loaderState");
            return (Criteria) this;
        }

        public Criteria andLoaderStateNotEqualTo(String value) {
            addCriterion("loader_state <>", value, "loaderState");
            return (Criteria) this;
        }

        public Criteria andLoaderStateGreaterThan(String value) {
            addCriterion("loader_state >", value, "loaderState");
            return (Criteria) this;
        }

        public Criteria andLoaderStateGreaterThanOrEqualTo(String value) {
            addCriterion("loader_state >=", value, "loaderState");
            return (Criteria) this;
        }

        public Criteria andLoaderStateLessThan(String value) {
            addCriterion("loader_state <", value, "loaderState");
            return (Criteria) this;
        }

        public Criteria andLoaderStateLessThanOrEqualTo(String value) {
            addCriterion("loader_state <=", value, "loaderState");
            return (Criteria) this;
        }

        public Criteria andLoaderStateLike(String value) {
            addCriterion("loader_state like", value, "loaderState");
            return (Criteria) this;
        }

        public Criteria andLoaderStateNotLike(String value) {
            addCriterion("loader_state not like", value, "loaderState");
            return (Criteria) this;
        }

        public Criteria andLoaderStateIn(List<String> values) {
            addCriterion("loader_state in", values, "loaderState");
            return (Criteria) this;
        }

        public Criteria andLoaderStateNotIn(List<String> values) {
            addCriterion("loader_state not in", values, "loaderState");
            return (Criteria) this;
        }

        public Criteria andLoaderStateBetween(String value1, String value2) {
            addCriterion("loader_state between", value1, value2, "loaderState");
            return (Criteria) this;
        }

        public Criteria andLoaderStateNotBetween(String value1, String value2) {
            addCriterion("loader_state not between", value1, value2, "loaderState");
            return (Criteria) this;
        }

        public Criteria andPanedIsNull() {
            addCriterion("paned is null");
            return (Criteria) this;
        }

        public Criteria andPanedIsNotNull() {
            addCriterion("paned is not null");
            return (Criteria) this;
        }

        public Criteria andPanedEqualTo(String value) {
            addCriterion("paned =", value, "paned");
            return (Criteria) this;
        }

        public Criteria andPanedNotEqualTo(String value) {
            addCriterion("paned <>", value, "paned");
            return (Criteria) this;
        }

        public Criteria andPanedGreaterThan(String value) {
            addCriterion("paned >", value, "paned");
            return (Criteria) this;
        }

        public Criteria andPanedGreaterThanOrEqualTo(String value) {
            addCriterion("paned >=", value, "paned");
            return (Criteria) this;
        }

        public Criteria andPanedLessThan(String value) {
            addCriterion("paned <", value, "paned");
            return (Criteria) this;
        }

        public Criteria andPanedLessThanOrEqualTo(String value) {
            addCriterion("paned <=", value, "paned");
            return (Criteria) this;
        }

        public Criteria andPanedLike(String value) {
            addCriterion("paned like", value, "paned");
            return (Criteria) this;
        }

        public Criteria andPanedNotLike(String value) {
            addCriterion("paned not like", value, "paned");
            return (Criteria) this;
        }

        public Criteria andPanedIn(List<String> values) {
            addCriterion("paned in", values, "paned");
            return (Criteria) this;
        }

        public Criteria andPanedNotIn(List<String> values) {
            addCriterion("paned not in", values, "paned");
            return (Criteria) this;
        }

        public Criteria andPanedBetween(String value1, String value2) {
            addCriterion("paned between", value1, value2, "paned");
            return (Criteria) this;
        }

        public Criteria andPanedNotBetween(String value1, String value2) {
            addCriterion("paned not between", value1, value2, "paned");
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

        public Criteria andPlanTimeIsNull() {
            addCriterion("plan_time is null");
            return (Criteria) this;
        }

        public Criteria andPlanTimeIsNotNull() {
            addCriterion("plan_time is not null");
            return (Criteria) this;
        }

        public Criteria andPlanTimeEqualTo(Date value) {
            addCriterion("plan_time =", value, "planTime");
            return (Criteria) this;
        }

        public Criteria andPlanTimeNotEqualTo(Date value) {
            addCriterion("plan_time <>", value, "planTime");
            return (Criteria) this;
        }

        public Criteria andPlanTimeGreaterThan(Date value) {
            addCriterion("plan_time >", value, "planTime");
            return (Criteria) this;
        }

        public Criteria andPlanTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("plan_time >=", value, "planTime");
            return (Criteria) this;
        }

        public Criteria andPlanTimeLessThan(Date value) {
            addCriterion("plan_time <", value, "planTime");
            return (Criteria) this;
        }

        public Criteria andPlanTimeLessThanOrEqualTo(Date value) {
            addCriterion("plan_time <=", value, "planTime");
            return (Criteria) this;
        }

        public Criteria andPlanTimeIn(List<Date> values) {
            addCriterion("plan_time in", values, "planTime");
            return (Criteria) this;
        }

        public Criteria andPlanTimeNotIn(List<Date> values) {
            addCriterion("plan_time not in", values, "planTime");
            return (Criteria) this;
        }

        public Criteria andPlanTimeBetween(Date value1, Date value2) {
            addCriterion("plan_time between", value1, value2, "planTime");
            return (Criteria) this;
        }

        public Criteria andPlanTimeNotBetween(Date value1, Date value2) {
            addCriterion("plan_time not between", value1, value2, "planTime");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table t_future_page_loader
     *
     * @mbggenerated do_not_delete_during_merge Mon Jul 23 11:10:52 CST 2018
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table t_future_page_loader
     *
     * @mbggenerated Mon Jul 23 11:10:52 CST 2018
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