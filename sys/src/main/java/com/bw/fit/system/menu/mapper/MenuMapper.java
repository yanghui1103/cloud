package com.bw.fit.system.menu.mapper;

import java.util.List;

import com.bw.fit.system.menu.model.Menu;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MenuMapper {

	/****
	 * 获取所有有效的菜单 
	 * @return
	 */
	public List<Menu> getMenus();
	/****
	 * 这个角色的有效菜单
	 * @param roleId
	 * @return
	 */
	public List<Menu> getMyRole2Menus(String roleId);
}
