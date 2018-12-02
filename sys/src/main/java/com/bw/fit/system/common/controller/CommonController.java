package com.bw.fit.system.common.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@RequestMapping("common")
@Controller
public class CommonController extends BaseController {

    /******
     * 模板：直接指向某个路径下页面
     * @param path1
     * @param path2
     * @param pageName
     * @param arg
     * @param model
     * @return
     */
    @RequestMapping(value="gotoIframe/{path1}/{path2}/{pageName}/{arg}",method = RequestMethod.GET)
    public String gotoIFrame(@PathVariable String path1, @PathVariable String path2, @PathVariable String pageName,
                             @PathVariable String arg, Model model){
        model.addAttribute("arg",arg);
        return path1+"/"+path2+"/"+pageName ;
    }

    /*****
     * 进入主页
     * @return
     */
    @RequestMapping(value = "gotoHome",method = RequestMethod.GET)
    public String gotoHome(@RequestParam(value="account") String account,Model model){
        model.addAttribute("account",account);
        System.out.println("homes");
        JSONObject jsonObject = new JSONObject();
        return "common/base/home";
    }

}
