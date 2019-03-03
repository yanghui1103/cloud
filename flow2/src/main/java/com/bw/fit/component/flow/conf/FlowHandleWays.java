package com.bw.fit.component.flow.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/*****
 * 流程办结方式
 */
@Component
@ConfigurationProperties(prefix = "flow.handleways")
public class FlowHandleWays {
    private List<Map<String,String>> listmap;


    public List<Map<String, String>> getListMap() {
        return listmap;
    }

    public void setListMap(List<Map<String, String>> listMap) {
        this.listmap = listMap;
    }
}
