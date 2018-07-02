package com.example.thymeleaf.dao;

import com.example.thymeleaf.model.FuturePageLoader;
import com.example.thymeleaf.model.FuturePageLoaderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FuturePageLoaderMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_future_page_loader
     *
     * @mbggenerated Fri Jun 29 19:09:55 CST 2018
     */
    int countByExample(FuturePageLoaderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_future_page_loader
     *
     * @mbggenerated Fri Jun 29 19:09:55 CST 2018
     */
    int deleteByExample(FuturePageLoaderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_future_page_loader
     *
     * @mbggenerated Fri Jun 29 19:09:55 CST 2018
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_future_page_loader
     *
     * @mbggenerated Fri Jun 29 19:09:55 CST 2018
     */
    int insert(FuturePageLoader record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_future_page_loader
     *
     * @mbggenerated Fri Jun 29 19:09:55 CST 2018
     */
    int insertSelective(FuturePageLoader record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_future_page_loader
     *
     * @mbggenerated Fri Jun 29 19:09:55 CST 2018
     */
    List<FuturePageLoader> selectByExample(FuturePageLoaderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_future_page_loader
     *
     * @mbggenerated Fri Jun 29 19:09:55 CST 2018
     */
    FuturePageLoader selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_future_page_loader
     *
     * @mbggenerated Fri Jun 29 19:09:55 CST 2018
     */
    int updateByExampleSelective(@Param("record") FuturePageLoader record, @Param("example") FuturePageLoaderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_future_page_loader
     *
     * @mbggenerated Fri Jun 29 19:09:55 CST 2018
     */
    int updateByExample(@Param("record") FuturePageLoader record, @Param("example") FuturePageLoaderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_future_page_loader
     *
     * @mbggenerated Fri Jun 29 19:09:55 CST 2018
     */
    int updateByPrimaryKeySelective(FuturePageLoader record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_future_page_loader
     *
     * @mbggenerated Fri Jun 29 19:09:55 CST 2018
     */
    int updateByPrimaryKey(FuturePageLoader record);
}