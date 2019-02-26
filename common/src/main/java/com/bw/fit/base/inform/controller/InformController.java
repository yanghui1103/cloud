package com.bw.fit.base.inform.controller;

import com.bw.fit.base.common.controller.BaseController;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author yangh
 * @Date 2019-2-26 16:39
 * @Param ${PARAM}
 * @Return ${RETURN}
 * @VERSION
 */
@EnableEurekaClient
@RequestMapping("inform")
@RestController
public class InformController extends BaseController {


}
