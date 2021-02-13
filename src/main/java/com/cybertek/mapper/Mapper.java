package com.cybertek.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;

@Component
public class Mapper {

    @Autowired
    private ModelMapper modelMapper;

    public <T> T convert(Object obj, T t){
        return modelMapper.map(obj,(Type)t.getClass());
    }

}
