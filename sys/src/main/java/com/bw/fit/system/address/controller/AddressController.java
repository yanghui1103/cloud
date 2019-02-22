package com.bw.fit.system.address.controller;

import static com.bw.fit.system.common.util.PubFun.returnSuccessJson;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import com.bw.fit.system.common.service.CommonService;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.bw.fit.system.account.model.Account;
import com.bw.fit.system.address.service.AddressService;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("address")
@Controller
public class AddressController {
	
	@Autowired
	private AddressService addressService ;
	@Autowired
	private CommonService commonService;
	
	/***
	 * 打开地址本页面
	 * @param model
	 * @param o 是否查询组织
	 * @param p 是否查询岗位
	 * @param a 是否查询账户
	 * @param ids 已选列表的id
	 * @param isMultiple 是否多选
	 * @return
	 */
	@RequestMapping("openAddressPage/{o}/{p}/{a}/{ids}/{isMultiple}")
	@ResponseBody
	public String openAddressPage(Model model, @PathVariable boolean o,
								  @PathVariable boolean p, @PathVariable boolean a,
								  @PathVariable(value="ids",required=false) String ids,
								  @PathVariable boolean isMultiple, HttpServletRequest request){
		JSONObject accountJson = commonService.getCurrentAccount(request) ;
		String orgId = accountJson.get("currentOrgId").toString() ;
		//待选列表
		model.addAttribute("selectList", addressService.getSelectAddr(o, p, a, orgId,false));
		//已选列表
		if(!"-9".equals(ids)) {
			model.addAttribute("selectedList", addressService.getSelectedAddr(ids));
		}
		model.addAttribute("ifshow_org", o);
		model.addAttribute("ifshow_position", p);
		model.addAttribute("ifshow_account", a);
		if(isMultiple) {
			model.addAttribute("isMultiple", "multiple");
		}
		return "system/address/addressPage" ;
	}
	
	/*****
	 * 地址本中点击左侧组织树(或者查询)，响应右侧待选
	 * @return
	 */
	@RequestMapping(value="address/{keyWords}/{o}/{p}/{a}/{type}",method=RequestMethod.GET,produces="application/json;charset=UTF8")
	@ResponseBody
	public JSONObject get(@PathVariable String keyWords,@PathVariable boolean o,@PathVariable boolean p,@PathVariable boolean a,@PathVariable boolean type){
		JSONObject json = new JSONObject();
		try {
			keyWords = (URLDecoder.decode(keyWords, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		json = new JSONObject();
		returnSuccessJson(json);
		json.put("addressList",JSONObject
				.toJSON(addressService
						.getSelectAddr(o, p, a, keyWords,type)));
		return json ;
	}
	
	@RequestMapping(value="addressDetail/{id}",method=RequestMethod.GET,produces="application/json;charset=UTF8")
	@ResponseBody
	public JSONObject addressDetail(@PathVariable String id){
		JSONObject json  = new JSONObject();
		if(id!=null&&id.indexOf("_")!=-1) {
			json.put("detali",addressService.getDetali(id.split("_")[0], id.split("_")[1]));
		}
		returnSuccessJson(json);
		return json;
	}
}
