package com.bw.fit.pc.sys.util;

import java.util.ResourceBundle;

/**
 * @Description
 * @Author yangh
 * @Date 2018-12-7 19:53
 * @Param ${PARAM}
 * @Return ${RETURN}
 * @VERSION
 */
public class PropertiesUtil {
    private static String fileName ="config/appConfig" ;
    public static String getValueByKey(String key){
        ResourceBundle rb=null;
        try {
            rb = ResourceBundle.getBundle(fileName);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return rb.getString(key);
    }

    public static  void main(String[] artgs){
        System.out.println(PropertiesUtil.getValueByKey("user.pw.slogmm"));
    }
}
