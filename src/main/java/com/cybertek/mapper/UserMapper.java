package com.cybertek.mapper;

import com.cybertek.dto.UserDTO;
import com.cybertek.entity.UserEntity;
import com.cybertek.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    @Autowired
    private ModelMapper modelMapper;

    public UserDTO convertToUserDto(UserEntity userEntity){
        return modelMapper.map(userEntity,UserDTO.class);
    }

    public UserEntity convertToUserEntity(UserDTO userDTO){
        return modelMapper.map(userDTO,UserEntity.class);
    }




}
