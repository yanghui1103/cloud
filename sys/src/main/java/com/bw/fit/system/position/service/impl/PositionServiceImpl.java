package com.bw.fit.system.position.service.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hutool.core.collection.CollectionUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.bw.fit.system.common.model.RbackException;
import com.bw.fit.system.common.util.PubFun;
import com.bw.fit.system.position.mapper.PositionMapper;
import com.bw.fit.system.position.entity.TOrganization2Position;
import com.bw.fit.system.position.model.Position;
import com.bw.fit.system.position.service.PositionService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service(value="positionService")
public class PositionServiceImpl implements PositionService{
	@Resource
	private PositionMapper positionMapper;
	@Value("${cutFlag}")
	private String cutFlag;

	@Transactional(rollbackFor = {Exception.class,RbackException.class})
	@Override
	public JSONObject createPosition(Position position) throws RbackException {
		JSONObject json = new JSONObject();		
		try {
			positionMapper.insert(position);
			PubFun.returnSuccessJson(json);
			String [] ids = position.getTempStr1().split(cutFlag);
			if(ids.length>0) {
				for(String orgId : ids) {
					TOrganization2Position to2p = new TOrganization2Position();
					to2p.setPositionId(position.getId());
					to2p.setOrgId(orgId);
					positionMapper.insertOrg2Position(to2p);
				}
			}
		} catch (RbackException e) {
			e.printStackTrace();
			json = new JSONObject();
			PubFun.returnFailJson(json, e.getMsg());
			throw   e;
		}finally{
			return json ;
		}
	}

	@Transactional(rollbackFor = {Exception.class,RbackException.class})
	@Override
	public JSONObject updatePosition(Position position) throws RbackException {
		JSONObject json = new JSONObject();		
		try {
			positionMapper.update(position);
			positionMapper.deleteO2PByPid(position.getId());
			String tmpIds = position.getTempStr1();
			if(tmpIds.indexOf(cutFlag)!=-1) {
				String [] ids = tmpIds.split(cutFlag);
				if(ids.length>0) {
					for(String orgId : ids) {
						TOrganization2Position to2p = new TOrganization2Position();
						to2p.setPositionId(position.getId());
						to2p.setOrgId(orgId);
						positionMapper.insertOrg2Position(to2p);
					}
				}
			}else {
				TOrganization2Position to2p = new TOrganization2Position();
				to2p.setPositionId(position.getId());
				to2p.setOrgId(tmpIds);
				positionMapper.insertOrg2Position(to2p);
			}
			
			PubFun.returnSuccessJson(json);
		} catch (RbackException e) {
			e.printStackTrace();
			json = new JSONObject();
			PubFun.returnFailJson(json, e.getMsg());
			throw   e;
		}finally{
			return json ;
		}
	}

	@Transactional(rollbackFor = {Exception.class,RbackException.class})
	@Override
	public JSONObject deletePosition(String id,String orgId) throws RbackException {
		JSONObject json = new JSONObject();		
		try {
			Map<String,String> map = new HashMap<>();
			map.put("id", id);
			map.put("orgId", orgId);
			positionMapper.deleteO2P(map);
			List<TOrganization2Position> list= positionMapper.checkO2P(id);
			if(list==null||list.size()<=0) {
				positionMapper.delete(id);
			}
			PubFun.returnSuccessJson(json);
		} catch (RbackException e) {
			e.printStackTrace();
			json = new JSONObject();
			PubFun.returnFailJson(json, e.getMsg());
			throw   e;
		}finally{
			return json ;
		}
	}

	@Override
	public Position get(String id) {
		return positionMapper.get(id);
	}

	@Override
	public Page<Position> all(Position position) {
		PageHelper.startPage(position.getPage(),position.getRows());
		Page<Position> pages = positionMapper.getPositions(position);
		pages.setTotal(pages.size());
		return pages;
	}

	@Override
	public void createPositionBySAP(Position position,TOrganization2Position to2p) throws RbackException {
		if(positionMapper.get(position.getId())!=null) {
			positionMapper.update(position);
		}else {
			positionMapper.insert(position);
		}
		if(positionMapper.checkO2P(position.getId())!=null&& positionMapper.checkO2P(position.getId()).size()>0) {
			positionMapper.deleteO2PByPid(position.getId());
		}
		
		if((null!=to2p.getOrgId()&&!"".equals(to2p.getOrgId()))&&(null!=to2p.getPositionId()&&!"".equals(to2p.getPositionId()))) {
			positionMapper.insertOrg2Position(to2p);
		}
	}
	

}
