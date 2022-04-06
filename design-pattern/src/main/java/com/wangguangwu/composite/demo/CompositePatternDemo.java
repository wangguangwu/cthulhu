package com.wangguangwu.composite.demo;

import com.wangguangwu.composite.entity.Employee;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wangguangwu
 */
@Slf4j
public class CompositePatternDemo {

    public static void main(String[] args) {
        Employee ceo = new Employee("John", "CEO", 30000);

        Employee headSales = new Employee("Robert", "Head Sales", 20000);

        Employee headMarketing = new Employee("Michel", "Head Marketing", 20000);

        Employee clerk1 = new Employee("Laura", "Marketing", 10000);
        Employee clerk2 = new Employee("Bob", "Marketing", 10000);

        Employee salesExecutive1 = new Employee("Richard", "Sales", 10000);
        Employee salesExecutive2 = new Employee("Rob", "Sales", 10000);

        ceo.add(headSales);
        ceo.add(headMarketing);

        headSales.add(salesExecutive1);
        headSales.add(salesExecutive2);

        headMarketing.add(clerk1);
        headMarketing.add(clerk2);

        //打印该组织的所有员工
        log.info("ceo: {}", ceo);
        for (Employee headEmployee : ceo.getSubordinates()) {
            log.info("headEmployee: {}", headEmployee);
            for (Employee employee : headEmployee.getSubordinates()) {
                log.info("employee: {}", employee);
            }
        }
    }

}
