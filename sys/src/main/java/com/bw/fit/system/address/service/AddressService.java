package com.bw.fit.system.address.service;

import java.util.List;
import java.util.Map;

import com.bw.fit.system.address.model.Address;

public interface AddressService {
	/****
	 * 获取已选列表
	 * @param ids 已选id
	 * @return
	 */
	public List<Address> getSelectedAddr(String ids);
	/***
	 * 获取待选列表
	 * @param o 是否查询组织
	 * @param p 是否查询岗位
	 * @param a 是否查询账号
	 * @param keyWords 组织id或者关键词
	 * @param type 查询类型(true :关键词查询,false :组织树查询)
	 * @return
	 */
	public List<Address> getSelectAddr(boolean o,boolean p,boolean a,String keyWords,boolean type);
	
	/***
	 * 获取描述
	 * @param id 地址本id
	 * @param underOrgId 所属组织id
	 * @return
	 */
	public String getDetali(String id,String underOrgId);
	/****
	 * 通过ids获取names
	 * @param ids 
	 * @return
	 */
	public String getNames(String[] ids);
}
