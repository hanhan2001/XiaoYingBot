package me.xiaoying.bot.utils;

import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

public class YamlUtil {
    String file;

    public YamlUtil(String file) {
        this.file = file;
    }

    private static Map<String, Map<String, Object>> properties = new HashMap<>();



    public Object getValueByKey(String key) {
        Yaml yaml = new Yaml();
        try (FileInputStream in = new FileInputStream(this.file)) {
            try {
                properties = yaml.loadAs(in, HashMap.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String separator = ".";
        String[] separatorKeys = null;
        if (key.contains(separator)) {
            separatorKeys = key.split("\\.");
        } else {
            return properties.get(key);
        }
        Map<String, Map<String, Object>> finalValue = new HashMap<>();
        for (int i = 0; i < separatorKeys.length - 1; i++) {
            if (i == 0) {
                finalValue = (Map) properties.get(separatorKeys[i]);
                continue;
            }
            if (finalValue == null) {
                break;
            }
            finalValue = (Map) finalValue.get(separatorKeys[i]);
        }
        return finalValue == null ? null : finalValue.get(separatorKeys[separatorKeys.length - 1]);
    }


}