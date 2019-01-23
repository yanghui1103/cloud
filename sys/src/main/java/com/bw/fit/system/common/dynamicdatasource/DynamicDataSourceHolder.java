package com.bw.fit.system.common.dynamicdatasource;

import java.util.ArrayList;
import java.util.List;

/*****
 * 使用ThreadLocal技术来记录当前线程中的数据源的key 
 * @author yangh
 *
 */
public class DynamicDataSourceHolder {
    //使用ThreadLocal记录当前线程的数据源key
    private static final ThreadLocal<String> holder = new ThreadLocal<String>();

    /**
     * 设置数据源key
     * @param key
     */
    public static void putDataSourceKey(String key) {
        holder.set(key);
    }

    /**
     * 获取数据源key
     * @return
     */
    public static String getDataSourceKey() {
        return holder.get();
    }

}
