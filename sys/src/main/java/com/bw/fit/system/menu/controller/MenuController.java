package com.bw.fit.system.menu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bw.fit.system.common.controller.BaseController;
import com.bw.fit.system.menu.mapper.MenuMapper;
import com.bw.fit.system.menu.model.Menu;

import javax.annotation.Resource;

@RequestMapping(value="menu",produces = "application/json; charset=utf-8")
@Controller
@EnableEurekaClient
public class MenuController extends BaseController {

	@Resource
	private MenuMapper menuMapper;
	
	/****
	 * 获取所有的菜单
	 * @return
	 */
	@RequestMapping(value="menus/{roleId}",method=RequestMethod.GET)
	@ResponseBody
	public JSONArray menus(@PathVariable String roleId){
		List<Menu> menus = menuMapper.getMenus();
		List<Menu> myMenus = menuMapper.getMyRole2Menus(roleId);
		JSONArray array = new JSONArray();
		for(Menu m:menus){
			JSONObject j = new JSONObject();
			j.put("id", m.getId());
			j.put("name", m.getTitle());
			j.put("pId",m.getParentId());
			j.put("open", true);
			if(myMenus!=null){
				java.util.Optional<Menu> ops = myMenus.parallelStream().filter(x->x.getId().equals(m.getId())).findAny();
				if(ops.isPresent()){
					j.put("checked", true);
				}
			}
			array.add(j);
		}
		return  array ;
	}
}
