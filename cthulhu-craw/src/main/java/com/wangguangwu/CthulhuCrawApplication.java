package com.wangguangwu;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import cn.hutool.core.util.RuntimeUtil;
import cn.hutool.system.SystemUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wangguangwu
 */
@Slf4j
@MapperScan("com.wangguangwu.mapper")
@SpringBootApplication
public class CthulhuCrawApplication {

    public static void main(String[] args) {
        killSelf();

        SpringApplication.run(CthulhuCrawApplication.class, args);
    }

    public static void killSelf() {
        if (!SystemUtil.getOsInfo().isLinux()) {
            return;
        }

        String myPid = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];

        List<String> pidList = new ArrayList<>();

        final String findProcessLinuxCommand = "ps -ef";
        for (String line : RuntimeUtil.execForLines(findProcessLinuxCommand)) {
            if (line.contains("java") && line.contains("cthulhu") && line.contains(".jar")) {
                String[] split = line.split("\\s+");
                String pid = split[1];

                if (!pid.equals(myPid)) {
                    pidList.add(pid);
                }
            }
        }

        for (String pid : pidList) {
            RuntimeUtil.exec("kill -9 " + pid);
        }
    }
}
