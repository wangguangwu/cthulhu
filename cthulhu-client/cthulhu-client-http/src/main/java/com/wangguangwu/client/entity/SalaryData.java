package com.wangguangwu.client.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * Parse responseBody and get the salary message.
 *
 * @author wangguangwu
 */
@Getter
@Setter
public class SalaryData {

    private String name;

    private String salary;

    private String description;

    @Override
    public String toString() {
        return "SalaryData{" +
                "name='" + name + '\'' +
                ", salary='" + salary + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
