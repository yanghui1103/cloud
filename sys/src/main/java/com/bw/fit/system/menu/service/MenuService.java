package com.bw.fit.system.menu.service;

import com.alibaba.fastjson.JSONArray;
import com.bw.fit.system.menu.model.Menu;

import java.util.List;

public interface MenuService {
    /****
     * 获取父子节点json
     * @param menus
     * @return
     */
    public JSONArray getMenuTreeJson(List<Menu> menus);
}
