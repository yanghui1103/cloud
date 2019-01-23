package com.bw.fit.system.common.conf;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description
 * @Author yangh
 * @Date 2019-1-23 10:50
 * @Param ${PARAM}
 * @Return ${RETURN}
 * @VERSION
 */
@Configuration
public class DynamicDataSource {

    @ConfigurationProperties(prefix = "custom.datasource.ds1")
    @Bean(name="datasource1")
    public DruidDataSource getDruidDataSource(){
        return new DruidDataSource();
    }

    @ConfigurationProperties(prefix = "custom.datasource.ds2")
    @Bean(name="datasource2")
    public DruidDataSource getDruidDataSource2(){
        return new DruidDataSource();
    }
}
