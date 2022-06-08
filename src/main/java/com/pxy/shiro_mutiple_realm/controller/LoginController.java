package com.pxy.shiro_mutiple_realm.controller;

import com.pxy.shiro_mutiple_realm.domain.SSOToken;
import com.pxy.shiro_mutiple_realm.domain.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class LoginController {

    @RequestMapping("sso")
    public String sso(){
        User user = new User();
        user.setUsername("zhangsan");
        user.setPassword("zhangsan123");
        SSOToken ssoToken = new SSOToken(user,"123abc");
        SecurityUtils.getSubject().login(ssoToken);
        return "success";
    }
    @RequestMapping("normal")
    public String normal(){
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("lisi","lisi1234");
        SecurityUtils.getSubject().login(usernamePasswordToken);
        return "success";
    }
    @RequestMapping("unauth")
    public String unauth(){
        return "unauth";
    }

    @RequestMapping("hello")
    public String hello(){
        return String.valueOf(SecurityUtils.getSubject().getPrincipal());
    }

    @RequestMapping("logout")
    public String logout(){
        return "logout";
    }
    @RequestMapping("login")
    public String login(){
        return "login";
    }
}
