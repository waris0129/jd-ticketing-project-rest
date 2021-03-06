package com.cybertek.service;

import com.cybertek.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface SecurityService extends UserDetailsService {


    @Override
    UserDetails loadUserByUsername(String s) throws UsernameNotFoundException;

    UserEntity loadUser(String param);
}
