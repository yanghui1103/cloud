package com.bw.fit.pc.sys.conf;

import com.alibaba.fastjson.JSONObject;
import com.bw.fit.pc.sys.controller.LoginController;
import com.bw.fit.pc.sys.service.CommonService;
import com.bw.fit.pc.sys.util.PropertiesUtil;
import com.google.common.base.Strings;
import com.netflix.discovery.converters.Auto;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @Description
 * @Author yangh
 * @Date 2018-12-8 17:55
 * @Param ${PARAM}
 * @Return ${RETURN}
 * @VERSION
 */
public class ShiroRealm extends AuthorizingRealm {
    private  static Logger logger = LoggerFactory.getLogger(ShiroRealm.class);

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    private Environment env;
    @Autowired
    private CommonService commonService;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken authcToken) throws AuthenticationException {
        //获取基于用户名和密码的令牌
        //实际上这个authcToken是从LoginController里面currentUser.login(token)传过来的
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        if(token == null){
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

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
        SimpleAuthorizationInfo simpleAuthorInfo = new SimpleAuthorizationInfo();
        simpleAuthorInfo.addStringPermission("test");//给当前用户授权url为hello的权限码
        System.out.println("经试验：并不是每次调用接口就会执行，而是调用需要操作码（permission）的接口就会执行");
        return simpleAuthorInfo;
    }
            ///  https://github.com/gaohanghang/springboot-shiro/  测试案例
}