package com.wangguangwu.client.utils;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static com.wangguangwu.client.entity.Symbol.*;

/**
 * some methods to operate file.
 *
 * @author wangguangwu
 */
@Slf4j
@Data
public class FileUtil {

    /**
     * default fileType.
     */
    private static String defaultFiletype = ".txt";

    /**
     * save data to file.
     *
     * @param fileName name of the file
     * @param data     data of the file
     */
    public static void saveData2File(String fileName, String data) {
        // filePath
        String filePath = createFile(fileName);

        try (BufferedWriter writer
                     = new BufferedWriter(new OutputStreamWriter
                (new FileOutputStream(filePath, false), StandardCharsets.UTF_8))) {
            // write data to the file
            writer.write(data);
            log.info("write data to file: {}", filePath);
        } catch (IOException e) {
            log.error("FileUtil saveData2File error: ", e);
        }
    }

    /**
     * determine whether the file exists
     * create the file if it does not exist.
     *
     * @param fileName name of the file, such as "helloWorld" and "HelloWorld.txt"
     */
    public static String createFile(String fileName) {
        // zhipin/robots.txt
        String tempFolder = fileName.contains(SLASH)
                ? fileName.substring(0, fileName.lastIndexOf(SLASH)) : BLANK;
        fileName = fileName.substring(fileName.indexOf(tempFolder) + tempFolder.length() + 1);
        fileName = fileName.indexOf(POINT) > 0 ? fileName : "root" + fileName;
        // file type
        String fileType = fileName.indexOf(POINT) != -1
                ? BLANK : defaultFiletype;
        // folderPath
        String folderPath = Objects.requireNonNull(FileUtil.class.getResource(SLASH))
                .getPath() + "export" + SLASH + tempFolder;
        // folder
        createFolder(folderPath);
        // filePath
        String filePath = folderPath + File.separator + fileName + fileType;
        File file = new File(filePath);
        if (file.exists() && file.exists()) {
            return filePath;
        }
        try {
            if (file.createNewFile()) {
                log.info("create file: {}", filePath);
            }
        } catch (IOException e) {
            log.error("FileUtil createFile error: ", e);
        }
        return filePath;
    }

    /**
     * determine whether the folder exists
     * create the folder if it does not exist.
     *
     * @param path path of the folder
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
