package com.pxy.shiro_mutiple_realm.constants;

public enum LoginType {

    SSO("SSORealm"),USERNAME("UserRealm");

    private final String type;

    LoginType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
