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
    private Environment env;
    @Autowired
    private CommonService commonService;

    /**
     * 密码校验规则HashedCredentialsMatcher
     * 这个类是为了对密码进行编码的 ,
     * 防止密码在数据库里明码保存 , 当然在登陆认证的时候 ,
     * 这个类也负责对form里输入的密码进行编码
     * 处理认证匹配处理器：如果自定义需要实现继承HashedCredentialsMatcher
     */
    @Bean("credentialsMatcher")
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        //指定加密方式为MD5
        credentialsMatcher.setHashAlgorithmName("MD5");
        //加密次数
        credentialsMatcher.setHashIterations(10);
        // credentialsMatcher.setStoredCredentialsHexEncoded(true);
        return credentialsMatcher;
    }
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
        SimpleAuthorizationInfo simpleAuthorInfo = new SimpleAuthorizationInfo();
        simpleAuthorInfo.addStringPermission("test");//给当前用户授权url为hello的权限码
        System.out.println("经试验：并不是每次调用接口就会执行，而是调用需要操作码（permission）的接口就会执行");
        return simpleAuthorInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken authcToken) throws AuthenticationException {
        //获取基于用户名和密码的令牌
        //实际上这个authcToken是从LoginController里面currentUser.login(token)传过来的
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        String account = token.getUsername();
        if(Strings.isNullOrEmpty(account)){
            return null;
        }
        JSONObject accountJSON = commonService.getOtherAppReturn( env.getProperty("zuul.routes.api-sys.url")+"account/account/"+account);

        //User user = userService.selectByAccount(account);//根据登陆名account从库中查询user对象
        if("".equals(accountJSON)){throw new AuthenticationException("账户不存在");}
        ByteSource salt = ByteSource.Util.bytes(PropertiesUtil.getValueByKey("user.pw.slogmm") + account );
        AuthenticationInfo authcInfo=new SimpleAuthenticationInfo(account, "123456", salt, getName());

        //清之前的授权信息
        super.clearCachedAuthorizationInfo(authcInfo.getPrincipals());
        SecurityUtils.getSubject().getSession().setAttribute("CurrentUser", null);
        return authcInfo;//返回给安全管理器，securityManager，由securityManager比对数据库查询出的密码和页面提交的密码
        //如果有问题，向上抛异常，一直抛到控制器
    }

}
