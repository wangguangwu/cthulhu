package com.wangguangwu.filter.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Person.
 *
 * @author wangguangwu
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {

    private String name;
    private String gender;
    private String maritalStatus;

}
