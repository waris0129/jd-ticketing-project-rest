package com.cybertek.service;

import com.cybertek.dto.ProjectDTO;

import java.util.List;

public interface ProjectService {

    List<ProjectDTO> findAllProjectList();
    ProjectDTO findProjectByProjectCode(String projectCode);
    ProjectDTO update(ProjectDTO projectDTO);
    ProjectDTO update(String projectCode);
    void save(ProjectDTO projectDTO);
    void save(String projectCode);
    void delete(String projectCode);
    List<ProjectDTO> findProjectListByUserName(String username);
    List<ProjectDTO> findAllUnCompletedProjectByUserName(String username);



}
