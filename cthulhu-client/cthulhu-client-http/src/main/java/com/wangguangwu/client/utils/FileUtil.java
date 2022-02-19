package com.wangguangwu.client.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

import static com.wangguangwu.client.entity.Symbol.POINT;
import static com.wangguangwu.client.entity.Symbol.SLASH;

/**
 * some methods to operate file.
 *
 * @author wangguangwu
 */
@Slf4j
public class FileUtil {

    /**
     * default fileType.
     */
    private static final String DEFAULT_FILETYPE = "txt";

    /**
     * commonly used fileType.
     */
    private static final List<String> FILETYPE_LIST =
            List.of("txt", "doc", "docx", "png", "jpg");

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
            log.error("FileUtil saveData2File error: \r\n", e);
        }
    }

    /**
     * determine whether the file exists
     * create the file if it does not exist.
     *
     * @param fileName name of the file, such as "helloWorld" and "HelloWorld.txt"
     */
    public static String createFile(String fileName) {
        // file type
        String fileType = fileName.indexOf(POINT) != -1
                ? getFileType(fileName) : DEFAULT_FILETYPE;
        // folderPath
        String folderPath = Objects.requireNonNull(FileUtil.class.getResource(SLASH))
                .getPath() + "export";
        // folder
        createFolder(folderPath);
        // filePath
        String filePath = folderPath + File.separator + fileName + POINT + fileType;
        File file = new File(filePath);
        if (file.exists() && file.exists()) {
            return filePath;
        }
        try {
            if (file.createNewFile()) {
                log.info("create file: {}", filePath);
            }
        } catch (IOException e) {
            log.error("FileUtil createFile error: \r\n", e);
        }
        return filePath;
    }

    /**
     * get fileType.
     *
     * @param fileName name of the file
     * @return fileType
     */
    private static String getFileType(String fileName) {
        String fileType = fileName.substring(fileName.lastIndexOf(POINT) + 1);
        return FILETYPE_LIST.contains(fileType) ? fileType : DEFAULT_FILETYPE;
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
