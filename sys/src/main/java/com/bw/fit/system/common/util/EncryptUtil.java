package com.bw.fit.system.common.util;

import java.io.IOException;  
import java.io.UnsupportedEncodingException;  
import java.security.MessageDigest;  

import javax.crypto.Cipher;  
import javax.crypto.spec.IvParameterSpec;  
import javax.crypto.spec.SecretKeySpec;  

import com.alibaba.fastjson.JSONObject;

  
  
/** 
 * 功能描述 
 * 加密常用类 
 */  
public class EncryptUtil {  
    // 密钥是16位长度的byte[]进行Base64转换后得到的字符串  
    public static String key = "LmMGStGtOpF4xNyvYt54EQ==";  

  
    /** 
     * <li> 
     * 方法名称:TripleDES_CBC_Encrypt</li> <li> 
     * 功能描述: 
     *  
     * <pre> 
     * 经过封装的三重DES/CBC加密算法，如果包含中文，请注意编码。 
     * </pre> 
     *  
     * </li> 
     *  
     * @param sourceBuf 
     *            需要加密内容的字节数组。 
     * @param deskey 
     *            KEY 由24位字节数组通过SecretKeySpec类转换而成。 
     * @param ivParam 
     *            IV偏转向量，由8位字节数组通过IvParameterSpec类转换而成。 
     * @return 加密后的字节数组 
     * @throws Exception 
     */  
    public static byte[] TripleDES_CBC_Encrypt(byte[] sourceBuf,  
            SecretKeySpec deskey, IvParameterSpec ivParam) throws Exception {  
        byte[] cipherByte;  
        // 使用DES对称加密算法的CBC模式加密  
        Cipher encrypt = Cipher.getInstance("TripleDES/CBC/PKCS5Padding");  
  
        encrypt.init(Cipher.ENCRYPT_MODE, deskey, ivParam);  
  
        cipherByte = encrypt.doFinal(sourceBuf, 0, sourceBuf.length);  
        // 返回加密后的字节数组  
        return cipherByte;  
    }  
  
    /** 
     * <li> 
     * 方法名称:TripleDES_CBC_Decrypt</li> <li> 
     * 功能描述: 
     *  
     * <pre> 
     * 经过封装的三重DES / CBC解密算法 
     * </pre> 
     *  
     * </li> 
     *  
     * @param sourceBuf 
     *            需要解密内容的字节数组 
     * @param deskey 
     *            KEY 由24位字节数组通过SecretKeySpec类转换而成。 
     * @param ivParam 
     *            IV偏转向量，由6位字节数组通过IvParameterSpec类转换而成。 
     * @return 解密后的字节数组 
     * @throws Exception 
     */  
    public static byte[] TripleDES_CBC_Decrypt(byte[] sourceBuf,  
            SecretKeySpec deskey, IvParameterSpec ivParam) throws Exception {  
  
        byte[] cipherByte;  
        // 获得Cipher实例，使用CBC模式。  
        Cipher decrypt = Cipher.getInstance("TripleDES/CBC/PKCS5Padding");  
        // 初始化加密实例，定义为解密功能，并传入密钥，偏转向量  
        decrypt.init(Cipher.DECRYPT_MODE, deskey, ivParam);  
  
        cipherByte = decrypt.doFinal(sourceBuf, 0, sourceBuf.length);  
        // 返回解密后的字节数组  
        return cipherByte;  
    }  
  
    /** 
     * <li> 
     * 方法名称:DES_CBC_Encrypt</li> <li> 
     * 功能描述: 
     *  
     * <pre> 
     * 经过封装的DES/CBC加密算法，如果包含中文，请注意编码。 
     * </pre> 
     *  
     * </li> 
     *  
     * @param sourceBuf 
     *            需要加密内容的字节数组。 
     * @param deskey 
     *            KEY 由8位字节数组通过SecretKeySpec类转换而成。 
     * @param ivParam 
     *            IV偏转向量，由8位字节数组通过IvParameterSpec类转换而成。 
     * @return 加密后的字节数组 
     * @throws Exception 
     */  
    public static byte[] DES_CBC_Encrypt(byte[] sourceBuf,  
            SecretKeySpec deskey, IvParameterSpec ivParam) throws Exception {  
        byte[] cipherByte;  
        // 使用DES对称加密算法的CBC模式加密  
        Cipher encrypt = Cipher.getInstance("DES/CBC/PKCS5Padding");  
  
        encrypt.init(Cipher.ENCRYPT_MODE, deskey, ivParam);  
  
        cipherByte = encrypt.doFinal(sourceBuf, 0, sourceBuf.length);  
        // 返回加密后的字节数组  
        return cipherByte;  
    }  
  
