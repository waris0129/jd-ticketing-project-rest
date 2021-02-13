package com.cybertek.mapper;

import com.cybertek.dto.RoleDTO;
import com.cybertek.entity.RoleEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

    @Autowired
    private ModelMapper modelMapper;


    public RoleDTO convertToRoleDto(RoleEntity entity){
        return modelMapper.map(entity,RoleDTO.class);
    }

    public RoleEntity convertToEntity(RoleDTO dto){
        return modelMapper.map(dto,RoleEntity.class);
    }

}
