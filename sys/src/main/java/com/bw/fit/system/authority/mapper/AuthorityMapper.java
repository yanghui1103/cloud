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
	 *  所有权限列表
	 * @param roleId
	 * @return
	 */
	public List<TAuthority> getAuthoritys(String roleId);
}
