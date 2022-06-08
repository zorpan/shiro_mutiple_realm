package com.pxy.shiro_mutiple_realm.service;

import com.pxy.shiro_mutiple_realm.constants.LoginType;
import com.pxy.shiro_mutiple_realm.domain.SSOToken;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class CustomModularRealmAuthenticator extends ModularRealmAuthenticator {
    @Override
    protected AuthenticationInfo doAuthenticate(AuthenticationToken authenticationToken) throws AuthenticationException {
        assertRealmsConfigured();
        Collection<Realm> realms = getRealms();
        Collection<Realm> typeRealms = new ArrayList<>();

        if (authenticationToken instanceof SSOToken) {
            // 单点登录方式选择SSORealm
            for (Realm realm : realms) {
                if (realm.getName().contains(LoginType.SSO.toString())) {
                    typeRealms.add(realm);
                }
            }

        } else if (authenticationToken instanceof UsernamePasswordToken) {
            //用户名密码方式登录，选择UserRealm
            for (Realm realm : realms) {
                if (realm.getName().contains(LoginType.USERNAME.toString())) {
                    typeRealms.add(realm);
                }
            }

        }

        if (typeRealms.size() == 1) {
            return doSingleRealmAuthentication(typeRealms.iterator().next(), authenticationToken);
        }
        return doMultiRealmAuthentication(typeRealms, authenticationToken);
    }
}
