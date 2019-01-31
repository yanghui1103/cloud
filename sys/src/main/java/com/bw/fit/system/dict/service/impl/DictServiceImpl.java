package com.bw.fit.system.dict.service.impl;

import java.util.*;

import com.bw.fit.system.common.util.treeHandler.DDictJsonTreeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.bw.fit.system.common.model.RbackException;
import com.bw.fit.system.common.util.PubFun;
import com.bw.fit.system.dict.mapper.DictMapper;
import com.bw.fit.system.dict.entity.TdataDict;
import com.bw.fit.system.dict.model.DataDict;
import com.bw.fit.system.dict.model.Dict;
import com.bw.fit.system.dict.service.DictService;

import javax.annotation.Resource;

@Service
public class DictServiceImpl implements DictService {

	@Resource
	private DictMapper dictMapper;
	
	@Override
	public Dict getDictsByParentValue(String value) {
		Dict d = dictMapper.getDictByValue(value);
		return d ;
		
	}

	@Override
	public DataDict getAllDataDict(String parent_id) {
		List<TdataDict> list = dictMapper.getDataDictList(parent_id);
		List<DataDict> lis = new ArrayList<>();
		for (TdataDict d : list) {
			DataDict dd = new DataDict();
			PubFun.copyProperties(dd, d);
			if ("0".equals(dd.getParent_id())) {
				dd.setParent_id("");
			}
			lis.add(dd);
		}

		List dataList = new ArrayList();
		for (DataDict d : lis) {
			HashMap dataRecord1 = new HashMap();
			dataRecord1.put("id", d.getId());
			dataRecord1.put("dict_value", d.getDict_value());
			dataRecord1.put("dict_remark", d.getDict_remark());
			dataRecord1.put("dict_name", d.getDict_name());
			dataRecord1.put("parent_id", d.getParent_id());
			dataRecord1.put("can_add", d.getCan_add());
			dataRecord1.put("can_edit", d.getCan_edit());
			dataRecord1.put("can_del", d.getCan_del());
			dataRecord1.put("num", d.getNum());
			dataList.add(dataRecord1);
		}
		DataDict node = DDictJsonTreeHandler.getJSONTree(dataList);
		return node;
	}

	@Override
	public JSONObject createDict(Dict dict) throws RbackException {
		JSONObject json = new JSONObject();		
		try {
			dictMapper.insert(dict);
			PubFun.returnSuccessJson(json);
		} catch (RbackException e) {
			e.printStackTrace();
			PubFun.returnFailJson(json, e.getMsg());
			throw   e;
		}finally{
			return json ;
		}
	}

	@Override
	public JSONObject updateDict(Dict dict) throws RbackException {
		JSONObject json = new JSONObject();		
		try {
			dictMapper.update(dict);
			PubFun.returnSuccessJson(json);
		} catch (RbackException e) {
			e.printStackTrace();
			PubFun.returnFailJson(json, e.getMsg());
			throw   e;
		}finally{
			return json ;
		}
	}

	@Override
	public Dict getDictByValue(String dictValue) {
		return dictMapper.getDictByValue(dictValue);
	}

	@Override
	public List<DataDict> getChildrens(String parentValue) {
		Dict d = dictMapper.getDictByValue(parentValue);
		List<DataDict> ds = dictMapper.getDictsByParentId(d.getId());
		return ds;
	}

	
}
