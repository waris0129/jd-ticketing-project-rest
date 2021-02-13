package com.cybertek.implementation;

import com.cybertek.dto.ProjectDTO;
import com.cybertek.dto.UserDTO;
import com.cybertek.entity.ProjectEntity;
import com.cybertek.entity.TaskEntity;
import com.cybertek.entity.UserEntity;
import com.cybertek.mapper.ProjectMapper;
import com.cybertek.mapper.UserMapper;
import com.cybertek.repository.ProjectRepository;
import com.cybertek.repository.TaskRepository;
import com.cybertek.repository.UserRepository;
import com.cybertek.service.ProjectService;
import com.cybertek.service.TaskService;
import com.cybertek.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private TaskService taskService;
    @Autowired
    private TaskRepository taskRepository;


    @Override
    public List<ProjectDTO> findAllProjectList() {

        List<ProjectEntity> projectEntities = projectRepository.findAll();

        return projectEntities.stream().map(projectMapper::convertToProjectDto).collect(Collectors.toList());
    }

    @Override
    public ProjectDTO findProjectByProjectCode(String projectCode) {

        ProjectEntity entity = projectRepository.findByProjectCode(projectCode);

        return projectMapper.convertToProjectDto(entity);
    }

    @Override
    public ProjectDTO update(ProjectDTO projectDTO) {

        ProjectEntity entity = projectRepository.findByProjectCode(projectDTO.getProjectCode());

        long id = entity.getId();

        ProjectEntity entity1 = projectMapper.convertToProjectEntity(projectDTO);

        entity1.setId(id);

        projectRepository.save(entity1);


        return projectDTO;
    }


    @Override
    public ProjectDTO update(String projectCode) {
        return null;
    }

    @Override
    public void save(ProjectDTO projectDTO) {

        ProjectEntity projectEntity = projectMapper.convertToProjectEntity(projectDTO);

        int countDuplicate = projectRepository.isDuplicate(projectEntity.getProjectCode());

        if(countDuplicate==0)
            projectRepository.save(projectEntity);
    }

    @Override
    public void save(String projectCode) {

    }

    @Override
    public void delete(String projectCode) {

        ProjectEntity entity = projectRepository.findByProjectCode(projectCode);

        entity.setIsDeleted(true);

        projectRepository.save(entity);

        // by using project code to find all task, and delete them all
        List<TaskEntity> taskEntityList = taskRepository.findAllByProjectProjectCode(projectCode);
        taskEntityList.stream().forEach(each -> taskService.delete(each.getId()));

    }

    @Override
    public List<ProjectDTO> findProjectListByUserName(String username) {
        // find user in db --> find projects from db by username,--> find each project tasks --> calculate completed and uncompleted --> project set complete and uncompleted tasks

        List<ProjectEntity> projectEntityList = projectRepository.findByAssignedManager(userMapper.convertToUserEntity(userService.findByUserName(username)));

        projectEntityList.forEach(each->{
            each.setCompleteTaskCounts(taskService.totalCompletedTasks(each.getProjectCode()));
            each.setUnfinishedTaskCounts(taskService.totalNonCompleteTasks(each.getProjectCode()));
        });

        return projectEntityList.stream().map(projectMapper::convertToProjectDto).collect(Collectors.toList());
    }

    @Override
    public List<ProjectDTO> findAllUnCompletedProjectByUserName(String username) {
        return projectRepository.findAllUnCompletedProjectByUserName(username).stream()
                .map(projectMapper::convertToProjectDto)
                .collect(Collectors.toList());
    }
}





/*
UserDTO userDTO = userService.findByUserName(username);

        UserEntity userEntity = userMapper.convertToUserEntity(userDTO);

        List<ProjectEntity> projectEntities = projectRepository.findByAssignedManager(userEntity);


        return projectEntities.stream()
                .map(obj -> {
                    ProjectDTO dto = projectMapper.convertToProjectDto(obj);

                    dto.setUnfinishedTaskCounts(taskService.totalNonCompleteTasks(dto.getProjectCode()));
                    dto.setCompleteTaskCounts(taskService.totalCompletedTasks(dto.getProjectCode()));
                    return dto;
                })
                .collect(Collectors.toList());

 */










