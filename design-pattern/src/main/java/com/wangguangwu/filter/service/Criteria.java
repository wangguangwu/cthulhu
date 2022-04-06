package com.wangguangwu.filter.service;

import com.wangguangwu.filter.entity.Person;

import java.util.List;

/**
 * Criteria interface.
 *
 * @author wangguangwu
 */
public interface Criteria {

    /**
     * get people who meet the conditions
     *
     * @param people all people
     * @return people who meet the conditions
     */
    List<Person> meetCriteria(List<Person> people);

}
