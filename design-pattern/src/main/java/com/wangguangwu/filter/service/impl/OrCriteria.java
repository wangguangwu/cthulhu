package com.wangguangwu.filter.service.impl;

import com.wangguangwu.filter.entity.Person;
import com.wangguangwu.filter.service.Criteria;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * @author wangguangwu
 */
@AllArgsConstructor
public class OrCriteria implements Criteria {

    private Criteria criteria;
    private Criteria otherCriteria;

    @Override
    public List<Person> meetCriteria(List<Person> people) {
        List<Person> firstCriteriaItems = criteria.meetCriteria(people);
        List<Person> otherCriteriaItems = otherCriteria.meetCriteria(people);
        // weightless union
        otherCriteriaItems.removeAll(firstCriteriaItems);
        firstCriteriaItems.addAll(otherCriteriaItems);
        return firstCriteriaItems;
    }
}
