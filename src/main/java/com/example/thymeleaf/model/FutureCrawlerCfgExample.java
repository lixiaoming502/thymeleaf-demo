package com.example.thymeleaf.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FutureCrawlerCfgExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_future_crawler_cfg
     *
     * @mbggenerated Fri Jun 29 09:08:42 CST 2018
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_future_crawler_cfg
     *
     * @mbggenerated Fri Jun 29 09:08:42 CST 2018
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_future_crawler_cfg
     *
     * @mbggenerated Fri Jun 29 09:08:42 CST 2018
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_future_crawler_cfg
     *
     * @mbggenerated Fri Jun 29 09:08:42 CST 2018
     */
    public FutureCrawlerCfgExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_future_crawler_cfg
     *
     * @mbggenerated Fri Jun 29 09:08:42 CST 2018
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_future_crawler_cfg
     *
     * @mbggenerated Fri Jun 29 09:08:42 CST 2018
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_future_crawler_cfg
     *
     * @mbggenerated Fri Jun 29 09:08:42 CST 2018
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_future_crawler_cfg
     *
     * @mbggenerated Fri Jun 29 09:08:42 CST 2018
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_future_crawler_cfg
     *
     * @mbggenerated Fri Jun 29 09:08:42 CST 2018
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_future_crawler_cfg
     *
     * @mbggenerated Fri Jun 29 09:08:42 CST 2018
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_future_crawler_cfg
     *
     * @mbggenerated Fri Jun 29 09:08:42 CST 2018
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_future_crawler_cfg
     *
     * @mbggenerated Fri Jun 29 09:08:42 CST 2018
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
     * This method corresponds to the database table t_future_crawler_cfg
     *
     * @mbggenerated Fri Jun 29 09:08:42 CST 2018
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_future_crawler_cfg
     *
     * @mbggenerated Fri Jun 29 09:08:42 CST 2018
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table t_future_crawler_cfg
     *
     * @mbggenerated Fri Jun 29 09:08:42 CST 2018
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

        public Criteria andDomainNameIsNull() {
            addCriterion("domain_name is null");
            return (Criteria) this;
        }

        public Criteria andDomainNameIsNotNull() {
            addCriterion("domain_name is not null");
            return (Criteria) this;
        }

        public Criteria andDomainNameEqualTo(String value) {
            addCriterion("domain_name =", value, "domainName");
            return (Criteria) this;
        }

        public Criteria andDomainNameNotEqualTo(String value) {
            addCriterion("domain_name <>", value, "domainName");
            return (Criteria) this;
        }

        public Criteria andDomainNameGreaterThan(String value) {
            addCriterion("domain_name >", value, "domainName");
            return (Criteria) this;
        }

        public Criteria andDomainNameGreaterThanOrEqualTo(String value) {
            addCriterion("domain_name >=", value, "domainName");
            return (Criteria) this;
        }

        public Criteria andDomainNameLessThan(String value) {
            addCriterion("domain_name <", value, "domainName");
            return (Criteria) this;
        }

        public Criteria andDomainNameLessThanOrEqualTo(String value) {
            addCriterion("domain_name <=", value, "domainName");
            return (Criteria) this;
        }

        public Criteria andDomainNameLike(String value) {
            addCriterion("domain_name like", value, "domainName");
            return (Criteria) this;
        }

        public Criteria andDomainNameNotLike(String value) {
            addCriterion("domain_name not like", value, "domainName");
            return (Criteria) this;
        }

        public Criteria andDomainNameIn(List<String> values) {
            addCriterion("domain_name in", values, "domainName");
            return (Criteria) this;
        }

        public Criteria andDomainNameNotIn(List<String> values) {
            addCriterion("domain_name not in", values, "domainName");
            return (Criteria) this;
        }

        public Criteria andDomainNameBetween(String value1, String value2) {
            addCriterion("domain_name between", value1, value2, "domainName");
            return (Criteria) this;
        }

        public Criteria andDomainNameNotBetween(String value1, String value2) {
            addCriterion("domain_name not between", value1, value2, "domainName");
            return (Criteria) this;
        }

        public Criteria andGapIsNull() {
            addCriterion("gap is null");
            return (Criteria) this;
        }

        public Criteria andGapIsNotNull() {
            addCriterion("gap is not null");
            return (Criteria) this;
        }

        public Criteria andGapEqualTo(Integer value) {
            addCriterion("gap =", value, "gap");
            return (Criteria) this;
        }

        public Criteria andGapNotEqualTo(Integer value) {
            addCriterion("gap <>", value, "gap");
            return (Criteria) this;
        }

        public Criteria andGapGreaterThan(Integer value) {
            addCriterion("gap >", value, "gap");
            return (Criteria) this;
        }

        public Criteria andGapGreaterThanOrEqualTo(Integer value) {
            addCriterion("gap >=", value, "gap");
            return (Criteria) this;
        }

        public Criteria andGapLessThan(Integer value) {
            addCriterion("gap <", value, "gap");
            return (Criteria) this;
        }

        public Criteria andGapLessThanOrEqualTo(Integer value) {
            addCriterion("gap <=", value, "gap");
            return (Criteria) this;
        }

        public Criteria andGapIn(List<Integer> values) {
            addCriterion("gap in", values, "gap");
            return (Criteria) this;
        }

        public Criteria andGapNotIn(List<Integer> values) {
            addCriterion("gap not in", values, "gap");
            return (Criteria) this;
        }

        public Criteria andGapBetween(Integer value1, Integer value2) {
            addCriterion("gap between", value1, value2, "gap");
            return (Criteria) this;
        }

        public Criteria andGapNotBetween(Integer value1, Integer value2) {
            addCriterion("gap not between", value1, value2, "gap");
            return (Criteria) this;
        }

        public Criteria andLastCrawlTimeIsNull() {
            addCriterion("last_crawl_time is null");
            return (Criteria) this;
        }

        public Criteria andLastCrawlTimeIsNotNull() {
            addCriterion("last_crawl_time is not null");
            return (Criteria) this;
        }

        public Criteria andLastCrawlTimeEqualTo(Date value) {
            addCriterion("last_crawl_time =", value, "lastCrawlTime");
            return (Criteria) this;
        }

        public Criteria andLastCrawlTimeNotEqualTo(Date value) {
            addCriterion("last_crawl_time <>", value, "lastCrawlTime");
            return (Criteria) this;
        }

        public Criteria andLastCrawlTimeGreaterThan(Date value) {
            addCriterion("last_crawl_time >", value, "lastCrawlTime");
            return (Criteria) this;
        }

        public Criteria andLastCrawlTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("last_crawl_time >=", value, "lastCrawlTime");
            return (Criteria) this;
        }

        public Criteria andLastCrawlTimeLessThan(Date value) {
            addCriterion("last_crawl_time <", value, "lastCrawlTime");
            return (Criteria) this;
        }

        public Criteria andLastCrawlTimeLessThanOrEqualTo(Date value) {
            addCriterion("last_crawl_time <=", value, "lastCrawlTime");
            return (Criteria) this;
        }

        public Criteria andLastCrawlTimeIn(List<Date> values) {
            addCriterion("last_crawl_time in", values, "lastCrawlTime");
            return (Criteria) this;
        }

        public Criteria andLastCrawlTimeNotIn(List<Date> values) {
            addCriterion("last_crawl_time not in", values, "lastCrawlTime");
            return (Criteria) this;
        }

        public Criteria andLastCrawlTimeBetween(Date value1, Date value2) {
            addCriterion("last_crawl_time between", value1, value2, "lastCrawlTime");
            return (Criteria) this;
        }

        public Criteria andLastCrawlTimeNotBetween(Date value1, Date value2) {
            addCriterion("last_crawl_time not between", value1, value2, "lastCrawlTime");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table t_future_crawler_cfg
     *
     * @mbggenerated do_not_delete_during_merge Fri Jun 29 09:08:42 CST 2018
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table t_future_crawler_cfg
     *
     * @mbggenerated Fri Jun 29 09:08:42 CST 2018
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