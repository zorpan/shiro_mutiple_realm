package com.pxy.shiro_mutiple_realm.domain;

import org.apache.shiro.authc.BearerToken;

public class SSOToken extends BearerToken {

    private User user;

    public SSOToken(User user, String token) {
        super(token);
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
