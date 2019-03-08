package com.bw.fit.component.flow.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bw.fit.pc.sys.service.CommonService;
import com.bw.fit.pc.sys.util.PubFun;
import com.bw.fit.pc.sys.util.RestTemplateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

@Api("流程服务-接口API")
@Controller
@EnableEurekaClient
public class FlowController {

    @Autowired
    private CommonService commonService;
    @Resource
    RestTemplateUtil restTemplateUtil;

    @ApiOperation("根据流程实例id查看流程详情")
    @GetMapping("flowDetail/pdInstId/{pdinstId}")
    public String flowDetail(@PathVariable String pdinstId, Model model, HttpServletRequest request){

        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        String returnJson = restTemplateUtil.get(request,"http://flow2-proj/flow/historyProcessVariables/"+pdinstId+"/"+"formKey",param);

        String handleHis = restTemplateUtil.get(request,"http://flow2-proj/flow/handleHistorys/"+pdinstId,param);
        if(StrUtil.isNotEmpty(handleHis)){
            model.addAttribute("handleHis",handleHis);
        }

        JSONObject jsonObject = JSONObject.parseObject(returnJson);
        if("2".equals(jsonObject.getString("res"))){
            model.addAttribute("formKey",jsonObject.getString("formKey"));
        }
        model.addAttribute("pdinstId",pdinstId);
        return "flow2/pc/component/flow/flowDetail";
    }

    @ApiOperation("根据流程实例id，任务id查看流程办理页面")
    @GetMapping("flowAudit/pdInstId/{pdInstId}/taskId/{taskId}")
    public String flowAuditPage(@PathVariable String pdInstId,
                                @PathVariable String taskId, Model model, HttpServletRequest request){

        /****
         * 根据任务id，查询可以展示出来的操作：通过/驳回至某节点（系统自动计算出可以驳回去的节点列表）/转办(地址簿)
         */
        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        String returnJson = restTemplateUtil.get(request,"http://flow2-proj/flow/historyProcessVariables/"+pdInstId+"/"+"formKey",param);

        String handleHis = restTemplateUtil.get(request,"http://flow2-proj/flow/handleHistorys/"+pdInstId,param);
        if(StrUtil.isNotEmpty(handleHis)){
            model.addAttribute("handleHis",handleHis);
        }
        JSONObject jsonObject = JSONObject.parseObject(returnJson);
        if("2".equals(jsonObject.getString("res"))){
            model.addAttribute("formKey",jsonObject.getString("formKey"));
        }
        model.addAttribute("pdInstId",pdInstId);
        model.addAttribute("taskId",taskId);
        return "flow2/pc/component/flow/flowAudit";
    }
}
