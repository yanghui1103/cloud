package com.bw.fit.pc.sys.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.bw.fit.pc.sys.model.RbackException;
import com.bw.fit.pc.sys.service.CommonService;
import com.bw.fit.pc.sys.util.PubFun;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
@Api("API网关工程通用接口")
@Controller
@EnableEurekaClient
public class ApiController {
    private  static Logger logger = LoggerFactory.getLogger(ApiController.class);
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private Environment env;
    @Autowired
    private CommonService commonService;


    @ApiOperation(value = "根据微服务名称获取Url" )
    @GetMapping("getMicroServiceUrl/{serviceName}")
    @ResponseBody
    public String getMicroServiceUrl(@PathVariable String serviceName){
        String url = env.getProperty("zuul.routes." + serviceName + ".url").toString();
        return  url!=null?url:"";
    }

    @ApiOperation(value = "远程调用微服务的接口" )
    @GetMapping(value="getMicroServiceResult/v1/{serviceName}/{controllerName}/{params}")
    @ResponseBody
    public JSONObject getMicroServiceResultV1(@PathVariable String serviceName,@PathVariable String controllerName,
                                            @PathVariable String params   ){
        JSONObject jsonObject = new JSONObject();
        String[] paramArray = params.split(",");
        StringBuffer stringBuffer = new StringBuffer();
        if(paramArray !=null ){
            for(int i=0;i<paramArray.length;i++){
                stringBuffer.append(paramArray[i]);
                if(i!=paramArray.length-1){
                    stringBuffer.append("/");
                }
            }
            jsonObject = restTemplate.getForObject("http://"+serviceName+"/"+controllerName+"/"+stringBuffer.toString() , JSONObject.class);
        }else{
            PubFun.returnFailJson(jsonObject,"抱歉，系统尚未提供无参数方法");
        }
        return jsonObject;
    }

    @ApiOperation("去往单元微服务系统所指定去往的页面，model[mapData]只支持微服务返回一个JSON对象")
    @GetMapping("towardMicroServicePage/v1/{serviceName}/{urlString}/{pageString}")
    public String toward(@PathVariable String serviceName, @PathVariable(value = "urlString",required = true) String urlString,
                         @PathVariable(value = "pageString",required = true) String pageString, Model model){
        JSONObject jsonObject = new JSONObject();
        String[] paramArray = urlString.split(",");
        String[] paramArray2 = pageString.split(",");
        StringBuffer stringBuffer = new StringBuffer();
        if (paramArray != null) {
            for (int i = 0; i < paramArray.length; i++) {
                stringBuffer.append(paramArray[i]);
                if (i != paramArray.length - 1) {
                    stringBuffer.append("/");
                }
            }
            jsonObject = restTemplate.getForObject("http://" + serviceName + "/" + stringBuffer.toString(), JSONObject.class);
            Map map = JSONObject.toJavaObject(jsonObject, Map.class);
            model.addAttribute("mapData", map);
        }
        stringBuffer = new StringBuffer();
        if (paramArray2 != null) {
            for (int i = 0; i < paramArray2.length; i++) {
                stringBuffer.append(paramArray2[i]);
                if (i != paramArray2.length - 1) {
                    stringBuffer.append("/");
                }
            }
        }
        return stringBuffer.toString();
    }

    @ApiOperation("跳转到对应的页面统一方法")
    @GetMapping("gotoIframePage/{path1}/{path2}/{path3}/{path4}/{pageName}/{arg}")
    public  String gotoframe(@PathVariable(value="path1") String path1,@PathVariable(value="path2") String path2,
                             @PathVariable(value="path3") String path3,@PathVariable(value="path4") String path4,
                             @PathVariable(value="pageName") String pageName,@PathVariable(value="arg") String arg,Model model) throws RbackException{
        Session session = PubFun.getCurrentSession();
        if(ObjectUtil.isNotNull(session)){
            String string  = commonService.getCacheValue("session:"+session.getId());
            if(StrUtil.isEmpty(string)){
                throw new RbackException("1","无效会话");
            }
        }

        model.addAttribute("arg",arg);
        return path1+"/"+path2+"/"+path3+"/"+path4+"/"+pageName  ;
    }
}
