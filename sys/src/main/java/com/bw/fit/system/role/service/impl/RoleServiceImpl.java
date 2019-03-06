package com.bw.fit.system.role.service.impl;

import java.util.ArrayList;
import java.util.List;

import cn.hutool.core.collection.CollectionUtil;
import com.bw.fit.system.authority.mapper.AuthorityMapper;
import com.bw.fit.system.user.entity.TUser;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.bw.fit.system.account.model.Account;
import com.bw.fit.system.authority.entity.TAuthority;
import com.bw.fit.system.authority.entity.TRole2dataauth;
import com.bw.fit.system.common.model.BaseModel;
import com.bw.fit.system.common.model.RbackException;
import com.bw.fit.system.common.util.PubFun;
import com.bw.fit.system.menu.model.Menu;
import com.bw.fit.system.role.mapper.RoleMapper;
import com.bw.fit.system.role.entity.TRole;
import com.bw.fit.system.role.entity.TRole2Authority;
import com.bw.fit.system.role.entity.TRole2dataauthOrgs;
import com.bw.fit.system.role.model.Role;
import com.bw.fit.system.role.service.RoleService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class RoleServiceImpl implements RoleService {

	@Resource
	private AuthorityMapper authorityMapper;
	@Resource
	private RoleMapper roleMapper;
	
	@Override
	public JSONObject delete(String id) throws RbackException {
		JSONObject json = new JSONObject();
		try{
			roleMapper.delete(id);
			PubFun.returnSuccessJson(json);
		}catch(RbackException ex){
			json = new JSONObject();
			PubFun.returnFailJson(json, ex.getMsg());
			throw ex;
		}finally{
			return json;
		}
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public JSONObject insert(Role role)  {
		JSONObject json = new JSONObject();
		TRole t = new TRole();
		PubFun.copyProperties(t, role);
		roleMapper.insert(t);
		PubFun.returnSuccessJson(json);
		return json;
	}

	@Override
	public JSONObject grantAuthority2Role(TRole2Authority taa) {
		JSONObject json = new JSONObject();
		roleMapper.createGrantAuthority2Role(taa);
		PubFun.returnSuccessJson(json);
		return json;
	}

	@Transactional(rollbackFor = {Exception.class,RbackException.class})
	@Override
	public JSONObject updateAuthsOfRole(String temp_str1, String[] authIds)
			throws Exception {
		try {
			TRole2Authority ta2 = new TRole2Authority();
			ta2.setRoleId(temp_str1);
			List<TAuthority> lisa = roleMapper.getAuthority2Role(ta2);
			if(CollectionUtil.isNotEmpty(lisa)){
				roleMapper.deleteAuthority2Role(ta2);
			}
			for(String s:authIds){
				TRole2Authority ta = new TRole2Authority();
				ta.setRoleId(temp_str1);
				ta.setAuthorityId(s);
				JSONObject j = grantAuthority2Role(ta);
				if(!"2".equals(j.get("res"))){
					throw new RbackException("1",j.getString("msg"));
				}
			}
		} catch (Exception e) {
			RbackException ex = new RbackException("1",e.getMessage());
			throw ex;
		}
		JSONObject json = new JSONObject();
		PubFun.returnSuccessJson(json);
		return json ;
	}

	@Override
	public JSONObject saveDataAuthsOfRole(String roleId, String authId,String rorgids)
			throws RbackException {
		JSONObject json = new JSONObject();
		try {
			BaseModel bm = new BaseModel();
			bm.setId(roleId);
			bm.setActionName(authId);
			TRole2dataauth alis = roleMapper.getDataAuthoritysByRole(roleId);
			if(alis!=null){
				roleMapper.deleteDataAuthority2Role(roleId);
			}
			roleMapper.grantDataAuthority2Role(bm);
			
			/****
			 * 指定组织部分
			 */
			BaseModel b = roleMapper.getRoleDataAuthOrgs(roleId);
			if(b!=null){
				roleMapper.deleteRoleDataAuthOrgs(roleId);
			}
			if(!"".equals(rorgids)){
				BaseModel bb = new BaseModel();
				bb.setId(roleId);
				bb.setTempStr2(rorgids);
				roleMapper.insertRoleDataAuthOrgs(bb);
			}
			
			PubFun.returnSuccessJson(json);
		} catch (RbackException e) {
			json = new JSONObject();
			PubFun.returnFailJson(json, e.getMsg());
			e.printStackTrace();
			throw e;
		}finally{
			return json ;
		}
	}

	@Override
	public JSONObject saverole2Menu(String roleId, String menuIds)
			throws RbackException {
		JSONObject json = new JSONObject();
		try {
			BaseModel bm = new BaseModel();
			bm.setId(roleId);
			List<Menu> ms = roleMapper.getMenusOfRole(roleId);
			if(ms!=null){
				roleMapper.deleteRole2Menus(roleId);
			}
			String[] array = menuIds.split(",");
			for(String s:array){
				bm.setLogId(s);
				roleMapper.grantMenus2role(bm);
			}
			PubFun.returnSuccessJson(json);
		} catch (RbackException e) {
			json = new JSONObject();
			PubFun.returnFailJson(json, e.getMsg());
			throw e;
		}
		return json ;
	}

	@Override
	public List<Role> getAllRoles(String keyWords) {
		List<TRole> rs = roleMapper.getAllRoles(keyWords);
		List<Role> list = new ArrayList<>();
		if(rs == null)
			return null ;
		for(TRole t:rs){
			Role role = new Role();
			PubFun.copyProperties(role, t);
			list.add(role);
		}
		return list;
	}

	@Override
	public TRole2dataauthOrgs getTRole2dataauthOrgs(String roleId) {
		return roleMapper.getTRole2dataauthOrgs(roleId);
	}

	@Override
	public List<Account> getAccountOfRole(String roleId) {
		return roleMapper.getAccountOfRole(roleId);
	}

	@Override
	public Page<TRole> selectAll(Role role) {
		TRole tRole = new TRole();
		PubFun.copyProperties(tRole,role);
		PageHelper.startPage(tRole.getPage(),tRole.getRows());
		Page<TRole> tLogs = roleMapper.getRoles(tRole);
		if(CollectionUtil.isNotEmpty(tLogs)){
			tLogs.stream().forEach(x->{
				x.setTempStr1(String.valueOf(roleMapper.getAccountOfRole(x.getId()).size()));
			});
		}
		return tLogs;
	}

}
