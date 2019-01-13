package com.bw.fit.pc.sys.controller;

import  static com.bw.fit.pc.sys.util.PubFun.*;

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
        String loginPage = "bounty/pc/common/base/login";
        String indexPage = "bounty/pc/common/base/home";
        Session session = null ;
        if("".equals(account.getLogName())
         ||"".equals(account.getLogPwd())){
            model.addAttribute("errorMsg","账号或密码不得为空");
            return loginPage;
        }
        // 如果当前客户端有未登出用户则还是去主页
        Account us_first = getCurrentAccount();
        if(us_first!=null||(us_first!=null &&!"".equals(us_first.getId()))){
            return indexPage;
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
        // 手动设置session 过期时间
        session.setTimeout(Integer.valueOf(PropertiesUtil.getValueByKey("api.session.timeout").toString()));
        sessionId = session.getId().toString();
        /****
         * 根据账号获取账户详情
         */
        JSONObject accountJSON = commonService.getOtherAppReturn( env.getProperty("zuul.routes.api-sys.url")+"account/account/"+account.getLogName());
        accountJSON.put("sessionId",sessionId);
        //commonService.setCacheValue(sessionId,accountJSON);
        SecurityUtils.getSubject().getSession().setAttribute("CurrentUser", accountJSON);
        logger.info(sessionId);
        logger.info(accountJSON.toJSONString());
        return  indexPage ;
    }

}
