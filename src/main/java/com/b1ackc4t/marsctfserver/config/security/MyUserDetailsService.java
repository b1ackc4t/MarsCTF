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
import java.util.List;

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
        List<GrantedAuthority> list = new ArrayList<>();
        list.add(new SimpleGrantedAuthority(user.getRole()));
        MyUserDetails res = new MyUserDetails(user.getUid(), username, user.getUpassword(), list);
        return res;
    }
}
