package com.cybertek.mapper;

import com.cybertek.dto.TaskDTO;
import com.cybertek.entity.TaskEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    @Autowired
    private ModelMapper modelMapper;


    public TaskDTO convertToTaskDTO(TaskEntity entity){
        return modelMapper.map(entity,TaskDTO.class);
    }

    public TaskEntity convertToTaskEntity(TaskDTO dto){
        return modelMapper.map(dto,TaskEntity.class);
    }

}
