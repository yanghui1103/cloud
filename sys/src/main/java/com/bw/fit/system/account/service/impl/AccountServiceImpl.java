package com.bw.fit.system.account.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.bw.fit.system.account.entity.TAccount;
import com.bw.fit.system.account.mapper.AccountMapper;
import com.bw.fit.system.account.model.Account;
import com.bw.fit.system.account.service.AccountService;
import com.bw.fit.system.authority.entity.TAuthority;
import com.bw.fit.system.authority.entity.TRole2dataauth;
import com.bw.fit.system.common.model.BaseModel;
import com.bw.fit.system.common.model.RbackException;
import com.bw.fit.system.common.util.PropertiesUtil;
import com.bw.fit.system.common.util.PubFun;
import com.bw.fit.system.dict.model.Dict;
import com.bw.fit.system.dict.service.DictService;
import com.bw.fit.system.menu.model.Menu;
import com.bw.fit.system.organization.entity.TOrganization;
import com.bw.fit.system.organization.model.Organization;
import com.bw.fit.system.organization.service.OrganizationService;
import com.bw.fit.system.position.entity.TPosition;
import com.bw.fit.system.role.entity.TRole;
import com.bw.fit.system.role.entity.TRole2Account;
import com.bw.fit.system.role.entity.TRole2dataauthOrgs;
import com.bw.fit.system.role.mapper.RoleMapper;
import com.bw.fit.system.role.model.Role2Account;
import com.bw.fit.system.role.service.RoleService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service(value="accountService")
public class AccountServiceImpl implements AccountService {


    @Resource
    private AccountMapper accountMapper ;
    @Resource
    private RoleMapper roleMapper ;
    @Autowired
    private DictService dictService;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private RoleService roleService;

    @Override
    public Account getAccountByLogName(String logName) {
        return accountMapper.getAccountByName(logName);
    }

    @Override
    public List<Menu> getMenusOfThisAccount(String logName) {
        List<Menu> menus = new ArrayList<>();
        List<TRole> roles = accountMapper.getRolesByAccount(logName);
        for(TRole t:roles){
            List<Menu> ms = roleMapper.getMenusByRole(t.getId());
            if(ms !=null && ms.size()>0){
                for(Menu m:ms){
                    if(!"".equals(m.getHref())){
                        m.setHref( m.getHref());
                    }
                    if(!(menus.stream().filter(x->x.getId().equals(m.getId()))).findAny().isPresent()){
                        menus.add(m);
                    }
                }
            }
            /****
             * 加入首页
             */
        }
        List<Menu> menus2 = menus.stream().distinct().sorted(Comparator.comparing(Menu::getId)).collect(Collectors.toList());
        return menus2 ;
    }

    @Transactional(rollbackFor = {Exception.class,RbackException.class})
    @Override
    public JSONObject delete(String id) throws RbackException {
        JSONObject json = new JSONObject();
        try {
            accountMapper.delete(id);
            PubFun.returnSuccessJson(json);
        } catch (Exception e) {
            e.printStackTrace();
            json = new JSONObject();
            PubFun.returnFailJson(json, e.getLocalizedMessage());
            throw e;
        }finally{
            return json ;
        }
    }

    @Override
    public JSONObject insert(Account account) throws RbackException {
        JSONObject json = new JSONObject();

        try {
//            account.setLogPwd(PubFun.getUserPasswordShiro(account.getLogName(), PropertiesUtil.getValueByKey("user.default.pwd"),
//                    "MD5", 10));
            accountMapper.insert(account);
            accountMapper.insertAccount2Org(account.getId(), account.getCreateOrgId());
            String pos = account.getPositionIds();
            if(pos!=null&&!"".equals(pos)){
                String[] poss = pos.split(",");
                for(String s :poss){
                    accountMapper.insertAccount2Position(account.getId(), s);
                }
            }
            String roles = account.getRoleIds();
            if(!"".equals(roles)){
                String[] roless = roles.split(",");
                for(String s :roless){
                    TRole2Account role2Account = new TRole2Account();
                    role2Account.setRoleId(account.getId());
                    role2Account.setAccountId(s);
                    accountMapper.insertRole2Account(role2Account);
                }
            }
            PubFun.returnSuccessJson(json);
        } catch (RbackException e) {
            json = new JSONObject();
            PubFun.returnFailJson(json, e.getMsg());
            e.printStackTrace();
            throw e;
        }
        return json ;
    }

    @Override
    public List<TPosition> getPositionsOfTheAccount(String accountId) {
        return accountMapper.getPositionsOfTheAccount(accountId);
    }

