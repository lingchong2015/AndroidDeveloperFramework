package com.curry.stephen.lcandroidlib.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

/**
 * Created by Administrator on 2017/2/17.
 */

public class MD5Helper {

    private static final String HEX_NUMS_STRING = "0123456789ABCDEF";
    private static final int SALT_LENGTH = 12;

    /**
     * 将16进制字符串转换成字节数组.
     */
    private static byte[] hexStringToByte(String hex) {
        int len = (hex.length() / 2);
        byte[] result = new byte[len];
        char[] hexChars = hex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (HEX_NUMS_STRING.indexOf(hexChars[pos]) << 4 | HEX_NUMS_STRING.indexOf(hexChars[pos + 1]));
        }
        return result;
    }


    /**
     * 将指定byte数组转换成16进制字符串.
     */
    private static String byteToHexString(byte[] b) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            hexString.append(hex.toUpperCase());
        }
        return hexString.toString();
    }

    /**
     * 验证口令是否合法.
     *
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static boolean validString(String sValidated, String s) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        byte[] pwdInDb = hexStringToByte(s);
        byte[] salt = new byte[SALT_LENGTH];

        System.arraycopy(pwdInDb, 0, salt, 0, SALT_LENGTH);
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(salt);
        md.update(sValidated.getBytes("UTF-8"));
        byte[] digest = md.digest();

        byte[] digestInDb = new byte[pwdInDb.length - SALT_LENGTH];
        System.arraycopy(pwdInDb, SALT_LENGTH, digestInDb, 0, digestInDb.length);
        return Arrays.equals(digest, digestInDb);
    }


    /**
     * 获得加密后的16进制形式口令.
     *
     * @param sEncrypted 密码字符串.
     * @return 返回加密后的16进制字符串.
     */
    public static String getEncryptedString(String sEncrypted) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        SecureRandom random = new SecureRandom();// 定义随机数生成器.
        byte[] salt = new byte[SALT_LENGTH];// 定义盐数组.
        random.nextBytes(salt);// 将随机生成的字节放入盐数组中.

        MessageDigest md = MessageDigest.getInstance("MD5");// 返回实现了MD5算法的消息摘要对象.
        md.update(salt);// 将数据盐放入消息摘要对象.
        md.update(sEncrypted.getBytes("UTF-8"));// 将密码放入消息摘要对象.
        byte[] digest = md.digest();// 执行哈希计算.

        byte[] pwd = new byte[digest.length + SALT_LENGTH];// 将哈希值与盐放入最终的字节数组中.
        System.arraycopy(salt, 0, pwd, 0, SALT_LENGTH);// 将盐的字节数组复制到最终字节数组的前12个字节中.
        System.arraycopy(digest, 0, pwd, SALT_LENGTH, digest.length);//将消息摘要对象的结果复制到最终字节数组中.
        return byteToHexString(pwd);
    }
}
