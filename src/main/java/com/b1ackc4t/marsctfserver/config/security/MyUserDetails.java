package com.b1ackc4t.marsctfserver.config.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class MyUserDetails extends User {
    private Integer uid;
    public MyUserDetails(Integer uid, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, true, true, true, true, authorities);
        this.uid = uid;
    }

    public Integer getUid() {
        return uid;
    }
}
