package com.wangguangwu.mapper;

import com.wangguangwu.entity.Job;
import com.wangguangwu.entity.JobExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface JobMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table job
     *
     * @mbg.generated Sun Mar 13 10:37:18 CST 2022
     */
    long countByExample(JobExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table job
     *
     * @mbg.generated Sun Mar 13 10:37:18 CST 2022
     */
    int deleteByExample(JobExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table job
     *
     * @mbg.generated Sun Mar 13 10:37:18 CST 2022
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table job
     *
     * @mbg.generated Sun Mar 13 10:37:18 CST 2022
     */
    int insert(Job row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table job
     *
     * @mbg.generated Sun Mar 13 10:37:18 CST 2022
     */
    int insertSelective(Job row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table job
     *
     * @mbg.generated Sun Mar 13 10:37:18 CST 2022
     */
    List<Job> selectByExample(JobExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table job
     *
     * @mbg.generated Sun Mar 13 10:37:18 CST 2022
     */
    Job selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table job
     *
     * @mbg.generated Sun Mar 13 10:37:18 CST 2022
     */
    int updateByExampleSelective(@Param("row") Job row, @Param("example") JobExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table job
     *
     * @mbg.generated Sun Mar 13 10:37:18 CST 2022
     */
    int updateByExample(@Param("row") Job row, @Param("example") JobExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table job
     *
     * @mbg.generated Sun Mar 13 10:37:18 CST 2022
     */
    int updateByPrimaryKeySelective(Job row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table job
     *
     * @mbg.generated Sun Mar 13 10:37:18 CST 2022
     */
    int updateByPrimaryKey(Job row);
}