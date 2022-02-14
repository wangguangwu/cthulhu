package com.wangguangwu.client.utils;

import org.junit.jupiter.api.Test;

import static com.wangguangwu.client.utils.FileUtil.saveData2File;

/**
 * @author wangguangwu
 * @date 2022/2/14 11:36 AM
 * @description FileUtil 测试类
 */
public class Test_FileUtil {

    @Test
    public void test_saveData2File() {
        saveData2File("wang", "txt", "HelloWorld");
    }

}
