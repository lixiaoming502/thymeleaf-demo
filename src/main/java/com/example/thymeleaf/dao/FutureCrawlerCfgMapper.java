package com.example.thymeleaf.dao;

import com.example.thymeleaf.model.FutureCrawlerCfg;
import com.example.thymeleaf.model.FutureCrawlerCfgExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FutureCrawlerCfgMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_future_crawler_cfg
     *
     * @mbggenerated Thu Jul 12 19:47:44 CST 2018
     */
    int countByExample(FutureCrawlerCfgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_future_crawler_cfg
     *
     * @mbggenerated Thu Jul 12 19:47:44 CST 2018
     */
    int deleteByExample(FutureCrawlerCfgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_future_crawler_cfg
     *
     * @mbggenerated Thu Jul 12 19:47:44 CST 2018
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_future_crawler_cfg
     *
     * @mbggenerated Thu Jul 12 19:47:44 CST 2018
     */
    int insert(FutureCrawlerCfg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_future_crawler_cfg
     *
     * @mbggenerated Thu Jul 12 19:47:44 CST 2018
     */
    int insertSelective(FutureCrawlerCfg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_future_crawler_cfg
     *
     * @mbggenerated Thu Jul 12 19:47:44 CST 2018
     */
    List<FutureCrawlerCfg> selectByExample(FutureCrawlerCfgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_future_crawler_cfg
     *
     * @mbggenerated Thu Jul 12 19:47:44 CST 2018
     */
    FutureCrawlerCfg selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_future_crawler_cfg
     *
     * @mbggenerated Thu Jul 12 19:47:44 CST 2018
     */
    int updateByExampleSelective(@Param("record") FutureCrawlerCfg record, @Param("example") FutureCrawlerCfgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_future_crawler_cfg
     *
     * @mbggenerated Thu Jul 12 19:47:44 CST 2018
     */
    int updateByExample(@Param("record") FutureCrawlerCfg record, @Param("example") FutureCrawlerCfgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_future_crawler_cfg
     *
     * @mbggenerated Thu Jul 12 19:47:44 CST 2018
     */
    int updateByPrimaryKeySelective(FutureCrawlerCfg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_future_crawler_cfg
     *
     * @mbggenerated Thu Jul 12 19:47:44 CST 2018
     */
    int updateByPrimaryKey(FutureCrawlerCfg record);
}