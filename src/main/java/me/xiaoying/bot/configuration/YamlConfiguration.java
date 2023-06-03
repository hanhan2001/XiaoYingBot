package me.xiaoying.bot.configuration;

import me.xiaoying.bot.interfaces.FileGetter;
import me.xiaoying.bot.utils.StringUtil;
import me.xiaoying.bot.utils.SystemUtil;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.*;

public class YamlConfiguration implements FileGetter {
    private static String file;
    private static HashMap<Object, Object> properties = new HashMap<>();

    public YamlConfiguration() {

    }

    private Object get(String key) {
        String separator = ".";
        String[] separatorKeys = null;
        if (key.contains(separator))
            separatorKeys = key.split("\\.");
        else
            return properties.get(key);

        Map<String, Map<String, Object>> finalValue = new HashMap<>();
        for (int i = 0; i < separatorKeys.length - 1; i++) {
            if (i == 0) {
                finalValue = (Map) properties.get(separatorKeys[i]);
                continue;
            }
            if (finalValue == null)
                break;

            finalValue = (Map) finalValue.get(separatorKeys[i]);
        }
        return finalValue == null ? null : finalValue.get(separatorKeys[separatorKeys.length - 1]);
    }

    @Override
    public String getString(String key) {
        return Objects.requireNonNull(get(key)).toString();
    }

    @Override
    public List<String> getStringList(String key) {
        return Collections.singletonList(String.valueOf(get(key)));
    }

    @Override
    public int getInt(String key) {
        return Integer.parseInt(String.valueOf(get(key)));
    }

    @Override
    public boolean getBoolean(String key) {
        return Boolean.parseBoolean(String.valueOf(get(key)));
    }

    @Override
    public double getDouble(String key) {
        return Double.parseDouble(String.valueOf(get(key)));
    }

    @Override
    public long getLong(String key) {
        return Long.parseLong(String.valueOf(get(key)));
    }

    public void changeYamlContent(String key, String vault) {
        try {
            if (key.contains(".")) {
                String[] strs = key.split("\\.");
                // 获取更改内容关键节点
                String changeNodeKey = strs[strs.length - 1];

                boolean isFindNode = false;
                FileInputStream fileInputStream = new FileInputStream(file);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, SystemUtil.getSystemEncoding());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuilder content = new StringBuilder();
                String text;
                int getNodeSize = 0;
                int nodesSpaceSize = 0;
                while ((text = bufferedReader.readLine()) != null) {
                    // 检测一级节点
                    if (text.equalsIgnoreCase(strs[getNodeSize] + ":") && !isFindNode)
                        isFindNode = true;
                    if (isFindNode) {
                        String changeText = StringUtil.removeStartingAllSpace(text);
                        if (changeText.startsWith(strs[getNodeSize] + ":") && StringUtil.getStartingSpaceSize(strs[getNodeSize]) >= nodesSpaceSize) {
                            getNodeSize++;
                            nodesSpaceSize++;
                        } else if (text.equalsIgnoreCase(strs[getNodeSize] + ":") && StringUtil.getStartingSpaceSize(strs[getNodeSize]) < nodesSpaceSize)
                            isFindNode = false;

                        if (StringUtil.removeStartingSpace(text).startsWith(changeNodeKey + ":") && getNodeSize == strs.length - (strs.length - 1) && isFindNode) {
                            text = text.replace(StringUtil.removeStartingAllSpace(text), changeNodeKey + ": " + vault);
                            isFindNode = false;
                        }
                    }
                    content.append(text).append("\n");
                }

                FileOutputStream fileOutputStream = new FileOutputStream(file);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, SystemUtil.getSystemEncoding());
                BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
                bufferedWriter.write(content.toString());
                bufferedWriter.close();
                return;
            }

