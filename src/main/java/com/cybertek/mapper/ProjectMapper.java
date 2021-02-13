package com.cybertek.mapper;

import com.cybertek.dto.ProjectDTO;
import com.cybertek.entity.ProjectEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProjectMapper{

    @Autowired
    private ModelMapper modelMapper;


    public ProjectDTO convertToProjectDto(ProjectEntity entity){
        return modelMapper.map(entity,ProjectDTO.class);
    }

    public ProjectEntity convertToProjectEntity(ProjectDTO dto){
        return modelMapper.map(dto,ProjectEntity.class);
    }


}
