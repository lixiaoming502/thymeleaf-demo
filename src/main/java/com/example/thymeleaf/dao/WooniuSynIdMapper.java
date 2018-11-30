package com.example.thymeleaf.dao;

import com.example.thymeleaf.model.WooniuSynId;
import com.example.thymeleaf.model.WooniuSynIdExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface WooniuSynIdMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_wooniu_syn_ids
     *
     * @mbggenerated Thu Nov 29 09:58:33 CST 2018
     */
    int countByExample(WooniuSynIdExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_wooniu_syn_ids
     *
     * @mbggenerated Thu Nov 29 09:58:33 CST 2018
     */
    int deleteByExample(WooniuSynIdExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_wooniu_syn_ids
     *
     * @mbggenerated Thu Nov 29 09:58:33 CST 2018
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_wooniu_syn_ids
     *
     * @mbggenerated Thu Nov 29 09:58:33 CST 2018
     */
    int insert(WooniuSynId record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_wooniu_syn_ids
     *
     * @mbggenerated Thu Nov 29 09:58:33 CST 2018
     */
    int insertSelective(WooniuSynId record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_wooniu_syn_ids
     *
     * @mbggenerated Thu Nov 29 09:58:33 CST 2018
     */
    List<WooniuSynId> selectByExample(WooniuSynIdExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_wooniu_syn_ids
     *
     * @mbggenerated Thu Nov 29 09:58:33 CST 2018
     */
    WooniuSynId selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_wooniu_syn_ids
     *
     * @mbggenerated Thu Nov 29 09:58:33 CST 2018
     */
    int updateByExampleSelective(@Param("record") WooniuSynId record, @Param("example") WooniuSynIdExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_wooniu_syn_ids
     *
     * @mbggenerated Thu Nov 29 09:58:33 CST 2018
     */
    int updateByExample(@Param("record") WooniuSynId record, @Param("example") WooniuSynIdExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_wooniu_syn_ids
     *
     * @mbggenerated Thu Nov 29 09:58:33 CST 2018
     */
    int updateByPrimaryKeySelective(WooniuSynId record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_wooniu_syn_ids
     *
     * @mbggenerated Thu Nov 29 09:58:33 CST 2018
     */
    int updateByPrimaryKey(WooniuSynId record);

    @Select("select count(1) as cnt from t_wooniu_syn_ids where DATE_FORMAT(create_time,'%Y%m%d')=#{date} and `status`=1")
    int querySynCountByDate(String date);
}