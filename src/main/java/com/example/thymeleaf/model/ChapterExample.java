package com.example.thymeleaf.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChapterExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_chapter
     *
     * @mbggenerated Mon Jul 23 11:10:52 CST 2018
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_chapter
     *
     * @mbggenerated Mon Jul 23 11:10:52 CST 2018
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_chapter
     *
     * @mbggenerated Mon Jul 23 11:10:52 CST 2018
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_chapter
     *
     * @mbggenerated Mon Jul 23 11:10:52 CST 2018
     */
    public ChapterExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_chapter
     *
     * @mbggenerated Mon Jul 23 11:10:52 CST 2018
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_chapter
     *
     * @mbggenerated Mon Jul 23 11:10:52 CST 2018
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_chapter
     *
     * @mbggenerated Mon Jul 23 11:10:52 CST 2018
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_chapter
     *
     * @mbggenerated Mon Jul 23 11:10:52 CST 2018
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_chapter
     *
     * @mbggenerated Mon Jul 23 11:10:52 CST 2018
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_chapter
     *
     * @mbggenerated Mon Jul 23 11:10:52 CST 2018
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_chapter
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
     * This method corresponds to the database table t_chapter
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
     * This method corresponds to the database table t_chapter
     *
     * @mbggenerated Mon Jul 23 11:10:52 CST 2018
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_chapter
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
     * This class corresponds to the database table t_chapter
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

        public Criteria andArtileIdIsNull() {
            addCriterion("artile_id is null");
            return (Criteria) this;
        }

        public Criteria andArtileIdIsNotNull() {
            addCriterion("artile_id is not null");
            return (Criteria) this;
        }

        public Criteria andArtileIdEqualTo(Integer value) {
            addCriterion("artile_id =", value, "artileId");
            return (Criteria) this;
        }

        public Criteria andArtileIdNotEqualTo(Integer value) {
            addCriterion("artile_id <>", value, "artileId");
            return (Criteria) this;
        }

        public Criteria andArtileIdGreaterThan(Integer value) {
            addCriterion("artile_id >", value, "artileId");
            return (Criteria) this;
        }

        public Criteria andArtileIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("artile_id >=", value, "artileId");
            return (Criteria) this;
        }

        public Criteria andArtileIdLessThan(Integer value) {
            addCriterion("artile_id <", value, "artileId");
            return (Criteria) this;
        }

        public Criteria andArtileIdLessThanOrEqualTo(Integer value) {
            addCriterion("artile_id <=", value, "artileId");
            return (Criteria) this;
        }

        public Criteria andArtileIdIn(List<Integer> values) {
            addCriterion("artile_id in", values, "artileId");
            return (Criteria) this;
        }

        public Criteria andArtileIdNotIn(List<Integer> values) {
            addCriterion("artile_id not in", values, "artileId");
            return (Criteria) this;
        }

        public Criteria andArtileIdBetween(Integer value1, Integer value2) {
            addCriterion("artile_id between", value1, value2, "artileId");
            return (Criteria) this;
        }

        public Criteria andArtileIdNotBetween(Integer value1, Integer value2) {
            addCriterion("artile_id not between", value1, value2, "artileId");
            return (Criteria) this;
        }

        public Criteria andSeqIdIsNull() {
            addCriterion("seq_id is null");
            return (Criteria) this;
        }

        public Criteria andSeqIdIsNotNull() {
            addCriterion("seq_id is not null");
            return (Criteria) this;
        }

        public Criteria andSeqIdEqualTo(Integer value) {
            addCriterion("seq_id =", value, "seqId");
            return (Criteria) this;
        }

        public Criteria andSeqIdNotEqualTo(Integer value) {
            addCriterion("seq_id <>", value, "seqId");
            return (Criteria) this;
        }

        public Criteria andSeqIdGreaterThan(Integer value) {
            addCriterion("seq_id >", value, "seqId");
            return (Criteria) this;
        }

        public Criteria andSeqIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("seq_id >=", value, "seqId");
            return (Criteria) this;
        }

        public Criteria andSeqIdLessThan(Integer value) {
            addCriterion("seq_id <", value, "seqId");
            return (Criteria) this;
        }

        public Criteria andSeqIdLessThanOrEqualTo(Integer value) {
            addCriterion("seq_id <=", value, "seqId");
            return (Criteria) this;
        }

        public Criteria andSeqIdIn(List<Integer> values) {
            addCriterion("seq_id in", values, "seqId");
            return (Criteria) this;
        }

        public Criteria andSeqIdNotIn(List<Integer> values) {
            addCriterion("seq_id not in", values, "seqId");
            return (Criteria) this;
        }

        public Criteria andSeqIdBetween(Integer value1, Integer value2) {
            addCriterion("seq_id between", value1, value2, "seqId");
            return (Criteria) this;
        }

        public Criteria andSeqIdNotBetween(Integer value1, Integer value2) {
            addCriterion("seq_id not between", value1, value2, "seqId");
            return (Criteria) this;
        }

        public Criteria andTitleIsNull() {
            addCriterion("title is null");
            return (Criteria) this;
        }

        public Criteria andTitleIsNotNull() {
            addCriterion("title is not null");
            return (Criteria) this;
        }

        public Criteria andTitleEqualTo(String value) {
            addCriterion("title =", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotEqualTo(String value) {
            addCriterion("title <>", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThan(String value) {
            addCriterion("title >", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThanOrEqualTo(String value) {
            addCriterion("title >=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThan(String value) {
            addCriterion("title <", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThanOrEqualTo(String value) {
            addCriterion("title <=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLike(String value) {
            addCriterion("title like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotLike(String value) {
            addCriterion("title not like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleIn(List<String> values) {
            addCriterion("title in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotIn(List<String> values) {
            addCriterion("title not in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleBetween(String value1, String value2) {
            addCriterion("title between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotBetween(String value1, String value2) {
            addCriterion("title not between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andUrlIsNull() {
            addCriterion("url is null");
            return (Criteria) this;
        }

        public Criteria andUrlIsNotNull() {
            addCriterion("url is not null");
            return (Criteria) this;
        }

        public Criteria andUrlEqualTo(String value) {
            addCriterion("url =", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotEqualTo(String value) {
            addCriterion("url <>", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlGreaterThan(String value) {
            addCriterion("url >", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlGreaterThanOrEqualTo(String value) {
            addCriterion("url >=", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlLessThan(String value) {
            addCriterion("url <", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlLessThanOrEqualTo(String value) {
            addCriterion("url <=", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlLike(String value) {
            addCriterion("url like", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotLike(String value) {
            addCriterion("url not like", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlIn(List<String> values) {
            addCriterion("url in", values, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotIn(List<String> values) {
            addCriterion("url not in", values, "url");
            return (Criteria) this;
        }

        public Criteria andUrlBetween(String value1, String value2) {
            addCriterion("url between", value1, value2, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotBetween(String value1, String value2) {
            addCriterion("url not between", value1, value2, "url");
            return (Criteria) this;
        }

        public Criteria andLocalUrlIsNull() {
            addCriterion("local_url is null");
            return (Criteria) this;
        }

        public Criteria andLocalUrlIsNotNull() {
            addCriterion("local_url is not null");
            return (Criteria) this;
        }

        public Criteria andLocalUrlEqualTo(String value) {
            addCriterion("local_url =", value, "localUrl");
            return (Criteria) this;
        }

        public Criteria andLocalUrlNotEqualTo(String value) {
            addCriterion("local_url <>", value, "localUrl");
            return (Criteria) this;
        }

        public Criteria andLocalUrlGreaterThan(String value) {
            addCriterion("local_url >", value, "localUrl");
            return (Criteria) this;
        }

        public Criteria andLocalUrlGreaterThanOrEqualTo(String value) {
            addCriterion("local_url >=", value, "localUrl");
            return (Criteria) this;
        }

        public Criteria andLocalUrlLessThan(String value) {
            addCriterion("local_url <", value, "localUrl");
            return (Criteria) this;
        }

        public Criteria andLocalUrlLessThanOrEqualTo(String value) {
            addCriterion("local_url <=", value, "localUrl");
            return (Criteria) this;
        }

        public Criteria andLocalUrlLike(String value) {
            addCriterion("local_url like", value, "localUrl");
            return (Criteria) this;
        }

        public Criteria andLocalUrlNotLike(String value) {
            addCriterion("local_url not like", value, "localUrl");
            return (Criteria) this;
        }

        public Criteria andLocalUrlIn(List<String> values) {
            addCriterion("local_url in", values, "localUrl");
            return (Criteria) this;
        }

        public Criteria andLocalUrlNotIn(List<String> values) {
            addCriterion("local_url not in", values, "localUrl");
            return (Criteria) this;
        }

        public Criteria andLocalUrlBetween(String value1, String value2) {
            addCriterion("local_url between", value1, value2, "localUrl");
            return (Criteria) this;
        }

        public Criteria andLocalUrlNotBetween(String value1, String value2) {
            addCriterion("local_url not between", value1, value2, "localUrl");
            return (Criteria) this;
        }

        public Criteria andCollectFlagIsNull() {
            addCriterion("collect_flag is null");
            return (Criteria) this;
        }

        public Criteria andCollectFlagIsNotNull() {
            addCriterion("collect_flag is not null");
            return (Criteria) this;
        }

        public Criteria andCollectFlagEqualTo(Boolean value) {
            addCriterion("collect_flag =", value, "collectFlag");
            return (Criteria) this;
        }

        public Criteria andCollectFlagNotEqualTo(Boolean value) {
            addCriterion("collect_flag <>", value, "collectFlag");
            return (Criteria) this;
        }

        public Criteria andCollectFlagGreaterThan(Boolean value) {
            addCriterion("collect_flag >", value, "collectFlag");
            return (Criteria) this;
        }

        public Criteria andCollectFlagGreaterThanOrEqualTo(Boolean value) {
            addCriterion("collect_flag >=", value, "collectFlag");
            return (Criteria) this;
        }

        public Criteria andCollectFlagLessThan(Boolean value) {
            addCriterion("collect_flag <", value, "collectFlag");
            return (Criteria) this;
        }

        public Criteria andCollectFlagLessThanOrEqualTo(Boolean value) {
            addCriterion("collect_flag <=", value, "collectFlag");
            return (Criteria) this;
        }

        public Criteria andCollectFlagIn(List<Boolean> values) {
            addCriterion("collect_flag in", values, "collectFlag");
            return (Criteria) this;
        }

        public Criteria andCollectFlagNotIn(List<Boolean> values) {
            addCriterion("collect_flag not in", values, "collectFlag");
            return (Criteria) this;
        }

        public Criteria andCollectFlagBetween(Boolean value1, Boolean value2) {
            addCriterion("collect_flag between", value1, value2, "collectFlag");
            return (Criteria) this;
        }

        public Criteria andCollectFlagNotBetween(Boolean value1, Boolean value2) {
            addCriterion("collect_flag not between", value1, value2, "collectFlag");
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
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table t_chapter
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
     * This class corresponds to the database table t_chapter
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