package com.example.thymeleaf.dao;

import com.example.thymeleaf.model.Chapter;
import com.example.thymeleaf.model.ChapterExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ChapterMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_chapter
     *
     * @mbggenerated Mon Jul 23 11:10:52 CST 2018
     */
    int countByExample(ChapterExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_chapter
     *
     * @mbggenerated Mon Jul 23 11:10:52 CST 2018
     */
    int deleteByExample(ChapterExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_chapter
     *
     * @mbggenerated Mon Jul 23 11:10:52 CST 2018
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_chapter
     *
     * @mbggenerated Mon Jul 23 11:10:52 CST 2018
     */
    int insert(Chapter record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_chapter
     *
     * @mbggenerated Mon Jul 23 11:10:52 CST 2018
     */
    int insertSelective(Chapter record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_chapter
     *
     * @mbggenerated Mon Jul 23 11:10:52 CST 2018
     */
    List<Chapter> selectByExample(ChapterExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_chapter
     *
     * @mbggenerated Mon Jul 23 11:10:52 CST 2018
     */
    Chapter selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_chapter
     *
     * @mbggenerated Mon Jul 23 11:10:52 CST 2018
     */
    int updateByExampleSelective(@Param("record") Chapter record, @Param("example") ChapterExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_chapter
     *
     * @mbggenerated Mon Jul 23 11:10:52 CST 2018
     */
    int updateByExample(@Param("record") Chapter record, @Param("example") ChapterExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_chapter
     *
     * @mbggenerated Mon Jul 23 11:10:52 CST 2018
     */
    int updateByPrimaryKeySelective(Chapter record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_chapter
     *
     * @mbggenerated Mon Jul 23 11:10:52 CST 2018
     */
    int updateByPrimaryKey(Chapter record);
}