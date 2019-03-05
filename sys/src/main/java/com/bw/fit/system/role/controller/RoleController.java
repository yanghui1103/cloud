package com.bw.fit.system.role.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.bw.fit.system.authority.mapper.AuthorityMapper;
import com.bw.fit.system.common.service.CommonService;
import com.bw.fit.system.dict.mapper.DictMapper;
import com.github.pagehelper.Page;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.bw.fit.system.account.model.Account;
import com.bw.fit.system.address.service.AddressService;
import com.bw.fit.system.authority.entity.TAuthority;
import com.bw.fit.system.authority.entity.TRole2dataauth;
import com.bw.fit.system.common.controller.BaseController;
import com.bw.fit.system.common.model.BaseModel;
import com.bw.fit.system.common.model.RbackException;
import com.bw.fit.system.common.util.PubFun;
import com.bw.fit.system.dict.entity.TdataDict;
import com.bw.fit.system.dict.model.Dict;
import com.bw.fit.system.dict.service.DictService;
import com.bw.fit.system.role.mapper.RoleMapper;
import com.bw.fit.system.role.entity.TRole;
import com.bw.fit.system.role.model.Role;
import com.bw.fit.system.role.service.RoleService;
import com.bw.fit.system.user.model.User;
import com.bw.fit.system.user.service.UserService;
/*****
 * 角色模块controller
 * @author yangh
 *
 */
@RequestMapping(value="role",produces = "application/json; charset=utf-8")
@Controller
@EnableEurekaClient
public class RoleController extends BaseController {

	@Autowired
	private CommonService commonService;
	@Autowired
	private UserService userService;
	@Autowired
	private AddressService addressService ;
	@Autowired
	private DictService dictService ;
	@Resource
	private RoleMapper roleMapper;
	@Autowired
	private RoleService roleService;
	@Resource
	private AuthorityMapper authorityMapper;
	@Resource
	private DictMapper dictMapper;
	
	@RequestMapping(value="roles",method=RequestMethod.GET,produces="application/json;charset=UTF-8")
	@ResponseBody
	public JSONObject roles(@ModelAttribute Role role){
		JSONObject js = new JSONObject();
		Page<TRole> logs = roleService.selectAll(role);
		js.put("total",((Page)logs).getTotal());
		js.put("rows",  JSONObject.toJSON(logs));
		return  js ;
	}
	
	@RequestMapping(value="role/{id}",method=RequestMethod.DELETE,produces="application/json;charset=UTF-8")
	@ResponseBody
	public JSONObject delete(@PathVariable String id){
		JSONObject json = new JSONObject();		
		try {
			json = roleService.delete(id);
		} catch (RbackException e) {
			e.printStackTrace();
			json = new JSONObject();
			PubFun.returnFailJson(json, e.getMsg());
		}finally{
			return json ;
		}
	}
	
