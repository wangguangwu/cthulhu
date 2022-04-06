package com.wangguangwu.filter.service.impl;

import com.wangguangwu.filter.entity.Person;
import com.wangguangwu.filter.service.Criteria;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * @author wangguangwu
 */
@AllArgsConstructor
public class AndCriteria implements Criteria {

    private Criteria criteria;
    private Criteria otherCriteria;

    @Override
    public List<Person> meetCriteria(List<Person> people) {
        List<Person> firstCriteriaPeople = criteria.meetCriteria(people);
        return otherCriteria.meetCriteria(firstCriteriaPeople);
    }
}
