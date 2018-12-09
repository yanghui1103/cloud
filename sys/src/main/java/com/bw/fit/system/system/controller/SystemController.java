package com.bw.fit.system.system.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bw.fit.system.common.controller.BaseController;
import com.bw.fit.system.common.util.PubFun;
import com.bw.fit.system.organization.model.Organization;

import static com.bw.fit.system.common.util.PubFun.*;

@RequestMapping("system")
@Controller
@EnableEurekaClient
public class SystemController extends BaseController {

	/**
	 * 功能描述: 页面跳转统一入口
	 *
	 * @param: 第一级路径，第二级路径，页面，参数
	 * @return: 指定页面
	 * @auther: yangh
	 * @date: 2018-12-8 17:29
	 */
	@RequestMapping(value="gotoIframePage/{sessionId}/{path1}/{path2}/{pageName}/{arg}",produces="text/html;charset=UTF-8")
	public String gotoIframePage(@PathVariable(value="sessionId") String sessionId,@PathVariable(value="path1") String path1,@PathVariable(value="path2") String path2,
			@PathVariable(value="pageName") String pageName,@PathVariable(value="arg") String arg,Model model){
		model.addAttribute("arg",arg);
		return path1+"/"+path2+"/"+pageName  ;
	}
}
