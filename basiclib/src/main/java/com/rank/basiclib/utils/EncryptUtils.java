package com.rank.basiclib.utils;

import android.text.TextUtils;
import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.regex.Pattern;

/**
 * <pre>
 *     author: ChenZhaoJun
 *     url  :
 *     time  : 2019/3/14
 *     desc  :
 * </pre>
 */
public class EncryptUtils {

    public static final String ENCRYPT_KEY = "NbE9(KQ8^xk33A9R";

    public static final String ALGORITHM = "AES/ECB/PKCS7Padding";

    /**
     * 生成key
     *
     * @param password
     * @return
     * @throws Exception
     */
    private static byte[] getKeyByte(String password) throws Exception {
        byte[] seed = new byte[24];
        if (!TextUtils.isEmpty(password)) {
            seed = password.getBytes();
        }
        return seed;
    }

    /**
     * 加密
     *
     * @param data
     * @return
     */
    public static String encrypt(String data) {
        String string = null;
        try {
            string = "";
            byte[] keyByte = getKeyByte(ENCRYPT_KEY);
            SecretKeySpec keySpec = new SecretKeySpec(keyByte, "AES"); //生成加密解密需要的Key
            byte[] byteContent = data.getBytes("utf-8");
            Cipher cipher = Cipher.getInstance(ALGORITHM, "BC");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            byte[] result = cipher.doFinal(byteContent);
            string = Base64.encodeToString(result, Base64.NO_WRAP);
//            string = parseByte2HexStr(result);  //转成String
        } catch (Exception e) {
            e.printStackTrace();
        }
        return string;
    }

    /**
     * 解密
     *
     * @param data
     * @return
     */
    public static String decrypt(String data) {
        String string = null;
        try {
            string = "";
            byte[] keyByte = getKeyByte(ENCRYPT_KEY);
            byte[] byteContent = hexString2Bytes(data);  //转成byte
            Cipher cipher = Cipher.getInstance(ALGORITHM, "BC");
            SecretKeySpec keySpec = new SecretKeySpec(keyByte, "AES"); //生成加密解密需要的Key
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            byte[] decoded = cipher.doFinal(byteContent);
            string = new String(decoded);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return string;
    }

    /**
     * 解密
     *
     * @param data
     * @param key
     * @return
     */
    public static String decrypt(String data, String key) {
        try {
            // 判断Key是否正确
            if (key == null) {
                System.out.print("Key为空null");
                return null;
            }

            byte[] raw = key.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] encrypted1 = Base64.decode(data, Base64.DEFAULT);//先用base64解密
            try {
                byte[] original = cipher.doFinal(encrypted1);
                return new String(original, "utf-8");
            } catch (Exception e) {
                System.out.println(e.toString());
                return null;
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

    /**
     * 加密
     *
     * @param data
     * @param key
     * @return
     */
    public static String encrypt(String data, String key) {
        String string = null;
        try {
            string = "";
            byte[] raw = key.getBytes("utf-8");
            SecretKeySpec keySpec = new SecretKeySpec(raw, "AES"); //生成加密解密需要的Key
            byte[] byteContent = data.getBytes("utf-8");
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            byte[] result = cipher.doFinal(byteContent);
            string = Base64.encodeToString(result, Base64.NO_WRAP);
//            string = parseByte2HexStr(result);  //转成String
        } catch (Exception e) {
            e.printStackTrace();
        }
        return string;
    }

    /**
     * 转化为String
     *
     * @param buf
     * @return
     */
    private static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    //判断字符串是否为数字
    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    private static byte[] hexString2Bytes(String hexString) {
        if (isSpace(hexString)) return null;
        int len = hexString.length();
        if (len % 2 != 0) {
            hexString = "0" + hexString;
            len = len + 1;
        }
        char[] hexBytes = hexString.toUpperCase().toCharArray();
        byte[] ret = new byte[len >> 1];
        for (int i = 0; i < len; i += 2) {
            ret[i >> 1] = (byte) (hex2Dec(hexBytes[i]) << 4 | hex2Dec(hexBytes[i + 1]));
        }
        return ret;
    }

    private static boolean isSpace(final String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static int hex2Dec(final char hexChar) {
        if (hexChar >= '0' && hexChar <= '9') {
            return hexChar - '0';
        } else if (hexChar >= 'A' && hexChar <= 'F') {
            return hexChar - 'A' + 10;
        } else {
            throw new IllegalArgumentException();
        }
    }
}
