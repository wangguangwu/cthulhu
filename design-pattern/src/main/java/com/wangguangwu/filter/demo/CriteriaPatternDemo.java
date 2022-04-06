package com.wangguangwu.filter.demo;

import com.wangguangwu.filter.entity.Person;
import com.wangguangwu.filter.service.Criteria;
import com.wangguangwu.filter.service.impl.*;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangguangwu
 */
@Slf4j
public class CriteriaPatternDemo {

    private static final String SINGLE = "Single";

    public static void main(String[] args) {
        List<Person> people = new ArrayList<>();

        people.add(new Person("Robert", "Male", SINGLE));
        people.add(new Person("John", "Male", "Married"));
        people.add(new Person("Laura", "Female", "Married"));
        people.add(new Person("Diana", "Female", SINGLE));
        people.add(new Person("Mike", "Male", SINGLE));
        people.add(new Person("Bobby", "Male", SINGLE));

        Criteria male = new CriteriaMale();
        Criteria female = new CriteriaFemale();
        Criteria single = new CriteriaSingle();
        Criteria singleMale = new AndCriteria(single, male);
        Criteria singleOrFemale = new OrCriteria(single, female);

        log.info("Males: ");
        printPersons(male.meetCriteria(people));

        log.info("Females: ");
        printPersons(female.meetCriteria(people));

        log.info("Single Males: ");
        printPersons(singleMale.meetCriteria(people));

        log.info("Single Or Females: ");
        printPersons(singleOrFemale.meetCriteria(people));
    }

    public static void printPersons(List<Person> persons) {
        for (Person person : persons) {
            log.info("Person : [ Name : " + person.getName()
                    + ", Gender : " + person.getGender()
                    + ", Marital Status : " + person.getMaritalStatus()
                    + " ]");
        }
    }

}