            // 更改单节点内容
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, SystemUtil.getSystemEncoding());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder content = new StringBuilder();
            String text;
            while ((text = bufferedReader.readLine()) != null) {
                if (text.startsWith(key)) {
                    text = text.replace(text, key + ": " + vault);
                }
                content.append(text).append("\n");
            }

            FileOutputStream fileOutputStream = new FileOutputStream(file);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, SystemUtil.getSystemEncoding());
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
            bufferedWriter.write(content.toString());
            bufferedWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取子节点
     *
     * @param key  需要获取子节点的节点
     * @return 子节点列表
     */
    public List<String> getChildrenNode(String key) {
        List<String> allNodes = new ArrayList<>();

        boolean alreadyInList = false;

        int brackets = 0;
        String content = Objects.requireNonNull(this.get(key)).toString();
        content = StringUtil.removeSomeString(content, 0);
        content = StringUtil.removeSomeString(content, content.length() - 1);
        String[] strings = content.split(", ");
        for (String string : strings) {
            if (string.contains("{")) {
                if (brackets <= 0) allNodes.add(StringUtil.removeBehindEqual(string));
                brackets = StringUtil.getKeySize(string, "{", false);
            }

            if (string.contains("}")) {
                brackets = brackets - StringUtil.getKeySize(string, "}", false);
                continue;
            }

            if (brackets <= 0) {
                for (String str : allNodes) {
                    if (str.equalsIgnoreCase(string)) {
                        alreadyInList = true;
                        break;
                    } else if (alreadyInList) {
                        alreadyInList = false;
                        break;
                    }
                }
                if (!alreadyInList) {
                    allNodes.add(StringUtil.removeBehindEqual(string));
                    alreadyInList = false;
                }
            }
        }
        return allNodes;
    }

    /**
     * 获取YAML文本的所有一级节点
     *
     * @return 子节点列表
     */
    public List<String> getChildrenNode() {
        List<String> allNodes = new ArrayList<>();

        boolean alreadyInList = false;

        int brackets = 0;
        Yaml yaml = new Yaml();
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            properties = yaml.loadAs(fileInputStream, HashMap.class);
            String content = String.valueOf(properties);
            content = StringUtil.removeSomeString(content, 0);
            content = StringUtil.removeSomeString(content, content.length() - 1);
            String[] strings = content.split(", ");
            for (String string : strings) {
                if (string.contains("{")) {
                    if (brackets <= 0) allNodes.add(StringUtil.removeBehindEqual(string));
                    brackets = brackets + StringUtil.getKeySize(string, "{", false);
                }

                if (string.contains("[")) {
                    if (brackets <= 0) allNodes.add(StringUtil.removeBehindEqual(string));
                    brackets = brackets + StringUtil.getKeySize(string, "[", false);
                }

                if (string.contains("}")) {
                    brackets = brackets - StringUtil.getKeySize(string, "}", false);
                    continue;
                }

                if (string.contains("]")) {
                    brackets = brackets - StringUtil.getKeySize(string, "]", false);
                    continue;
                }

                if (brackets <= 0) {
                    for (String str : allNodes) {
                        if (str.equalsIgnoreCase(string)) {
                            alreadyInList = true;
                            break;
                        } else if (alreadyInList) {
                            alreadyInList = false;
                            break;
                        }
                    }
                    if (!alreadyInList) {
                        allNodes.add(StringUtil.removeBehindEqual(string));
                        alreadyInList = false;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return allNodes;
    }

    public static YamlConfiguration loadConfiguration(File file) {
        Yaml yaml = new Yaml();
        try (FileInputStream in = new FileInputStream(file)) {
            try {
                YamlConfiguration.file = file.getPath();
                properties = yaml.loadAs(in, HashMap.class);
                properties.replaceAll((k, v) -> String.valueOf(v));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new YamlConfiguration();
    }

    public static YamlConfiguration loadConfiguration(String file) {
        Yaml yaml = new Yaml();
        properties = yaml.loadAs(file, HashMap.class);
        properties.replaceAll((k, v) -> String.valueOf(v));
        return new YamlConfiguration();
    }
}