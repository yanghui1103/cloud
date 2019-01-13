package com.bw.fit.system.authority.mapper;

import java.util.List;

import com.bw.fit.system.authority.entity.TAuthority;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuthorityMapper {

	/***
	 * 获取功能权限
	 * @param code
	 * @return
	 */
	public TAuthority get(String code);
	/*****
	 * 查询权限列表
	 * @param ta
	 * @return
	 */
	public List<TAuthority> authoritys(TAuthority ta);
}
