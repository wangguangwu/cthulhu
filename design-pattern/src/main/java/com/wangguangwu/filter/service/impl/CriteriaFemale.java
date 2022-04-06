package com.wangguangwu.filter.service.impl;

import com.wangguangwu.filter.entity.Person;
import com.wangguangwu.filter.service.Criteria;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wangguangwu
 */
public class CriteriaFemale implements Criteria {

    @Override
    public List<Person> meetCriteria(List<Person> people) {
        return people.stream()
                .filter(person -> "FEMALE".equalsIgnoreCase(person.getGender()))
                .collect(Collectors.toList());
    }
}
