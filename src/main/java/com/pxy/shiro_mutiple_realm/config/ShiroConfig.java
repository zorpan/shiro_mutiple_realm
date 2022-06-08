package com.pxy.shiro_mutiple_realm.config;

import com.pxy.shiro_mutiple_realm.realm.SSORealm;
import com.pxy.shiro_mutiple_realm.realm.UserRealm;
import org.apache.shiro.authc.BearerToken;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 权限配置加载
 *
 * @author bims
 */
@Configuration
public class ShiroConfig {

    @Autowired
    private ModularRealmAuthenticator customModularRealmAuthenticator;

    /**
     * 自定义Realm
     */
    @Bean
    public UserRealm userRealm() {
        UserRealm userRealm = new UserRealm();
        return userRealm;
    }

    @Bean
    public SSORealm ssoRealm() {
        SSORealm userRealm = new SSORealm();
        userRealm.setAuthenticationTokenClass(BearerToken.class);
        return userRealm;
    }

    /**
     * 安全管理器
     */
    @Bean
    public DefaultWebSecurityManager securityManager(Collection<Realm> realms) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //设置多realm识别
        securityManager.setAuthenticator(customModularRealmAuthenticator);
        // 设置realm.
        securityManager.setRealms(realms);

        return securityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // Shiro的核心安全接口,这个属性是必须的
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 身份认证失败，则跳转到登录页面的配置
        shiroFilterFactoryBean.setLoginUrl("/login");
        // 权限认证失败，则跳转到指定页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/unauth");
        // Shiro连接约束配置，即过滤链的定义
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();

        filterChainDefinitionMap.put("/logout", "logout");
        filterChainDefinitionMap.put("/normal", "anon");
        filterChainDefinitionMap.put("/sso", "anon");
        filterChainDefinitionMap.put("/**", "authc");


        Map<String, Filter> filters = new LinkedHashMap<String, Filter>();
        shiroFilterFactoryBean.setFilters(filters);
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }


}