    @Override
    public String getPositionStrOfTheAccount(String accountId) {
        List<String> slist = getPositionsOfTheAccount(accountId).stream().map(TPosition::getName).collect(Collectors.toList());
        StringBuffer sb = new StringBuffer();
        for(String s:slist){
            sb.append(s+";");
        }
        return sb.toString();
    }

    @Override
    public Account get(String id) {
        return accountMapper.getAccount(id);
    }

    @Override
    public List<Organization> getDataAuthOrgsOfAccount(String accountId) {
        List<Organization> all = new ArrayList<>();
        Account a = accountMapper.getAccount(accountId);
        List<TRole> roles = accountMapper.getRolesByAccount(a.getLogName());
        int level = 0 ;
        String levelname = "";
        for(TRole tr:roles){
            TRole2dataauth ta = roleMapper.getDataAuthoritysByRole(tr.getId());
            if(ObjectUtil.isNotNull(ta)){
                if(level<dictService.getDictByValue(ta.getAuthId()).getSortNumber()){
                    level = dictService.getDictByValue(ta.getAuthId()).getSortNumber() ;
                    levelname = dictService.getDictByValue(ta.getAuthId()).getDictValue();
                }
            }


            TRole2dataauthOrgs tt = roleService.getTRole2dataauthOrgs(tr.getId());
            if(tt!=null&&!"".equals(tt.getOrgIds())){
                String[] arr = tt.getOrgIds().split(",");
                for(String ss:arr){
                    Organization organization = organizationService.get(ss);
                    if(organization != null){
                        all.add(organization);
                    }
                }
            }
        }

        TOrganization o = accountMapper.getOrgByAccount(a.getLogName());
        String currentOrgId = o.getId() ;
        Organization organization = organizationService.get(currentOrgId);
        Dict curtOrg = dictService.getDictByValue(organization.getType());
        /*****
         * 将指定组织纳入
         */
        /*****
         * 1,如果当前帐号所在的组织的级别大于等于当前帐号所有角色关联数据权限的最高级别,那么就去当前组织及子孙组织
         * 2,则向上追溯到[当前帐号所有角色关联数据权限的最高级别]的组织，然后将此组织及此组织下所有子孙组织
         */
        if(curtOrg.getSortNumber()>=level){//1
            List<Organization>  orgss = organizationService.getChildrenAndCurt(currentOrgId);
            all.addAll(orgss);
            return all ;
        }else{//2
            List<Organization>  orgss = organizationService.getParentsAndCurt(currentOrgId);
            for(int i=0;i<orgss.size();i++){ // 直至追溯到和当前最高数据级别一致
                if(dictService.getDictByValue(orgss.get(i).getType()).getSortNumber()== level){
                    List<Organization> ccorgs = organizationService.getChildrenAndCurt(orgss.get(i).getId());
                    all.addAll(ccorgs);
                }
            }
            return all ;
        }
    }

    @Override
    public JSONObject updateTransferAccount(Account account) throws RbackException {
        JSONObject json = new JSONObject();
        try {
            accountMapper.transferAccount(account);
            PubFun.returnSuccessJson(json);
        } catch (RbackException e) {
            e.printStackTrace();
            json = new JSONObject();
            PubFun.returnFailJson(json, e.getMsg());
            throw e;
        }finally{
            return json ;
        }}

    @Transactional(rollbackFor = {Exception.class,RbackException.class})
    @Override
    public JSONObject updateRole2Account(Role2Account ra) {
        JSONObject json = new JSONObject();
        List list = roleMapper.getAccountOfRole(ra.getRoleId());
        if(CollectionUtil.isNotEmpty(list)){
            roleMapper.deleteRole2Account(ra.getRoleId());
        }
        String[] ss = ra.getAccountIds().split(",");
        for(String s:ss){
            TRole2Account raa  = new TRole2Account();
            raa.setRoleId(ra.getRoleId());
            raa.setAccountId(s);
            accountMapper.insertRole2Account(raa);
        }
        PubFun.returnSuccessJson(json);
        return json ;
    }

    @Override
    public List<TAuthority> getOwnAuths(String accountId) {
        return accountMapper.getOwnAuths(accountId);
    }

    @Override
    public Page<Account> all(Account account) {
        PageHelper.startPage(account.getPage(),account.getRows());
        Page<Account> tLogs = accountMapper.getAccounts(account);
        return tLogs;
    }


    @Override
    public Account get(String logName, BaseModel baseModel) {
        Account account = new Account();
        TAccount tAccount= accountMapper.getByLogName(logName);
        PubFun.copyProperties(account,tAccount);

        return account;
    }

}
