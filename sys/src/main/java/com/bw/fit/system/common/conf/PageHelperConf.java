package com.bw.fit.system.common.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.github.pagehelper.PageHelper;
import java.util.Properties;
/**
 * @Description
 * @Author yangh
 * @Date 2019-1-31 9:07
 * @Param ${PARAM}
 * @Return ${RETURN}
 * @VERSION
 */
@Configuration
public class PageHelperConf {
    @Bean
    public PageHelper getPageHelper(){
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("pageSizeZero","true");
        properties.setProperty("rowBoundsWithCount","true");
        properties.setProperty("reasonable","true");
        pageHelper.setProperties(properties);
        return pageHelper;
    }
}