	@RequestMapping(value="role",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	public JSONObject add(@Valid @ModelAttribute Role role, HttpServletRequest httpServletRequest){
		JSONObject json = new JSONObject();		
		try {
			commonService.fillCommonProptities(role,httpServletRequest,true);
			json = roleService.insert(role);
		} catch (RbackException e) {
			e.printStackTrace();
			json = new JSONObject();
			PubFun.returnFailJson(json, e.getMsg());
		}finally{
			return json ;
		}
	}
	
	
	@RequestMapping("openAddRole/{id}/{name}")
	public String openAddRole(@PathVariable(value="id") String id,@PathVariable(value="name") String name,Model model){
		model.addAttribute("roleName", name);
		
		return null ;
	}
	
	
	@RequestMapping("authsOfRole/{roleId}")
	public String authsOfRole(@PathVariable String roleId,Model model){
		model.addAttribute("role", roleMapper.get(roleId));
		TAuthority ta = new TAuthority();
		List<TAuthority> all = authorityMapper.authoritys(ta);
		List<TAuthority> my = roleMapper.getAuthoritysByRole(roleId);
		for(TAuthority t:all){
			if(my!=null){
				Optional<TAuthority> ops = my.parallelStream().filter(x->x.getCode().equals(t.getCode())).findAny();
				t.setDesp(ops.isPresent()?"checked":"false");
			}
		}
		model.addAttribute("all", all);
		return "system/role/role2AuthPage";
	}

	/*****
	 * 分配功能权限
	 * @param temp_str1
	 * @param id
	 * @return
	 * @throws RbackException
	 */
	@RequestMapping(value="authsOfRole",method=RequestMethod.PUT,produces="application/json;charset=UTF-8")
	@ResponseBody
	public JSONObject update(@RequestParam(value="temp_str1") String temp_str1,
			@RequestParam(value="id") String[] id) throws RbackException{
		JSONObject json = new JSONObject();		
		json = roleService.updateAuthsOfRole(temp_str1,id);
		return json ;	
	}
	

	@RequestMapping("dataAuthsOfRole/{roleId}")
	public String dataAuthsOfRole(@PathVariable String roleId,Model model){
		model.addAttribute("role", roleMapper.get(roleId));
		Dict dd = dictService.getDictsByParentValue("dataAuth");
		List<TdataDict> tds =  dictMapper.getDictsByPid(dd.getId());
		TRole2dataauth my = roleMapper.getDataAuthoritysByRole(roleId);
		for(TdataDict t:tds){
			if(my!=null){
				if(my.getAuthId().equalsIgnoreCase(t.getDict_value())){
					t.setLogId("checked");
				}
			}
		}
		model.addAttribute("all", tds);
		BaseModel b = roleMapper.getRoleDataAuthOrgs(roleId);
		if(b!=null){
			model.addAttribute("orgIds",b.getTempStr2());
			String[] arr = addressService.getNames(b.getTempStr2().split(",")) ;
			
			model.addAttribute("orgNames", Arrays.stream(arr).collect(Collectors.joining(",")));
		}
		
		return "system/role/role2DataAuthPage";
	}

	
	@RequestMapping(value="saveDataAuthsOfRole",method=RequestMethod.PUT,produces="application/json;charset=UTF-8")
	@ResponseBody
	public JSONObject saveAuthsOfRole(@RequestParam(value="rorgids") String rorgids,@RequestParam(value="temp_str1") String temp_str1,
			@RequestParam(value="id") String id) throws RbackException{
		JSONObject json = new JSONObject();		
		json = roleService.saveDataAuthsOfRole(temp_str1,id,rorgids);
		return json ;	
	}
	
	@RequestMapping("openMenusOfRole/{roleId}")
	public String openMenusOfRole(@PathVariable String roleId,Model model){
		model.addAttribute("role", roleMapper.get(roleId));
		return "system/role/role2MenuPage";
	}

	@RequestMapping(value="role2Menu",method=RequestMethod.PUT,produces="application/json;charset=UTF-8")
	@ResponseBody
	public JSONObject role2Menu(@RequestParam(value="roleId") String roleId,@RequestParam(value="menus") String menus) throws Exception{
		JSONObject json = new JSONObject();	 
				json = roleService.saverole2Menu(roleId,menus);
		 
		return json ;	
	}
	
	/*****
	 * 打开角色分配人员界面，将已有得人员输出
	 * @param roleId
	 * @param model
	 * @return
	 */
	@RequestMapping("openAccountOfRole/{roleId}")
	public String openAccountOfRole(@PathVariable String roleId,Model model){		
		List<Account> as = roleService.getAccountOfRole(roleId);
		if(as!=null && as.size()>0){
			for(Account a:as){
				User u = userService.getByCode(a.getCode());
				a.setName(u.getName());
			}

			String s = as.stream().map(Account::getName).collect(Collectors.joining(",")) ;
			model.addAttribute("accountNames", s);
			model.addAttribute("accountIds", as.stream().map(Account::getId).collect(Collectors.joining(",")));
		}
		model.addAttribute("role", roleMapper.get(roleId));
		
		return "system/role/role2AccountPage";
	}
}
