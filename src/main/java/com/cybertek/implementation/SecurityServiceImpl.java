package com.cybertek.implementation;

import com.cybertek.dto.UserDTO;
import com.cybertek.entity.UserEntity;
import com.cybertek.entity.common.UserPrincipal;
import com.cybertek.mapper.Mapper;
import com.cybertek.repository.UserRepository;
import com.cybertek.service.SecurityService;
import com.cybertek.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class SecurityServiceImpl implements SecurityService {

    @Autowired
    private UserService userService;
    @Autowired
    private Mapper mapper;



    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        UserDTO user = userService.findByUserName(s);

        if(user == null)
            throw new UsernameNotFoundException("USER NOT FOUND !");

        return new org.springframework.security
                .core
                .userdetails
                .User(
                user.getId()+"",
                user.getPassword(),
                listAuthorities(user)
        );
    }


    @Override
    public UserEntity loadUser(String param) {
        UserDTO user = userService.findByUserName(param);

        return mapper.convert(user,new UserEntity());
    }


    private Collection<? extends GrantedAuthority> listAuthorities(UserDTO user){
        List<GrantedAuthority> authorityList= new ArrayList<>();

        GrantedAuthority authority= new SimpleGrantedAuthority(user.getRole().getDescription());
        authorityList.add(authority);

        return authorityList;
    }

}
