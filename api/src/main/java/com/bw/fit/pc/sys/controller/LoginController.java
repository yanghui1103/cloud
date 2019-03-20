package com.bw.fit.pc.sys.controller;

import  static com.bw.fit.pc.sys.util.PubFun.*;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.bw.fit.pc.sys.model.Account;
import com.bw.fit.pc.sys.service.CommonService;
import com.bw.fit.pc.sys.util.PropertiesUtil;
import com.bw.fit.pc.sys.util.PubFun;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import sun.misc.Request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@EnableEurekaClient
public class LoginController {
    private  static Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private Environment env;
    @Autowired
    private CommonService commonService;

    @GetMapping("test")
    @ResponseBody
    public String test(){
        Map<String, Object> urlVariables = new HashMap<String, Object>();
        urlVariables.put("account","w33");
        String result = restTemplate.getForObject("https://www.2cto.com/ask/question/6876",
                String.class );
        return  result  ;
    }

    @RequestMapping(value="login",method = {RequestMethod.GET,RequestMethod.POST})
    public String gotoLogin(@ModelAttribute Account account, BindingResult result,
                            HttpServletRequest request, Model model){
        String sessionId = "";
        String loginPage = "sys/pc/common/base/login";
        String indexPage = "sys/pc/common/base/home";
        Session session = null ;
        if("".equals(account.getLogName())
         ||"".equals(account.getLogPwd())){
            model.addAttribute("errorMsg","账号或密码不得为空");
            return loginPage;
        }
        // 如果当前客户会话仍然存在
        sessionId = getCurrentSessionId();
        if(StrUtil.isNotEmpty(sessionId)){
            JSONObject usJson = commonService.getAccount(sessionId);
            if("2".equals(usJson.get("res").toString())){
                model.addAttribute("sessionId",sessionId);
                commonService.expireKey("session:"+sessionId,1800);
                return indexPage;
            }
        }
        if (result.hasErrors()) {
            FieldError error = result.getFieldError();
            model.addAttribute("errorMsg", error.getDefaultMessage());
            return loginPage;
        }

        /**** 开始shiro登录 *****/
        try {
            UsernamePasswordToken token = new UsernamePasswordToken(
                    account.getLogName(), account.getLogPwd());
            Subject currentUser = SecurityUtils.getSubject();
            token.setRememberMe(true);

            currentUser.login(token);
            if (!currentUser.isAuthenticated()) {
                logger.info("=================认证失败==============");
                model.addAttribute("errorMsg", "登录验证失败");
                return loginPage;
            }
            session = currentUser.getSession();
        } catch (AuthenticationException e) {
            e.printStackTrace();
            model.addAttribute("errorMsg", "登录失败,认证拦截");
            return loginPage;
        }
        if (session == null) {
            model.addAttribute("errorMsg", "无效登录");
            return loginPage;
        }
        sessionId = session.getId().toString();
        /****
         * 根据账号获取账户详情
         */
        JSONObject accountJSON =  restTemplate.getForObject("http://sys-proj/account/account/"+account.getLogName(), JSONObject.class);
        accountJSON.put("sessionId",sessionId);
        JSONObject jj = commonService.setCacheValue("session:"+sessionId,accountJSON);
        commonService.expireKey("session:"+sessionId,1800);
        session.setAttribute("sessionId",sessionId);
        session.setAttribute("currentUser",accountJSON.toJSONString());
        session.setAttribute("currentUserMap",JSONObject.toJavaObject(accountJSON, Map.class));
        model.addAttribute("sessionId",sessionId);
        logger.info(sessionId);
        logger.info(accountJSON.toJSONString());
        return  indexPage ;
    }

    @PostMapping("logout")
    public  void logout(){
        SecurityUtils.getSubject().logout();
    }
}
