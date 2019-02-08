package com.bw.fit.component.flow.controller;

import com.bw.fit.pc.sys.service.CommonService;
import com.bw.fit.pc.sys.util.PubFun;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

@Controller
@EnableEurekaClient
public class FlowController {

    @Autowired
    private CommonService commonService;

    @GetMapping("getActivitiProccessImage/{pProcessInstanceId}")
    public void getActivitiProccessImage(@PathVariable String pProcessInstanceId,
            HttpServletResponse response) throws Exception {

    }
}
