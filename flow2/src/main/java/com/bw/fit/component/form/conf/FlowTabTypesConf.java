package com.bw.fit.component.form.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author yangh
 * @Date 2019-3-4 16:28
 * @Param ${PARAM}
 * @Return ${RETURN}
 * @VERSION
 */
@Component
@ConfigurationProperties(prefix = "form.tabtypes")
public class FlowTabTypesConf {
    private List<Map<String,String>> listmap;


    public List<Map<String, String>> getListMap() {
        return listmap;
    }

    public void setListMap(List<Map<String, String>> listMap) {
        this.listmap = listMap;
    }

}
