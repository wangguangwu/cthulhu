package com.wangguangwu.client.entity;

import lombok.Data;

import java.util.List;

/**
 * Parse the robots.txt file.
 *
 * @author wangguangwu
 * @date 2022/2/23
 */
@Data
public class Robot {

    private String userAgentName;

    private List<String> allows;

    private List<String> disAllows;

}
