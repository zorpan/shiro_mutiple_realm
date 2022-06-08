package com.pxy.shiro_mutiple_realm.realm;

import com.pxy.shiro_mutiple_realm.domain.SSOToken;
import com.pxy.shiro_mutiple_realm.domain.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * 自定义Realm 处理登录 权限
 *
 * @author bims
 */
public class SSORealm extends AuthorizingRealm implements Serializable {

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Set<String> roles = new HashSet<>();
        roles.add("admin");
        info.setRoles(roles);
        return info;
    }

    /**
     * 登录认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        SSOToken upToken = (SSOToken) token;
        // 获取sso登录传入的user实体
        User user = upToken.getUser();

        //不传入拒绝登录
        if(user==null){
            throw new UnknownAccountException();
        }

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, upToken.getToken(), getName());
        return info;

    }

}
