package com.bw.fit.system.account.controller;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bw.fit.system.account.mapper.AccountMapper;
import com.bw.fit.system.account.entity.TAccount;
import com.bw.fit.system.account.model.Account;
import com.bw.fit.system.account.model.AccountItem;
import com.bw.fit.system.account.service.AccountService;
import com.bw.fit.system.authority.entity.TAuthority;
import com.bw.fit.system.common.controller.BaseController;
import com.bw.fit.system.common.model.RbackException;
import com.bw.fit.system.common.service.CommonService;
import com.bw.fit.system.common.util.PubFun;
import com.bw.fit.system.menu.model.Menu;
import com.bw.fit.system.menu.service.MenuService;
import com.bw.fit.system.organization.entity.TOrganization;
import com.bw.fit.system.organization.model.Organization;
import com.bw.fit.system.position.entity.TPosition;
import com.bw.fit.system.role.entity.TRole;
import com.bw.fit.system.role.mapper.RoleMapper;
import com.bw.fit.system.role.model.Role2Account;
import com.bw.fit.system.user.entity.TUser;
import com.bw.fit.system.user.mapper.UserMapper;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import com.github.pagehelper.Page;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.bw.fit.system.common.util.PubFun.returnFailJson;

/**
 * @Description
 * @Author yangh
 * @Date 2018-12-8 19:12
 * @Param ${PARAM}
 * @Return ${RETURN}
 * @VERSION
 */
@Api("账户模块提供的接口")
@Controller
@RequestMapping(value="account",produces = "application/json; charset=utf-8")
@EnableEurekaClient
public class AccountController extends BaseController {

    @Autowired
    RestTemplate restTemplate;
    @Resource
    private UserMapper userMapper;
    @Resource
    private AccountMapper accountMapper;
    @Autowired
    private CommonService commonService;
    @Resource
    private AccountService accountService ;
    @Resource
    private MenuService menuService ;
    @Resource
    private RoleMapper roleMapper;


    @GetMapping (value="account/{logName}")
    @ResponseBody
    public JSONObject get(@PathVariable(value = "logName") String logName){
        JSONObject jsonObject = new JSONObject();
        Account account = new Account();
        TAccount tAccount = new TAccount();
        tAccount.setLogName(logName);
        TAccount tAccount1 = accountMapper.getByLogName(logName);
        if(ObjectUtil.isNull(tAccount1)){
            jsonObject.put("res","1");
            jsonObject.put("msg","账号不存在或无效");
            return  jsonObject;
        }
        TUser tUser = userMapper.getByCode(tAccount1.getUserId());
        PubFun.copyProperties(account,tUser);// 用户与租户信息
        account.setTempStr1(tUser.getId());
        account.setLogName(tAccount1.getLogName());
        account.setUserId(tUser.getCode());
        account.setId(tAccount1.getId());
        account.setCreator(tAccount1.getCreator());
        account.setCreatorName(tAccount1.getCreatorName());
        account.setCreateOrgId(tAccount1.getCreateOrgId());
        TOrganization tOrganization = accountMapper.getOrgByAccount(logName);
        if(ObjectUtil.isNotNull(tOrganization)){
            account.setCurrentOrgId(tOrganization.getId());
        }// 归属组织信息
        List<TPosition> pos = accountMapper.getPositionsOfTheAccount(account.getId());
        if(ObjectUtil.isNotNull(pos)){
            account.setPositionIds(pos.stream().map(TPosition::getId).collect(Collectors.joining(",")));
        }//岗位信息
        List<TRole> tRoles = accountMapper.getRolesByAccount(logName);
        if(ObjectUtil.isNotNull(tRoles)){
            account.setRoleIds(tRoles.stream().map(TRole::getId).collect(Collectors.joining(",")));
            List<TAuthority> rs = new ArrayList<>();
            for(TRole tRole:tRoles){
                List<TAuthority> as = roleMapper.getAuthsOfThisRole(tRole.getId());
                rs.addAll(as);
            }
            account.setAuthCodes(rs.stream().map(TAuthority::getCode).collect(Collectors.joining(",")));
            List<Organization> orgs = accountService.getDataAuthOrgsOfAccount(account.getId());
            if(ObjectUtil.isNotNull(orgs)){
                account.setHaveOrgListAuth(orgs.stream().map(Organization::getId).collect(Collectors.toList()));
            }
        }//角色信息，功能权限信息，数据权限{包含哪些组织权限}
        jsonObject = (JSONObject)JSONObject.toJSON(account);
        jsonObject.put("res","2");
        return  jsonObject;
    }

