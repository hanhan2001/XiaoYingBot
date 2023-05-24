package me.xiaoying.bot.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * 工具类 文件
 */
public class FileUtil {
    /**
     * 文件隐写术
     *
     * @param files 隐写文件
     * @param newName 生成文件
     * @throws Exception 抛出异常
     */
    public static void merge(List<String> files, String newName) throws Exception {
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(Files.newOutputStream(Paths.get(newName)));

        for (String file : files) {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(Files.newInputStream(Paths.get(file)));
            byte[] buf = new byte[bufferedInputStream.available()];
            int bytesRead;
            while ((bytesRead = bufferedInputStream.read(buf)) > 0)
                bufferedOutputStream.write(buf, 0, bytesRead);

            bufferedOutputStream.write("\r\n".getBytes());
            bufferedInputStream.close();
        }

        bufferedOutputStream.close();
    }
}