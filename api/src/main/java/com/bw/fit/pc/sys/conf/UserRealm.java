package com.bw.fit.pc.sys.conf;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bw.fit.pc.sys.util.PropertiesUtil;
import com.bw.fit.pc.sys.util.RestTemplateUtil;
import com.google.common.base.Strings;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @Description:
 * @author: Gao Hang Hang
 * @date 2019/01/22 11:07
 */
public class UserRealm extends AuthorizingRealm {
    @Autowired
    RestTemplate restTemplate;
    @Resource
    RestTemplateUtil restTemplateUtil;

    /**
     * 执行授权逻辑
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行授权逻辑");
        JSONObject authsJSON =  restTemplate.getForObject("http://sys-proj/account/account/"+principalCollection.getPrimaryPrincipal(), JSONObject.class);

        //SimpleAuthorizationInfo simpleAuthorInfo = new SimpleAuthorizationInfo();
        //simpleAuthorInfo.addStringPermission("test");//给当前用户授权url为hello的权限码
        //System.out.println("经试验：并不是每次调用接口就会执行，而是调用需要操作码（permission）的接口就会执行");
        Set<String> auths = new HashSet<>();
        if(authsJSON.getString("res").equals("2")){
            Arrays.stream(authsJSON.getString("authCodes").split(",")).forEach(x->{
                auths.add(x);
            });
        }

        //创建SimpleAuthorizationInfo,并设置其roles属性
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(auths);
        //返回SimpleAuthorizationInfo
        return info;
    }

    /**
     * 执行认证逻辑
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("执行认证逻辑");


        //获取基于用户名和密码的令牌
        //实际上这个authcToken是从LoginController里面currentUser.login(token)传过来的
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        if(token==null || StrUtil.isEmpty(token.getUsername())){
            throw new AuthenticationException("认证失败，请重新登录");
        }
        String account = token.getUsername();
        String password = String.valueOf(token.getPassword());
        if(Strings.isNullOrEmpty(account)){
            throw new AuthenticationException("账号不存在");
        }
        /****
         * 根据账号获取账户详情
         */
        JSONObject accountJSON =  restTemplate.getForObject("http://sys-proj/account/account/"+account, JSONObject.class);
        if("1".equals(accountJSON.get("res"))){throw new AuthenticationException(accountJSON.get("msg").toString());}
        ByteSource salt = ByteSource.Util.bytes( PropertiesUtil.getValueByKey("user.pw.slogmm").toString() + account );
//        AuthenticationInfo authcInfo=new SimpleAuthenticationInfo(account, PropertiesUtil.getValueByKey("sys.default.password").toString(), salt, getName());
//
//        //清之前的授权信息
//        super.clearCachedAuthorizationInfo(authcInfo.getPrincipals());
        //5,最后返回的用户信息，
        Object principal = accountJSON.get("logName") ;
        Object credentials = password ;
        //6 盐值
        SimpleAuthenticationInfo info = null ;
        String name = getName();
        info = new SimpleAuthenticationInfo(principal,credentials,salt,getName());
        return info ;
    }
}
