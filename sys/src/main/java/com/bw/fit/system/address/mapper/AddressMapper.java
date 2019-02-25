package com.bw.fit.system.address.mapper;

import java.util.List;
import java.util.Map;

import com.bw.fit.system.address.entity.VAddress;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AddressMapper {
	/***
	 * 根据underOrgId和addressType获取地址
	 * @param map 入参，addressType类型 underOrgId所属组织id
	 * @return
	 */
	public List<VAddress> getAddressByOrgId(Map<String,String> map);
	
	/***
	 * 根据underOrgId和key获取地址
	 * @param map 入参，addressType类型 keyWord关键词
	 * @return
	 */
	public List<VAddress> getAddressByKey(Map<String,String> map);
	
	/***
	 * 根据id获取地址
	 * @param addr
	 * @return
	 */
	public VAddress get (VAddress addr);
	/***
	 * 根据id数组获取地址本名称数组
	 * @param ids
	 * @return
	 */
	public String[] getNames(String [] ids);
}
