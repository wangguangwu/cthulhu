package com.wangguangwu.server.util;

import java.io.*;
import java.util.Objects;

/**
 * Some methods to operate static resource.
 *
 * @author wangguangwu
 */
public class StaticResourceUtil {

    /**
     * get the absolute path of the static resource file.
     *
     * @param path relative path
     * @return absolute path
     */
    public static String getAbsolutePath(String path) {
        String absolutePath =
                Objects.requireNonNull(StaticResourceUtil.class.getResource("/")).getPath();
        return absolutePath.replaceAll("\\\\", "/") + path;
    }

    /**
     * read the static resource file and output through the outputStream.
     *
     * @param file         static resource
     * @param outputStream socketOutputStream
     * @throws IOException IOException
     */
    public static void outputStaticResource(File file, OutputStream outputStream) throws IOException {
        InputStream inputStream = new FileInputStream(file);
        // number of bytes
        int resourceSize = inputStream.available();

        // output response line、header and empty line to outputStream
        outputStream.write(HttpProtocolUtil.getHttp200(resourceSize).getBytes());

        // output response body
        // length of content already read
        long written = 0L;
        // buffer array length
        int byteSize = 1024;
        byte[] bytes = new byte[1024];

        while (written < resourceSize) {
            if (written + byteSize > resourceSize) {
                byteSize = (int) (resourceSize - written);
                bytes = new byte[byteSize];
            }
            if (inputStream.read(bytes) != -1) {
                outputStream.write(bytes);
                outputStream.flush();
            }
            written += byteSize;
        }
    }

}
