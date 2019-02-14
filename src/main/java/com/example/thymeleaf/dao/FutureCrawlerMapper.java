package com.example.thymeleaf.dao;

import com.example.thymeleaf.model.FutureCrawler;
import com.example.thymeleaf.model.FutureCrawlerExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FutureCrawlerMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_future_crawler
     *
     * @mbggenerated Wed Feb 13 16:38:32 CST 2019
     */
    int countByExample(FutureCrawlerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_future_crawler
     *
     * @mbggenerated Wed Feb 13 16:38:32 CST 2019
     */
    int deleteByExample(FutureCrawlerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_future_crawler
     *
     * @mbggenerated Wed Feb 13 16:38:32 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_future_crawler
     *
     * @mbggenerated Wed Feb 13 16:38:32 CST 2019
     */
    int insert(FutureCrawler record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_future_crawler
     *
     * @mbggenerated Wed Feb 13 16:38:32 CST 2019
     */
    int insertSelective(FutureCrawler record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_future_crawler
     *
     * @mbggenerated Wed Feb 13 16:38:32 CST 2019
     */
    List<FutureCrawler> selectByExample(FutureCrawlerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_future_crawler
     *
     * @mbggenerated Wed Feb 13 16:38:32 CST 2019
     */
    FutureCrawler selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_future_crawler
     *
     * @mbggenerated Wed Feb 13 16:38:32 CST 2019
     */
    int updateByExampleSelective(@Param("record") FutureCrawler record, @Param("example") FutureCrawlerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_future_crawler
     *
     * @mbggenerated Wed Feb 13 16:38:32 CST 2019
     */
    int updateByExample(@Param("record") FutureCrawler record, @Param("example") FutureCrawlerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_future_crawler
     *
     * @mbggenerated Wed Feb 13 16:38:32 CST 2019
     */
    int updateByPrimaryKeySelective(FutureCrawler record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_future_crawler
     *
     * @mbggenerated Wed Feb 13 16:38:32 CST 2019
     */
    int updateByPrimaryKey(FutureCrawler record);
}