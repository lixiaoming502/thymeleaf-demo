package com.example.thymeleaf.dao;

import com.example.thymeleaf.model.Seed;
import com.example.thymeleaf.model.SeedExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SeedMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_seeds
     *
     * @mbggenerated Fri Jun 29 19:09:55 CST 2018
     */
    int countByExample(SeedExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_seeds
     *
     * @mbggenerated Fri Jun 29 19:09:55 CST 2018
     */
    int deleteByExample(SeedExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_seeds
     *
     * @mbggenerated Fri Jun 29 19:09:55 CST 2018
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_seeds
     *
     * @mbggenerated Fri Jun 29 19:09:55 CST 2018
     */
    int insert(Seed record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_seeds
     *
     * @mbggenerated Fri Jun 29 19:09:55 CST 2018
     */
    int insertSelective(Seed record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_seeds
     *
     * @mbggenerated Fri Jun 29 19:09:55 CST 2018
     */
    List<Seed> selectByExample(SeedExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_seeds
     *
     * @mbggenerated Fri Jun 29 19:09:55 CST 2018
     */
    Seed selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_seeds
     *
     * @mbggenerated Fri Jun 29 19:09:55 CST 2018
     */
    int updateByExampleSelective(@Param("record") Seed record, @Param("example") SeedExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_seeds
     *
     * @mbggenerated Fri Jun 29 19:09:55 CST 2018
     */
    int updateByExample(@Param("record") Seed record, @Param("example") SeedExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_seeds
     *
     * @mbggenerated Fri Jun 29 19:09:55 CST 2018
     */
    int updateByPrimaryKeySelective(Seed record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_seeds
     *
     * @mbggenerated Fri Jun 29 19:09:55 CST 2018
     */
    int updateByPrimaryKey(Seed record);
}