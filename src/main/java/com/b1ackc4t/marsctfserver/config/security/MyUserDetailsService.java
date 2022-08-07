package com.b1ackc4t.marsctfserver.config.security;

import com.b1ackc4t.marsctfserver.dao.UserMapper;
import com.b1ackc4t.marsctfserver.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    public UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.selectLoginByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException(username + "not found");
        }
        Set<String> set = new HashSet<>();
        set.add(user.getRole());
        MyUserDetails res = new MyUserDetails(user.getUid(), user, set);
        return res;
    }
}
