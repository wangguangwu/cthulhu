package com.wangguangwu.client.utils;

import org.junit.jupiter.api.Test;

import static com.wangguangwu.client.utils.FileUtil.saveData2File;

/**
 * FileUtil test class.
 *
 * @author wangguangwu
 * @date 2022/2/14
 */
public class Test_FileUtil {

    @Test
    public void test_saveData2File() {
        saveData2File("wang","HelloWorld");
    }

}
