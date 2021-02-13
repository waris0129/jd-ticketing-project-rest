package com.cybertek.implementation;

import com.cybertek.dto.UserDTO;
import com.cybertek.entity.UserEntity;
import com.cybertek.entity.common.UserPrincipal;
import com.cybertek.mapper.Mapper;
import com.cybertek.repository.UserRepository;
import com.cybertek.service.SecurityService;
import com.cybertek.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements SecurityService {

    @Autowired
    private UserService userService;
    @Autowired
    private Mapper mapper;



    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        UserDTO dto = userService.findByUserName(s);

        if(dto == null)
            throw new UsernameNotFoundException("USER NOT FOUND !");

        UserEntity userEntity = mapper.convert(dto, new UserEntity());

        UserDetails details = new UserPrincipal(userEntity);

        return details;
    }
}
