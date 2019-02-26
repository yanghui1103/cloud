package com.bw.fit.system.organization.controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import cn.hutool.core.collection.CollectionUtil;
import com.bw.fit.system.common.service.CommonService;
import com.bw.fit.system.common.util.PubFun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bw.fit.system.common.controller.BaseController;
import com.bw.fit.system.common.model.RbackException;

import static com.bw.fit.system.common.util.PubFun.*;

import com.bw.fit.system.organization.mapper.OrganizationMapper;
import com.bw.fit.system.organization.model.Organization;
import com.bw.fit.system.organization.service.OrganizationService;

/*****
 * 组织Controller
 * @author yangh
 *
 */
@RequestMapping(value="org",produces = "application/json; charset=utf-8")
@EnableEurekaClient
@Controller
public class OrganizationController extends BaseController {

	@Autowired
	private OrganizationService organizationService;
	@Resource
	private OrganizationMapper organizationMapper;
	@Autowired
	private CommonService commonService;
	/******
	 * 增加组织
	 * @param org
	 * @param result
	 * @return
	 */
	@RequestMapping(value="organization",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	public JSONObject add(@Valid @ModelAttribute Organization org, BindingResult result, HttpServletRequest httpServletRequest){
		JSONObject json = new JSONObject();
		if (result.hasErrors()) {
			FieldError error = result.getFieldError()                                                                                                                                                    ;
			json.put("res", "1");
			returnFailJson(json, error.getDefaultMessage());
			return json ;
		}
		try {
			commonService.fillCommonProptities(org,httpServletRequest,true);
			org.setId(getUUID());
			json = organizationService.add(org);
		} catch (RbackException e) {
			e.printStackTrace();
			json = new JSONObject();
			returnFailJson(json, e.getMsg());
		}finally{
			return json  ;
		}
	}
	
	/****
	 * 删除组织
	 * @param id
	 * @return
	 */
	@RequestMapping(value="organization/{id}",method=RequestMethod.DELETE,produces="application/json;charset=UTF8")
	@ResponseBody
	public JSONObject delete(@PathVariable String id){
		JSONObject json = new JSONObject();
		try {
			List<Organization> orgs = organizationService.getChildrenAndCurt(id);
			if(CollectionUtil.isNotEmpty(orgs) && orgs.size()>1){ // 因为orgs里包含当前id这个组织
				/***如果有子孙组织***/
				Optional<Organization> forgs = orgs.parallelStream().filter(x->x.getIsDeleted().equals("0")).findAny();
				if(forgs.isPresent()){
					json = new JSONObject();
					PubFun.returnFailJson(json,"尚存在有效的子组织，故不得删除此组织");
					return json;
				}
			}
			json = organizationService.delete(id);
		} catch (RbackException e) {
			e.printStackTrace();
			json = new JSONObject();
			returnFailJson(json, e.getMsg());
		}finally{
			return json  ;
		}	
	}
	
	/*****
	 * 修改组织
	 * @param org
	 * @return
	 */
	@RequestMapping(value="organization",method=RequestMethod.PUT,produces="application/json;charset=UTF8")
	@ResponseBody
	public JSONObject update(@Valid @ModelAttribute Organization org){
		
		return null ;
	}
	
	/****
	 * 获取单个组织
	 * @param
	 * @return
	 */
	@RequestMapping(value="organization/{id}",method=RequestMethod.GET,produces="application/json;charset=UTF8")
	@ResponseBody
	public JSONObject get(@PathVariable String id){
		JSONObject json = new JSONObject();
		Organization  o = organizationMapper.get(id);
		if(o==null){
			json = new JSONObject();
			returnFailJson(json, "该组织不存在");
			return json ;
		}
		json = new JSONObject();
		returnSuccessJson(json);
		json.put("org", (JSONObject)JSONObject.toJSON(o) );
		return json ;
	}


	/*****
	 * 可以翻页，获取组织列表
	 * @param org
	 * @return
	 */
	@RequestMapping(value="organizations",method=RequestMethod.GET,produces="application/json;charset=UTF-8")
	@ResponseBody
	public JSONObject organizations(@ModelAttribute Organization org){
		JSONObject js = new JSONObject();
		JSONArray array = new JSONArray();
		org.setPaginationEnable("0");
		List<Organization> list = organizationMapper.getOrganizations(org);
		if(list==null||list.size()<1){
			returnFailJson(js, "无数据");
			return js ;
		}
		for(Organization o:list){
			JSONObject j = new JSONObject();
			j.put("id", o.getId());
			j.put("pId", o.getParentId());
			j.put("name", o.getName());
			j.put("open", true); // 默认全部打开
			array.add(j);
		}
		js.put("res", "2");
		js.put("list", array);
		return js ;
	}

	@RequestMapping("get")
	public String get(){
		return "system/organization/organizationList";
	}
}
