package com.wangguangwu.filter.service.impl;

import com.wangguangwu.filter.entity.Person;
import com.wangguangwu.filter.service.Criteria;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wangguangwu
 */
public class CriteriaMale implements Criteria {

    @Override
    public List<Person> meetCriteria(List<Person> people) {
        return people.stream().
                filter(person -> "MALE".equalsIgnoreCase(person.getGender()))
                .collect(Collectors.toList());

    }
}
