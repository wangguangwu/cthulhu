package com.wangguangwu.client.utils;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.internal.StringUtil;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @author wangguangwu
 * @date 2022/2/14 11:18 AM
 * @description 文件工具类
 */
@Slf4j
public class FileUtil {

    public static void saveData2File(String fileName, String fileFormat, String data) {
        String filePath = createFile(fileName, fileFormat);

        try (BufferedWriter writer
                     = new BufferedWriter(new OutputStreamWriter
                (new FileOutputStream(filePath, false), StandardCharsets.UTF_8))) {
            // 往文件中写入数据
            writer.write(data);
            log.info("write data to file: {}", filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 判断文件是否存在，不存在则创建文件
     *
     * @param fileName   文件名
     * @param fileFormat 文件格式
     */
    public static String createFile(String fileName, String fileFormat) {
        if (StringUtil.isBlank(fileFormat)) {
            fileFormat = "txt";
        }
        String folderPath = System.getProperty("user.dir")
                + File.separator + "ragdoll-client/ragdoll-client-http/export";
        // 判断文件夹是否存在
        createFolder(folderPath);
        String filePath = folderPath + File.separator + fileName + "." + fileFormat;
        File file = new File(filePath);
        if (file.exists() && file.exists()) {
            return filePath;
        }
        try {
            if (file.createNewFile()) {
                log.info("create file: {}", filePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filePath;
    }

    /**
     * 创建文件夹
     *
     * @param path 文件夹路径
     */
    public static void createFolder(String path) {
        File file = new File(path);
        if (file.exists() && file.isDirectory()) {
            return;
        }
        if (file.mkdirs()) {
            log.info("create folder: {}", path);
        }
    }
}
