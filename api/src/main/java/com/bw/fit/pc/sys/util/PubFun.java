package com.bw.fit.pc.sys.util;

/**
 * @Description
 * @Author yangh
 * @Date 2018-12-7 19:30
 * @Param ${PARAM}
 * @Return ${RETURN}
 * @VERSION
 */

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import com.alibaba.fastjson.JSONObject;
import com.bw.fit.pc.sys.model.Account;
import com.bw.fit.pc.sys.model.BaseModel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.util.ByteSource;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.servlet.ModelAndView;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


/*****
 * 工具类组件（组件类）
 *
 * @author yangh
 *
 */
public class PubFun {
    private static final double PI = 3.1415926535898;
    private static double EARTH_RADIUS = 6378.137;
    private static Log log = LogFactory.getLog(PubFun.class);

    public static String getSysDate() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    public static String getSysDateM() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(date);
    }

    public static String getTruncSysDate() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    public static boolean checkSessionValidate(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            return true;
        }

        return false;
    }

    public static String getFixLenthString(int strLength) throws Exception {

        Date d = new Date();

        return String.valueOf(d.getTime());
    }

    public static String getMutilLongIntId() {
        return String.valueOf(System.currentTimeMillis());
    }

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    public static double GetDistance(double lat1, double lng1, double lat2,
                                     double lng2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);

        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        return s;
    }

    public static double[] getAround(double lat, double lon, int raidus) {

        Double latitude = lat;
        Double longitude = lon;

        Double degree = (24901 * 1609) / 360.0;
        double raidusMile = raidus;

        Double dpmLat = 1 / degree;
        Double radiusLat = dpmLat * raidusMile;
        Double minLat = latitude - radiusLat;
        Double maxLat = latitude + radiusLat;

        Double mpdLng = degree * Math.cos(latitude * (PI / 180));
        Double dpmLng = 1 / mpdLng;
        Double radiusLng = dpmLng * raidusMile;
        Double minLng = longitude - radiusLng;
        Double maxLng = longitude + radiusLng;
        return new double[] { minLat, minLng, maxLat, maxLng };
    }

    public static ClassPathXmlApplicationContext getSysSpringCtx() {
        ClassPathXmlApplicationContext ctx = null;
        {
            if (ctx == null) {
                ctx = new ClassPathXmlApplicationContext("springAppContext.xml");
            }
        }
        return ctx;
    }

    public static String getFileTypeName(String s) {
        int index = 0;
        for (int i = s.length() - 1; i > 0; i--) {
            if ('.' == (s.charAt(i))) {
                index = i;
                break;
            }
        }
        return (s.substring(index, s.length()));
    }

    public static String getUUID(boolean isContainMLine) {
        String s = UUID.randomUUID().toString();
        if (isContainMLine) {
            return s;
        }
        return s.replace("-", "");
    }

    public static String getUUID() {
        return getUUID(false);
    }


    public static int getPageStartNum(String pageNo, String tatol) {
        log.info(tatol);
        log.info(Integer.valueOf(tatol));
        return Integer.valueOf(pageNo) * Integer.valueOf(tatol);
    }

    public static int getPageEndNum(String pageNo, String tatol) {
        return Integer.valueOf(pageNo) * Integer.valueOf(tatol)
                + Integer.valueOf(tatol);
    }

    public static int getPageTatolNum(int size, int page_size) {
        if (page_size <= 0) {
            return -9;
        }
        int zheng = size / page_size;
        return zheng + (size % page_size > 0 ? 1 : 0);
    }

    public static String getTruncDouleNumber(String s) {
        int i = s.indexOf(".");
        return (s.substring(0, i + 2));
    }

    public static String getTruncDouleNumber(Double s) {
        String s2 = String.valueOf(s);
        int i = s2.indexOf(".");
        return (s2.substring(0, i + 2));
    }

    /**
     * date2比date1多的天数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDays(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if (year1 != year2) // 同一年
        {
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) // 闰年
                {
                    timeDistance += 366;
                } else // 不是闰年
                {
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2 - day1);
        } else // 不同年
        {
            System.out.println("判断day2 - day1 : " + (day2 - day1));
            return day2 - day1;
        }
    }

    /**
     * 这个主要用在查询条件返回时候 去掉-9
     */
    public static String getCleanString(String s) {
        if ("-9".equals(s))
            return "";
        return s;
    }


    public static String getMACAddress(String ip) {
        String str = "";
        String macAddress = "";
        try {
            Process p = Runtime.getRuntime().exec("nbtstat -a " + ip);
            InputStreamReader ir = new InputStreamReader(p.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            for (int i = 1; i < 100; i++) {
                str = input.readLine();
                if (str != null) {
                    // if (str.indexOf("MAC Address") > 1) {
                    if (str.indexOf("MAC") > 1) {
                        macAddress = str.substring(str.indexOf("=") + 2,
                                str.length());
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
        return macAddress;
    }

    /****
     * 前者包含后者
     *
     * @param longString
     * @param str
     * @return
     */
    public static boolean isContains(String longString, String str) {
        if (longString != null && longString.contains(str)) {
            return true;
        }
        return false;
    }

    /** 将Base64 转换为file文件 */
    public static boolean base64ToFile(String base64, String path) {
        byte[] buffer;
        try {
            buffer = Base64.getDecoder().decode(base64);
            FileOutputStream out = new FileOutputStream(path);
            out.write(buffer);
            out.close();
            return true;
        } catch (Exception e) {
            throw new RuntimeException("base64字符串异常或地址异常\n" + e.getMessage());
        }
    }

    /** 将 file 转化为 Base64 */
    public static String fileToBase64(String path) {
        File file = new File(path);
        FileInputStream inputFile;
        try {
            inputFile = new FileInputStream(file);
            byte[] buffer = new byte[(int) file.length()];
            inputFile.read(buffer);
            inputFile.close();
            return Base64.getEncoder().encodeToString(buffer);
        } catch (Exception e) {
            throw new RuntimeException("文件路径无效\n" + e.getMessage());
        }
    }

    /*****
     * 将一个数据扩充长度 只能比原来的长度更长
     *
     * @param oldArray
     * @param newSize
     * @return
     */
    private static Object resizeArray(Object oldArray, int newSize) {
        int oldSize = java.lang.reflect.Array.getLength(oldArray);
        Class elementType = oldArray.getClass().getComponentType();
        Object newArray = java.lang.reflect.Array.newInstance(elementType,
                newSize);
        int preserveLength = Math.min(oldSize, newSize);
        if (preserveLength > 0)
            System.arraycopy(oldArray, 0, newArray, 0, preserveLength);
        return newArray;
    }


    /*****
     * 从后台返回JSON/Xml/String 数据至前台
     */
    public static void returnJson(HttpServletResponse response, String json)
            throws Exception {
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try {
            writer = response.getWriter();
            writer.print(json);

        } catch (IOException e) {
            log.error("response error", e);
        } finally {
            if (writer != null)
                writer.close();
        }
    }

    /********
     * 获取licnece
     */
    public static String getLicneceMiWen(String key) {
        ResourceBundle rb = null;
        try {
            rb = ResourceBundle.getBundle("com/bw/fit/system/common/conf/licence");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return rb.getString(key);
    }


    /****
     * 根据shrio加密方式 得到的密文
     *
     * @param userName
     * @param passwd
     * @param hashalgorithmName
     *            加密方式
     * @param iterations
     *            加密次数
     * @return
     */
    public static String getUserPasswordShiro(String userName, String passwd,
                                              String hashalgorithmName, int iterations) {
        String credentials = passwd;
        System.getProperties();
        Object salt = ByteSource.Util.bytes(PropertiesUtil.getValueByKey("user.pw.slogmm") + userName);
        Object result = new SimpleHash(hashalgorithmName, credentials, salt,
                iterations);
        return result.toString();
    }

    public static void main(String[] args){
        String s = getUserPasswordShiro("admin","123456","MD5",10);
        System.out.println(s);
    }


    /****
     * 填充成功返回JSON
     *
     * @param j
     */
    public static void returnSuccessJson(JSONObject j) {
        j.put("res", "2");
        j.put("msg", "操作成功");
    }

    /****
     * 填充失败/异常返回JSON
     * @param j
     * @param errorMsg
     *            失败消息
     */
    public static void returnFailJson(JSONObject j, String errorMsg) {
        j.put("res", "1");
        j.put("msg", errorMsg);
    }

    /*****
     * 获取当前用户的会话 shrio 方法
     *
     * @return
     */
    public static org.apache.shiro.session.Session getCurrentSession() {
        return SecurityUtils.getSubject().getSession();
    }

    public static JSONObject getCurrentUser(){
        return JSONObject.parseObject(getCurrentSession().getAttribute("currentUser").toString());
    }

    /****
     * 获取当前会话Id
     */
    public static String getCurrentSessionId() {
        try {
            return   SecurityUtils.getSubject().getSession().getAttribute("sessionId").toString();
        } catch (Throwable e) {
            return null;
        }
    }

    public static void fillCommonProptities(BaseModel b, boolean isFillFdid, Session session) {
        if(isFillFdid){
            b.setId(getUUID());
        }
    }

    /****
     * 根据返回标志，查询其含义
     * @param code
     * @return
     */
    public static String getResDesp(String code){
        if("2".equals(code)){
            return "成功";
        }else if("3".equals(code)){
            return "提示";
        }else if("1".equals(code)){
            return "失败";
        }else{
            return "抱歉，未定义";
        }
    }

    public static byte[] strToByteArray(String str) throws Exception {
        if (str == null) {
            return null;
        }
        byte[] byteArray = str.getBytes("ISO-8859-1");
        return byteArray;
    }




    /**
     * 获取用户真实IP地址，不使用request.getRemoteAddr()的原因是有可能用户使用了代理软件方式避免真实IP地址,
     * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值
     *
     * @return ip
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        System.out.println("x-forwarded-for ip: " + ip);
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if( ip.indexOf(",")!=-1 ){
                ip = ip.split(",")[0];
            }
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
            System.out.println("Proxy-Client-IP ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
            System.out.println("WL-Proxy-Client-IP ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
            System.out.println("HTTP_CLIENT_IP ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            System.out.println("HTTP_X_FORWARDED_FOR ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
            System.out.println("X-Real-IP ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            System.out.println("getRemoteAddr ip: " + ip);
        }
        System.out.println("获取客户端ip: " + ip);
        return ip;
    }

}