    /** 
     * <li> 
     * 方法名称:DES_CBC_Decrypt</li> <li> 
     * 功能描述: 
     *  
     * <pre> 
     * 经过封装的DES/CBC解密算法。 
     * </pre> 
     *  
     * </li> 
     *  
     * @param sourceBuf 
     *            需要解密内容的字节数组 
     * @param deskey 
     *            KEY 由8位字节数组通过SecretKeySpec类转换而成。 
     * @param ivParam 
     *            IV偏转向量，由6位字节数组通过IvParameterSpec类转换而成。 
     * @return 解密后的字节数组 
     * @throws Exception 
     */  
    public static byte[] DES_CBC_Decrypt(byte[] sourceBuf,  
            SecretKeySpec deskey, IvParameterSpec ivParam) throws Exception {  
  
        byte[] cipherByte;  
        // 获得Cipher实例，使用CBC模式。  
        Cipher decrypt = Cipher.getInstance("DES/CBC/PKCS5Padding");  
        // 初始化加密实例，定义为解密功能，并传入密钥，偏转向量  
        decrypt.init(Cipher.DECRYPT_MODE, deskey, ivParam);  
  
        cipherByte = decrypt.doFinal(sourceBuf, 0, sourceBuf.length);  
        // 返回解密后的字节数组  
        return cipherByte;  
    }  
  
    /** 
     * <li> 
     * 方法名称:MD5Hash</li> <li> 
     * 功能描述: 
     *  
     * <pre> 
     * MD5，进行了简单的封装，以适用于加，解密字符串的校验。 
     * </pre> 
     *  
     * </li> 
     *  
     * @param buf 
     *            需要MD5加密字节数组。 
     * @param offset 
     *            加密数据起始位置。 
     * @param length 
     *            需要加密的数组长度。 
     * @return 
     * @throws Exception 
     */  
    public static byte[] MD5Hash(byte[] buf, int offset, int length)  
            throws Exception {  
        MessageDigest md = MessageDigest.getInstance("MD5");  
        md.update(buf, offset, length);  
        return md.digest();  
    }  
  
    /** 
     * <li> 
     * 方法名称:byte2hex</li> <li> 
     * 功能描述: 
     *  
     * <pre> 
     * 字节数组转换为二行制表示 
     * </pre> 
     *  
     * </li> 
     *  
     * @param inStr 
     *            需要转换字节数组。 
     * @return 字节数组的二进制表示。 
     */  
    public static String byte2hex(byte[] inStr) {  
        String stmp;  
        StringBuffer out = new StringBuffer(inStr.length * 2);  
  
        for (int n = 0; n < inStr.length; n++) {  
            // 字节做"与"运算，去除高位置字节 11111111  
            stmp = Integer.toHexString(inStr[n] & 0xFF);  
            if (stmp.length() == 1) {  
                // 如果是0至F的单位字符串，则添加0  
                out.append("0" + stmp);  
            } else {  
                out.append(stmp);  
            }  
        }  
        return out.toString();  
    }  
  
    /** 
     * <li> 
     * 方法名称:addMD5</li> <li> 
     * 功能描述: 
     *  
     * <pre> 
     * MD校验码 组合方法，前16位放MD5Hash码。 把MD5验证码byte[]，加密内容byte[]组合的方法。 
     * </pre> 
     *  
     * </li> 
     *  
     * @param md5Byte 
     *            加密内容的MD5Hash字节数组。 
     * @param bodyByte 
     *            加密内容字节数组 
     * @return 组合后的字节数组，比加密内容长16个字节。 
     */  
    public static byte[] addMD5(byte[] md5Byte, byte[] bodyByte) {  
        int length = bodyByte.length + md5Byte.length;  
        byte[] resutlByte = new byte[length];  
  
        // 前16位放MD5Hash码  
        for (int i = 0; i < length; i++) {  
            if (i < md5Byte.length) {  
                resutlByte[i] = md5Byte[i];  
            } else {  
                resutlByte[i] = bodyByte[i - md5Byte.length];  
            }  
        }  
  
        return resutlByte;  
    }  

}  