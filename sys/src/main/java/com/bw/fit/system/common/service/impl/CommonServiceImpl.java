package com.bw.fit.system.common.service.impl;

import com.bw.fit.system.common.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @Description
 * @Author yangh
 * @Date 2019-1-30 15:39
 * @Param ${PARAM}
 * @Return ${RETURN}
 * @VERSION
 */
@Service(value="commonService")
public class CommonServiceImpl implements CommonService {

    @Autowired
    private RestTemplate restTemplate;
    @Override
    public String getCacheValue(String key) {
        String response =
                restTemplate.getForObject("http://cache-proj/cache/key/" + key,  String.class);
        return response;
    }
}
