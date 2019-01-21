package com.bw.fit.system.menu.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.bw.fit.system.menu.model.Menu;
import com.bw.fit.system.menu.service.MenuService;
import org.springframework.stereotype.Service;

import javax.xml.ws.ServiceMode;
import java.util.*;

@Service
public class MenuServiceImpl implements MenuService {

    @Override
    public JSONArray getMenuTreeJson(List<Menu> menus) {

        return null;
    }

    private List<Map<String,Object>> createComboTreeTree(List<Menu> menus, String fid) {
        List<Map<String,Object>> comboTreeList  =new ArrayList<Map<String,Object>>();
        for (int i = 0; i < menus.size(); i++) {
            Map<String, Object> map = null;
            Menu menu = (Menu) menus.get(i);
            if (menu.getParentId().equals("0")) {
                map = new HashMap<String, Object>();
                //这里必须要将对象角色的id、name转换成ComboTree在页面的显示形式id、text
                //ComboTree,不是数据表格，没有在页面通过columns转换数据的属性
                map.put("id", menus.get(i).getId());
                map.put("children", createComboTreeChildren(menus, menu.getId()));
            }
            if (map != null)
                comboTreeList.add(map);
        }
        return comboTreeList ;
    }

    /**
     * 递归设置role树
     * @param fid
     * @return
     */
    private List<Map<String, Object>> createComboTreeChildren(List<Menu> menus, String fid) {
        List<Map<String, Object>> childList = new ArrayList<Map<String, Object>>();
        for (int j = 0; j < menus.size(); j++) {
            Map<String, Object> map = null;
            Menu treeChild = (Menu) menus.get(j);
            if (treeChild.getParentId().equals(fid)) {
                map = new HashMap<String, Object>();
                //这里必须要将对象角色的id、name转换成ComboTree在页面的显示形式id、text
                //ComboTree,不是数据表格，没有在页面通过columns转换数据的属性
                map.put("id", menus.get(j).getId());
                map.put("children", createComboTreeChildren(menus, treeChild.getId()));
            }

            if (map != null)
                childList.add(map);
        }
        return childList;
    }
}
