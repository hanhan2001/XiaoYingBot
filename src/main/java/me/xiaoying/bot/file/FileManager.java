package me.xiaoying.bot.file;

import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.file.Files;
import java.util.Objects;

/**
 * 文件管理
 */
public class FileManager {
    public static void saveResource(File path, String file) {
        try {
            if (path.exists())
                path.mkdirs();

            File file1 = new File(path, file);
            if (file1.exists())
                file1.createNewFile();

            InputStream inputStream = Yaml.class.getClassLoader().getResourceAsStream(file);
            OutputStream outputStream = Files.newOutputStream(file1.toPath());
            int byteWritten = 0;
            int byteCount = 0;
            assert inputStream != null;
            byte[] bytes = new byte[inputStream.available()];
            while ((byteCount = Objects.requireNonNull(inputStream).read(bytes)) != -1) {
                outputStream.write(bytes, byteWritten, byteCount);
                byteWritten += byteCount;
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}