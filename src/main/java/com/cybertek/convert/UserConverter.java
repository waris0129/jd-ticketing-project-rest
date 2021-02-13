package com.cybertek.convert;

import com.cybertek.dto.UserDTO;
import com.cybertek.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@ConfigurationPropertiesBinding
public class UserConverter implements Converter<String, UserDTO> {

    @Autowired
    private UserService userService;


    @Override
    public UserDTO convert(String username) {
        return userService.findByUserName(username);
    }
}
