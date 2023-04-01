package me.xiaoying.bot.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * 工具类 加密
 */
public class EncryptUtil {
    /**
     * base64 加密
     *
     * @param str 被加密内容
     * @return 加密内容
     */
    public static String base64Encrypt(String str) {
        byte[] bytes = str.getBytes();
        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * base64 解密
     * @param str 被解密内容
     * @return 加密内容
     */
    public static String base64Decrypt(String str) {
        byte[] bytes = str.getBytes();
        return new String(Base64.getDecoder().decode(bytes));
    }

    public static String md5Encrypt(String str) {
        StringBuffer hexString = new StringBuffer();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[] hash = md.digest();
            for (int i = 0; i < hash.length; i++) {
                if ((0xff & hash[i]) < 0x10) {
                    hexString.append("0" + Integer.toHexString((0xFF & hash[i])));
                } else {
                    hexString.append(Integer.toHexString(0xFF & hash[i]));
                }
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hexString.toString();
    }
}