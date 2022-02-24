package com.wangguangwu;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import cn.hutool.core.util.RuntimeUtil;
import cn.hutool.system.SystemUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wangguangwu
 */
@Slf4j
@SpringBootApplication
public class CthulhuCrawApplication {

    public static void main(String[] args) {
        // Kill old process
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
                String[] strs = line.split("\\s+");
                String pid = strs[1];

                if (!pid.equals(myPid)) {
                    log.info("Find old cthulhu: [{}]", line);
                    pidList.add(pid);
                }
            }
        }

        for (String pid : pidList) {
            log.info("Kill process, pid is [{}].", pid);
            RuntimeUtil.exec("kill -9 " + pid);
        }

    }
}