    @GetMapping(value="menus2/{sessionId}")
    @ResponseBody
    public JSONArray menus(@PathVariable String sessionId){
        JSONArray jsonArray = new JSONArray();
        String str = restTemplate.getForObject("http://cache-proj/cache/key/"+sessionId, String.class);
        JSONObject jsonObject = JSONObject.parseObject(str);
        return jsonArray;
    }

    /****
     * 获取当前用户左侧菜单树
     * menu.js 调用
     * @return
     */
    @RequestMapping(value="menus",method=RequestMethod.GET)
    @ResponseBody
    public String getMenus( HttpServletRequest request,@RequestHeader(value = "sessionId", required = false) String sessionId ){
        String sessionId3 = request.getParameter("sessionId");
        String ac = request.getParameter("ac");
        Map<String,String[]> token = request.getParameterMap();
        JSONObject json = commonService.checkSessionValid(sessionId);
        if("1".equals(json.get("res").toString())){
            return null;
        }
        try {
            List<Menu> menus = accountService.getMenusOfThisAccount(json.get("logName").toString());
            JSONArray array = menuService.getMenuTreeJson(menus);
            return array.toJSONString() ;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value="accounts",method=RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject accounts(@ModelAttribute Account account){
        JSONObject js = new JSONObject();
        PageHelper.startPage(account.getPage(),account.getRows());
        Page<Account> list = accountMapper.getAccounts(account);
        for(Account a:list){
            a.setIsDeleted("0".equals(a.getTempStr1())?"正常":"已作废");
        }
        js.put("total",((Page)(list)).getTotal());
        js.put("rows",  JSONObject.toJSON(list));
        return js ;
    }

    @GetMapping(value="account/id/{id}",produces="application/json;charset=UTF-8")
    @ResponseBody
    public String getOne(@PathVariable String id){
        Account account = accountService.get(id);
        return JSONObject.toJSONString(account);
    }

    @RequestMapping(value="account/{id}",method=RequestMethod.DELETE,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject delete(@PathVariable String id){
        JSONObject json = new JSONObject();
        try {
            json = accountService.delete(id);
        } catch (RbackException e) {
            e.printStackTrace();
            json = new JSONObject();
            returnFailJson(json, e.getMsg());
            throw e;
        }finally{
            return json ;
        }
    }

    @RequestMapping(value="account",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject insert(@Valid @ModelAttribute AccountItem account, BindingResult result,HttpServletRequest request){
        JSONObject json = new JSONObject();
        if (result.hasErrors()) {
            FieldError error = result.getFieldError();
            json.put("res", "1");
            returnFailJson(json, error.getDefaultMessage());
            return json ;
        }
        try {
            commonService.fillCommonProptities(account,request, true );
            Account acc = new Account();
            PubFun.copyProperties(acc, account);
            json = accountService.insert(acc);
        } catch (RbackException e) {
            e.printStackTrace();
            json = new JSONObject();
            returnFailJson(json, e.getMsg());
        }finally{
            return json  ;
        }
    }

    /****
     * 支持过户
     * @param account
     * @param result
     * @return
     */
    @RequestMapping(value="account",method=RequestMethod.PUT,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject update(@Valid @ModelAttribute Account account,BindingResult result,HttpServletRequest request){
        JSONObject json = new JSONObject();
        if (result.hasErrors()) {
            FieldError error = result.getFieldError();
            json.put("res", "1");
            returnFailJson(json, error.getDefaultMessage());
            return json ;
        }
        try {
            commonService.fillCommonProptities(account,request, true );
            json = accountService.updateTransferAccount(account);
        } catch (RbackException e) {
            e.printStackTrace();
            json = new JSONObject();
            returnFailJson(json, e.getMsg());
        }finally{
            return json  ;
        }
    }

    @RequestMapping("openAccountTransferPage/{accountId}")
    public String openAccountTransferPage(@PathVariable String accountId, Model model){
        Account ac = accountService.get(accountId);
        model.addAttribute("account", ac);
        return "system/account/accountTransferPage";
    }

    @RequestMapping(value="role2Account",method=RequestMethod.PUT,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject role2Account(@ModelAttribute Role2Account ra, @RequestParam(value="roleId") String roleId, HttpServletRequest request){
        JSONObject json = new JSONObject();
        try {
            commonService.fillCommonProptities(ra,request, true );
            json = accountService.updateRole2Account(ra);
        } catch (Exception e) {
            e.printStackTrace();
            json = new JSONObject();
            returnFailJson(json, e.getLocalizedMessage());
        }finally{
            return json  ;
        }

    }

}
