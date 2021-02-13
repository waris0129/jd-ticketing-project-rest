package com.cybertek.convert;


import com.cybertek.dto.ProjectDTO;
import com.cybertek.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProjectConverter implements Converter<String, ProjectDTO> {

    @Autowired
    private ProjectService projectService;

    @Override
    public ProjectDTO convert(String projectCode) {
        return projectService.findProjectByProjectCode(projectCode);
    }
}
