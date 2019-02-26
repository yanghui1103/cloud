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

@RequestMapping(value="system",produces = "application/json; charset=utf-8")
@Controller
@EnableEurekaClient
public class SystemController extends BaseController {


}
