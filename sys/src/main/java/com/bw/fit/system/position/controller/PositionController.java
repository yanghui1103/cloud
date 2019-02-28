package com.bw.fit.system.position.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.alibaba.fastjson.JSON;
import com.bw.fit.system.common.service.CommonService;
import com.bw.fit.system.organization.mapper.OrganizationMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.bw.fit.system.common.model.RbackException;
import com.bw.fit.system.common.util.PubFun;
import com.bw.fit.system.organization.model.Organization;
import com.bw.fit.system.position.mapper.PositionMapper;
import com.bw.fit.system.position.model.Position;
import com.bw.fit.system.position.service.PositionService;

@RequestMapping(value="position",produces = "application/json; charset=utf-8")
@Controller
@EnableEurekaClient
public class PositionController {
	@Resource
	private PositionMapper positionMapper;
	@Autowired
	private PositionService positionService ;
	@Resource
	private OrganizationMapper organizationMapper;
	@Autowired
	private CommonService commonService;
	/*****
	 * 查询岗位管理列表
	 *
	 *            UI-Model
	 * @param p
	 *            岗位
	 * @param request
	 *            请求
	 * @return
	 */
	@RequestMapping(value="positions/{orgId}",method=RequestMethod.GET)
	@ResponseBody
	public JSONObject companyList(@ModelAttribute Position p, HttpServletRequest request,@PathVariable String orgId) {
		JSONObject json = new JSONObject();
		p.setTempStr(orgId);
		Page<Position> list = positionService.all(p);
		if(list!=null&&list.size()>0) {
			for(Position tmp : list) {
				String orgNames="";
				List<Organization> tmpOrgs = positionMapper.getOrgByPositionId(tmp.getId());
				if(tmpOrgs!=null&&tmpOrgs.size()>0) {
					for(Organization s1 : tmpOrgs) {
						orgNames += s1.getName()+";";
					}
					orgNames = orgNames.substring(0, orgNames.length()-1);
					tmp.setTempStr1(orgNames);
				}
			}
			json.put("total",list.getTotal());
			json.put("rows",  JSONObject.toJSON(list));
			return  json;
		}else {
			json.put("total",0);
			json.put("rows", null);
			return json;
		}

	}

	/***
	 * 打开新增岗位页
	 * @param orgIds
	 * @param model
	 * @return
	 */
	@RequestMapping("openPositionAddPage/{orgIds}")
	@ResponseBody
	public String openPositionAddPage(@PathVariable String orgIds,Model model){
		String orgNames="";
		for(String id : orgIds.split(",")) {
			orgNames += organizationMapper.get(id).getName()+";";
		}
		Map<String,String> map = new HashMap<>();
		map.put("orgIds", orgIds);
		map.put("orgNames", orgNames);
		return JSONObject.toJSON(map).toString() ;
	}
	
	/*****
	 * 打开update岗位页
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("openPositionEditPage/{id}")
	@ResponseBody
	public JSONObject openPositionEditPage(@PathVariable String id,Model model){
		Position p = positionMapper.get(id);
		List<Organization> orgList = positionMapper.getOrgByPositionId(id);
		String ids = "";
		String names = "";
		for(Organization o : orgList) {
			ids += o.getId()+',';
			names += o.getName()+',';
		}
		Map<String,Object> map = new HashMap<>();
		map.put("position", p);
		map.put("orgIds", ids);
		map.put("orgNames", names);
		map.put("item", null);
		JSONObject postionJson = (JSONObject) JSONObject.toJSON(map);
		return  postionJson ;
	}
	
	@RequestMapping(value="position",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	public JSONObject insert(@Valid @ModelAttribute Position p, HttpServletRequest request){
		JSONObject json = new JSONObject();
		try {
			commonService.fillCommonProptities(p,request,true);
			json = positionService.createPosition(p);
		} catch (RbackException e) { 
			json = new JSONObject();
			PubFun.returnFailJson(json, e.getMsg());
		}finally{
			return json ;
		}
	}
	
	@RequestMapping(value="position",method=RequestMethod.PUT,produces="application/json;charset=UTF-8")
	@ResponseBody
	public JSONObject update(@Valid @ModelAttribute Position p, HttpServletRequest request){
		JSONObject json = new JSONObject();
		try {
			commonService.fillCommonProptities(p,request,false);
			json = positionService.updatePosition(p);
		} catch (RbackException e) { 
			json = new JSONObject();
			PubFun.returnFailJson(json, e.getMsg());
		}finally{
			return json ;
		}
	}
	
	@RequestMapping(value="position/{id}/{orgId}",method=RequestMethod.DELETE,produces="application/json;charset=UTF-8")
	@ResponseBody
	public JSONObject delete(@PathVariable String id,@PathVariable String orgId){	
		JSONObject json = new JSONObject();		
		try {
			json = positionService.deletePosition(id,orgId);
		} catch (RbackException e) {
			e.printStackTrace();
			json =  new JSONObject();
			PubFun.returnFailJson(json, e.getMsg());
		}		finally{
			return json ;
		}
	}
}
